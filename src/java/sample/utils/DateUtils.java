/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sample.utils;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

/**
 *
 * @author phuon
 */
public class DateUtils {

    public static long now() {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date();
        return date.getTime();
    }

    public static String toHTMLDate(java.sql.Date inDate) {
        LocalDate localDate = inDate.toLocalDate();
        String year = Integer.toString(localDate.getYear());
        String month = Integer.toString(localDate.getMonthValue());
        String day = Integer.toString(localDate.getDayOfMonth());

        return padLeftZeros(year, 4) + "-" + padLeftZeros(month, 2) + "-" + padLeftZeros(day, 2);
    }

    private static String padLeftZeros(String inputString, int length) {
        if (inputString.length() >= length) {
            return inputString;
        }
        StringBuilder sb = new StringBuilder();
        while (sb.length() < length - inputString.length()) {
            sb.append('0');
        }
        sb.append(inputString);

        return sb.toString();
    }
}
