package ping;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.GeneralPath;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import javax.swing.JComponent;

import de.dnb.basics.applicationComponents.strings.StringUtils;
import de.dnb.basics.collections.TreeMultimap;

public class MaraudersMap extends JComponent {

	private Rooms rooms;
	Set<String> roomNumbers;

	List<Mitarbeiter> workerList;
	TreeMultimap<String, Mitarbeiter> room2Worker;

	private AffineTransform transform;
	private Shape shape;

	public MaraudersMap() {

		this.rooms = new Rooms();
		this.roomNumbers = this.rooms.getRoomNumbers();
		fillMultimap();

		// Nur Figur skalieren, Linie bleibt schmal.

		// Zunächst die Transformation, da die Texte in die Kästchen
		// eingetragen werden müssen:
		final int sx = 57;
		final int sy = 70;
		this.transform = AffineTransform.getScaleInstance(sx, -sy);
		this.transform.concatenate(AffineTransform
				.getTranslateInstance(0.2, -9));

		// Karte erstellen und Fenster an diese anpassen:
		final Path2D.Float path = createMap();
		this.shape = path.createTransformedShape(this.transform);
		final Rectangle bounds = this.shape.getBounds();
		final Dimension dimension = new Dimension(bounds.width + 20,
				bounds.height);
		setPreferredSize(dimension);

	}

	private void fillMultimap() {
		this.room2Worker = new TreeMultimap<>();
		if (this.workerList == null)
			return;
		for (final Mitarbeiter mitarbeiter : this.workerList) {
			this.room2Worker.add(mitarbeiter.roomNumber, mitarbeiter);
		}
	}

	synchronized void setWorkerList(final List<Mitarbeiter> list) {
		this.workerList = list;
		fillMultimap();
		repaint();
	}

	@Override
	public void paintComponent(final Graphics g) {
		super.paintComponent(g);
		final Graphics2D g2d = (Graphics2D) g;

		g2d.draw(this.shape);

		fillMultimap();
		for (final String number : this.roomNumbers) {
			final Room room = this.rooms.getRoom(number);
			final Point2D ll = room.getLowerLeft();
			final Point2D ul = room.getUpperLeft();
			final Point2D lowerLeft = this.transform.transform(ll, null);
			final Point2D upperLeft = this.transform.transform(ul, null);

			g2d.drawString(number, (int) lowerLeft.getX() + 2,
					(int) lowerLeft.getY() - 2);
			final Collection<Mitarbeiter> mitarbeiterCol = this.room2Worker
					.getNullSafe(number);
			final int fontsize = getFont().getSize();
			int y = (int) upperLeft.getY();
			final int x = (int) upperLeft.getX();
			setFont(getFont().deriveFont((float) 10));
			for (final Mitarbeiter mitarbeiter : mitarbeiterCol) {
				y += fontsize + 2;
				final String s = StringUtils.cut(mitarbeiter.lastName, 8);
				g2d.drawString(s, x + 2, y);
			}

		}

	}

	public Path2D.Float createMap() {
		final Path2D.Float path = new GeneralPath();
		for (final String number : this.roomNumbers) {
			final Room room = this.rooms.getRoom(number);
			final Shape shape = room.getRect();
			path.append(shape, false);
		}
		return path;
	}
}