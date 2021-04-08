package tsg.team5.ecommerce.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ResponseBody;
import tsg.team5.ecommerce.entity.*;
import tsg.team5.ecommerce.service.MoneyManip;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class PurchaseDaoDB implements PurchaseDao{

    @Autowired
    JdbcTemplate jdbc;

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
            prc.setItems(getItemsForPurchase(prc.getPurchaseId()));
            prc.setQuantities(getQuantitiesForPurchase(prc.getPurchaseId()));
        }
    }

    @Override
    @ResponseBody
    public Map<Integer,Double> getTotalCostForAllPurchases(List<Purchase> purchases) {
        Map<Integer,Double> totalCost = new HashMap<>();
        for (Purchase purchase : purchases) {
            totalCost.put(purchase.getPurchaseId(),MoneyManip.calculateTotalInvoice(purchase));
        }
        return totalCost;
    }

    @Override
    public Purchase getPurchaseById(int purchaseId) {
        try{
            final String GET_PURCHASE_BY_ID= "select * from purchase where purchaseId = ?";
            Purchase purchase = jdbc.queryForObject(GET_PURCHASE_BY_ID, new PurchaseMapper(), purchaseId);
            purchase.setCustomer(getCustomerForPurchase(purchaseId));
            purchase.setExchange(getExchangeForPurchase(purchaseId));
            purchase.setItems(getItemsForPurchase(purchaseId));
            purchase.setQuantities(getQuantitiesForPurchase(purchaseId));
            return purchase;

        } catch (DataAccessException ex){
            return null;
        }
    }

    private Exchange getExchangeForPurchase(int purchaseId) {
        final String GET_EXCHANGE_FOR_PURCHASE = "select exr.* FROM exchangeRate exr "
                + "join purchase p on exr.exchangeId = p.exchangeId where p.purchaseId = ?";
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

    private List<Integer> getQuantitiesForPurchase(int purchaseId) {
        final String SELECT_QTY_FOR_PURCHASE = "SELECT quantity FROM item_purchase WHERE purchaseId = ?";
        return jdbc.query(SELECT_QTY_FOR_PURCHASE, new QuantityMapper(), purchaseId);
    }

    private List<Item> getItemsForPurchase(int purchaseId) {
        final String GET_ITEMS_FOR_PURCHASE = "select it.* FROM item it "
                + "join item_purchase ip on ip.itemId = it.itemId where ip.purchaseId= ?";
        return jdbc.query(GET_ITEMS_FOR_PURCHASE, new ItemDaoDB.ItemMapper(), purchaseId);
    }

    @Override
    public List<Purchase> getPurchasesRangeDate(LocalDate from, LocalDate to) {
        final String getPurchaseRangeDate_sql= "select * from purchase where purchaseDate between ? and ?;";
        List<Purchase> purchases =  jdbc.query(getPurchaseRangeDate_sql, new PurchaseMapper(),from,to);
        associateCustomerExchangeItems(purchases);
        return purchases;
    }

    @Override
    public List<Purchase> getPurchasesForCustomer(int customerId) {
        final String getCustomerForPurchase_sql = "select p.* FROM purchase p "
                + "join customer cu on cu.customerId = p.customerId where p.customerId = ?";
        List<Purchase> purchases = jdbc.query(getCustomerForPurchase_sql, new PurchaseMapper(), customerId);
        associateCustomerExchangeItems(purchases);
        return purchases;
    }

    @Override
    public List<Purchase> getPurchasesByDate(LocalDate date) {
        final String getPurchaseByDate_sql= "select * from purchase where purchaseDate = ?";
        List<Purchase> purchases =  jdbc.query(getPurchaseByDate_sql, new PurchaseMapper(), date);
        associateCustomerExchangeItems(purchases);
        return purchases;
    }

    @Override
    public List<Purchase> getPurchasesByCurrency(String curr) {
        final String getPurchasesByCurrency_sql= "select * from purchase where currency = ?";
        List<Purchase> purchases =  jdbc.query(getPurchasesByCurrency_sql, new PurchaseMapper(), curr);
        associateCustomerExchangeItems(purchases);
        return purchases;
    }

    @Override
    @Transactional
    public Purchase addPurchase(Purchase purchase) {
        final String addPurchase_sql = "insert into purchase (purchaseDate, currency, exchangeId, customerId)"+
                "value (?,?,?,?)";
        jdbc.update(addPurchase_sql, purchase.getPurchaseDate(), purchase.getCurrency(),
                         purchase.getExchange().getExchangeId(),
                         purchase.getCustomer().getCustomerId());

        int newId=jdbc.queryForObject("select LAST_INSERT_ID()", Integer.class);
        purchase.setPurchaseId(newId);
        insertItemsForPurchase(purchase);

        return purchase;
    }

    private void insertItemsForPurchase(Purchase purchase) {
        final String insertItemsForPurchase_sql = "insert into item_purchase(itemId, purchaseId, quantity) VALUES(?,?,?)";
        int index = 0;
        for(Item item : purchase.getItems()) {
            jdbc.update(insertItemsForPurchase_sql, item.getItemId(), purchase.getPurchaseId(), purchase.getQuantities().get(index));
            index++;
        }
    }

    @Override
    @Transactional
    public void updatePurchase(Purchase purchase) {
        final String sql = "UPDATE purchase SET purchaseID = ?, " +
                "purchaseDate = ?, " +
                "currency = ?, " +
                "exchangeId = ?, " +
                "customerId = ? " +
                "WHERE purchaseID = ?";

        jdbc.update(sql,
                purchase.getPurchaseId(),
                purchase.getPurchaseDate(),
                purchase.getCurrency(),
                purchase.getExchange().getExchangeId(),
                purchase.getCustomer().getCustomerId(),
                purchase.getPurchaseId());

        // updating the bridge table
        final String DELETE_ITEM_PURCHASE = "DELETE FROM item_purchase WHERE purchaseId = ?";
        jdbc.update(DELETE_ITEM_PURCHASE, purchase.getPurchaseId());
        insertItemsForPurchase(purchase);
    }

    @Override
    public void deletePurchase(int id) {
        final String DELETE_ITEM_PURCHASE = "DELETE FROM item_purchase WHERE purchaseId = ?";
        jdbc.update(DELETE_ITEM_PURCHASE, id);

        final String DELETE_PURCHASE = "DELETE FROM purchase WHERE purchaseId = ?";
        jdbc.update(DELETE_PURCHASE, id);
    }

    public final static class PurchaseMapper implements RowMapper<Purchase>
    {
        @Override
        public Purchase mapRow (ResultSet rs, int index) throws SQLException{
            Purchase purchase = new Purchase();
            purchase.setPurchaseId(rs.getInt("purchaseId"));
            purchase.setPurchaseDate(rs.getDate("purchaseDate").toLocalDate());
            purchase.setCurrency(rs.getString("currency"));
            return purchase;
        }
    }

    public final static class QuantityMapper implements RowMapper<Integer>
    {
        @Override
        public Integer mapRow (ResultSet rs, int index) throws SQLException{
            return rs.getInt(("quantity"));

        }
    }
}
