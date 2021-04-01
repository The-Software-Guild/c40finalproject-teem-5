package tsg.team5.ecommerce.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import tsg.team5.ecommerce.entity.Customer;
import tsg.team5.ecommerce.entity.Exchange;
import tsg.team5.ecommerce.entity.Item;
import tsg.team5.ecommerce.entity.Purchase;
import tsg.team5.ecommerce.dao.ExchangeDao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

@Repository
public class PurchaseDaoDB implements PurchaseDao{

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    ExchangeDao exchangeDao;

    @Autowired
    CustomerDao customerDao;

    @Autowired
    ItemDao itemDao;

    @Override
    public Purchase getPurchaseById(int purchaseId) {
        final String getPurchaseById_sql= "select * from Purchase where purchaseId=?;";
        return jdbcTemplate.queryForObject(getPurchaseById_sql, new PurchaseMapper(),purchaseId);
    }

    @Override
    public List<Purchase> getAllPurchases() {
        final String getAllPurchases_sql="select * from Purchase;";
        List<Purchase> purchases =jdbcTemplate.query(getAllPurchases_sql, new PurchaseMapper());
        associateCustomerExchangeitems(purchases);
        return purchases;
    }

    private void associateCustomerExchangeitems(List<Purchase> purchases) {
        for (Purchase prc: purchases) {
            prc.setCustomer(getCustomerForPurchase(prc.getPurchaseId()));
            prc.setExchange(getExchangeForPurchase(prc.getPurchaseId()));
            prc.setPurchasedItems(getItemsForPurchase(prc.getPurchaseId()));
        }
    }

    private Exchange getExchangeForPurchase(int purchaseId) {
        final String getExchangeForPurchase_sql = "select exr.* FROM ExchangeRate exr "
                + "join purchase p on exr.exchangeId = p.exchangeId where p.exchangeId = ?";
       // return jdbcTemplate.queryForObject(getExchangeForPurchase_sql, new ExchangeDao., id);
        return null;
    }

    private Customer getCustomerForPurchase(int purchaseId) {
        return null;
    }

    private List<Item> getItemsForPurchase(int purchaseId) {
        return null;
    }


    @Override
    public List<Purchase> getPurchaseRangeDate(LocalDate from, LocalDate to) {
        final String getPurchaseRangeDate_sql= "select * from Purchase where purchaseDate between between ? and ?;";
        return jdbcTemplate.query(getPurchaseRangeDate_sql, new PurchaseMapper(),from,to);
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
