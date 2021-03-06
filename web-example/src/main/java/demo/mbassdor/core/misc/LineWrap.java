/**
 * Copyright 2014-2015, NetEase, Inc. All Rights Reserved.
 * 
 * Date: 2017年5月14日
 */

package demo.mbassdor.core.misc;

import java.util.LinkedList;
import java.util.List;

/**
 * Desc:TODO
 * 
 * @author wei.zw
 * @since 2017年5月14日 下午5:32:27
 * @version v 0.1
 */
public class LineWrap {
	/**
	 * According to RFC2812, this is strictly 510 characters (as the maximum
	 * allowed for the command and its parameters), however we'll allow less
	 * than that to provide for room for the overhead. This is a rough value and
	 * may need to become more smaller.
	 */
	public static final int MAX_IRC_LENGTH = 400;

	/**
	 * Wrap line to lines of the maximum IRC length. Any newline (\n) characters
	 * will force a line break where they exist.
	 * 
	 * @param line
	 *            Long line to wrap
	 * @return List of wrapped lines containing at least one string
	 */
	public static List<String> wrap(String line) {
		return wrap(line, MAX_IRC_LENGTH);
	}

	/**
	 * Wrap a line to lines of at most the specified length. Any newline (\n)
	 * characters will force a line break where they exist. Otherwise, line
	 * breaks will be assigned to the last whitespace character in a line. If
	 * neither of these methods works, the line maximum length will be enforced
	 * by splitting the string at the length limit.
	 * 
	 * @param line
	 *            Long line to wrap.
	 * @param maxLineLength
	 *            The maximum length of a line to allow.
	 * @return List of wrapped lines containing at least one line.
	 */
	public static List<String> wrap(String raw, final int maxLineLength) {
		// Is this a base case?
		if (raw.length() <= maxLineLength) {
			// \n characters force a break regardless of where they are.
			// Search the remainder segment for one.
			int endIndex = raw.indexOf('\n');
			if (endIndex != -1) {
				String line = raw.substring(0, endIndex);
				List<String> lines = wrap(raw.substring(endIndex + 1), raw.length());
				lines.add(0, line);
				return lines;
			} else {
				// Otherwise this really is a base case

				// Init return list
				List<String> lines = new LinkedList<String>();

				// Add the whole remainder as the first inserted line
				lines.add(raw);

				return lines;
			}
		} else {
			int endIndex = -1;

			// \n characters force a break regardless of where they are.
			// Search in our segment for one.
			endIndex = raw.substring(0, maxLineLength).indexOf('\n');

			// If no \n was found, search for a whitespace character closest to
			// the end of our segment.
			if (endIndex == -1) {
				char[] rawArray = raw.toCharArray();
				for (int i = maxLineLength; i > 0 && endIndex == -1; i--) {
					if (Character.isWhitespace(rawArray[i])) {
						endIndex = i;
					}
				}
			}

			// If no intelligent split point was found, just use the max size as
			// that point
			if (endIndex == -1) {
				endIndex = maxLineLength;
			}

			int splitSpacing = 0;

			/*
			 * Remove/skip-over whitespace splitting characters. This avoids
			 * including \n manual split characters, which aren't allowed by the
			 * IRC protocol, as well as having a space at the start of each
			 * automatically wrapped line.
			 */
			if (Character.isWhitespace(raw.charAt(endIndex))) {
				splitSpacing = 1;
			}

			String line = raw.substring(0, endIndex);

			// Recurse on remainder of raw
			List<String> lines = wrap(raw.substring(endIndex + splitSpacing), maxLineLength);

			// Prepend our line, so the text stays in the original order
			lines.add(0, line);

			return lines;
		}
	}
}
