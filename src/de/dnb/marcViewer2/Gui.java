/**
 *
 */
package de.dnb.marcViewer2;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JEditorPane;

import java.awt.BorderLayout;
import javax.swing.JSplitPane;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.JList;
import javax.swing.AbstractListModel;
import javax.swing.JTabbedPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.JTable;
import java.awt.Frame;
import javax.swing.JLabel;

/**
 * @author baumann
 *
 */
public class Gui {

	JFrame frame;
	JButton btnLadeDatei;
	JSplitPane splitPane;
	JList<String> list;
	private JScrollPane scrollPaneList;
	JTabbedPane tabbedPane;
	JScrollPane scrollPane;
	JEditorPane editorPaneRaw;
	JScrollPane scrollPane_1;
	JTable table;
	JLabel lblInfo;

	/**
	 * Launch the application.
	 */
	public static void main(final String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					final Gui window = new Gui();
					window.frame.setVisible(true);
				} catch (final Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Gui() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setExtendedState(Frame.MAXIMIZED_BOTH);
		frame.setBounds(100, 100, 800, 900);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		btnLadeDatei = new JButton("Lade Datei");
		frame.getContentPane().add(btnLadeDatei, BorderLayout.SOUTH);

		splitPane = new JSplitPane();
		frame.getContentPane().add(splitPane, BorderLayout.CENTER);

		scrollPaneList = new JScrollPane();

		splitPane.setLeftComponent(scrollPaneList);
		list = new JList<>();
		list.setModel(new AbstractListModel() {
			String[] values = new String[] {
					"                                                                              " };

			@Override
			public int getSize() {
				return values.length;
			}

			@Override
			public Object getElementAt(final int index) {
				return values[index];
			}
		});
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		scrollPaneList.setViewportView(list);

		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		splitPane.setRightComponent(tabbedPane);

		scrollPane = new JScrollPane();
		tabbedPane.addTab("Rohdaten", null, scrollPane, null);

		editorPaneRaw = new JEditorPane("text/html", "");
		scrollPane.setViewportView(editorPaneRaw);

		scrollPane_1 = new JScrollPane();
		scrollPane_1.setHorizontalScrollBarPolicy(
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		tabbedPane.addTab("detailliert", null, scrollPane_1, null);

		table = new JTable();
		scrollPane_1.setViewportView(table);
		splitPane.setDividerLocation(0.1);

		lblInfo = new JLabel("   ");
		frame.getContentPane().add(lblInfo, BorderLayout.NORTH);

	}

}
