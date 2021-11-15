package chomiuk.jacek.ui.menu;

import chomiuk.jacek.ui.exception.UserDataServiceException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

public final class UserDataService {
    private UserDataService() {}

    private static final Scanner sc = new Scanner(System.in);

    public static String getString(String message) {
        System.out.println(message);
        return sc.nextLine();
    }

    public static int getInt(String message) {
        System.out.println(message);

        String value = sc.nextLine();
        if (!value.matches("\\d+")) {
            throw new UserDataServiceException("value is not a number");
        }
        return Integer.parseInt(value);
    }

    public static Long getLong(String message) {
        System.out.println(message);

        String value = sc.nextLine();
        if (!value.matches("\\d+")) {
            throw new UserDataServiceException("value is not a number");
        }
        return Long.parseLong(value);
    }

    public static BigDecimal getBigDecimal(String message) {
        System.out.println(message);
        String value = sc.nextLine();
        if (!value.matches("(\\d+\\.)?\\d+")){
            new UserDataServiceException("value is not a number");
        }
        return new BigDecimal(value);
    }

    public static LocalDate getLocalDate(String message){
        System.out.println(message + " (date format: dd.MM.yyyy)");
        String value = sc.nextLine();
        System.out.println(value);
        if (!value.matches("\\d{2}\\.\\d{2}\\.\\d{4}")){
            new UserDataServiceException("date has wrong format");
        }
        String[] digits = value.split("\\.");
        return LocalDate.of(Integer.parseInt(digits[2]),Integer.parseInt(digits[1]),Integer.parseInt(digits[0]));
    }

    public static LocalDateTime getLocalDateTime(String message){
        System.out.println(message + " (date format: dd.MM.yyyy hh:mm)");
        String value = sc.nextLine();
        //System.out.println(value);
        if (!value.matches("\\d{2}\\.\\d{2}\\.\\d{4}\\s\\d{2}:\\d{2}")){
            new UserDataServiceException("date has wrong format");
        }
        String[] digits = value.split("[\\:\\. ]");
        return LocalDateTime.of(Integer.parseInt(digits[2]),Integer.parseInt(digits[1]),Integer.parseInt(digits[0]),Integer.parseInt(digits[3]),Integer.parseInt(digits[4]));
    }
}
