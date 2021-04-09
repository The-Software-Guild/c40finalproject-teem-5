package tsg.team5.ecommerce.service;

import tsg.team5.ecommerce.entity.Item;
import tsg.team5.ecommerce.entity.Purchase;

import java.math.BigDecimal;
import java.math.RoundingMode;

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

        int index = 0;
        for(Item item : purchase.getItems()){
            invoice += evaluateTotalPriceForQuantity(item.getPrice(), purchase.getQuantities().get(index));
            index++;
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
                double tempd = evaluatePriceForRate(invoice, purchase.getExchange().getJpy());
                BigDecimal temp = new BigDecimal(Double.toString(tempd));
                temp = temp.setScale(0, RoundingMode.HALF_UP);
                invoice = temp.doubleValue();
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
