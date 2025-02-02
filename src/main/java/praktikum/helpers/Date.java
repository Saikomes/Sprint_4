package praktikum.helpers;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Date {

    public static LocalDate parseStringToDate(String dateTimePattern, String dateString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(dateTimePattern);
        return LocalDate.parse(dateString, formatter);
    }
}
