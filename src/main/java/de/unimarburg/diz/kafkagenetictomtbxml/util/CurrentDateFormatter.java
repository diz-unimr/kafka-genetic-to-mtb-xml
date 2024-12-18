package de.unimarburg.diz.kafkagenetictomtbxml.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class CurrentDateFormatter {
    public static String formatCurrentDate() {

        LocalDate currentDate = LocalDate.now();

        // Definiere das gewünschte Format
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        // Formatiere das aktuelle Datum
        return currentDate.format(formatter);
    }

}
