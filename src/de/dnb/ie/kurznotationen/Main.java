/**
 * 
 */
package de.dnb.ie.kurznotationen;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;
import java.util.function.Consumer;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ScrollPaneConstants;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

import de.dnb.gnd.utils.SGUtils;

/**
 * @author baumann
 *
 */
public class Main {

	private JFrame frame;
	JEditorPane editorPaneShorts;
	JButton buttonCalc;
	JLabel lblNeueKurznotationen;	
	JTable tableStatistics;
	private Map<String, Long> statistikMap = new TreeMap<>();	
	JComboBox<String> comboBox;
	private ActionListener myActionListener = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			String sg = comboBox.getItemAt(comboBox.getSelectedIndex());
			String left = editorPaneShorts.getText();
			String[] lines = left.split("\n");
			Collection<String> kurze = Arrays.asList(lines);
			statistikMap = statistik.machStatistik(sg, kurze);
			TableModel model = makeNewTableModel(statistikMap);
			tableStatistics.setModel(model);
		}
	};
	private Statistik statistik;
	JScrollPane scrollPane_1;
	JEditorPane editorPaneError;
	JLabel lblFehlermeldungen;
	private Consumer<String> errf = new Consumer<String>() {
		@Override
		public void accept(String error) {
			Document document = editorPaneError.getDocument();
			try {
				document.insertString(document.getLength(), error + "\n", null);
			} catch (BadLocationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	};
	JButton btnMeldungenLschen;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main window = new Main();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Main() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 887, 815);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setVerticalScrollBarPolicy(
				ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setHorizontalScrollBarPolicy(
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollPane.setBounds(20, 98, 145, 536);
		frame.getContentPane().add(scrollPane);

		editorPaneShorts = new JEditorPane();
		scrollPane.setViewportView(editorPaneShorts);

		buttonCalc = new JButton("\u2192");

		buttonCalc.addActionListener(myActionListener);
		buttonCalc.setBounds(189, 335, 89, 23);
		frame.getContentPane().add(buttonCalc);

		lblNeueKurznotationen = new JLabel("Neue Kurznotationen:");
		lblNeueKurznotationen.setBounds(10, 73, 132, 14);
		frame.getContentPane().add(lblNeueKurznotationen);

		JScrollPane scrollPane_2 = new JScrollPane();
		scrollPane_2.setHorizontalScrollBarPolicy(
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollPane_2.setVerticalScrollBarPolicy(
				ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane_2.setBounds(299, 98, 542, 536);
		frame.getContentPane().add(scrollPane_2);

		tableStatistics = new JTable();
		tableStatistics.setModel(makeNewTableModel(statistikMap));
		tableStatistics.getColumnModel().getColumn(0).setPreferredWidth(57);
		tableStatistics.getColumnModel().getColumn(0).setMinWidth(22);
		scrollPane_2.setViewportView(tableStatistics);

		JLabel lblSachgruppe = new JLabel("Sachgruppe:");
		lblSachgruppe.setBounds(10, 23, 78, 14);
		frame.getContentPane().add(lblSachgruppe);

		comboBox = new JComboBox<>();
		comboBox.setBounds(85, 20, 132, 20);
		fillCombo();
		frame.getContentPane().add(comboBox);

		scrollPane_1 = new JScrollPane();
		scrollPane_1.setVerticalScrollBarPolicy(
				ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane_1.setHorizontalScrollBarPolicy(
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollPane_1.setBounds(20, 671, 821, 95);
		frame.getContentPane().add(scrollPane_1);

		editorPaneError = new JEditorPane();
		editorPaneError.setEditable(false);
		scrollPane_1.setViewportView(editorPaneError);

		lblFehlermeldungen = new JLabel("Fehlermeldungen:");
		lblFehlermeldungen.setBounds(30, 646, 135, 14);
		frame.getContentPane().add(lblFehlermeldungen);
		
		btnMeldungenLschen = new JButton("Meldungen löschen");
		btnMeldungenLschen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				editorPaneError.setText("");
			}
		});
		btnMeldungenLschen.setBounds(695, 645, 145, 18);
		frame.getContentPane().add(btnMeldungenLschen);

		try {
			statistik = new Statistik();
		} catch (ClassNotFoundException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		statistik.setErrorFunction(errf);
	}

	/**
	 * 
	 */
	private void fillCombo() {
		Collection<String> sgs = SGUtils.allDHSasString();
		sgs.forEach(comboBox::addItem);

	}

	/**
	 * @return
	 */
	private TableModel makeNewTableModel(Map<String, Long> map) {

		String[] keys = map.keySet().toArray(new String[0]);

		return new TableModel() {

			@Override
			public void setValueAt(Object aValue, int rowIndex,
					int columnIndex) {
				// TODO Auto-generated method stub
			}

			@Override
			public void removeTableModelListener(TableModelListener l) {
				// TODO Auto-generated method stub
			}

			@Override
			public boolean isCellEditable(int rowIndex, int columnIndex) {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public Object getValueAt(int rowIndex, int columnIndex) {
				String key = keys[rowIndex];
				if (columnIndex == 0) {
					return key;
				}
				if (columnIndex == 1) {
					return map.get(key);
				}
				return null;
			}

			@Override
			public int getRowCount() {
				return map.size();
			}

			@Override
			public String getColumnName(int columnIndex) {
				if (columnIndex == 0) {
					return "Kurznotation";
				}
				if (columnIndex == 1) {
					return "Anzahl";
				}
				return null;
			}

			@Override
			public int getColumnCount() {
				return 2;
			}

			@Override
			public Class<?> getColumnClass(int columnIndex) {
				if (columnIndex == 0) {
					return String.class;
				}
				if (columnIndex == 1) {
					return Long.class;
				}
				return null;
			}

			@Override
			public void addTableModelListener(TableModelListener l) {
				// TODO Auto-generated method stub
			}
		};
	}
}
