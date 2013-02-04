package fitnesse.tools;

import static org.apache.commons.lang.StringUtils.isEmpty;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import fr.....utils.DateUtils;

public class FitnesseUtils {
	public static final String BLANK = "--";
	public static final String DENIED = "denied";
	public static final String ERROR_MISSING_MESSAGE = "Message d'erreur manquant";
	public static final Long KEEP_CURRENT_VALUE = -1L;

	public static String escapeFitnesse(String value) {
		return value == null ? null : value.replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\\p{Z}", " ");
	}

	public static String buildMapKey(String... keyParams) {
		String key = "";
		for (int i = 0; i < keyParams.length - 1; i++) {
			key += keyParams[i] + "#";
		}
		key += keyParams[keyParams.length - 1];
		return key;
	}

	public static String nullIt(String stringToSetToNull) {
		return isEmpty(stringToSetToNull) ? null : stringToSetToNull;
	}

	public static String blankIt(Object objectToSetToBlank) {
		return objectToSetToBlank == null || objectToSetToBlank.toString().isEmpty() ? BLANK : objectToSetToBlank
				.toString();
	}

	public static String toString(List<String> messages) {
		Collections.sort(messages);
		return StringUtils.join(messages, ", ");
	}

	public static String formatDate(Date date) {
		if (date == null) {
			return BLANK;
		}
		return DateUtils.formatDate(date);
	}

	public static String formatDateTime(Date date) {
		if (date == null) {
			return BLANK;
		}
		return new SimpleDateFormat(DateUtils.DATE_TIME_FORMAT).format(date);
	}

	public static int getIndex(String linePosition) {
		return Integer.valueOf(linePosition.replaceAll("(\\d*).*", "$1")) - 1;
	}

	public static String formatAsCode(String text) {
		return "<pre>" + text.trim().replace("\r\n", "\n") + "</pre>";
	}

}
