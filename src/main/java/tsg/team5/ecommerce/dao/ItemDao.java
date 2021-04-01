package tsg.team5.ecommerce.dao;

import tsg.team5.ecommerce.entity.Item;

import java.util.List;

public interface ItemDao {

    List<Item> getAllItems();
    Item getItemById(int Id);
    Item addItem(Item item);
    void updateItem(Item item);
    void deleteItem(int Id);

}
