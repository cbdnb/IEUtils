package ping;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.Point2D;

public class Room {

	final private int x;
	final private int y;
	final private int floor;

	Rectangle getRect() {
		return new Rectangle(this.x, this.y, 1, 1);
	}

	public Room(final int x, final int y, final int floor) {
		super();
		this.x = x;
		this.y = y;
		this.floor = floor;
	}

	public int getX() {
		return this.x;
	}

	public int getY() {
		return this.y;
	}

	Point2D getLowerLeft() {
		return new Point(this.x, this.y);
	}

	Point2D getUpperLeft() {
		return new Point(this.x, this.y + 1);
	}

}
