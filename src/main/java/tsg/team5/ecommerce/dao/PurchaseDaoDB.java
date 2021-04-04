package tsg.team5.ecommerce.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import tsg.team5.ecommerce.entity.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

@Repository
public class PurchaseDaoDB implements PurchaseDao{

    @Autowired
    JdbcTemplate jdbc;

    @Override
    public Purchase getPurchaseById(int purchaseId) {
        try{
            final String GET_PURCHASE_BY_ID= "select * from purchase where purchaseId = ?";
            Purchase purchase = jdbc.queryForObject(GET_PURCHASE_BY_ID, new PurchaseMapper(), purchaseId);
            purchase.setCustomer(getCustomerForPurchase(purchaseId));
            purchase.setExchange(getExchangeForPurchase(purchaseId));
            purchase.setPurchasedItems(getItemsForPurchase(purchaseId));
            return purchase;

        } catch (DataAccessException ex){
            return null;
        }
    }

    @Override
    public List<Purchase> getAllPurchases() {
        final String GET_ALL_PURCHASES="select * from purchase";
        List<Purchase> purchases =jdbc.query(GET_ALL_PURCHASES, new PurchaseMapper());
        associateCustomerExchangeItems(purchases);
        return purchases;
    }

    private void associateCustomerExchangeItems(List<Purchase> purchases) {
        for (Purchase prc: purchases) {
            prc.setCustomer(getCustomerForPurchase(prc.getPurchaseId()));
            prc.setExchange(getExchangeForPurchase(prc.getPurchaseId()));
            prc.setPurchasedItems(getItemsForPurchase(prc.getPurchaseId()));
        }
    }

    private Exchange getExchangeForPurchase(int purchaseId) {
        final String GET_EXCHANGE_FOR_PURCHASE = "select exr.* FROM exchangeRate exr "
                + "join purchase p on exr.exchangeId = p.exchangeId where p.exchangeId = ?";
        return jdbc.queryForObject(GET_EXCHANGE_FOR_PURCHASE, new ExchangeDaoDB.ExchangeMapper(), purchaseId);
    }

    private Customer getCustomerForPurchase(int purchaseId) {
        final String GET_CUSTOMER_FOR_PURCHASE = "select cu.* FROM customer cu "
                + "join purchase p on cu.customerId = p.customerId where p.purchaseId = ?";
        Customer customer =  jdbc.queryForObject(GET_CUSTOMER_FOR_PURCHASE, new CustomerDaoDB.CustomerMapper(), purchaseId);

        // methods copied from CustomerDaoDB class
        customer.setAddress(getAddressForCustomer(customer.getCustomerId()));

        return customer;
    }

    // Gets the address object for the customer instead of having the address id
    private Address getAddressForCustomer(int id) {
        final String SELECT_ADDRESS_FOR_CUSTOMER = "SELECT a.* FROM address a JOIN customer c ON c.addressId = a.addressId WHERE c.customerId = ?";
        return jdbc.queryForObject(SELECT_ADDRESS_FOR_CUSTOMER, new AddressDaoDB.AddressMapper(), id);
    }

    private List<Item> getItemsForPurchase(int purchaseId) {
        final String GET_ITEMS_FOR_PURCHASE = "select it.* FROM item it "
                + "join item_purchase ip on ip.purchaseId = it.purchaseId where ip.purchaseId= ?";
        return jdbc.query(GET_ITEMS_FOR_PURCHASE, new ItemDaoDB.ItemMapper(), purchaseId);
    }

    @Override
    public List<Purchase> getPurchasesRangeDate(LocalDate from, LocalDate to) {
        final String getPurchaseRangeDate_sql= "select * from purchase where purchaseDate between ? and ?;";
        return jdbc.query(getPurchaseRangeDate_sql, new PurchaseMapper(),from,to);
    }

    @Override
    public List<Purchase> getPurchasesForCustomer(int customerId) {
        final String getCustomerForPurchase_sql = "select p.* FROM purchase p "
                + "join customer cu on cu.customerId = p.customerId where p.customerId = ?";
        return jdbc.query(getCustomerForPurchase_sql, new PurchaseMapper(), customerId);
    }

    @Override
    public List<Purchase> getPurchasesByDate(LocalDate date) {
        final String getPurchaseByDate_sql= "select * from purchase where purchaseDate = ?";
        return jdbc.query(getPurchaseByDate_sql, new PurchaseMapper(), date);
    }

    @Override
    public List<Purchase> getPurchasesByCurrency(String curr) {
        final String getPurchasesByCurrency_sql= "select * from Purchase where baseCurrency = ?";
        return jdbc.query(getPurchasesByCurrency_sql, new PurchaseMapper(), curr);
    }

    @Override
    @Transactional
    public Purchase addPurchase(Purchase purchase) {
        final String addPurchase_sql = "insert into purchase (purchaseDate,quantity,baseCurrency,exchangeId,customerId)"+
                "value (?,?,?,?,?)";
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
            jdbc.update(insertItemsForPurchase_sql, purchase.getPurchaseId(), item.getItemId());
        }
    }

    @Override
    @Transactional
    public void UpdatePurchase(Purchase purchase) {
        final String sql = "UPDATE purchase SET purchaseID = ?, " +
                "purchaseDate = ?, " +
                "quantity = ?, " +
                "exchangeId = ?, " +
                "customerId = ? " +
                "WHERE purchaseID = ?";

        jdbc.update(sql,
                purchase.getPurchaseId(),
                purchase.getPurchaseDate(),
                purchase.getQuantity(),
                purchase.getExchange().getExchangeId(),
                purchase.getCustomer().getCustomerId(),
                purchase.getPurchaseId());

        // updating the bridge table
        final String DELETE_ITEM_PURCHASE = "DELETE FROM item_purchase WHERE purchaseId = ?";
        jdbc.update(DELETE_ITEM_PURCHASE, purchase.getPurchaseId());
        insertItemsForPurchase(purchase);
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
