package de.dnb.ie.search;

import java.awt.GridBagConstraints;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyListener;
import java.util.Properties;

import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class TextfieldPanel extends GridPanel implements IPanelModel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField textField;
	private String searchIndex;
	private String propertyStr;
	private SearchModel searchModel;

	public TextfieldPanel(
			String label,
			String searchIndex,
			String propertyString) {
		//@formatter:off
		super(label);
		//@formatter:on
		initialize();
		this.searchIndex = searchIndex;
		this.propertyStr = propertyString;
		this.textField.addFocusListener(new FocusListener() {

			@Override
			public void focusLost(FocusEvent e) {
				// nix
			}

			@Override
			public void focusGained(FocusEvent arg0) {
				searchModel.setFocusModel(TextfieldPanel.this);
			}
		});
	}

	@Override
	public void setText(String text) {
		textField.setText(text);
	}

	@Override
	public String getText() {
		return textField.getText();
	}

	@Override
	public void getValueFromProperties(Properties prop) {
		String content = prop.getProperty(propertyStr, "");
		textField.setText(content);
		String focus = prop.getProperty(propertyStr + ".focus", "");
		if (Boolean.parseBoolean(focus))
			requestFocusInWindow();
	}

	@Override
	public void saveValueInProperties(Properties prop) {
		prop.setProperty(propertyStr, textField.getText());
		prop.setProperty(propertyStr + ".focus",
				Boolean.toString(isFocusOwner()));
	}

	/* (non-Javadoc)
	 * @see de.dnb.ie.scrap.IPanelModel#getSearchPhrase()
	 */
	@Override
	public String getSearchPhrase() {
		String text = textField.getText();
		if (text.trim().length() > 0)
			text = searchIndex + " (" + text + ")";
		return text;
	}

	// wenn sich der eingetippte Text ändert
	@Override
	public synchronized void addKeyListener(KeyListener l) {
		textField.addKeyListener(l);
	}

	// wenn Enter gedrückt wird
	public void addActionListener(ActionListener al) {
		textField.addActionListener(al);
	}

	@Override
	public boolean requestFocusInWindow() {
		return textField.requestFocusInWindow();
	}

	@Override
	public boolean isFocusOwner() {
		return textField.isFocusOwner();
	}

	private void initialize() {
		textField = new JTextField();
		textField.setHorizontalAlignment(SwingConstants.LEFT);
		GridBagConstraints gbc_textField = new GridBagConstraints();
		gbc_textField.gridwidth = 3;
		gbc_textField.fill = GridBagConstraints.BOTH;
		gbc_textField.gridx = 1;
		gbc_textField.gridy = 0;
		add(textField, gbc_textField);
		textField.setColumns(40);
	}

	@Override
	public void clear() {
		this.setText("");		
	}

	@Override
	public void setSearchModel(SearchModel searchModel) {
		this.searchModel = searchModel;
	}

}
