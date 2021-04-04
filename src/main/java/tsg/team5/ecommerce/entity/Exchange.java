package tsg.team5.ecommerce.entity;

import java.math.BigDecimal;
import java.util.Objects;

public class Exchange {
    private int exchangeId;
    private BigDecimal usd;
    private BigDecimal cad;
    private BigDecimal eur;
    private BigDecimal gbp;
    private BigDecimal jpy;

    public int getExchangeId() {
        return exchangeId;
    }
    public void setExchangeId(int exchangeId) {
        this.exchangeId = exchangeId;
    }

    public BigDecimal getUsd() {
        return usd;
    }
    public void setUsd(BigDecimal usd) {
        this.usd = usd;
    }

    public BigDecimal getCad() {
        return cad;
    }
    public void setCad(BigDecimal cad) {
        this.cad = cad;
    }

    public BigDecimal getEur() {
        return eur;
    }
    public void setEur(BigDecimal eur) {
        this.eur = eur;
    }

    public BigDecimal getGbp() {
        return gbp;
    }
    public void setGbp(BigDecimal gbp) {
        this.gbp = gbp;
    }

    public BigDecimal getJpy() {
        return jpy;
    }
    public void setJpy(BigDecimal jpy) {
        this.jpy = jpy;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Exchange exchange = (Exchange) o;
        return exchangeId == exchange.exchangeId && usd.equals(exchange.usd) && cad.equals(exchange.cad) && eur.equals(exchange.eur) && gbp.equals(exchange.gbp) && jpy.equals(exchange.jpy);
    }

    @Override
    public int hashCode() {
        return Objects.hash(exchangeId, usd, cad, eur, gbp, jpy);
    }
}
