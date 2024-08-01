package de.dnb.ie.search;

import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionListener;

import javax.swing.JButton;

public class CommandPanel extends GridPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JButton btn1;
	private JButton btn2;
	private JButton btn3;

	public void addBtn1Listener(ActionListener cl) {
		btn1.addActionListener(cl);
	}
	
	public void addBtn2Listener(ActionListener sl) {
		btn2.addActionListener(sl);
	}
	
	public void addBtn3Listener(ActionListener el) {
		btn3.addActionListener(el);
	}
	
	/**
	 * Create the panel.
	 * @param btn1Text TODO
	 * @param btn2Text TODO
	 * @param btn3Text TODO
	 */
	public CommandPanel(String btn1Text, String btn2Text, String btn3Text) {
		super("");
		initialize(btn1Text, btn2Text, btn3Text);
	}

	private void initialize(String btn1Text, String btn2Text, String btn3Text) {
		
		btn1 = new JButton(btn1Text);
		GridBagConstraints gbc_btnAllesLschen = new GridBagConstraints();
		gbc_btnAllesLschen.insets = new Insets(0, 0, 0, 5);
		gbc_btnAllesLschen.gridx = 1;
		gbc_btnAllesLschen.gridy = 0;
		add(btn1, gbc_btnAllesLschen);
		
		btn2 = new JButton(btn2Text);
		GridBagConstraints gbc_btnSuchen = new GridBagConstraints();
		gbc_btnSuchen.insets = new Insets(0, 0, 0, 5);
		gbc_btnSuchen.gridx = 2;
		gbc_btnSuchen.gridy = 0;
		add(btn2, gbc_btnSuchen);
		
		btn3 = new JButton(btn3Text);
		GridBagConstraints gbc_btnAbbruch = new GridBagConstraints();
		gbc_btnAbbruch.gridx = 3;
		gbc_btnAbbruch.gridy = 0;
		add(btn3, gbc_btnAbbruch);
	}

}
