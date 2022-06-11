package com.takado.myportfoliobackend.service;

import com.takado.myportfoliobackend.domain.Ticker;
import com.takado.myportfoliobackend.domain.Trade;
import com.takado.myportfoliobackend.domain.User;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class TradeDbServiceTest {
    @Autowired
    TradeDbService dbService;
    @Autowired
    UserDbService userDbService;
    @Autowired
    TickerDbService tickerDbService;

    final static String user1Email = "mail@ccc123.com";
    final static String user2Email = "mail@mmvd245.com";
    static Long ticker1Id;
    static Long ticker2Id;

    @BeforeAll
    static void beforeAll(@Autowired UserDbService userDbService, @Autowired TickerDbService tickerDbService) {
        User user1 = userDbService.getUserByEmail(user1Email);
        if (user1 == null) {
            userDbService.saveUser(new User(1L, user1Email, "agaga", "baba",
                    null, null));
        }
        User user2 = userDbService.getUserByEmail(user2Email);
        if (user2 == null) {
            userDbService.saveUser(new User(1L, user2Email, "bhbhb", "alala",
                    null, null));
        }
        Random random = new Random();
        var randomNb = random.nextInt(10_000_000);
        Ticker ticker1 = tickerDbService.saveTicker(
                new Ticker(3L, "AAAtest" + randomNb, "aaatest" + randomNb, null, null));
        Ticker ticker2 = tickerDbService.saveTicker(
                new Ticker(3L, "BBBtest" + randomNb, "BBBtest" + randomNb, null, null));
        ticker1Id = ticker1.getId();
        ticker2Id = ticker2.getId();
    }

    @AfterAll
    static void AfterAll(@Autowired UserDbService userDbService, @Autowired TickerDbService tickerDbService) {
        userDbService.deleteUser(userDbService.getUserByEmail(user1Email).getId());
        userDbService.deleteUser(userDbService.getUserByEmail(user2Email).getId());
        tickerDbService.deleteTicker(ticker1Id);
        tickerDbService.deleteTicker(ticker2Id);
    }

    @Test
    void getAllTradesByUserIdAndTickerId() {
        //given
        Ticker ticker1 = tickerDbService.getTicker(ticker1Id);
        Ticker ticker2 = tickerDbService.getTicker(ticker2Id);

        User user1 = userDbService.getUserByEmail(user1Email);
        User user2 = userDbService.getUserByEmail(user2Email);

        Trade trade1 = new Trade(1L, "100", "200", Trade.Type.ASK,
                LocalDateTime.now(), ticker1, user1);
        Trade trade2 = new Trade(2L, "100", "200", Trade.Type.ASK,
                LocalDateTime.now(), ticker2, user2);
        Trade trade3 = new Trade(3L, "100", "200", Trade.Type.ASK,
                LocalDateTime.now(), ticker1, user2);
        Trade trade4 = new Trade(4L, "100", "200", Trade.Type.ASK,
                LocalDateTime.now(), ticker2, user1);
        Trade trade5 = new Trade(5L, "150", "200", Trade.Type.ASK,
                LocalDateTime.now(), ticker1, user1);

        trade1 = dbService.saveTrade(trade1);
        trade2 = dbService.saveTrade(trade2);
        trade3 = dbService.saveTrade(trade3);
        trade4 = dbService.saveTrade(trade4);
        trade5 = dbService.saveTrade(trade5);
        //when
        var trades = dbService.getAllTradesByUserIdAndTickerId(user1.getId(), ticker1.getId());
        //then
        assertTrue(trades.contains(trade1));
        assertTrue(trades.contains(trade5));
        assertEquals(2, trades.size());

        //cleanup
        dbService.deleteTrade(trade1.getId());
        dbService.deleteTrade(trade2.getId());
        dbService.deleteTrade(trade3.getId());
        dbService.deleteTrade(trade4.getId());
        dbService.deleteTrade(trade5.getId());
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