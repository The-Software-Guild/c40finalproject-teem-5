package tsg.team5.ecommerce.entity;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

public class Purchase {
    private int purchaseId;
    private LocalDate purchaseDate;
    private String currency;
    private Exchange exchange;
    private Customer customer;
    private List<Item> items;

    public int getPurchaseId() {
        return purchaseId;
    }

    public void setPurchaseId(int purchaseId) {
        this.purchaseId = purchaseId;
    }

    public LocalDate getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(LocalDate purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
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

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Purchase purchase = (Purchase) o;
        return purchaseId == purchase.purchaseId && purchaseDate.equals(purchase.purchaseDate) && currency.equals(purchase.currency) && exchange.equals(purchase.exchange) && customer.equals(purchase.customer) && items.equals(purchase.items);
    }

    @Override
    public int hashCode() {
        return Objects.hash(purchaseId, purchaseDate, currency, exchange, customer, items);
    }
}
