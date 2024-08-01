package de.dnb.ie.ddcTk;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

public class GUI extends JFrame {

	private JPanel contentPane;
	JTextField textFieldDDC;
	JButton btnSearch;
	private JScrollPane scrollPane;
	private JScrollPane scrollPane_1;
	private JScrollPane scrollPane_2;
	JEditorPane editorPaneTk;
	JEditorPane editorPaneMarc;
	JEditorPane editorPaneDebug;
	JLabel labelInfo;
	private JLabel label;
	private JSplitPane splitPane;
	private JTabbedPane tabbedPane_1;
	JScrollPane scrollPaneTree;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI frame = new GUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public GUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1076, 685);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.NORTH);

		JLabel lblDdcEingeben = new JLabel("DDC eingeben: ");
		panel.add(lblDdcEingeben);

		textFieldDDC = new JTextField();
		panel.add(textFieldDDC);
		textFieldDDC.setColumns(30);

		btnSearch = new JButton("Suche");
		btnSearch.setEnabled(false);
		panel.add(btnSearch);

		label = new JLabel("                 ");
		panel.add(label);

		labelInfo = new JLabel("        ");
		panel.add(labelInfo);

		splitPane = new JSplitPane();
		splitPane.setDividerSize(1);
		contentPane.add(splitPane, BorderLayout.CENTER);

		tabbedPane_1 = new JTabbedPane(JTabbedPane.TOP);
		splitPane.setRightComponent(tabbedPane_1);
		
				scrollPane = new JScrollPane();
				tabbedPane_1.addTab("Tk", null, scrollPane, null);
				
						editorPaneTk = new JEditorPane();
						editorPaneTk.setEditable(false);
						editorPaneTk.setContentType("text/html");
						scrollPane.setViewportView(editorPaneTk);

		scrollPane_1 = new JScrollPane();
		tabbedPane_1.addTab("Marc", null, scrollPane_1, null);

		editorPaneMarc = new JEditorPane();
		editorPaneMarc.setEditable(false);
		scrollPane_1.setViewportView(editorPaneMarc);

		scrollPane_2 = new JScrollPane();
		tabbedPane_1.addTab("Debug", null, scrollPane_2, null);

		editorPaneDebug = new JEditorPane();
		editorPaneDebug.setEditable(false);
		scrollPane_2.setViewportView(editorPaneDebug);
		
		scrollPaneTree = new JScrollPane();
		splitPane.setLeftComponent(scrollPaneTree);
		splitPane.setDividerLocation(500);
	}

}
