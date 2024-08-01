package ping;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.TreeSet;

import de.dnb.basics.applicationComponents.FileUtils;

/**
 * Lade die DNB-F-Computer mittels
 * 
 * net view > d:\net.txt
 * 
 * und füge die neuen zur Liste hinzu
 * 
 * 
 * @author baumann
 * 
 */
public class Computers {

	public static void main(final String[] args) throws IOException {
		final TreeSet<String> computers = new TreeSet<>();
		FileUtils.readFileIntoCollection("d:/computers.txt", computers);
		final Collection<String> newLines = new ArrayList<>();
		FileUtils.readFileIntoCollection("d:/net.txt", newLines);
		for (String line : newLines) {
			if (line.contains("F-W7WS-")) {
				line = line.substring(2).trim();
				computers.add(line);
			}
		}

		final PrintWriter printWriter = new PrintWriter("d:/computers.txt");
		for (final String line : computers) {
			printWriter.println(line);
		}
		FileUtils.safeClose(printWriter);

	}

}
