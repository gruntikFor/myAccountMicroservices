package ru.test.account.model;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PersonAccount {

    UUID Id;
    UUID personId;
    String accountNum;
    BigDecimal balance;

}
