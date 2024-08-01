/**
 *
 */
package de.dnb.marcViewer;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JEditorPane;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.ScrollPaneConstants;
import java.awt.Frame;

/**
 * @author baumann
 *
 */
public class GUI {

	private JFrame frmMarctool;
	private JTabbedPane tabbedPane;
	private JScrollPane scrollPaneRaw;
	JScrollPane scrollPaneTable;
	private JPanel panel;
	JButton btnHoleIdn;
	JTable table;
	JEditorPane editorPaneRaw;
	JLabel lblInfo;

	/**
	 * Launch the application.
	 */
	public static void main(final String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					final GUI gui = new GUI();
					gui.setVisible(true);
				} catch (final Exception e) {
					e.printStackTrace();
				}
			}

		});
	}

	/**
	 * @param gui
	 */
	public void setVisible(final boolean vis) {
		frmMarctool.setVisible(vis);
	}

	/**
	 * Create the application.
	 */
	public GUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmMarctool = new JFrame();
		frmMarctool.setExtendedState(Frame.MAXIMIZED_BOTH);
		frmMarctool.setTitle("Marc-Tool");
		frmMarctool.setBounds(100, 100, 1015, 760);
		frmMarctool.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		frmMarctool.getContentPane().add(tabbedPane, BorderLayout.CENTER);

		scrollPaneRaw = new JScrollPane();
		tabbedPane.addTab("Rohdaten", null, scrollPaneRaw, null);

		editorPaneRaw = new JEditorPane("text/html", "");
		scrollPaneRaw.setViewportView(editorPaneRaw);

		scrollPaneTable = new JScrollPane();
		scrollPaneTable.setHorizontalScrollBarPolicy(
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		tabbedPane.addTab("detailliert", null, scrollPaneTable, null);

		table = new JTable();
		scrollPaneTable.setViewportView(table);

		panel = new JPanel();
		frmMarctool.getContentPane().add(panel, BorderLayout.SOUTH);

		btnHoleIdn = new JButton("Hole idn aus Zwischenablage und lade MARC21");
		panel.add(btnHoleIdn);

		lblInfo = new JLabel("Info");
		lblInfo.setHorizontalAlignment(SwingConstants.CENTER);
		frmMarctool.getContentPane().add(lblInfo, BorderLayout.NORTH);
	}
}
