package de.dnb.marcViewer2;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ListModel;
import javax.swing.SwingUtilities;
import javax.swing.event.ListDataListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.marc4j.marc.Record;

import de.dnb.basics.applicationComponents.strings.StringUtils;

public class Marc2Controller {

	View view;

	Model model;

	private Marc2Controller() {
		model = new Model();
		view = new View(model);
		view.addButtonListener(new ButtonListener());
		view.addListListener(new ListListener());
	}

	class ButtonListener implements ActionListener {
		@Override
		public void actionPerformed(final ActionEvent e) {
			final String datei = StringUtils.readClipboard();
			System.err.println("Lade:" + datei);
			SwingUtilities.invokeLater(() -> model.loadFile(datei));
			view.setListModel(listModel);
		}

	}

	class ListListener implements ListSelectionListener {
		private int selectedIndex = -1;

		@Override
		public void valueChanged(final ListSelectionEvent e) {
			final int ind = view.getSelectedIndex();
			if (ind != selectedIndex) {
				selectedIndex = ind;
				final Record record = model.getElementAt(ind);
				if (record == null)
					return;
				view.setRecord(record);
			}
		}
	}

	ListModel<String> listModel = new ListModel<String>() {

		@Override
		public void removeListDataListener(final ListDataListener l) {
			// TODO Auto-generated method stub

		}

		@Override
		public int getSize() {
			return model.getSize();
		}

		@Override
		public String getElementAt(final int index) {
			return model.getNameOf(index);
		}

		@Override
		public void addListDataListener(final ListDataListener l) {
			// TODO Auto-generated method stub

		}
	};

	public static void main(final String[] args) {
		new Marc2Controller();
	}

}
