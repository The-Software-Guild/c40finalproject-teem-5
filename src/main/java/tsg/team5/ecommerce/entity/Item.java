package tsg.team5.ecommerce.entity;

import java.util.Objects;

public class Item {

    private int itemId;
    private String itemName;
    private String category;
    private Double price;

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getCategory() { return category; }

    public void setCategory(String category) { this.category = category; }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return itemId == item.itemId && itemName.equals(item.itemName) && category.equals(item.category) && price.equals(item.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(itemId, itemName, category, price);
    }
}