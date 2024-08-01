package de.dnb.ie.search;

import java.util.Properties;

public interface IPanelModel {

	String getSearchPhrase();

	void getValueFromProperties(Properties prop);

	void saveValueInProperties(Properties prop);

	void setText(String text);

	String getText();

	void clear();
	
	boolean requestFocusInWindow();

	void setSearchModel(SearchModel searchModel);

}