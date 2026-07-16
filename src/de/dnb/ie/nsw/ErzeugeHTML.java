package de.dnb.ie.nsw;

import java.awt.FileDialog;
import java.io.IOException;
import java.io.PrintWriter;

import de.dnb.basics.applicationComponents.MyFileUtils;
import de.dnb.basics.utils.OutputUtils;
import de.dnb.basics.utils.TimeUtils;
import de.dnb.gnd.utils.isbd.HTMLformatter;
import de.dnb.gnd.utils.isbd.WV;

public class ErzeugeHTML {

	static boolean debug = true;

	public static void main(final String[] args) throws IOException {
		FileDialog dialog = new FileDialog((java.awt.Frame) null, "Select NSW-Records", FileDialog.LOAD);
		dialog.setDirectory("D:\\Analysen\\karg\\NSW\\");
		dialog.setFile("*.txt");
		dialog.setVisible(true);
		String nswFile = dialog.getFile();
		if (nswFile == null) {
			System.out.println("No file selected. Exiting.");
			System.exit(0);
		}
		final String directory = dialog.getDirectory();
		nswFile = directory + nswFile;

		dialog = new FileDialog((java.awt.Frame) null, "Select Über-Records", FileDialog.LOAD);
		dialog.setDirectory("D:\\Analysen\\karg\\NSW\\");
		dialog.setFile("*.txt");
		dialog.setVisible(true);
		String ueberFile = dialog.getFile();
		if (ueberFile != null) {
			final String directory2 = dialog.getDirectory();
			ueberFile = directory2 + ueberFile;
		}

		final WV wv = WV.createWV(nswFile, ueberFile);
		final HTMLformatter formatter = new HTMLformatter();
		final String html = formatter.format(wv);
		final String path = directory + "out_" + TimeUtils.getToday() + ".html";
		System.err.println(path);
		final PrintWriter out = MyFileUtils.outputFile(path, false);
		OutputUtils.show(html);
		out.println(html);
		System.out.println(html);

	}

}
