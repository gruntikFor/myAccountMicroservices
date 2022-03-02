package ru.test.account.service;

import org.springframework.stereotype.Service;
import ru.test.account.model.command.CheckAccount;

@Service
public class AccountCommandHandler {

    private final EventSender eventSender;
    private final AccountService accountService;

    public AccountCommandHandler(EventSender eventSender, AccountService accountService) {
        this.eventSender = eventSender;
        this.accountService = accountService;
    }

    public void checkAccount(CheckAccount command) {
        try {
            accountService.createBalanceRequest(command.getRequestId(), command.getPersonId(), command.getSum());
            eventSender.sendRequestConfirmed(command.getRequestId(), command.getSum());
        } catch (Exception e) {
            e.printStackTrace();
            eventSender.sendRequestRejected(command.getRequestId(), command.getSum());
        }
    }
}
