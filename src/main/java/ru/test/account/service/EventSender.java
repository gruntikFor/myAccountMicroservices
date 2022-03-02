package ru.test.account.service;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.UUID;

@Component
public class EventSender {

    public void sendRequestConfirmed(UUID requestId, BigDecimal sum) {
        //todo
    }

    public void sendRequestRejected(UUID requestId, BigDecimal sum) {
        //todo
    }

    public void sendRequestChanged(UUID requestId, UUID personId, BigDecimal sum) {
        //todo
    }


}
