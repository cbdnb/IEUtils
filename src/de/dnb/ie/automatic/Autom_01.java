package de.dnb.ie.automatic;

import javax.swing.SwingUtilities;

import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import java.awt.Rectangle;
import javax.swing.JList;

import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JButton;

import java.awt.Dimension;
import javax.swing.JTextArea;
import javax.swing.ImageIcon;

public class Autom_01 extends JFrame {
	//@formatter:on

	private static final long serialVersionUID = 1L;

	private JPanel jContentPane = null;

	private JScrollPane jScrollPaneVLB = null;

	private JList jList5560 = null;

	private JButton jButtonSend = null;

	private JButton jButtonQuit = null;

	private JButton jButtonAuswahl = null;

	private JScrollPane jScrollPaneAusw = null;

	private JTextArea jTextAreaAusw = null;

	/**
	 * This method initializes jScrollPaneVLB
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPaneVLB() {
		if (jScrollPaneVLB == null) {
			try {
				jScrollPaneVLB = new JScrollPane();
				jScrollPaneVLB.setBounds(new Rectangle(217, 51, 199, 341)); // Generated
				jScrollPaneVLB.setViewportView(getJList5560()); // Generated
			} catch (java.lang.Throwable e) {
				// TODO: Something
			}
		}
		return jScrollPaneVLB;
	}

	//@formatter:on
	/**
	 * This method initializes jListVLB
	 * 
	 * @return javax.swing.JList
	 */
	private JList getJList5560() {
		if (jList5560 == null) {
			try {
				jList5560 = new JList();

			} catch (java.lang.Throwable e) {
				// TODO: Something
			}
		}
		return jList5560;
	}

	/**
	 * This method initializes jButtonSend
	 * 
	 * @return javax.swing.JButton
	 */
	//@formatter:on
	private JButton getJButtonSend() {
		if (jButtonSend == null) {
			try {
				jButtonSend = new JButton();
				jButtonSend.setBounds(new Rectangle(222, 0, 68, 48)); // Generated
				jButtonSend.setText("send"); // Generated
				jButtonSend
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(
							java.awt.event.ActionEvent e) {
							String ausw = getJTextAreaAusw().getText();

							ausw = ausw.trim();
							String patternStr = "\n+";
							String replaceStr = "\n";
							Pattern pattern = Pattern.compile(patternStr);
							Matcher matcher = pattern.matcher(ausw);
							System.out.println(matcher
								.replaceAll(replaceStr));

							System.exit(0);
						}
					});
			} catch (java.lang.Throwable e) {
				// TODO: Something
			}
		}
		return jButtonSend;
	}

	/**
	 * This method initializes jButtonQuit
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButtonQuit() {
		if (jButtonQuit == null) {
			try {
				jButtonQuit = new JButton();
				jButtonQuit.setBounds(new Rectangle(308, 0, 68, 48)); // Generated
				jButtonQuit.setText("quit"); // Generated
				jButtonQuit
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(
							java.awt.event.ActionEvent e) {
							System.exit(0);
						}
					});
			} catch (java.lang.Throwable e) {
				// TODO: Something
			}
		}
		return jButtonQuit;
	}

	/**
	 * This method initializes jButtonAuswahl	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButtonAuswahl() {
		if (jButtonAuswahl == null) {
			try {
				jButtonAuswahl = new JButton();
				jButtonAuswahl.setBounds(new Rectangle(156, 211, 56, 43)); // Generated
				jButtonAuswahl
					.setIcon(new ImageIcon(
						"C:/Programme/Java/jdk-6u18-docs/docs/technotes/guides/beans/spec/images/prev.gif")); // Generated
				jButtonAuswahl.setText(""); // Generated
				jButtonAuswahl
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(
							java.awt.event.ActionEvent e) {
							Object[] values = jList5560.getSelectedValues();
							String ausw = "";
							for (int i = 0; i < values.length; i++) {
								ausw += values[i] + "\n";
							}
							int pos = getJTextAreaAusw().getCaretPosition();
							getJTextAreaAusw().insert("\n" + ausw, pos);
						}
					});
			} catch (java.lang.Throwable e) {
				// TODO: Something
			}
		}
		return jButtonAuswahl;
	}

	/**
	 * This method initializes jScrollPaneAusw	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */
	private JScrollPane getJScrollPaneAusw() {
		if (jScrollPaneAusw == null) {
			try {
				jScrollPaneAusw = new JScrollPane();
				jScrollPaneAusw.setBounds(new Rectangle(32, 54, 120, 331)); // Generated
				jScrollPaneAusw.setViewportView(getJTextAreaAusw()); // Generated
			} catch (java.lang.Throwable e) {
				// TODO: Something
			}
		}
		return jScrollPaneAusw;
	}

	/**
	 * This method initializes jTextAreaAusw	
	 * 	
	 * @return javax.swing.JTextArea	
	 */
	private JTextArea getJTextAreaAusw() {
		if (jTextAreaAusw == null) {
			try {
				jTextAreaAusw = new JTextArea();
			} catch (java.lang.Throwable e) {
				// TODO: Something
			}
		}
		return jTextAreaAusw;
	}

	/**
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		if (args.length < 1) {
			System.err.println("zu wenig args");
			return;
		}
		final String argsStr = args[0];

		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				Autom_01 thisClass = new Autom_01();
				thisClass.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				DefaultListModel daten = new DefaultListModel();
				JList jList = thisClass.getJList5560();
				jList.setModel(daten);

				LinkedHashSet<String> vlbs = getLines(argsStr);
				for (String string : vlbs) {
					daten.addElement(string);
				}

				thisClass.setVisible(true);

			}

			
		});
	}
	
	private static LinkedHashSet<String> getLines(String argsStr) {
		LinkedHashSet<String> strSet = new LinkedHashSet<String>();
		String regex = "^044H (.*)$";
		Pattern pat5560 = Pattern.compile(regex, Pattern.MULTILINE);
		Matcher m5560 = pat5560.matcher(argsStr);
		while (m5560.find()) {
			strSet.add(m5560.group(1));
		}
		return strSet;
	}

	/**
	 * This is the default constructor
	 */
	public Autom_01() {
		super();
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(450, 435);
		this.setContentPane(getJContentPane());
		this.setTitle("JFrame");
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new JPanel();
			jContentPane.setLayout(null);
			jContentPane.add(getJScrollPaneVLB(), null); // Generated
			jContentPane.add(getJButtonSend(), null); // Generated
			jContentPane.add(getJButtonQuit(), null); // Generated
			jContentPane.add(getJButtonAuswahl(), null); // Generated
			jContentPane.add(getJScrollPaneAusw(), null); // Generated
		}
		return jContentPane;
	}

} // @jve:decl-index=0:visual-constraint="10,10"
