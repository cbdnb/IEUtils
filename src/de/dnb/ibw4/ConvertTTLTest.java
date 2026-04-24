package de.dnb.ibw4;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import de.dnb.basics.applicationComponents.strings.StringUtils;

public class ConvertTTLTest {

	@Test
	public void testParseLine() {
		Map<String, String> mapSource = ConvertTTL.parseLine("ALL TAG \"Eingabe:\" DELETE");
		Map<String, String> mapTarg = toMap("TAG=Eingabe:, ACTION=DELETE");
		assertEquals(mapSource, mapTarg);

		mapSource = ConvertTTL.parseLine("");
		mapTarg = toMap("");
		assertEquals(mapSource, mapTarg);

		mapSource = ConvertTTL.parseLine("TAG \"1100\" BEGTAGCONT DELETE");
		mapTarg = toMap("TAG=1100, BEGIN=BEGTAGCONT, ACTION=DELETE");
		assertEquals(mapSource, mapTarg);

		mapSource = ConvertTTL.parseLine("TAG \"0500\" BEGTAGCONT DELETE FROM 3");
		mapTarg = toMap("TAG=0500, ACTION=DELETE, BEGIN=BEGTAGCONT, FROM=3");
		assertEquals(mapSource, mapTarg);

		mapSource = ConvertTTL.parseLine("TAG \"0500\" BEGTAGCONT DELETE FROM 3 TO 5");
		mapTarg = toMap("TAG=0500, ACTION=DELETE, BEGIN=BEGTAGCONT, FROM=3, TO=5");
		assertEquals(mapSource, mapTarg);

		mapSource = ConvertTTL.parseLine("TAG \"1100\" DELETE FROM \"[\" TO \"]\" EXCLUSIVE WITHIN THISTAG");
		mapTarg = toMap(
				"TAG=1100, ACTION=DELETE, FROM=\"[\", TO=\"]\", INCLUSIVEFROM=false, INCLUSIVETO=false, WITHINTAG=true");
		assertEquals(mapSource, mapTarg);

		mapSource = ConvertTTL.parseLine("TAG \"1100\" DELETE FROM \"[\" TO \"]\" INCLUSIVE WITHIN THISTAG");
		mapTarg = toMap(
				"TAG=1100, ACTION=DELETE, FROM=\"[\", TO=\"]\", INCLUSIVEFROM=true, INCLUSIVETO=true, WITHINTAG=true");
		assertEquals(mapSource, mapTarg);

		mapSource = ConvertTTL.parseLine("TAG \"5530\" DELETE TO ENDFILE");
		mapTarg = toMap("TAG=5530, ACTION=DELETE, TO=ENDFILE");
		assertEquals(mapSource, mapTarg);

		mapSource = ConvertTTL.parseLine("INSERT TAG \"0701\"");
		mapTarg = toMap("ACTION=INSERT, TAG=0701");
		assertEquals(mapSource, mapTarg);

		mapSource = ConvertTTL.parseLine("TAG \"1100\" BEGTAGCONT INSERT \" 1997 \"");
		mapTarg = toMap("ACTION=INSERT, TAG=1100, BEGIN=BEGTAGCONT, TEXT= 1997 ");
		assertEquals(mapSource, mapTarg);

		mapSource = ConvertTTL.parseLine("TAG \"424.\" INSERT \"WinIBW\" BEFORE 3");
		mapTarg = toMap("TAG=424., ACTION=INSERT, TEXT=WinIBW, FROM=3");
		assertEquals(mapSource, mapTarg);

		mapSource = ConvertTTL.parseLine("ALL TAG \"2009\" BEGTAG DELETE TO 5 INSERT \"2001\"");
		mapTarg = toMap("TAG=2009, ACTION=REPLACE, BEGIN=BEGTAG, TO=5, TEXT=2001");
		assertEquals(mapSource, mapTarg);

		mapSource = ConvertTTL.parseLine("ENDFILE INSERT \"...\"");
		mapTarg = toMap("FROM=ENDFILE, ACTION=INSERT, TEXT=...");
		assertEquals(mapSource, mapTarg);

		mapSource = ConvertTTL.parseLine("DELETE FROM TAG \"7001\" TO ENDFILE");
		mapTarg = toMap("ACTION=DELETE, TAG=7001, TO=ENDFILE");
		assertEquals(mapSource, mapTarg);

	}

	public Map<String, String> toMap(final String line) {
		final Map<String, String> map = new HashMap<>();
		if (StringUtils.isNullOrWhitespace(line)) {
			return map;
		}
		final String[] fracs = line.split(", +");

		for (int i = 0; i < fracs.length; i++) {
			final String[] keyVal = fracs[i].split("=");
			if (keyVal.length == 0) {
				continue;
			}
			map.put(keyVal[0], keyVal[1]);
		}
		return map;
	}

}
