package waamir104.converter.view;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JComboBox;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Image;
import java.awt.LayoutManager;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;

public class ConverterSelectionFrame extends JFrame {
	private JLabel lbImage, lbQuestion;
	private JComboBox cbConverterOptions;
	private JButton btnContinue;

	public ConverterSelectionFrame() {
		ImageIcon img = new ImageIcon(this.getClass().getResource("/waamir104/logos/white_background.png"));
		this.setTitle("Converter");
		this.setIconImage(img.getImage());
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout((LayoutManager) null);
		
		Container container = this.getContentPane();
		container.setBackground(new Color(255, 255, 255));
		
		this.configureJElements(container);
		
		this.setSize(630, 240);
		this.setLocationRelativeTo((Component) null);
		this.setVisible(true);
	}
	
	private void configureJElements(Container container) {
		ImageIcon img = new ImageIcon(new ImageIcon(this.getClass().getResource("/waamir104/logos/white_background.png")).getImage().getScaledInstance(200, 170, Image.SCALE_DEFAULT));
	
		this.lbImage = new JLabel();
		this.lbImage.setIcon(img);
		this.lbImage.setBounds(20, 10, img.getIconWidth(), img.getIconHeight());
		
		this.lbQuestion = new JLabel();
		this.lbQuestion.setBounds(260, 0, 400, 90);
		this.lbQuestion.setFont(new Font("Arial", Font.PLAIN, 34));
		this.lbQuestion.setForeground(Color.BLACK);
		this.lbQuestion.setText("<html>What do you want to convert this time?</html>");
		
		this.btnContinue = new JButton("Continue");
		this.btnContinue.setBounds(490, 150, 100, 30);
		this.btnContinue.setFont(new Font("Arial", Font.PLAIN, 15));
		this.btnContinue.setForeground(Color.BLACK);
		this.configureMouseEventBtn(btnContinue);
		
		this.cbConverterOptions = new JComboBox();
		this.cbConverterOptions.setBounds(290, 120, 140, 30);
		this.cbConverterOptions.setFont(new Font("Arial", Font.PLAIN, 15));
		this.cbConverterOptions.addItem("Divisas");
		
		
		container.add(this.lbImage);
		container.add(this.lbQuestion);
		container.add(this.btnContinue);
		container.add(this.cbConverterOptions);
	}
	
	private void configureConverterSelectionFrameActions() {
		this.btnContinue.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				ConverterSelectionFrame.this.openFrame();
			}
		});
	}
	
	public void configureMouseEventBtn(JButton btn) {
		btn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				btn.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));;
			}
		});
	}
	
	private void openFrame() {
		// TODO
	}
}