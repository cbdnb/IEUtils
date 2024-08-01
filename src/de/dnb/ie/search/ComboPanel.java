package de.dnb.ie.search;

import java.awt.GridBagConstraints;
import java.awt.event.ActionListener;
import java.util.Properties;

import javax.swing.JComboBox;

public class ComboPanel extends GridPanel implements IPanelModel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String searchIndex;
	private String propertyStr;
	private JComboBox comboBox;
	private String prefix; // Was vor dem Wert der Combobox steht
	private String postfix;
	private SearchModel searchModel;

	public ComboPanel(final String label, final String searchIndex,
			final String prefix, final String postfix,
			final String propertyString) {
		super(label);
		initialize();
		this.searchIndex = searchIndex;
		this.propertyStr = propertyString;
		this.prefix = prefix;
		this.postfix = postfix;
	}

	@Override
	public void getValueFromProperties(final Properties prop) {
		final String content = prop.getProperty(this.propertyStr, "");
		this.comboBox.setSelectedItem(content);
	}

	@Override
	public void saveValueInProperties(final Properties prop) {
		prop.setProperty(this.propertyStr, this.comboBox.getSelectedItem()
				.toString());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.dnb.ie.scrap.IPanelModel#getSearchPhrase()
	 */
	@Override
	public String getSearchPhrase() {
		String selected = (String) this.comboBox.getSelectedItem();
		selected = selected.trim();
		if (!selected.isEmpty())
			return this.searchIndex + " \"" + this.prefix
					+ this.comboBox.getSelectedItem().toString() + this.postfix
					+ "\"";
		else
			return "";
	}

	@Override
	public void setText(final String text) {
		this.comboBox.setSelectedItem(text);
	}

	@Override
	public String getText() {
		return this.comboBox.getSelectedItem().toString();
	}

	public void addText(final String item) {
		this.comboBox.addItem(item);
	}

	public synchronized void addActionListener(final ActionListener al) {
		this.comboBox.addActionListener(al);
	}

	private void initialize() {

		this.comboBox = new JComboBox();
		final GridBagConstraints gbc_comboBox = new GridBagConstraints();
		gbc_comboBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox.gridx = 1;
		gbc_comboBox.gridy = 0;
		add(this.comboBox, gbc_comboBox);
	}

	@Override
	public void clear() {
		// TODO Auto-generated method stub

	}

	@Override
	public void setSearchModel(final SearchModel searchModel) {
		this.searchModel = searchModel;
	}

}
