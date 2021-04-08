package tsg.team5.ecommerce.entity;
import java.util.Objects;

public class ReactService {
    private double totalCost;
    private String currency;

    public double getTotalCost() {
        return totalCost;
    }
    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }

    public String getCurrency() {
        return currency;
    }
    public void setCurrency(String currency) {
        this.currency = currency;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReactService reactservice = (ReactService) o;
        return totalCost == reactservice.totalCost && currency.equals(this.currency);
    }

    @Override
    public int hashCode() {
        return Objects.hash(totalCost,currency);
    }
}