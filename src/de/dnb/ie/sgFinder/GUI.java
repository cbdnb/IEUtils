package de.dnb.ie.sgFinder;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import de.dnb.gnd.utils.DDC_SG;
import de.dnb.gnd.utils.SGUtils;

public class GUI extends JFrame {

	private JPanel contentPane;
	private JTextField textFieldDDC;
	private JTextPane textFieldSG;

	/**
	 * Launch the application.
	 */
	public static void main(final String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					final GUI frame = new GUI();
					frame.setVisible(true);
				} catch (final Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public GUI() {
		setTitle("Sachgruppe zu DDC finden");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 459, 175);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		final JLabel lblDdc = new JLabel("DDC:");
		lblDdc.setBounds(26, 49, 46, 14);
		contentPane.add(lblDdc);

		textFieldDDC = new JTextField();
		textFieldDDC.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent e) {
				final String ddc = textFieldDDC.getText();
				DDC_SG sg = null;
				try {
					sg = SGUtils.getSG(ddc);
				} catch (final IllegalArgumentException e1) {
					textFieldSG.setText(e1.getMessage());
					return;
				}
				if (sg == null)
					textFieldSG.setText("nicht vorhanden");
				else
					textFieldSG.setText(sg.toString());
			}
		});
		textFieldDDC.setBounds(61, 46, 372, 20);
		contentPane.add(textFieldDDC);
		textFieldDDC.setColumns(10);

		final JLabel lblSg = new JLabel("SG:");
		lblSg.setBounds(26, 77, 46, 14);
		contentPane.add(lblSg);

		textFieldSG = new JTextPane();
		textFieldSG.setBorder(new LineBorder(new Color(0, 0, 0)));
		textFieldSG.setEditable(false);
		textFieldSG.setBounds(61, 74, 373, 52);
		contentPane.add(textFieldSG);
	}
}
