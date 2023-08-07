package waamir104.converter.view;

import waamir104.converter.controller.CurrencyAPIController;
import waamir104.converter.dto.CurrencyNameOptionDTO;
import waamir104.converter.dto.CurrencyResultDTO;
import waamir104.converter.factory.CurrencyConnectionFactory;
import waamir104.converter.factory.CurrencyConnectionFactoryValues;
import waamir104.converter.model.Currency;
import waamir104.converter.model.CurrencyAPI;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Color;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.text.DecimalFormat;
import java.util.ArrayList;
import javax.swing.BorderFactory;

import javax.swing.ImageIcon;

public class ConverterResultFrame extends JFrame{
	private static final long serialVersionUID = 1L;
	private JLabel lbAmountFrom, lbAmountTo;
	private JButton btnOk;
	private ConverterFrame converterFrame;

	public ConverterResultFrame() {}
	
	public ConverterResultFrame(ConverterFrame converterFrame) {
		this.converterFrame = converterFrame;

		ImageIcon img = new ImageIcon(this.getClass().getResource("/waamir104/logos/white_background.png"));
		this.setTitle("Converter");
		this.setIconImage(img.getImage());
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout((LayoutManager) null);
		
		Container container = this.getContentPane();
		container.setBackground(new Color(255, 255, 255));
		
		this.configureJElements(container);
		this.configureConverterResultFrameActions();
		//	TODO
		
		this.setSize(510, 200);
		this.setLocationRelativeTo(converterFrame);
		this.setVisible(true);
	}
	
	private void configureJElements(Container container) {
        this.lbAmountFrom = new JLabel();
        this.lbAmountFrom.setBounds(20, 10, 450, 30);
        this.lbAmountFrom.setFont(new Font("Arial", 0, 17));
        this.lbAmountFrom.setForeground(Color.BLACK);
        
        this.lbAmountTo = new JLabel();
        this.lbAmountTo.setBounds(20, 50, 450, 30);
        this.lbAmountTo.setFont(new Font("Arial", 1, 30));
        this.lbAmountTo.setForeground(Color.BLACK);
        
        this.setLabelText();
        
        this.btnOk = new JButton("Ok");
        this.btnOk.setForeground(Color.BLACK);
        this.btnOk.setBackground(new Color(252, 202, 209));
        this.btnOk.setBorder(BorderFactory.createLineBorder(new Color(245, 154, 166), 1));
        this.btnOk.setBounds(220, 110, 50, 30);
        this.btnOk.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                ConverterResultFrame.this.btnOk.setCursor(new Cursor(12));
            }

            public void mouseExited(MouseEvent e) {
                ConverterResultFrame.this.btnOk.setCursor(new Cursor(0));
            }
        });
        
        container.add(this.lbAmountFrom);
        container.add(this.lbAmountTo);
        container.add(this.btnOk);
    }
	
	private void configureConverterResultFrameActions() {
		this.btnOk.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				int selectedOption = JOptionPane.showConfirmDialog(null, "Would you want to convert another amount?", "Continue", JOptionPane.YES_NO_OPTION);
				
				if (selectedOption == JOptionPane.YES_OPTION) {
					new ConverterSelectionFrame();
					ConverterResultFrame.this.dispose();
				} else if (selectedOption == JOptionPane.NO_OPTION) {
					ConverterResultFrame.this.dispose();
				}
			}
		});
	}
	
	private ArrayList<Currency> getExchangeRates (){
		CurrencyAPI api = new CurrencyAPI();
		HttpURLConnection conn = (HttpURLConnection) new CurrencyConnectionFactory(api, CurrencyConnectionFactoryValues.RATES).getConnection();
		CurrencyAPIController apiController = new CurrencyAPIController(conn);

		CurrencyNameOptionDTO currencyFrom = (CurrencyNameOptionDTO) this.converterFrame.getCbFrom().getSelectedItem();
		CurrencyNameOptionDTO currencyTo = (CurrencyNameOptionDTO) this.converterFrame.getCbTo().getSelectedItem();

		return apiController.getExchangeRates(currencyFrom, currencyTo);
	}

	private ArrayList<CurrencyResultDTO> getExchangeResults() {
		ArrayList<Currency>  currenciesList = this.getExchangeRates();
		ArrayList<CurrencyResultDTO> currencyResultDTOS = new ArrayList<>();
		DecimalFormat df = new DecimalFormat("#.##");

		double amountFrom = Double.parseDouble(this.converterFrame.getTxtAmount().getText());
		double amountFromInUSD = amountFrom / currenciesList.get(0).getOneUSDEquals().doubleValue();
		double amountTo = amountFromInUSD * currenciesList.get(1).getOneUSDEquals().doubleValue();

		amountFrom = Double.parseDouble(df.format(amountFrom).replace(",", "."));
		amountTo = Double.parseDouble(df.format(amountTo).replace(",", "."));


		currencyResultDTOS.add(new CurrencyResultDTO(currenciesList.get(0).getLongName(), new BigDecimal(amountFrom)));
		currencyResultDTOS.add(new CurrencyResultDTO(currenciesList.get(1).getLongName(), new BigDecimal(amountTo)));

		return currencyResultDTOS;
	}

	private void setLabelText(){
		ArrayList<CurrencyResultDTO> currencyResultDTOS = this.getExchangeResults();

		this.lbAmountFrom.setText(String.format("%.2f %s equals", currencyResultDTOS.get(0).getAmount(), currencyResultDTOS.get(0)));
		this.lbAmountTo.setText((String.format("%.2f %s", currencyResultDTOS.get(1).getAmount(), currencyResultDTOS.get(1))));
	}
}
