package ping;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Rooms {

	Map<String, Room> number2rooms;

	Set<String> getRoomNumbers() {
		return this.number2rooms.keySet();
	}

	Room getRoom(final String number) {
		return this.number2rooms.get(number);
	}

	public Rooms() {
		this.number2rooms = new HashMap<>();
		this.number2rooms.put("210", new Room(0, 0, 2));
		this.number2rooms.put("209", new Room(1, 0, 2));
		this.number2rooms.put("208", new Room(2, 0, 2));
		this.number2rooms.put("207", new Room(3, 1, 2));
		this.number2rooms.put("206", new Room(4, 1, 2));
		this.number2rooms.put("205", new Room(5, 1, 2));
		this.number2rooms.put("204", new Room(6, 1, 2));
		this.number2rooms.put("203", new Room(7, 1, 2));
		this.number2rooms.put("202", new Room(8, 1, 2));
		this.number2rooms.put("201", new Room(9, 1, 2));
		this.number2rooms.put("WC1", new Room(10, 1, 2));

		this.number2rooms.put("211", new Room(10, 2, 2));
		this.number2rooms.put("212", new Room(10, 3, 2));
		this.number2rooms.put("213", new Room(10, 4, 2));
		this.number2rooms.put("214", new Room(10, 5, 2));
		this.number2rooms.put("214a", new Room(10, 6, 2));

		this.number2rooms.put("238", new Room(12, -1, 2));
		this.number2rooms.put("217b", new Room(12, 1, 2));
		this.number2rooms.put("Aufz1", new Room(12, 2, 2));
		this.number2rooms.put("217", new Room(12, 3, 2));
		this.number2rooms.put("216", new Room(12, 4, 2));
		this.number2rooms.put("215", new Room(12, 5, 2));

		this.number2rooms.put("218", new Room(13, 1, 2));
		this.number2rooms.put("219", new Room(14, 1, 2));
		this.number2rooms.put("220", new Room(15, 1, 2));
		this.number2rooms.put("221", new Room(16, 1, 2));
		this.number2rooms.put("WC2", new Room(17, 1, 2));

		this.number2rooms.put("222", new Room(17, 2, 2));
		this.number2rooms.put("223", new Room(17, 3, 2));
		this.number2rooms.put("224", new Room(17, 4, 2));
		this.number2rooms.put("225", new Room(17, 5, 2));
		this.number2rooms.put("225a", new Room(17, 6, 2));

		this.number2rooms.put("237", new Room(19, -1, 2));
		this.number2rooms.put("228b", new Room(19, 1, 2));
		this.number2rooms.put("Aufz2", new Room(19, 2, 2));
		this.number2rooms.put("228", new Room(19, 3, 2));
		this.number2rooms.put("227", new Room(19, 4, 2));
		this.number2rooms.put("226", new Room(19, 5, 2));

		this.number2rooms.put("229", new Room(20, 1, 2));
		this.number2rooms.put("230", new Room(21, 1, 2));
		this.number2rooms.put("231", new Room(22, 1, 2));
		this.number2rooms.put("232", new Room(23, 1, 2));
		this.number2rooms.put("233", new Room(24, 1, 2));

		this.number2rooms.put("236", new Room(26, -1, 2));
		this.number2rooms.put("Technik", new Room(25, 1, 2));
		this.number2rooms.put("233b", new Room(25, 2, 2));
		this.number2rooms.put("234", new Room(25, 3, 2));
		this.number2rooms.put("235", new Room(25, 4, 2));

	}

}
