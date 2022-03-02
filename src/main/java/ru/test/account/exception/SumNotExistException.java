package ru.test.account.exception;

import java.math.BigDecimal;
import java.util.UUID;

public class SumNotExistException extends Exception {

    public SumNotExistException(UUID personId, BigDecimal sum) {
        System.out.println("Person with id: " + personId + " doesn't have " + sum.longValue());
    }
}
