package com.jumia.domain.order;

import com.jumia.domain.item.Item;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * @author rafael.ferrari
 */
@Entity(name = "product_order")
@Table(name = "product_order")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Set<Item> items;

    @NotNull
    private String customerName;

    @NotNull
    private String customerContact;

    @NotNull
    private String shippingAddress;

    @NotNull
    private BigDecimal grandTotal;

    @NotNull
    private LocalDateTime placedDate;

    public Long getId() {
        return id;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(final String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerContact() {
        return customerContact;
    }

    public void setCustomerContact(final String customerContact) {
        this.customerContact = customerContact;
    }

    public String getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(final String shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public BigDecimal getGrandTotal() {
        return grandTotal;
    }

    public void setGrandTotal(final BigDecimal grandTotal) {
        this.grandTotal = grandTotal;
    }

    public Set<Item> getItems() {
        return items;
    }

    public void setItems(final Set<Item> items) {
        this.items = items;
    }

    public LocalDateTime getPlacedDate() {
        return placedDate;
    }

    public void setPlacedDate(final LocalDateTime placedDate) {
        this.placedDate = placedDate;
    }

}
