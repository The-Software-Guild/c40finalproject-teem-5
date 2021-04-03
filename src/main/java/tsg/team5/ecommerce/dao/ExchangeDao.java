package tsg.team5.ecommerce.dao;

import tsg.team5.ecommerce.entity.Exchange;

import java.util.List;

public interface ExchangeDao {
    public Exchange getExchangeById(int exchangeId);
    public List<Exchange> getAllExchanges();
    public Exchange addExchange(Exchange exchange);
    public void updateExchange(Exchange exchange);
    public void deleteExchangeById(int exchangeId);
}
