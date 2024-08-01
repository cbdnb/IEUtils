package ping;

import de.dnb.basics.utils.OutputUtils;

public final class LaunchMap {
	public static void main(final String[] args) {
		// final JFrame appFrame = new JFrame("Java2DPathDrawingExample");
		// appFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		// appFrame.add(new MaraudersMap(), BorderLayout.CENTER);
		// appFrame.setSize(2000, 700);
		// appFrame.setVisible(true);
		final MaraudersMap mMap = new MaraudersMap();
		mMap.setWorkerList(Mitarbeiter.getMitarbeiterList());
		OutputUtils.show(mMap, "Karte des Rumtreibers");
	}
}
