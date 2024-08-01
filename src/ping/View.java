package ping;

import java.awt.Component;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.Set;
import java.util.Vector;

import javax.swing.JLabel;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import de.dnb.basics.applicationComponents.strings.StringUtils;

public class View implements Observer {

	private GUI gui;
	private Model model;

	private static final char FILL_CHAR = '+';
	private DefaultTableCellRenderer leftRenderer;

	public View(final Model model) {
		this.model = model;
		this.leftRenderer = new DefaultTableCellRenderer();
		this.leftRenderer.setHorizontalAlignment(JLabel.LEFT);
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					View.this.gui = new GUI();
					View.this.gui.setVisible(true);
					final JTableHeader header = View.this.gui.table
							.getTableHeader();
					final DefaultTableCellRenderer renderer = (DefaultTableCellRenderer) header
							.getDefaultRenderer();
					renderer.setHorizontalAlignment(JLabel.LEFT);
					header.setDefaultRenderer(renderer);
				} catch (final Exception e) {
					e.printStackTrace();
				}
			}
		});

	}

	public final void addSpinnerListener(final ChangeListener changeListener) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				View.this.gui.spinner.addChangeListener(changeListener);
			}
		});

	}

	public int getMax() {
		final SpinnerNumberModel model = (SpinnerNumberModel) this.gui.spinner
				.getModel();
		final int number = (Integer) model.getNumber();
		return number;

	}

	@Override
	public final void update(final Observable obs, final Object message) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				makeTable();
				listStatus();
				final List<Mitarbeiter> workers = View.this.model
						.getPresent();
				View.this.gui.maraudersMap.setWorkerList(workers);
			}
		});

	}

	private void listStatus() {
		final Set<Mitarbeiter> coming = this.model.coming();
		final Set<Mitarbeiter> going = this.model.going();
		final List<Mitarbeiter> present = this.model.getPresent();

		String comingGoing = "";
		if (!coming.isEmpty()) {
			comingGoing += "Kommt:";
			for (final Mitarbeiter mitarbeiter : coming) {
				comingGoing += "\n" + mitarbeiter;
			}
		}
		if (!going.isEmpty()) {
			if (!coming.isEmpty())
				comingGoing += "\n";
			comingGoing += "Geht:";
			for (final Mitarbeiter mitarbeiter : going) {
				comingGoing += "\n" + mitarbeiter;
			}
		}

		String newStatus = comingGoing;

		if (!present.isEmpty()) {
			newStatus += "\n\n";
			newStatus += "Anwesend (" + present.size() + "):";
			for (final Mitarbeiter mitarbeiter : present) {
				newStatus += "\n" + mitarbeiter;
			}
		}
		final String oldStatus = this.gui.txtareaChanges.getText();
		if (!StringUtils.equals(newStatus, oldStatus)) {
			this.gui.txtareaChanges.setText(newStatus);
			final TrayIcon trayIcon = this.gui.trayIcon;
			trayIcon.displayMessage(GUI.WORKERS, "Änderung",
					TrayIcon.MessageType.INFO);
			trayIcon.setToolTip(comingGoing);
			final Toolkit tk = Toolkit.getDefaultToolkit();
			tk.beep();
		}
	}

	private void makeTable() {

		final List<String> timeStampStrings = this.model.getTimeStampStrings();
		final Vector<Object> columnNames = makeVector("Mitarbeiter",
				timeStampStrings);
		final DefaultTableModel tmodel = new DefaultTableModel(columnNames, 0);

		String oldunit = "";

		final ArrayList<Mitarbeiter> workerList = this.model.getWorkerList();
		for (int i = 0; i < workerList.size(); i++) {
			final Mitarbeiter mitarbeiter = workerList.get(i);
			final String unit = mitarbeiter.unit;
			if (!unit.equalsIgnoreCase(oldunit)) {
				final String s = unit.toUpperCase() + ":";
				final Vector<Object> unitvector = makeVector(s, null);
				tmodel.addRow(unitvector);
			}
			oldunit = unit;

			final Vector<Object> vector = new Vector<>();
			String mitarbeiterS = mitarbeiter.toString().trim();
			if (!this.model.isPresent(mitarbeiter))
				mitarbeiterS += " ";
			vector.add(mitarbeiterS);
			final List<Boolean> timeLine = this.model.getTimeLine(mitarbeiter);
			boolean past = false;
			for (int j = 0; j < timeLine.size(); j++) {
				final int colwidth = getDataColumnWidth(j);
				String cellEntry;

				final boolean present = timeLine.get(j);
				if (present) {
					if (!past) {
						cellEntry = extend2ColumsWidth(" [ ", FILL_CHAR,
								colwidth);
					} else {
						cellEntry = extend2ColumsWidth("" + FILL_CHAR,
								FILL_CHAR, colwidth);
					}

				} else { // nicht mehr da
					if (past) {
						cellEntry = "+]";
					} else {
						cellEntry = "";
					}
				}
				vector.add(cellEntry);
				past = present;
			}

			tmodel.addRow(vector);
		}

		this.gui.table.setModel(tmodel);
		setColumnWidths();

	}

	private String extend2ColumsWidth(final String initial,
			final char fillChar, final int columnWidth) {

		String s1 = initial;
		final Font font = this.gui.table.getFont();
		final FontMetrics metrics = this.gui.table.getFontMetrics(font);

		while (true) {
			final String s2 = s1 + fillChar;
			if (metrics.stringWidth(s1 + fillChar) > columnWidth - 3)
				break;
			s1 = s2;
		}
		return s1;

	}

	/**
	 * 
	 */
	private void setColumnWidths() {
		// Breite einstellen:
		final TableColumnModel columnModel = this.gui.table.getColumnModel();
		final TableColumn column0 = columnModel.getColumn(0);
		column0.setPreferredWidth(180);

		for (int i = 0; i < this.model.getSize(); i++) {
			final TableColumn column = columnModel.getColumn(i + 1);
			column.setPreferredWidth(getDataColumnWidth(i));
			column.setCellRenderer(this.leftRenderer);
		}
	}

	public final Component getGui() {
		return this.gui;
	}

	/**
	 * 
	 * @param firstValue
	 *            wenn null, wird nichts eingefügt
	 * @param strings
	 *            wenn null, wird nichts eingefügt
	 * @return
	 */
	private Vector<Object> makeVector(final Object firstValue,
			final Collection<String> strings) {
		final Vector<Object> objects = new Vector<>();
		if (firstValue != null)
			objects.add(firstValue);
		if (strings != null)
			objects.addAll(strings);
		return objects;
	}

	/**
	 * 
	 * @param i
	 *            Datenspalte i (=Spalte i + 1 der Tabelle)
	 * @return
	 */
	private int getDataColumnWidth(final int i) {
		final int diff = this.model.getTimeDiff(i);
		int width = (diff * 8) / 5;
		// SPREAD_FAC;
		width = Math.max(40, width);
		return width;
	}

}
