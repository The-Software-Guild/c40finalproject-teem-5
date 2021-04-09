package tsg.team5.ecommerce.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import tsg.team5.ecommerce.dao.AddressDao;
import tsg.team5.ecommerce.dao.CustomerDao;
import tsg.team5.ecommerce.entity.Address;
import tsg.team5.ecommerce.entity.Customer;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class CustomerController {

    @Autowired
    CustomerDao customerDao;

    @Autowired
    AddressDao addressDao;

    @GetMapping("customers")
    public String displayCustomers(Model model){
        List<Customer> customers = customerDao.getAllCustomers();
        List<Address> addresses = addressDao.getAllAddresses();

        model.addAttribute("customers", customers);
        model.addAttribute("addresses", addresses);

        return "customers";
    }

    @PostMapping("addCustomer")
    public String addCustomer(Customer customer, HttpServletRequest request){
        String addressId = request.getParameter("addressId");
        customer.setAddress(addressDao.getAddressById(Integer.parseInt(addressId)));
        customerDao.addCustomer(customer);

        return "redirect:/customers";
    }

    @GetMapping("customerDetail")
    public String superhumanDetail(Integer id, Model model){
        Customer customer = customerDao.getCustomerById(id);
        model.addAttribute("customer", customer);
        return "customerDetail";
    }

    @GetMapping("editCustomer")
    public String editSuperhuman(Integer id, Model model){
        Customer customer = customerDao.getCustomerById(id);
        List<Address> addresses = addressDao.getAllAddresses();

        model.addAttribute("customer", customer);
        model.addAttribute("addresses", addresses);

        return "editCustomer";
    }

    @PostMapping("editCustomer")
    public String performEditSuperhuman(Customer customer, HttpServletRequest request){
        String addressId = request.getParameter("addressId");
        customer.setAddress(addressDao.getAddressById(Integer.parseInt(addressId)));
        customerDao.updateCustomer(customer);

        return "redirect:/customers";
    }
}
