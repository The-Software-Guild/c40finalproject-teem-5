package tsg.team5.ecommerce.entity;

import java.math.BigDecimal;
import java.util.Objects;

public class Exchange {
    private int exchangeId;
    private BigDecimal USD;
    private BigDecimal CAD;
    private BigDecimal EUR;
    private BigDecimal GBP;
    private BigDecimal JPY;

    public int getExchangeId() {
        return exchangeId;
    }
    public void setExchangeId(int exchangeId) {
        this.exchangeId = exchangeId;
    }

    public BigDecimal getUSD() {
        return USD;
    }
    public void setUSD(BigDecimal USD) {
        this.USD = USD;
    }

    public BigDecimal getCAD() {
        return CAD;
    }
    public void setCAD(BigDecimal CAD) {
        this.CAD = CAD;
    }

    public BigDecimal getEUR() {
        return EUR;
    }
    public void setEUR(BigDecimal EUR) {
        this.EUR = EUR;
    }

    public BigDecimal getGBP() {
        return GBP;
    }
    public void setGBP(BigDecimal GBP) {
        this.GBP = GBP;
    }

    public BigDecimal getJPY() {
        return JPY;
    }
    public void setJPY(BigDecimal JPY) {
        this.JPY = JPY;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Exchange exchange = (Exchange) o;
        return exchangeId == exchange.exchangeId && USD.equals(exchange.USD) && CAD.equals(exchange.CAD) && EUR.equals(exchange.EUR) && GBP.equals(exchange.GBP) && JPY.equals(exchange.JPY);
    }

    @Override
    public int hashCode() {
        return Objects.hash(exchangeId, USD, CAD, EUR, GBP, JPY);
    }
}
