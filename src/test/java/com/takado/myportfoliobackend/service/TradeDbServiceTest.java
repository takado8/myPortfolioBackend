package com.takado.myportfoliobackend.service;

import com.takado.myportfoliobackend.domain.Ticker;
import com.takado.myportfoliobackend.domain.Trade;
import com.takado.myportfoliobackend.domain.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class TradeDbServiceTest {
    @Autowired
    TradeDbService dbService;
    @Autowired
    UserDbService userDbService;

    @Test
    void getAllTrades() {
    }

    @Test
    void getAllTradesByUserId() {
    }

    @Test
    void saveTrade() {
        //given
        Ticker ticker = new Ticker(1L, "ETH", "ethereum",
                Collections.emptyList(), Collections.emptyList());
        User user = new User(1L, "mail@mm.com", "asdfg", "baba",
                Collections.emptyList(), Collections.emptyList());
        Trade trade = new Trade(1L, "100", "200", Trade.Type.ASK,
                LocalDateTime.now(), ticker, user);
        //when
        var savedTrade = dbService.saveTrade(trade);
        //then
        var allTrades = dbService.getAllTrades();
        assertTrue(allTrades.contains(savedTrade));
        //cleanup
        dbService.deleteTrade(savedTrade.getId());
        userDbService.deleteUser(userDbService.getUserByEmail(user.getEmail()).getId());
    }
}