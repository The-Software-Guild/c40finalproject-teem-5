package tsg.team5.ecommerce.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import tsg.team5.ecommerce.entity.Exchange;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ExchangeDaoDBTest {

    @Autowired
    ExchangeDao exchangeDao;

    @BeforeEach
    void setUp() {
        List<Exchange> exchanges = exchangeDao.getAllExchanges();
        for(Exchange exchange: exchanges){
            exchangeDao.deleteExchangeById(exchange.getExchangeId());
        }
    }

    @Test
    void testAddGetExchange() {
        Exchange exchange = new Exchange();
        exchange.setUsd(new BigDecimal("1.1234"));
        exchange.setCad(new BigDecimal("1.1234"));
        exchange.setEur(new BigDecimal("1.1234"));
        exchange.setGbp(new BigDecimal("1.1234"));
        exchange.setJpy(new BigDecimal("1.1234"));
        exchange = exchangeDao.addExchange(exchange);

        Exchange fromDao = exchangeDao.getExchangeById(exchange.getExchangeId());
        assertEquals(exchange, fromDao);
    }

    @Test
    void testAllExchanges() {
        Exchange exchange = new Exchange();
        exchange.setUsd(new BigDecimal("1.1234"));
        exchange.setCad(new BigDecimal("1.1234"));
        exchange.setEur(new BigDecimal("1.1234"));
        exchange.setGbp(new BigDecimal("1.1234"));
        exchange.setJpy(new BigDecimal("1.1234"));
        exchange = exchangeDao.addExchange(exchange);

        Exchange exchange2 = new Exchange();
        exchange2.setUsd(new BigDecimal("1.1234"));
        exchange2.setCad(new BigDecimal("1.1234"));
        exchange2.setEur(new BigDecimal("1.1234"));
        exchange2.setGbp(new BigDecimal("1.1234"));
        exchange2.setJpy(new BigDecimal("1.1234"));
        exchange2 = exchangeDao.addExchange(exchange2);

        List<Exchange> exchanges = exchangeDao.getAllExchanges();

        assertEquals(2, exchanges.size());
        assertTrue(exchanges.contains(exchange));
        assertTrue(exchanges.contains(exchange2));
    }

    @Test
    void testUpdateExchange() {
        Exchange exchange = new Exchange();
        exchange.setUsd(new BigDecimal("1.1234"));
        exchange.setCad(new BigDecimal("1.1234"));
        exchange.setEur(new BigDecimal("1.1234"));
        exchange.setGbp(new BigDecimal("1.1234"));
        exchange.setJpy(new BigDecimal("1.1234"));
        exchange = exchangeDao.addExchange(exchange);

        Exchange fromDao = exchangeDao.getExchangeById(exchange.getExchangeId());
        assertEquals(exchange, fromDao);

        exchange.setUsd(new BigDecimal("1.4321"));
        exchangeDao.updateExchange(exchange);
        assertNotEquals(exchange, fromDao);

        fromDao = exchangeDao.getExchangeById(exchange.getExchangeId());
        assertEquals(exchange, fromDao);
    }

    @Test
    void testDeleteAddress() {
        Exchange exchange = new Exchange();
        exchange.setUsd(new BigDecimal("1.1234"));
        exchange.setCad(new BigDecimal("1.1234"));
        exchange.setEur(new BigDecimal("1.1234"));
        exchange.setGbp(new BigDecimal("1.1234"));
        exchange.setJpy(new BigDecimal("1.1234"));
        exchange = exchangeDao.addExchange(exchange);
        Exchange fromDao = exchangeDao.getExchangeById(exchange.getExchangeId());
        assertNotNull(exchange);

        exchangeDao.deleteExchangeById(exchange.getExchangeId());
        fromDao = exchangeDao.getExchangeById(exchange.getExchangeId());
        assertNull(fromDao);
    }
}