package tsg.team5.ecommerce.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import tsg.team5.ecommerce.entity.Item;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class ItemDaoDB implements ItemDao{

    @Autowired
    JdbcTemplate jdbc;

    @Override
    public List<Item> getAllItems() {
        final String sql = "Select * From Item";
        return jdbc.query(sql, new ItemMapper());
    }

    @Override
    public Item getItemById(int Id) {
        try {
            final String sql = "Select * From Item Where itemId = ?";
            return jdbc.queryForObject(sql, new ItemMapper(), Id);
        }catch(Exception e){
            return null;
        }
    }

    @Override
    public Item addItem(Item item) {
        final String sql = "Insert into Item(itemId, itemName, category, price, quantity) Values(?, ?, ?, ?, ?)";
        jdbc.update(sql, item.getItemId(), item.getItemName(), item.getCategory(), item.getPrice(), item.getQuantity());

        //ID does not need to be updated since every item is assigned an ID by API
        return item;
    }

    @Override
    public void updateItem(Item item) {
        final String sql = "Update Item Set itemId = ?, itemName = ?, category = ?, price = ?, quantity = ? Where itemId = ?";
        jdbc.update(sql, item.getItemId(), item.getItemName(), item.getCategory(), item.getPrice(), item.getQuantity(), item.getItemId());
    }

    @Override
    public void deleteItem(int Id) {
        final String sql = "Delete From Item Where itemId = ?";
        jdbc.update(sql, Id);
    }

    public static final class ItemMapper implements RowMapper<Item> {

        @Override
        public Item mapRow(ResultSet rs, int index) throws SQLException {
            Item item = new Item();
            item.setItemId(rs.getInt("itemId"));
            item.setItemName(rs.getString("itemName"));
            item.setCategory(rs.getString("category"));
            item.setPrice(rs.getDouble("price"));
            item.setQuantity(rs.getInt("quantity"));
            return item;
        }
    }
}
