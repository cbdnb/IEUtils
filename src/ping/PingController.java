package ping;

import java.util.Date;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class PingController {

	View tableview;

	Model model;

	private static final long period = 1000 * 60 * 3;

	private static final int DELAY_MIN = 1000 * 60 * 1;

	private static final int DELAY_MAX = 1000 * 60 * 2;

	private static PingController rc;

	private GUI gui;

	private PingController() {
		this.model = new Model();

		this.tableview = new View(this.model);
		this.model.addObserver(this.tableview);
		this.tableview.addSpinnerListener(new SpinnerListener());
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				PingController.this.model.setMax(PingController.this.tableview
						.getMax());
			}
		});

		setTimer();
		// detect();
	}

	private void detect() {
		final int maxdiff = DELAY_MAX - DELAY_MIN;
		final Random random = new Random();
		while (true) {
			PingController.this.model.update();
			final int diff = random.nextInt(maxdiff);
			final int delay = diff + DELAY_MIN;
			System.err.println(delay);
			try {
				Thread.sleep(delay);
			} catch (final InterruptedException e) {
			}
		}

	}

	private void setTimer() {
		final TimerTask timerTask = new TimerTask() {
			@Override
			public void run() {
				PingController.this.model.update();
			}
		};
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				final Timer timer = new Timer();
				timer.schedule(timerTask, new Date(), period);
			}
		});
	}

	public static void main(final String[] args) {
		rc = new PingController();
	}

	class SpinnerListener implements ChangeListener {

		@Override
		public void stateChanged(final ChangeEvent e) {
			PingController.this.model.setMax(PingController.this.tableview
					.getMax());
		}

	}

}
