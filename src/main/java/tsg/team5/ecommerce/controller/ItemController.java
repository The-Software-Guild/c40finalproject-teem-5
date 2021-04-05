package tsg.team5.ecommerce.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import tsg.team5.ecommerce.dao.ItemDao;
import tsg.team5.ecommerce.entity.Item;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class ItemController {

    @Autowired
    ItemDao itemDao;

    @GetMapping("items")
    public String displayItems(Model model){

        List<Item> items = itemDao.getAllItems();
        model.addAttribute("items", items);
        //this adds item so that it can be accessed from the html/jsx
        return "items";

    }

    @PostMapping("addItem")
    public String addItem(HttpServletRequest request){

        String id = request.getParameter("itemID");
        String name = request.getParameter("itemName");
        String price = request.getParameter("price");

        Item item = new Item();
        item.setItemId(Integer.parseInt(id));
        item.setItemName(name);
        item.setPrice(Double.parseDouble(price));

        return "redirect:/items";

    }

    @GetMapping("deleteItem")
    public String deleteItem(HttpServletRequest request){
        int id = Integer.parseInt(request.getParameter("itemID"));
        //itemID correspond the name that will be given in the html/jsx
        Item item = itemDao.getItemById(id);

        return "redirect:/items";
    }

    @GetMapping("editItem")
    public String editItem(HttpServletRequest request, Model model){

        int id = Integer.parseInt(request.getParameter("itemid"));
        Item item = itemDao.getItemById(id);

        model.addAttribute("Item", item);

        return "editItem";
    }

    @PostMapping("editItem")
    public String performEditItem(HttpServletRequest request){

        int id = Integer.parseInt(request.getParameter("itemid"));
        Item item = itemDao.getItemById(id);
        item.setItemId(Integer.parseInt(request.getParameter("itemid")));
        item.setItemName(request.getParameter("itemName"));
        item.setPrice(Double.parseDouble(request.getParameter("price")));

        return "redirect:/items";
    }

}
