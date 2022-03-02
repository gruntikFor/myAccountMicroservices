package ru.test.account.service;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;
import ru.test.account.exception.SumNotExistException;
import ru.test.account.model.BalanceRequest;
import ru.test.account.model.PersonAccount;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
public class AccountService {

    private static final String SELECT_ACCOUNT
            = "select id, person_id, account_num, balance from person_account where person_id = :personId for update";

    private static final String SELECT_BALANCE_REQUEST
            = "select * from balance_request bl where bl.person_id = :personId for update";

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public AccountService(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void createBalanceRequest(UUID requestId, UUID personId, BigDecimal requiredSum) throws SumNotExistException {
        Optional<BigDecimal> exitSum = getPersonAccount(personId).stream().map(PersonAccount::getBalance).reduce(BigDecimal::add);
        if (exitSum.isEmpty() || exitSum.get().compareTo(requiredSum) < 0) {
            throw new SumNotExistException(personId, requiredSum);
        }

        Optional<BigDecimal> blockedSum = getBalanceRequest(personId).stream().map(BalanceRequest::getBalance).reduce(BigDecimal::add);
        //exitSum - blockedSum
        if (blockedSum.isPresent() && exitSum.get().add(blockedSum.get().negate()).compareTo(requiredSum) < 0) {
            throw new SumNotExistException(personId, requiredSum);
        }

    }

    public List<PersonAccount> getPersonAccount(UUID personId) {
        return jdbcTemplate.query(SELECT_ACCOUNT, Map.of("personId", personId),
                new BeanPropertyRowMapper<>(PersonAccount.class));
    }

    public List<BalanceRequest> getBalanceRequest(UUID personId) {
        return jdbcTemplate.query(SELECT_BALANCE_REQUEST, Map.of("personId", personId),
                new BeanPropertyRowMapper<>(BalanceRequest.class));
    }
}
