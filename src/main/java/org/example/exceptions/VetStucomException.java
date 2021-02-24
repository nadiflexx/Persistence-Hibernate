package org.example.exceptions;

import java.util.Arrays;
import java.util.List;

public class VetStucomException extends Exception {

    public static final int incorrectFormatDNI = 0;
    public static final int contentVoid = 1;
    public static final int wrongLogin = 2;
    public static final int expedientNotFound = 3;
    public static final int userNotFound = 4;

    private final int value;

    public VetStucomException(int value) {
        this.value = value;
    }

    private List<String> message = Arrays.asList(
            "Incorrect format of DNI. Please introduce 8 numbers and 1 letter",
            "The content you are requesting to show is not in the database. Create it first and try again.",
            "Error. Username or password not found. Try again.",
            "Expedient not found. Try again",
            "User not found. Try again"
    );

    @Override
    public String getMessage() {
        return message.get(value);
    }
}