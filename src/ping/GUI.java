package ping;

import java.awt.AWTException;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Image;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowStateListener;
import java.awt.font.TextAttribute;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.SpinnerNumberModel;
import javax.swing.table.TableCellRenderer;

public class GUI extends JFrame {

	private static final Integer SPINNER_MAX = new Integer(15);

	static final String WORKERS = "συνεργάτες";

	JTable table;

	private SystemTray tray;

	TrayIcon trayIcon;
	JTextArea txtareaChanges;

	JSpinner spinner;

	MaraudersMap maraudersMap;

	/**
	 * Create the frame.
	 */
	public GUI() {
		setIconImage(Toolkit
				.getDefaultToolkit()
				.getImage(
						GUI.class
								.getResource("/com/sun/java/swing/plaf/motif/icons/Inform.gif")));
		setTitle(WORKERS);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 20, 1300, 950);
		new JPanel();

		final JSplitPane splitPane = new JSplitPane();
		splitPane.setDividerSize(2);

		this.table = new JTable() {
			@Override
			public Component prepareRenderer(final TableCellRenderer renderer,
					final int row, final int column) {
				final JLabel c = (JLabel) super.prepareRenderer(renderer, row,
						column);
				if (column == 0) {
					final Object element = GUI.this.table.getModel()
							.getValueAt(row, column);
					// Abteilungsnamen sind Strings:
					final String elementS = element.toString();
					if (elementS.endsWith(":")) {
						final Map<TextAttribute, Integer> fontAttributes = new HashMap<TextAttribute, Integer>();
						fontAttributes.put(TextAttribute.UNDERLINE,
								TextAttribute.UNDERLINE_ON);
						c.setFont(c.getFont().deriveFont(fontAttributes)
								.deriveFont(Font.BOLD));
					} else if (elementS.endsWith(" ")) {
						final Map<TextAttribute, Boolean> fontAttributes = new HashMap<TextAttribute, Boolean>();
						fontAttributes.put(TextAttribute.STRIKETHROUGH,
								TextAttribute.STRIKETHROUGH_ON);
						c.setFont(c.getFont().deriveFont(fontAttributes)
								.deriveFont(Font.ITALIC));
					}
				}
				return c;
			}
		};
		this.table.setEnabled(false);

		this.table.setPreferredScrollableViewportSize(new Dimension(500, 70));
		this.table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

		final JTabbedPane tabbedPane = new JTabbedPane();
		getContentPane().add(tabbedPane, BorderLayout.CENTER);
		final JPanel jPanel = new JPanel();
		tabbedPane.addTab("Zeittafel", jPanel);
		jPanel.setLayout(new BorderLayout(0, 0));

		final JScrollPane scrollPane = new JScrollPane(this.table);
		jPanel.add(splitPane);
		splitPane.setLeftComponent(scrollPane);

		final Color light_blue = new Color(205, 235, 255);
		scrollPane.setBackground(light_blue);

		final JScrollPane scrollPane_1 = new JScrollPane();
		splitPane.setRightComponent(scrollPane_1);

		this.txtareaChanges = new JTextArea();
		this.txtareaChanges.setEditable(false);
		scrollPane_1.setViewportView(this.txtareaChanges);
		this.txtareaChanges.setFont(new Font("Verdana", Font.PLAIN, 13));
		splitPane.setDividerLocation(1070);

		final JPanel panel = new JPanel();
		jPanel.add(panel, BorderLayout.NORTH);

		final JLabel lblNewLabel = new JLabel("Maximale Anzahl der Zeitpunkte:");
		panel.add(lblNewLabel);

		this.spinner = new JSpinner();
		final Dimension preferredSize = new Dimension(40, 20);
		this.spinner.setPreferredSize(preferredSize);
		this.spinner.setMinimumSize(preferredSize);

		this.spinner.setModel(new SpinnerNumberModel(SPINNER_MAX,
				new Integer(1), null, new Integer(1)));
		panel.add(this.spinner);

		final JScrollPane scrollPane_2 = new JScrollPane();
		tabbedPane.addTab("Karte des Rumtreibers", null, scrollPane_2, null);

		this.maraudersMap = new MaraudersMap();
		scrollPane_2.setViewportView(this.maraudersMap);
		scrollPane.getViewport().setBackground(light_blue);
		this.table.getTableHeader().setBackground(light_blue);

		createTrayIcon();
		addStateListener();
	}

	private void addStateListener() {
		addWindowStateListener(new WindowStateListener() {
			@Override
			public void windowStateChanged(final WindowEvent e) {
				if (e.getNewState() == ICONIFIED) {
					setVisible(false);
				}
				if (e.getNewState() == 7) {
					setVisible(false);
				}
				if (e.getNewState() == MAXIMIZED_BOTH) {
					setVisible(true);
				}
				if (e.getNewState() == NORMAL) {
					setVisible(true);
				}
			}
		});

	}

	/**
	 * 
	 */
	public void createTrayIcon() {
		final URL imageURL = GUI.class
				.getResource("/com/sun/java/swing/plaf/motif/icons/Inform.gif");
		final Image image = (new ImageIcon(imageURL, "tray icon")).getImage();
		this.trayIcon = new TrayIcon(image);
		this.tray = SystemTray.getSystemTray();
		this.trayIcon.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent e) {
				setVisible(true);
				setExtendedState(Frame.NORMAL);
			}
		});
		try {
			this.tray.add(this.trayIcon);
		} catch (final AWTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
