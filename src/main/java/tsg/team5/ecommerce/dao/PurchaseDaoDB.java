package tsg.team5.ecommerce.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import tsg.team5.ecommerce.entity.Purchase;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

@Repository
public class PurchaseDaoDB implements PurchaseDao{

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public Purchase getPurchaseById(int purchaseId) {
        final String getPurchaseById_sql= "select * from Purchase where purchaseId=?;";
        return jdbcTemplate.queryForObject(getPurchaseById_sql, new PurchaseMapper(),purchaseId);
    }

    @Override
    public List<Purchase> getAllPurchases() {
        final String getAllPurchases="select * from Purchase;";
        //List<Purchase>
        return null;
    }

    @Override
    public List<Purchase> getPurchaseRangeDate(LocalDate from, LocalDate to) {
        return null;
    }

    @Override
    public List<Purchase> getPurchaseForCustomer(int customerId) {
        return null;
    }

    @Override
    public List<Purchase> getPurchaseByDate(LocalDate date) {
        return null;
    }

    @Override
    public List<Purchase> getPurchasesByCurrency(String curr) {
        return null;
    }

    @Override
    public Purchase addPurchase(Purchase purchase) {
        return null;
    }

    @Override
    public void UpdatePurchase(Purchase purchase) {

    }

    public final static class PurchaseMapper implements RowMapper<Purchase>
    {
        @Override
        public Purchase mapRow (ResultSet rs, int index) throws SQLException{
            Purchase purchase = new Purchase();
            purchase.setPurchaseId(rs.getInt("purchaseId"));
            purchase.setPurchaseDate(rs.getDate("purchaseDate").toLocalDate());
            purchase.setQuantity(rs.getInt("quantity"));
            return purchase;
        }
    }

}
