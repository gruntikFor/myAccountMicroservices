package ru.test.account.model.command;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CheckAccount {

    UUID requestId;
    UUID personId;
    BigDecimal sum;

}
