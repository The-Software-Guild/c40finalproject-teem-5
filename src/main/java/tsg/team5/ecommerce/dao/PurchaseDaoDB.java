package tsg.team5.ecommerce.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import tsg.team5.ecommerce.entity.Customer;
import tsg.team5.ecommerce.entity.Exchange;
import tsg.team5.ecommerce.entity.Item;
import tsg.team5.ecommerce.entity.Purchase;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

@Repository
public class PurchaseDaoDB implements PurchaseDao{

    @Autowired
    JdbcTemplate jdbc;

    @Autowired
    ExchangeDao exchangeDao;

    @Autowired
    CustomerDao customerDao;

    @Autowired
    ItemDao itemDao;

    @Override
    public Purchase getPurchaseById(int purchaseId) {
        final String getPurchaseById_sql= "select * from Purchase where purchaseId=?;";
        return jdbc.queryForObject(getPurchaseById_sql, new PurchaseMapper(),purchaseId);
    }

    @Override
    public List<Purchase> getAllPurchases() {
        final String getAllPurchases_sql="select * from Purchase;";
        List<Purchase> purchases =jdbc.query(getAllPurchases_sql, new PurchaseMapper());
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
       return jdbc.queryForObject(getExchangeForPurchase_sql, new ExchangeDaoDB.ExchangeMapper(), purchaseId);

    }

    private Customer getCustomerForPurchase(int purchaseId) {
        final String getCustomerForPurchase_sql = "select cu.* FROM customer cu "
                + "join purchase p on cu.customerId  = p.customerId  where p.purchaseId = ?";
        return jdbc.queryForObject(getCustomerForPurchase_sql, new CustomerDaoDB.CustomerMapper(), purchaseId);
    }

    private List<Item> getItemsForPurchase(int purchaseId) {
        final String getItemsForPurchase_sql = "select it.* FROM item_purchase ip "
                + "join purchase p on ip.purchaseId  = p.purchaseId join Item it"
                + " on it.itemId=ip.itemId where ip.purchaseId= ?";
        return jdbc.query(getItemsForPurchase_sql, new ItemDaoDB.ItemMapper(), purchaseId);
    }


    @Override
    public List<Purchase> getPurchaseRangeDate(LocalDate from, LocalDate to) {
        final String getPurchaseRangeDate_sql= "select * from Purchase where purchaseDate between between ? and ?;";
        return jdbc.query(getPurchaseRangeDate_sql, new PurchaseMapper(),from,to);
    }

    @Override
    public List<Purchase> getPurchaseForCustomer(int customerId) {
        final String getCustomerForPurchase_sql = "select p.* FROM customer cu "
                + "join purchase p on cu.customerId  = p.customerId  where p.customerId = ?";
        return jdbc.query(getCustomerForPurchase_sql, new PurchaseMapper(), customerId);
    }

    @Override
    public List<Purchase> getPurchaseByDate(LocalDate date) {
        final String getPurchaseByDate_sql= "select * from Purchase where purchaseDate=?;";
        return jdbc.query(getPurchaseByDate_sql, new PurchaseMapper(),date);
    }

    @Override
    public List<Purchase> getPurchasesByCurrency(String curr) {
        final String getPurchasesByCurrency_sql= "select * from Purchase where baseCurrency=?;";
        return jdbc.query(getPurchasesByCurrency_sql, new PurchaseMapper(),curr);
    }

    @Override
    public Purchase addPurchase(Purchase purchase) {
        final String addPurchase_sql = "insert into purchase (purchaseDate,quantity,baseCurrency,exchangeId,customerId)"+
                "value (?,?,?,?,?);";
        jdbc.update(addPurchase_sql, purchase.getPurchaseDate(),
                                     purchase.getQuantity(),
                                     purchase.getBaseCurrency(),
                                     purchase.getExchange().getExchangeId(),
                                     purchase.getCustomer().getCustomerId()
                );
        int newId=jdbc.queryForObject("select LAST_INSERT_ID()", Integer.class);
        purchase.setPurchaseId(newId);
        insertItemsForPurchase(purchase);
        return purchase;

    }

    private void insertItemsForPurchase(Purchase purchase) {
        final String insertItemsForPurchase_sql = "insert into item_purchase(itemId, purchaseId) VALUES(?,?)";
        for(Item item : purchase.getPurchasedItems()) {
            jdbc.update(insertItemsForPurchase_sql,
                        purchase.getPurchaseId(),
                        item.getItemId());
        }
    }

    @Override
    public void UpdatePurchase(Purchase purchase) {

        final String sql = "Update Purchase Set purchaseID = ?," +
                "purchaseDate = ?, " +
                "quantity = ?, " +
                "exchangeId = ?, " +
                "customerId = ? " +
                "Where purchaseID = ?";

        jdbc.update(sql,
                purchase.getPurchaseId(),
                purchase.getPurchaseDate(),
                purchase.getQuantity(),
                purchase.getExchange().getExchangeId(),
                purchase.getCustomer().getCustomerId(),
                purchase.getPurchaseId());

    }

    public final static class PurchaseMapper implements RowMapper<Purchase>
    {
        @Override
        public Purchase mapRow (ResultSet rs, int index) throws SQLException{
            Purchase purchase = new Purchase();
            purchase.setPurchaseId(rs.getInt("purchaseId"));
            purchase.setPurchaseDate(rs.getDate("purchaseDate").toLocalDate());
            purchase.setQuantity(rs.getInt("quantity"));
            purchase.setBaseCurrency(rs.getString("baseCurrency"));
            return purchase;
        }
    }

}
