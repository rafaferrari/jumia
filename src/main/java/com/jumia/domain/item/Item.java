package com.jumia.domain.item;

import com.jumia.domain.order.Order;
import com.jumia.domain.product.Product;
import java.math.BigDecimal;
import javax.persistence.*;

/**
 * @author rafael.ferrari
 */
@Entity(name = "ITEM")
@Table(name = "ITEM")
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID", nullable = false)
    private Long id;

    @ManyToOne
    @PrimaryKeyJoinColumn(name = "ORDER_ID")
    private Order order;

    @OneToOne(fetch = FetchType.LAZY)
    @PrimaryKeyJoinColumn(name = "ID_PRODUCT")
    private Product product;

    @Column(name = "COST", nullable = false)
    private BigDecimal cost;

    @Column(name = "SHIPPING_FEE", nullable = false)
    private BigDecimal shippingFee;

    @Column(name = "TAX_AMOUNT", nullable = false)
    private BigDecimal taxAmount;

    public Long getId() {
        return id;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(final BigDecimal cost) {
        this.cost = cost;
    }

    public BigDecimal getShippingFee() {
        return shippingFee;
    }

    public void setShippingFee(final BigDecimal shippingFee) {
        this.shippingFee = shippingFee;
    }

    public BigDecimal getTaxAmount() {
        return taxAmount;
    }

    public void setTaxAmount(final BigDecimal taxAmount) {
        this.taxAmount = taxAmount;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(final Order order) {
        this.order = order;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(final Product product) {
        this.product = product;
    }

}
