package waamir104.converter.view;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
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
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;

import waamir104.converter.dto.CurrencyNameOptionDTO;
import waamir104.converter.factory.CurrencyConnectionFactory;
import waamir104.converter.factory.CurrencyConnectionFactoryValues;
import waamir104.converter.model.CurrencyAPI;
import waamir104.converter.controller.CurrencyAPIController;

import java.net.HttpURLConnection;

public class ConverterFrame extends JFrame{
	private JLabel lbAmount, lbFrom, lbTo;
	private JTextField txtAmount;
	private JComboBox cbFrom, cbTo;
	private JButton btnConvert, btnCancel;
	
	public ConverterFrame(ConverterSelectionFrame converterSelectionFrame) {
		ImageIcon img = new ImageIcon(this.getClass().getResource("/waamir104/logos/white_background.png"));
		this.setTitle("Converter");
		this.setIconImage(img.getImage());
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout((LayoutManager) null);
		
		Container container = this.getContentPane();
		container.setBackground(new Color(255, 255, 255));
		
		this.configureForm(container);
		this.configureConverterFrameActions();
		
		this.setSize(585, 210);
		this.setLocationRelativeTo(converterSelectionFrame);
		this.setVisible(true);
	}
	
	public JTextField getTxtAmount() {
		return txtAmount;
	}

	public JComboBox getCbFrom() {
		return cbFrom;
	}

	public JComboBox getCbTo() {
		return cbTo;
	}

	private void configureForm(Container container) {
		this.lbAmount = new JLabel();
		this.lbAmount.setBounds(25, 20, 130, 25);
		this.lbAmount.setFont(new Font("Arial", Font.BOLD, 14));
		this.lbAmount.setForeground(Color.BLACK);
		this.lbAmount.setText("AMOUNT");
		
		this.lbFrom = new JLabel();
		this.lbFrom.setBounds(185, 20, 130, 25);
		this.lbFrom.setFont(new Font("Arial", Font.BOLD, 14));
		this.lbFrom.setForeground(Color.BLACK);
		this.lbFrom.setText("FROM");
		
		this.lbTo = new JLabel();
		this.lbTo.setBounds(385, 20, 130, 25);
		this.lbTo.setFont(new Font("Arial", Font.BOLD, 14));
		this.lbTo.setForeground(Color.BLACK);
		this.lbTo.setText("TO");
		
		this.txtAmount = new JTextField();
		this.txtAmount.setBounds(20, 50, 130, 35);
		this.txtAmount.setFont(new Font("Arial", Font.PLAIN, 14));
		this.txtAmount.setForeground(Color.BLACK);
		this.txtAmount.setMargin(new Insets(5, 10, 5, 5));
		this.txtAmount.setBackground(new Color(230, 232, 231));
		
		this.cbFrom = new JComboBox();
		this.cbFrom.setBounds(180, 50, 170, 35);;
		this.cbFrom.setFont(new Font("Arial", Font.PLAIN, 14));
		this.cbFrom.setForeground(Color.BLACK);
		this.cbFrom.setBackground(new Color(230, 232, 231));
		
		this.loadCurrencies(cbFrom);	
		
		this.cbTo = new JComboBox();
		this.cbTo.setBounds(380, 50, 170, 35);;
		this.cbTo.setFont(new Font("Arial", Font.PLAIN, 14));
		this.cbTo.setForeground(Color.BLACK);
		this.cbTo.setBackground(new Color(230, 232, 231));
		
		this.loadPartialCurrencies(cbFrom, cbTo);
		
		this.btnConvert = new JButton("Convert");
		this.btnConvert.setBounds(145, 115, 100, 30);
		this.btnConvert.setFont(new Font("Arial", 1, 17));
        this.btnConvert.setForeground(Color.BLACK);
        this.btnConvert.setBackground(new Color(252, 202, 209));
        this.btnConvert.setBorder(BorderFactory.createLineBorder(new Color(245, 154, 166), 1));
        
        this.configureCursorOnBtn(this.btnConvert);
        
        this.btnCancel = new JButton("Cancel");
        this.btnCancel.setBounds(310, 115, 100, 30);
        this.btnCancel.setFont(new Font("Arial", 1, 17));
        this.btnCancel.setForeground(Color.BLACK);
        this.btnCancel.setBackground(new Color(252, 202, 209));
        this.btnCancel.setBorder(BorderFactory.createLineBorder(new Color(245, 154, 166), 1));
        
        this.configureCursorOnBtn(this.btnCancel);	
        
        container.add(this.lbAmount);
        container.add(this.lbFrom);
        container.add(this.lbTo);
        container.add(this.txtAmount);
        container.add(this.cbFrom);
        container.add(this.cbTo);
        container.add(this.btnConvert);
        container.add(this.btnCancel);
	}
	
	private void configureConverterFrameActions() {
		this.cbFrom.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				ConverterFrame.this.loadPartialCurrencies(cbFrom, cbTo); 
			}
		});
		this.btnConvert.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				boolean valid = ConverterFrame.this.validateAmountData(ConverterFrame.this.txtAmount);
				
				if (valid) {
					ConverterFrame.this.openFrame();
				} else {
					JOptionPane.showMessageDialog(null, "Inserted a invalid amount. None of the following characters or letters are allowed.\n  (ºª/!\"·$%&/=^*`+´¡)", "Input Error", JOptionPane.ERROR_MESSAGE);
				} 
			}
		});
		this.btnCancel.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				ConverterFrame.this.returnToConverterSelection();
				
			}
		});
	}
	
	private void configureCursorOnBtn(JButton btn) {
		btn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				btn.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}
		});
	}
	
	private void returnToConverterSelection() {
		new ConverterSelectionFrame();
		this.dispose();
	}
	
	private void loadCurrencies(JComboBox cbFrom) {
		ArrayList<CurrencyNameOptionDTO> currenciesList = this.getCurrenciesList();
		
		for(CurrencyNameOptionDTO currency : currenciesList) {
			cbFrom.addItem(currency);
		}
	}
	
	private void loadPartialCurrencies(JComboBox cbReference, JComboBox cbToLoad) {
		ArrayList<CurrencyNameOptionDTO> currenciesList = this.getCurrenciesList();
		CurrencyNameOptionDTO currencySelected = (CurrencyNameOptionDTO) cbReference.getSelectedItem();
		
		for (CurrencyNameOptionDTO currency : currenciesList) {
			if(currency.getShortName().equals(currencySelected.getShortName())) {
				currenciesList.remove(currency);
				break;
			}
		}
		
		cbToLoad.removeAllItems();
		
		for (CurrencyNameOptionDTO currency : currenciesList) {
			cbToLoad.addItem(currency);
		}
	}
	
	private ArrayList<CurrencyNameOptionDTO> getCurrenciesList(){
		CurrencyAPI api = new CurrencyAPI();
		HttpURLConnection conn = new CurrencyConnectionFactory(api, CurrencyConnectionFactoryValues.NAMES).getConnection();
		CurrencyAPIController apiController = new CurrencyAPIController(conn);
		
		ArrayList<CurrencyNameOptionDTO> currenciesList = apiController.listCurrencies();
		return currenciesList;
	}
	
	private boolean validateAmountData(JTextField txt) {
		boolean valid = false;
		String value = txt.getText().strip().replace(".", "").replace(",", "");
		String regex = "[^0-9.,]";
		String regexNumber = "[0-9.,]";
		Pattern pattern = Pattern.compile(regex);
		Pattern patternNumber = Pattern.compile(regexNumber);
		Matcher matcher = pattern.matcher(value);
		Matcher matcherNumber = patternNumber.matcher(value);
		
		if (matcherNumber.find()) {
			valid = true;
		} 
		
		if(value.isEmpty() || matcher.find()) {
			valid = false;
		} 
		
		return valid;
	}
	
	private void openFrame() {
		//	TODO
	}
}
