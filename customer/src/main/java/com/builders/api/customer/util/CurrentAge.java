package com.builders.api.customer.util;

import java.time.LocalDate;

public class CurrentAge {

	public static int age(final LocalDate birthDate) {

    	LocalDate date = LocalDate.now();
        int age = date.getYear() - birthDate.getYear();

        return verifyAge(date, birthDate) ? age : age - 1;
    }

    private static boolean verifyAge(final LocalDate date, final LocalDate birthDate) {
        if (date.getMonth().getValue() > birthDate.getMonth().getValue()) {
            return Boolean.TRUE;
        } else if (date.getMonth().getValue() == birthDate.getMonth().getValue()
            && birthDate.getDayOfMonth() <= date.getDayOfMonth()) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }
}
