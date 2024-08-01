package ping;

import java.io.FileNotFoundException;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Timer;
import java.util.TimerTask;
import java.util.TreeSet;

import javax.swing.SwingUtilities;

import de.dnb.basics.applicationComponents.FileUtils;
import de.dnb.basics.collections.Frequency;
import de.dnb.basics.utils.TimeUtils;

public class WatchComputers {

	private static Collection<String> newPresent;

	private static Collection<String> oldPresent = Collections
			.synchronizedSet(new LinkedHashSet<String>());

	private static MitarbeiterPinger pinger = new MitarbeiterPinger();

	private static TreeSet<String> computers = new TreeSet<>();

	private static int i = 0;

	private static int TRIES = 3;

	final static long PERIOD = 1000 * 60 * 2;

	final static Frequency<String> EVENTS = new Frequency<>();

	static void update() {
		if (i == 0)
			newPresent = new TreeSet<>();
		i++;
		newPresent.addAll(pinger.getPresentComputers(computers));
		if (i < TRIES)
			return;

		i = 0;
		if (!oldPresent.equals(newPresent)) {
			final Collection<String> coming = new TreeSet<>(newPresent);
			final Collection<String> going = new TreeSet<>(oldPresent);
			coming.removeAll(oldPresent);
			going.removeAll(newPresent);

			final String date = TimeUtils.getActualTimehhMM();
			System.out.println();
			System.out.println(date);
			System.out.println("kommt: " + coming);
			System.out.println("geht: " + going);

			for (final String g : going) {
				EVENTS.add(g);
			}
			for (final String c : coming) {
				EVENTS.add(c);
			}

			System.out.println("Instabil: " + EVENTS.getGreater(2));
		}

		oldPresent = newPresent;

	}

	public static void main(final String[] args) throws FileNotFoundException {

		FileUtils.readFileIntoCollection("d:/computers.txt", computers);

		final TimerTask timerTask = new TimerTask() {
			@Override
			public void run() {
				update();
			}

		};
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				final Timer timer = new Timer();
				timer.schedule(timerTask, new Date(), PERIOD);
			}
		});

	}

}
