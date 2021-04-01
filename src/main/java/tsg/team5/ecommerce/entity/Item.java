package tsg.team5.ecommerce.entity;

import java.util.Objects;

public class Item {

    private int itemid;

    private String itemName;

    private Double price;

    public int getItemid() {
        return itemid;
    }

    public void setItemid(int itemid) {
        this.itemid = itemid;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }



    @Override
    public boolean equals(Object o){
        Item item1 = (Item)o;
        if(item1.itemid == this.itemid && item1.itemName.equals(this.itemName) && item1.price == this.price){
            return true;
        }
        return false;
    }

    @Override
    public int hashCode(){
        return Objects.hash(itemid, itemName, price);
    }


}
