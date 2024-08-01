package de.dnb.ie.search;

import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

/**
 * Observer wird nicht wirklich benötigt, da in das View nur die einzelnen
 * Komponenten eingehängt werden. Diese sind auch gleichzeitig Teile des Models.
 * 
 * @author baumann
 * 
 */
public class View extends JFrame implements Observer {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JMenuBar menuBar;
	private JMenu mnNewMenu;
	JMenuItem mntmInfo;

	/**
	 * Create the frame.
	 * 
	 * @param title
	 *            TODO
	 */
	public View(final String title) {
		initialize(title);
	}

	public void addComponent(final Component comp) {
		this.contentPane.add(comp);
	}

	public void addInfoListener(final ActionListener il) {
		this.mntmInfo.addActionListener(il);
	}

	private void initialize(final String title) {
		setTitle(title);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 589, 400);

		this.menuBar = new JMenuBar();
		setJMenuBar(this.menuBar);

		this.mnNewMenu = new JMenu("?");
		this.menuBar.add(this.mnNewMenu);

		this.mntmInfo = new JMenuItem("Info");
		this.mnNewMenu.add(this.mntmInfo);
		this.contentPane = new JPanel();
		this.contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(this.contentPane);
		this.contentPane.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));

	}

	@Override
	public void update(final Observable obs, final Object message) {

	}

}
