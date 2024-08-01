package de.dnb.ie.search;

import java.util.LinkedList;
import java.util.List;
import java.util.Observable;
import java.util.Properties;

public class SearchModel extends Observable {

	private List<IPanelModel> inputModels = new LinkedList<IPanelModel>();

	private IPanelModel focusModel; // Modell, das den Focus zuletzt hatte

	private IPanelModel outputModel;

	public void setOutputModel(IPanelModel outputModel) {
		this.outputModel = outputModel;
		outputModel.setSearchModel(this);
	}

	public void addInputModel(IPanelModel model) {
		inputModels.add(model);
		model.setSearchModel(this);
	}
	
	public void setFocusModel(IPanelModel model) {
		this.focusModel = model;
	}

	public void refresh() {
		String searchPhrase = buildSearchPhrase();
		if (outputModel != null)
			outputModel.setText(searchPhrase);
		setChanged();
		notifyObservers(null);
	}

	private String buildSearchPhrase() {
		String phrase = "f ";
		int initialLength = phrase.length();
		for (IPanelModel model : inputModels) {
			String modelPhrase = model.getSearchPhrase();
			if (!modelPhrase.trim().equals("")) {
				if (phrase.length() > initialLength)
					phrase += " and ";
				phrase += modelPhrase;
			}
		}
		return phrase;

	}

	public void getValuesFromProperties(Properties properties) {
		for (IPanelModel model : inputModels) {
			model.getValueFromProperties(properties);
		}
		outputModel.getValueFromProperties(properties);
	}

	public void saveValuesInProperties(Properties properties) {
		for (IPanelModel model : inputModels) {
			model.saveValueInProperties(properties);
		}
		outputModel.saveValueInProperties(properties);
	}

	public void clear() {
		for (IPanelModel model : inputModels) {
			model.clear();
		}
		refresh();
		if (focusModel != null)
			focusModel.requestFocusInWindow();
	}

}
