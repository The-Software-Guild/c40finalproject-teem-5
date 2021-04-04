package tsg.team5.ecommerce.dao;

import tsg.team5.ecommerce.entity.Purchase;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface PurchaseDao {
    Purchase getPurchaseById(int purchaseId);   //return a purchase object by id
    List<Purchase> getAllPurchases();   //return list of all purchases
    List<Purchase> getPurchasesRangeDate(LocalDate from, LocalDate to);  //return all purchases in date range
    List<Purchase> getPurchasesForCustomer(int customerId);  //return all the purchases made by a particular customer
    List<Purchase> getPurchasesByDate(LocalDate date);  //return all purchases made in a specific date
    List<Purchase> getPurchasesByCurrency(String  curr);    //return all the purchases made with a specific currency
    Purchase addPurchase(Purchase purchase);    //add a new purchase
    void updatePurchase(Purchase purchase);    //update a purchase
    void deletePurchase(int id);
}
