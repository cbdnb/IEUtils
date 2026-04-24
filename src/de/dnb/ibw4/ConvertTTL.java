package de.dnb.ibw4;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import de.dnb.basics.applicationComponents.strings.StringUtils;
import de.dnb.basics.applicationComponents.tuples.Pair;

public class ConvertTTL {

	private static String actualline;

	public static enum TOKEN3 {
		ALL, BEGFILE, ENDFILE, BEGTAG, BEGTAGCONT, ENDTAG, NEXTTAG, STRING, INTEGER, DELETE, INSERT, FROM, TO, INCLUSIVE, EXCLUSIVE, BEFORE, TAG
	}

	private static final List<String> POSITIONS = Arrays.asList("BEGFILE",
			"ENDFILE", "BEGTAGCONT", "BEGTAG", "ENDTAG", "NEXTTAG");

	public static void main(final String[] args) {
		final String file = StringUtils.readClipboard();

		final String winIBW4TTL = toWinIBW4TTL(file);
		System.out.println(winIBW4TTL);
		StringUtils.writeToClipboard(winIBW4TTL);
	}

	private static Pattern fromPattern = Pattern.compile("FROM +(\\S+)",
			Pattern.CASE_INSENSITIVE);

	private static Pattern toPattern = Pattern.compile("TO +(\\S+)",
			Pattern.CASE_INSENSITIVE);

	private static Pattern beforePattern = Pattern.compile("BEFORE +(\\S+)",
			Pattern.CASE_INSENSITIVE);

	private static Pattern inclusivePattern = Pattern.compile("INCLUSIVE",
			Pattern.CASE_INSENSITIVE);

	private static Pattern exclusivePattern = Pattern.compile("EXCLUSIVE",
			Pattern.CASE_INSENSITIVE);

	private static Pattern withinPattern = Pattern.compile("WITHIN +THISTAG",
			Pattern.CASE_INSENSITIVE);

	public static String toWinIBW4line(final Map<String, String> map) {
		return map.keySet().stream().map(k -> k + "=" + map.get(k))
				.collect(Collectors.joining(", "));
	}

	public static String toWinIBW4TTL(final String oldTTL) {
		return oldTTL.lines().map(line -> toWinIBW4line(parseLine(line)))
				.collect(Collectors.joining("\n"));
	}

	public static Map<String, String> parseLine(final String line) {
		final LinkedHashMap<String, String> map = new LinkedHashMap<>();
		if (StringUtils.isNullOrWhitespace(line))
			return map;
		actualline = line.replace("ALL", "");
		actualline = line.replace("all", "");
		actualline = actualline.trim();

		final String tag = parseTag();
		if (tag != null)
			map.put("TAG", tag);

		final Pair<String, String> actionP = parseAction();
		if (actionP == null)
			throw new IllegalArgumentException("no Action: " + actualline);
		map.put("ACTION", actionP.first);
		final String text = actionP.second;
		if (text != null)
			map.put("TEXT", text);

		// Behandlung FROM | TO | BEFORE:
		final Matcher fromMatcher = fromPattern.matcher(actualline);
		if (fromMatcher.find()) {
			final String found = fromMatcher.group();
			final String position = fromMatcher.group(1);
			map.put("FROM", position);
			actualline = actualline.replace(found, "");
		}
		final Matcher beforeMatcher = beforePattern.matcher(actualline);
		if (beforeMatcher.find()) {
			final String found = beforeMatcher.group();
			final String position = beforeMatcher.group(1);
			map.put("FROM", position);
			actualline = actualline.replace(found, "");
		}
		final Matcher toMatcher = toPattern.matcher(actualline);
		if (toMatcher.find()) {
			final String found = toMatcher.group();
			final String position = toMatcher.group(1);
			map.put("TO", position);
			actualline = actualline.replace(found, "");
		}

		// IN(EX)CLUSIVE:
		final Matcher inclMatcher = inclusivePattern.matcher(actualline);
		if (inclMatcher.find()) {
			final String found = inclMatcher.group();
			map.put("INCLUSIVEFROM", "true");
			map.put("INCLUSIVETO", "true");
			actualline = actualline.replace(found, "");
		}
		final Matcher exclMatcher = exclusivePattern.matcher(actualline);
		if (exclMatcher.find()) {
			final String found = exclMatcher.group();
			map.put("INCLUSIVEFROM", "false");
			map.put("INCLUSIVETO", "false");
			actualline = actualline.replace(found, "");
		}
		// WITHIN
		final Matcher withinMatcher = withinPattern.matcher(actualline);
		if (withinMatcher.find()) {
			final String found = withinMatcher.group();
			map.put("WITHINTAG", "true");
			actualline = actualline.replace(found, "");
		}

		/*
		 * Behandlung von alleinstehenden BEGFILE | ENDFILE | BEGTAG |
		 * BEGTAGCONT | ENDTAG | NEXTTAG
		 *
		 */
		for (final String position : POSITIONS) {
			if (actualline.toUpperCase().contains(position)) {
				// Beispiel in Product Description, verstehe ich aber nicht
				if (position.equals("ENDFILE"))
					map.put("FROM", position);
				else
					map.put("BEGIN", position);
				break;
			}
		}
		return map;
	}

	private static Pattern textPattern = Pattern.compile("INSERT +\"(.+?)\".*",
			Pattern.CASE_INSENSITIVE);// ? = non greedy

	private static Pair<String, String> parseAction() {
		final boolean del = actualline.contains("DELETE")
				|| actualline.contains("delete");
		String insertText = null;
		final boolean ins = actualline.contains("INSERT")
				|| actualline.contains("insert");
		if (ins) {
			final Matcher textMatcher = textPattern.matcher(actualline);
			if (textMatcher.find())
				insertText = textMatcher.group(1);
		}
		if (del && ins)
			return new Pair<>("REPLACE", insertText);
		if (del)
			return new Pair<>("DELETE", insertText);
		if (ins)
			return new Pair<>("INSERT", insertText);
		return null;
	}

	// ? = non greedy
	private static Pattern tagPattern = Pattern
			.compile("(FROM )?TAG +\"(.+?)\" *", Pattern.CASE_INSENSITIVE);

	private static String parseTag() {
		final Matcher tagMatcher = tagPattern.matcher(actualline);
		if (!tagMatcher.find())
			return null;
		final String tag = tagMatcher.group(2);
		actualline = actualline.replace(tagMatcher.group(), "");
		return tag;
	}

}
