package tsg.team5.ecommerce.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import tsg.team5.ecommerce.entity.Exchange;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class ExchangeDaoDB implements ExchangeDao{
    @Autowired
    JdbcTemplate jdbc;

    @Override
    public Exchange getExchangeById(int exchangeId) {
        try{
            final String GET_EXCHANGE_BY_ID = "SELECT * FROM exchangeRate WHERE exchangeId = ?";
            return jdbc.queryForObject(GET_EXCHANGE_BY_ID, new ExchangeMapper(), exchangeId);
        } catch (DataAccessException ex) {
            return null;
        }
    }

    @Override
    public List<Exchange> getAllExchanges() {
        final String GET_ALL_EXCHANGES = "SELECT * FROM exchangeRate";
        return jdbc.query(GET_ALL_EXCHANGES, new ExchangeMapper());
    }

    @Override
    public Exchange addExchange(Exchange exchange){
        final String ADD_EXCHANGE = "INSERT INTO exchangeRate " +
                "(CAD, EUR, GBP, JPY, CNY) VALUES " +
                "(?, ?, ?, ?, ?)";
        jdbc.update(ADD_EXCHANGE,
                exchange.getCad(),
                exchange.getEur(),
                exchange.getGbp(),
                exchange.getJpy(),
                exchange.getCny());

        int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID();", Integer.class);
        exchange.setExchangeId(newId);

        return exchange;
    }

    @Override
    public void updateExchange(Exchange exchange) {
        final String UPDATE_EXCHANGE =
                "UPDATE exchangeRate SET " +
                "cad = ?, " +
                "eur = ?, " +
                "gbp = ?, " +
                "jpy = ?, " +
                "cny = ? " +
                "WHERE exchangeId = ?";
        jdbc.update(UPDATE_EXCHANGE,
                exchange.getCad(),
                exchange.getEur(),
                exchange.getGbp(),
                exchange.getJpy(),
                exchange.getCny(),
                exchange.getExchangeId());
    }

    @Override
    public void deleteExchangeById(int exchangeId) {
        final String DELETE_EXCHANGE_BY_ID = "DELETE FROM exchangeRate WHERE exchangeId = ?";
        jdbc.update(DELETE_EXCHANGE_BY_ID, exchangeId);
    }

    public static final class ExchangeMapper implements RowMapper<Exchange> {
        @Override
        public Exchange mapRow(ResultSet rs, int index) throws SQLException {
            Exchange exchange = new Exchange();
            exchange.setExchangeId(rs.getInt("exchangeId"));
            exchange.setCad(rs.getBigDecimal("cad"));
            exchange.setEur(rs.getBigDecimal("eur"));
            exchange.setGbp(rs.getBigDecimal("gbp"));
            exchange.setJpy(rs.getBigDecimal("jpy"));
            exchange.setCny(rs.getBigDecimal("cny"));
            return exchange;
        }
    }
}
