package ping;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import de.dnb.basics.applicationComponents.FileUtils;
import de.dnb.basics.applicationComponents.strings.StringUtils;

public class MitarbeiterPinger {

	final static int NUMER_TRIES = 1;

	IllegalStateException illStateEx = new IllegalStateException(
			"Ausführung nicht möglich");

	MitarbeiterPinger() {

	}

	public static void main(final String args[]) {
		final MitarbeiterPinger ping = new MitarbeiterPinger();
		System.out.println(">>>"
				+ ping.getPingText("F-W10WS-06353", 3));
		System.out.println(">>>" + ping.isPresent("F-W10WS-06353", 3));
	}

	/**
	 * wird der erste Ping nicht beantwortet, erfolgen weitere Versuche
	 * (mindestens aber immer einer).
	 * 
	 * @param computer
	 *            ip des Computers
	 * @param times
	 *            Zahl der Pings
	 * @return ob anwesend
	 */
	public boolean isPresent(final String computer, final int times) {

		for (int i = 1; i < NUMER_TRIES; i++) {
			if (lookup(computer, times))
				return true;
			// System.err.println((i + 1) + ". Versuch: " + computer);
		}
		// letzter Versuch:
		return lookup(computer, times);
	}

	/**
	 * 
	 * @param computer
	 *            ip des Computers
	 * @param times
	 *            Zahl der Pings
	 * @return ob anwesend
	 */
	private boolean lookup(final String computer, final int times) {
		final String pingText = getPingText(computer, times);
		if (pingText.isEmpty())
			return false;
		return parse(pingText);
	}

	public Collection<String> getPresentComputers(
			final Collection<String> computers) {

		final Set<String> presentComputers = Collections
				.synchronizedSet(new LinkedHashSet<String>());
		final List<Thread> threads = new LinkedList<>();

		for (final String computer : computers) {

			final Runnable runnable = new Runnable() {

				@Override
				public void run() {
					final boolean computerPresent;
					if (StringUtils.isNullOrEmpty(computer))
						computerPresent = false;
					else
						computerPresent = isPresent(computer, 5);
					if (computerPresent) {
						presentComputers.add(computer);
					}
				}
			};

			final Thread thread = new Thread(runnable);
			threads.add(thread);
			thread.start();

		}

		for (final Thread thread : threads) {
			try {
				thread.join();
			} catch (final InterruptedException e) {
				e.printStackTrace();
			}
		}
		return presentComputers;
	}

	public boolean parse(final String pingText) {
		return !(pingText.contains("nicht finden")
				|| pingText.contains("nicht erreichbar")
				|| pingText.contains("berschreitung") || pingText
					.contains("angegeben"));
	}

	/**
	 * 
	 * @param computer
	 * @param times
	 * @return leeren String, wenn Fehler auftritt
	 */
	public String getPingText(final String computer, final int times) {
		final String option = "-n";
		BufferedReader stdInput = null;
		try {
			String text = "";
			String s = null;
			final List<String> command = Arrays.asList("ping", option,
					Integer.toString(times), computer);
			final ProcessBuilder pb = new ProcessBuilder(command);
			Process process;
			process = pb.start();
			stdInput = new BufferedReader(new InputStreamReader(
					process.getInputStream()));
			// read the output from the command
			while ((s = stdInput.readLine()) != null) {
				text += "\n" + s;
			}
			FileUtils.safeClose(stdInput);
			return text;

		} catch (final IOException e) {
			FileUtils.safeClose(stdInput);
			return "";
		}
	}

}
