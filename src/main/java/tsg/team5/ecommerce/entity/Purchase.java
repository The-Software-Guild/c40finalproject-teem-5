package tsg.team5.ecommerce.entity;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

public class Purchase {
    private int purchaseId;
    private LocalDate purchaseDate;
    private int quantity;
    private Exchange exchange;
    private Customer customer;
    private List<Item> PurchasedItems;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Purchase purchase = (Purchase) o;
        return purchaseId == purchase.purchaseId && quantity == purchase.quantity && purchaseDate.equals(purchase.purchaseDate) && exchange.equals(purchase.exchange) && customer.equals(purchase.customer) && PurchasedItems.equals(purchase.PurchasedItems);
    }

    @Override
    public int hashCode() {
        return Objects.hash(purchaseId, purchaseDate, quantity, exchange, customer, PurchasedItems);
    }

    public LocalDate getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(LocalDate purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Exchange getExchange() {
        return exchange;
    }

    public void setExchange(Exchange exchange) {
        this.exchange = exchange;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public List<Item> getPurchasedItems() {
        return PurchasedItems;
    }

    public void setPurchasedItems(List<Item> purchasedItems) {
        PurchasedItems = purchasedItems;
    }

    public int getPurchaseId() {
        return purchaseId;
    }

    public void setPurchaseId(int purchaseId) {
        this.purchaseId = purchaseId;
    }
}
