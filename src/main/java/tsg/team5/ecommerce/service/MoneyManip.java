package tsg.team5.ecommerce.service;

import tsg.team5.ecommerce.entity.Item;
import tsg.team5.ecommerce.entity.Purchase;

import java.math.BigDecimal;

// This class is used to calculate the real price of items with the given exchange rate
public class MoneyManip {

    public static double evaluatePriceForRate(double price, BigDecimal rate){
        return rate.multiply(new BigDecimal(price)).doubleValue();
    }

    public static double evaluateTotalPriceForQuantity(double price, int quantity){
        return price * quantity;
    }

    public static double calculateTotalInvoice(Purchase purchase){
        double invoice = 0.0;

        for(Item item : purchase.getItems()){
            invoice += evaluateTotalPriceForQuantity(item.getPrice(), item.getQuantity());
        }

        switch (purchase.getCurrency()){
            case "CAD":
                invoice = evaluatePriceForRate(invoice, purchase.getExchange().getCad());
                break;
            case "EUR":
                invoice = evaluatePriceForRate(invoice, purchase.getExchange().getEur());
                break;
            case "GBP":
                invoice = evaluatePriceForRate(invoice, purchase.getExchange().getGbp());
                break;
            case "JPY":
                invoice = evaluatePriceForRate(invoice, purchase.getExchange().getJpy());
                break;
            case "CNY":
                invoice = evaluatePriceForRate(invoice, purchase.getExchange().getCny());
                break;
            default:
                break;
        }

        return invoice;
    }
}
