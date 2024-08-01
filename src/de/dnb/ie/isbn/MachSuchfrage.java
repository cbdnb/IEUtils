/**
 * 
 */
package de.dnb.ie.isbn;

import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

import de.dnb.basics.utils.TimeUtils;

/**
 * @author baumann
 *
 */
public class MachSuchfrage {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		List<Calendar> list = TimeUtils.getDaysWeekBefore();
		String c = list.stream().map(TimeUtils::toYYMMDD).map(s ->
		{
			return "\"" + s + " b\"";
		}).collect(Collectors.joining(" OR "));

		System.out.println("f STA (" + c + ")" + " AND ICD ra");

	}

}
