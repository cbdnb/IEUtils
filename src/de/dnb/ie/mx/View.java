/**
 *
 */
package de.dnb.ie.mx;

import java.awt.EventQueue;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.ListModel;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListDataListener;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

import de.dnb.gnd.utils.mx.Library;
import de.dnb.gnd.utils.mx.MXAddress;

/**
 * @author baumann
 *
 */
public class View {

	GUI gui;

	/**
	 *
	 */
	View() {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					gui = new GUI();
					gui.frame.setVisible(true);
				} catch (final Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * @param args
	 */
	public static void main(final String[] args) {
		new View();

	}

	void setDateListener(final ListSelectionListener listener) {
		SwingUtilities.invokeLater(() ->
		{
			gui.listDates.addListSelectionListener(listener);
			listener.valueChanged(null);
		});
	}

	void setAntwortAlle(final ActionListener listener) {
		SwingUtilities.invokeLater(() ->
		{
			gui.buttonAntwortAlle.addActionListener(listener);
		});
	}

	void setAntwortAbs(final ActionListener listener) {
		SwingUtilities.invokeLater(() ->
		{
			gui.buttonAntwortAbs.addActionListener(listener);
		});
	}

	void setMxUrheber(final ActionListener listener) {
		SwingUtilities.invokeLater(() ->
		{
			gui.btnUrheber.addActionListener(listener);
		});
	}

	void setMxRed(final ActionListener listener) {
		SwingUtilities.invokeLater(() ->
		{
			gui.btnRedaktion.addActionListener(listener);
		});
	}

	void setAntwClip(final ActionListener listener) {
		SwingUtilities.invokeLater(() ->
		{
			gui.buttonAntwortClip.addActionListener(listener);
		});
	}

	void setAddEmpf(final ActionListener listener) {
		SwingUtilities.invokeLater(() ->
		{
			gui.mntmHinzu.addActionListener(listener);
		});
	}

	void setDelEmpf(final ActionListener listener) {
		SwingUtilities.invokeLater(() ->
		{
			gui.mntmDel.addActionListener(listener);
		});
	}

	void setEditAbs(final ActionListener listener) {
		SwingUtilities.invokeLater(() ->
		{
			gui.mntmAbsEdit.addActionListener(listener);
		});
	}

	void setEditEmpf(final ActionListener listener) {
		SwingUtilities.invokeLater(() ->
		{
			gui.mntmEmpfEdit.addActionListener(listener);
		});
	}

	void setstandardEmpf(final ActionListener listener) {
		SwingUtilities.invokeLater(() ->
		{
			gui.mntmStandard.addActionListener(listener);
		});
	}

	/**
	 * @param dates
	 * @param antwortVorhanden
	 *            TODO
	 */
	void setDates(final List<String> dates, final int selectedDate,
			final boolean antwortVorhanden) {
		SwingUtilities.invokeLater(() ->
		{
			gui.listDates.setModel(new ListModel<String>() {

				@Override
				public int getSize() {
					return dates.size();
				}

				@Override
				public String getElementAt(final int index) {
					String date = dates.get(index);
					if (antwortVorhanden && index == getSize() - 1)
						date = date + " - Antwort";
					return date;
				}

				@Override
				public void addListDataListener(final ListDataListener l) {
					// TODO Auto-generated method stub

				}

				@Override
				public void removeListDataListener(final ListDataListener l) {
					// TODO Auto-generated method stub

				}
			});
			if (!dates.isEmpty()) {
				gui.listDates.setSelectedIndex(selectedDate);
			}
		});
	}

	/**
	 * @param abs
	 */
	void setAbsender(final MXAddress abs) {
		SwingUtilities.invokeLater(() ->
		{
			final TableModel model = gui.tableAbsender.getModel();

			final Library library = abs != null ? abs.getLibrary()
					: Library.getNullLibrary();

			final String bibStr = library != null ? library.getKurzPlusIsil()
					: "?";

			model.setValueAt(bibStr, 0, 0);
			final String red = abs != null ? abs.getRedaktionsTyp().asText
					: null;
			model.setValueAt(red, 0, 1);
			final String rest = abs != null ? abs.getSubadress() : null;
			model.setValueAt(rest, 0, 2);

			gui.tableAbsender.getColumnModel().getColumn(0)
					.setPreferredWidth(GUI.WIDTH_ABS_BIB);
			gui.tableAbsender.getColumnModel().getColumn(1)
					.setPreferredWidth(GUI.WIDTH_ABS_RED);
			gui.tableAbsender.getColumnModel().getColumn(2)
					.setPreferredWidth(GUI.WIDTH_ABS_UNTER);
		});

	}

	/**
	 * @param text
	 */
	void setText(final String text) {
		SwingUtilities.invokeLater(() ->
		{
			gui.textArea.setText(text);
			gui.textArea.setCaretPosition(0);
		});
	}

	void setTextListener(final DocumentListener listener) {
		SwingUtilities.invokeLater(() ->
		{
			gui.textArea.getDocument().addDocumentListener(listener);

		});
	}

	String getText() {
		return gui.textArea.getText();
	}

	int getNrdesEmpfaengers() {
		return gui.tableWeitereBeteiligte.getSelectedRow();
	}

	/**
	 * @param beteiligte
	 */
	void setBeteiligte(final List<MXAddress> beteiligte) {
		SwingUtilities.invokeLater(() ->
		{
			final TableModel model = new TableModel() {

				String[] ueberschr = new String[] { "a/e", "stumm",
						"Bibliothek", "Redaktion", "Untergliederung" };

				@Override
				public int getRowCount() {
					return beteiligte.size();
				}

				@Override
				public int getColumnCount() {
					return 5;
				}

				@Override
				public String getColumnName(final int columnIndex) {
					return ueberschr[columnIndex];
				}

				@Override
				public Class<?> getColumnClass(final int columnIndex) {
					return String.class;
				}

				@Override
				public boolean isCellEditable(final int rowIndex,
						final int columnIndex) {
					// TODO Auto-generated method stub
					return false;
				}

				@Override
				public Object getValueAt(final int rowIndex,
						final int columnIndex) {
					final MXAddress bet = beteiligte.get(rowIndex);
					switch (columnIndex) {
					case 0:
						return bet.getAdressierung().text;
					case 1:
						return bet.isStumm ? "+" : "";
					case 2:
						final Library library = bet.getLibrary();
						if (library == null)
							return "?";
						else
							return library.getKurzPlusIsil();
					case 3:
						return bet.getRedaktionsTyp().asText;
					case 4:
						return bet.getSubadress();

					}
					return null;
				}

				@Override
				public void setValueAt(final Object aValue, final int rowIndex,
						final int columnIndex) {
					// TODO Auto-generated method stub

				}

				@Override
				public void addTableModelListener(final TableModelListener l) {
					// TODO Auto-generated method stub

				}

				@Override
				public void removeTableModelListener(
						final TableModelListener l) {
					// TODO Auto-generated method stub

				}

			};
			gui.tableWeitereBeteiligte.setModel(model);
			gui.tableWeitereBeteiligte.getColumnModel().getColumn(0)
					.setPreferredWidth(GUI.W_BET_AE);
			gui.tableWeitereBeteiligte.getColumnModel().getColumn(0)
					.setMaxWidth(GUI.W_BET_AE);
			gui.tableWeitereBeteiligte.getColumnModel().getColumn(1)
					.setPreferredWidth(GUI.W_BET_STUMM);
			gui.tableWeitereBeteiligte.getColumnModel().getColumn(1)
					.setMaxWidth(GUI.W_BET_STUMM + 3);
			gui.tableWeitereBeteiligte.getColumnModel().getColumn(2)
					.setPreferredWidth(GUI.W_BET_BIB);
			gui.tableWeitereBeteiligte.getColumnModel().getColumn(3)
					.setMaxWidth(GUI.W_BET_RED);
			gui.tableWeitereBeteiligte.getColumnModel().getColumn(4)
					.setPreferredWidth(GUI.W_BET_UNTER);

		});

	}

	/**
	 * @return
	 */
	int getSelectedIndex() {

		return gui.listDates.getSelectedIndex();

	}

	/**
	 * @param title
	 */
	public void setTitle(final String title) {
		SwingUtilities.invokeLater(() ->
		{
			gui.frame.setTitle(title);
		});
	}

}
