package tsg.team5.ecommerce.entity;

import java.math.BigDecimal;
import java.util.Objects;

public class Exchange {
    private int exchangeId;
    private BigDecimal cad;
    private BigDecimal eur;
    private BigDecimal gbp;
    private BigDecimal jpy;
    private BigDecimal cny;

    public int getExchangeId() {
        return exchangeId;
    }
    public void setExchangeId(int exchangeId) {
        this.exchangeId = exchangeId;
    }

    public BigDecimal getCny() {
        return cny;
    }
    public void setCny(BigDecimal cny) {
        this.cny = cny;
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
        return exchangeId == exchange.exchangeId && cny.equals(exchange.cny) && cad.equals(exchange.cad) && eur.equals(exchange.eur) && gbp.equals(exchange.gbp) && jpy.equals(exchange.jpy);
    }

    @Override
    public int hashCode() {
        return Objects.hash(exchangeId, cny, cad, eur, gbp, jpy);
    }
}
