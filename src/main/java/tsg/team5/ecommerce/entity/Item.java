package tsg.team5.ecommerce.entity;

import java.util.Objects;

public class Item {

    private int itemId;
    private String itemName;
    private String category;
    private double price;

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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return itemId == item.itemId && Double.compare(item.price, price) == 0 && itemName.equals(item.itemName) && category.equals(item.category);
    }

    @Override
    public int hashCode() {
        return Objects.hash(itemId, itemName, category, price);
    }
}