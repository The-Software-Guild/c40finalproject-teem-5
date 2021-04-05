package tsg.team5.ecommerce.service;

import java.math.BigDecimal;

// This class is used to calculate the real price of items with the given exchange rate
public class MoneyManip {

    public static double evaluateRealPrice(double price, BigDecimal rate){
        return rate.multiply(new BigDecimal(price)).doubleValue();
    }

    public static double evaluateRealTotalPrice(double price, int quantity){
        return price * quantity;
    }
}
