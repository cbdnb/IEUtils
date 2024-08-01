package ping;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Observable;
import java.util.Set;

import javax.swing.SwingUtilities;

import de.dnb.basics.applicationComponents.strings.StringUtils;
import de.dnb.basics.collections.ListMultimap;
import de.dnb.basics.collections.ListUtils;
import de.dnb.basics.collections.Multimap;
import de.dnb.basics.filtering.FilterUtils;
import de.dnb.basics.utils.TimeUtils;

public class Model extends Observable {

	private static final int PINGS = 2;

	private ArrayList<Date> timeStamps;

	private Multimap<Mitarbeiter, Boolean> timeTable;

	private MitarbeiterPinger ping;

	/**
	 * Alle Mitarbeiter.
	 */
	private ArrayList<Mitarbeiter> workerList;

	public ArrayList<Mitarbeiter> getWorkerList() {
		return this.workerList;
	}

	/**
	 * anwesende.
	 */
	private Set<Mitarbeiter> present;

	/**
	 * 
	 * @return alphabetisch sortierte Liste der Anwesenden.
	 */
	public List<Mitarbeiter> getPresent() {
		final List<Mitarbeiter> list = new LinkedList<>(this.present);
		final Comparator<Mitarbeiter> comp = new Comparator<Mitarbeiter>() {
			@Override
			public int compare(final Mitarbeiter o1, final Mitarbeiter o2) {
				return o1.nameCompare(o2);
				// return o1.lastName.compareToIgnoreCase(o2.lastName);
			}
		};
		Collections.sort(list, comp);
		return list;
	}

	public boolean isPresent(final Mitarbeiter mitarbeiter) {
		return this.present.contains(mitarbeiter);
	}

	private Set<Mitarbeiter> past;

	private int max = 26;

	/**
	 * 
	 * @param max
	 *            Maximalzahl der zurückzugebenden Informationen. Standard = 26
	 */
	public void setMax(final int max) {
		this.max = max;
		setChanged();
		notifyObservers();
	}

	private <E> List<E> getTail(final Collection<E> source) {
		final int size = source.size();
		if (size < this.max)
			return new ArrayList<>(source);
		else {
			final AbstractList<E> list = ListUtils.convertToList(source);
			return new ArrayList<>(list.subList(size - this.max, size));
		}
	}

	/**
	 * 
	 * @param mitarbeiter
	 * @return die max letzten Anwesenheiten des Mitarbeiters
	 */
	public List<Boolean> getTimeLine(final Mitarbeiter mitarbeiter) {
		return getTail(this.timeTable.getNullSafe(mitarbeiter));
	}

	/**
	 * 
	 * @return die max letzten Zeitpunkte
	 */
	public List<Date> getTimeStamps() {
		return getTail(this.timeStamps);
	}

	public int getSize() {
		final List<Date> stamps = getTimeStamps();
		return stamps.size();
	}

	/**
	 * 
	 * @return die max letzten Zeitpunkte
	 */
	public List<String> getTimeStampStrings() {
		return FilterUtils.map(getTimeStamps(), TimeUtils.mxhhMM);
	}

	/**
	 * 
	 * @param i
	 * @return Die Zeitdifferenz zwischen Zeitpunkt i und i + 1; 0, wenn
	 *         außerhalb des zulässigen Bereichs
	 */
	public int getTimeDiff(final int i) {
		final List<Date> stamps = getTimeStamps();
		int diff = 0;
		if (i < stamps.size() - 1 && i >= 0)
			diff = (int) TimeUtils.getMinuteDifference(stamps.get(i),
					stamps.get(i + 1));
		return diff;
	}

	public synchronized void update() {
		final Set<Mitarbeiter> newHarvest = Collections
				.synchronizedSet(new LinkedHashSet<Mitarbeiter>());

		final List<Thread> threads = new LinkedList<>();

		for (final Mitarbeiter mitarbeiter : this.workerList) {
			final String computer = mitarbeiter.computer;

			final Runnable runnable = new Runnable() {

				@Override
				public void run() {
					final boolean workerPresent;
					if (StringUtils.isNullOrEmpty(computer))
						workerPresent = false;
					else
						workerPresent = Model.this.ping.isPresent(computer,
								PINGS);

					if (workerPresent) {
						newHarvest.add(mitarbeiter);
						System.err.println(mitarbeiter);
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
		System.err.println("-");

		if (!newHarvest.equals(this.present)) {
			// zum Schutz vor parallelem Zugriff
			SwingUtilities.invokeLater(new Runnable() {
				@Override
				public void run() {
					changeStructures(newHarvest);
				}
			});
			setChanged();
			notifyObservers();
		}

	}

	/**
	 * @param newHarvest
	 */
	private void changeStructures(final Set<Mitarbeiter> newHarvest) {
		this.past = this.present;
		this.present = newHarvest;
		for (final Mitarbeiter mitarbeiter : this.workerList) {
			if (this.present.contains(mitarbeiter))
				this.timeTable.add(mitarbeiter, true);
			else
				this.timeTable.add(mitarbeiter, false);
		}
		final Date date = new Date();
		this.timeStamps.add(date);
	}

	public Set<Mitarbeiter> coming() {
		final Set<Mitarbeiter> coming = new HashSet<>(this.present);
		if (this.past != null)
			coming.removeAll(this.past);
		return coming;
	}

	public Set<Mitarbeiter> going() {
		if (this.past == null)
			return new HashSet<>();
		final Set<Mitarbeiter> going = new HashSet<>(this.past);
		going.removeAll(this.present);
		return going;
	}

	public Model() {
		this.timeStamps = new ArrayList<>();
		this.timeTable = new ListMultimap<>();
		this.workerList = Mitarbeiter.getMitarbeiterList();

		this.present = new LinkedHashSet<Mitarbeiter>();
		this.ping = new MitarbeiterPinger();

	}

}
