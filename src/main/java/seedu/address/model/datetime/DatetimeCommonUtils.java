package seedu.address.model.datetime;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.Locale;
import java.util.Objects;

import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Contains utilities for other datetime-related classes, including parsers.
 */
public class DatetimeCommonUtils {

    public static final String DATE_INPUT_FORMAT = "yyyy-MM-dd";
    public static final DateTimeFormatter DATE_INPUT_FORMATTER = DateTimeFormatter.ofPattern(DATE_INPUT_FORMAT);
    public static final String DATE_FORMAT_REGEX = "\\d{4}-\\d{2}-\\d{2}";
    public static final String DATE_READABLE_FORMAT = "yyyy MMM dd";
    public static final DateTimeFormatter DATE_READABLE_FORMATTER = DateTimeFormatter.ofPattern(DATE_READABLE_FORMAT);
    public static final String DATE_MESSAGE_CONSTRAINTS =
            "Date should be in yyyy-MM-dd format, e.g. 2022-01-01";

    public static final String TIME_FORMAT = "HH:mm";
    public static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern(TIME_FORMAT);
    public static final String TIME_FORMAT_REGEX = "\\d{2}:\\d{2}";
    public static final String TIME_MESSAGE_CONSTRAINTS =
            "Time should be in HH:mm format, e.g. 08:00";

    public static final String DAY_FORMAT = "d";
    public static final String DAY_FORMAT_REGEX = "[1-7]";
    public static final String DAY_MESSAGE_CONSTRAINTS =
            "Day should only contain a number from 1 (Monday) to 7 (Sunday)";

    public static final String DATETIME_INPUT_FORMAT = DATE_INPUT_FORMAT + " " + TIME_FORMAT;
    public static final DateTimeFormatter DATETIME_INPUT_FORMATTER =
            DateTimeFormatter.ofPattern(DATETIME_INPUT_FORMAT);
    public static final String DATETIME_FORMAT_REGEX = DATE_FORMAT_REGEX + " " + TIME_FORMAT_REGEX;
    public static final String DATETIME_READABLE_FORMAT = DATE_READABLE_FORMAT + " " + TIME_FORMAT;
    public static final DateTimeFormatter DATETIME_READABLE_FORMATTER =
            DateTimeFormatter.ofPattern(DATETIME_READABLE_FORMAT);
    public static final String DATETIME_MESSAGE_CONSTRAINTS =
            "Datetime should be in yyyy-MM-dd HH:mm format, e.g. 2022-01-01 08:00";

    public static final String TIMESLOT_MESSAGE_CONSTRAINTS =
            "The start time should not be after the end time!";

    /**
     * Checks whether the start time and end time is logically valid
     * @param startTime Start time
     * @param endTime End time
     * @throws ParseException If the start time is after end time
     */
    public static void assertTimeRangeValid(LocalTime startTime, LocalTime endTime) throws ParseException {
        if (!endTime.isAfter(startTime)) {
            throw new ParseException(TIMESLOT_MESSAGE_CONSTRAINTS);
        }
    }

    /**
     * Converts a DayOfWeek to a readable form, e.g. Mon, Tue
     * @param day A DayOfWeek to convert
     * @return Human-readable day
     */
    public static String dayOfWeekToReadable(DayOfWeek day) {
        return day.getDisplayName(TextStyle.SHORT, Locale.getDefault());
    }

    /**
     * Parses a {@code String datetimeString} into a {@code Datetime}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code datetimeString} is invalid.
     */
    public static Datetime parseDatetime(String datetimeString) throws ParseException {
        Objects.requireNonNull(datetimeString);

        String trimmedDatetime = datetimeString.trim();

        if (!trimmedDatetime.matches(DATETIME_FORMAT_REGEX)) {
            throw new ParseException(DATETIME_MESSAGE_CONSTRAINTS);
        }

        LocalDateTime datetime = LocalDateTime.parse(trimmedDatetime, DATETIME_INPUT_FORMATTER);
        return new Datetime(datetime);
    }

    /**
     * Parses a {@code String timeRange} into a {@code DatetimeRange}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code timeRange} is invalid.
     */
    public static DatetimeRange parseDatetimeRange(String date, String timeRange) throws ParseException {
        Objects.requireNonNull(date);
        Objects.requireNonNull(timeRange);

        if (!date.matches(DATE_FORMAT_REGEX)) {
            throw new ParseException(DATE_MESSAGE_CONSTRAINTS);
        }

        String[] times = timeRange.trim().split("-");
        if (times.length != 2
                || !times[0].matches(TIME_FORMAT_REGEX)
                || !times[1].matches(TIME_FORMAT_REGEX)) {
            throw new ParseException(TIME_MESSAGE_CONSTRAINTS);
        }

        LocalTime startTime = LocalTime.parse(times[0], TIME_FORMATTER);
        LocalTime endTime = LocalTime.parse(times[1], TIME_FORMATTER);
        assertTimeRangeValid(startTime, endTime);

        return DatetimeRange.fromFormattedString(date, times[0], times[1]);
    }

    /**
     * Parses a {@code String timeslot} into a {@code WeeklyTimeslot}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code timeslot} is invalid.
     */
    public static WeeklyTimeslot parseWeeklyTimeslot(String dayString, String timeslot) throws ParseException {
        Objects.requireNonNull(dayString);
        Objects.requireNonNull(timeslot);

        if (!dayString.matches(DAY_FORMAT_REGEX)) {
            throw new ParseException(DAY_MESSAGE_CONSTRAINTS);
        }

        String[] times = timeslot.trim().split("-");
        if (times.length != 2
                || !times[0].matches(TIME_FORMAT_REGEX)
                || !times[1].matches(TIME_FORMAT_REGEX)) {
            throw new ParseException(TIMESLOT_MESSAGE_CONSTRAINTS);
        }

        LocalTime startTime = LocalTime.parse(times[0], TIME_FORMATTER);
        LocalTime endTime = LocalTime.parse(times[1], TIME_FORMATTER);
        assertTimeRangeValid(startTime, endTime);

        return WeeklyTimeslot.fromFormattedString(dayString, times[0], times[1]);
    }
}
