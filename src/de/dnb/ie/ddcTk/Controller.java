package de.dnb.ie.ddcTk;

import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.function.Function;

import javax.swing.JTree;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;

import de.dnb.basics.Constants;
import de.dnb.basics.trees.huge.HugeFactory;
import de.dnb.basics.utils.PortalUtils;
import de.dnb.gnd.exceptions.IllFormattedLineException;
import de.dnb.gnd.parser.Record;
import de.dnb.gnd.utils.formatter.RDAFormatter;

public class Controller {

	private static final String INFILE = Constants.DDC_XML;
	View view;

	private class DDCActionListener implements ActionListener {
		@Override
		public void actionPerformed(final ActionEvent e) {
			final String ddc = view.getDDC();
			setRecords(ddc);
			view.expandTo(ddc);
		}
	}

	/**
	 * @param ddc
	 */
	private void setRecords(final String ddc) {
		final Record tkRecord = Database.getTkByDDC(ddc);
		final org.marc4j.marc.Record marcRecord = Database.getMarcByDDC(ddc);
		view.setTk(tkRecord);
		view.setMarc(marcRecord);
		view.setDebug(tkRecord);
	}

	private final class MyHyperlinkListener implements HyperlinkListener {
		@Override
		public void hyperlinkUpdate(final HyperlinkEvent e) {
			if (e.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
				final URL url = e.getURL();
				if (url == null)
					return;
				if (url.getAuthority().contains("pansoft")) {
					try {
						Desktop.getDesktop().browse(url.toURI());
					} catch (IOException | URISyntaxException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				final String id = url.getAuthority();
				if (id == null)
					return;
				final Record tkRecord = Database.getTkByID(id);
				if (tkRecord == null) {
					// dann ist es wohl ein Normdatensatz:
					try {
						final URI authUri = PortalUtils.getPortalUri(id);
						Desktop.getDesktop().browse(authUri);
					} catch (final IOException e1) {
						e1.printStackTrace();
					}
					return;
				} else {
					final org.marc4j.marc.Record marcRecord = Database
							.getMarcByID(id);
					view.setDebug(tkRecord);
					view.setTk(tkRecord);
					view.setMarc(marcRecord);
					final String ddc = Database.getDDCByID(id);
					view.expandTo(ddc);
				}
			}
		}
	}

	public Controller() {
		view = new View();
		final Converter converter = new Converter();
		try {
			view.setInfo("Lade Daten ...");
			converter.load(INFILE);
		} catch (final FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		view.setInfo("");
		view.setEnabled();
		view.setSearchListener(new DDCActionListener());
		view.setLinkListener(new MyHyperlinkListener());

		final Function<String, String> textFunction = new Function<String, String>() {
			@Override
			public String apply(final String ddc) {
				final Record tkRecord = Database.getTkByDDC(ddc);
				if (tkRecord == null)
					return "";
				else
					try {
						return RDAFormatter.getRDAHeading(tkRecord);
					} catch (final IllFormattedLineException e) {
						return "";
					}
			}
		};

		final HugeFactory<String> factory = new HugeFactory<>();
		factory.setTextfunction(textFunction);
		factory.setNodeSelectionListener(this::setRecords);
		factory.setTreeFunction(Database.getTreeFunction());
		factory.setTopTerms(Database.getTopTerms());
		factory.setRootname("ddc");

		final JTree jTree = factory.createTree(false);

		view.setTree(jTree);
	}

	public static void main(final String[] args) {
		new Controller();

	}

}
