package com.jumia.domain.order;

import com.jumia.domain.item.Item;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;
import javax.persistence.*;

/**
 * @author rafael.ferrari
 */
@Entity(name = "PRODUCT_ORDER")
@Table(name = "PRODUCT_ORDER")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID", nullable = false)
    private Long id;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "ORDER_ID")
    private Set<Item> items;

    @Column(name = "CUSTOMER_NAME", nullable = false)
    private String customerName;

    @Column(name = "CUSTOMER_CONTACT", nullable = false)
    private String customerContact;

    @Column(name = "SHIPPING_ADDRESS", nullable = false)
    private String shippingAddress;

    @Column(name = "GRAND_TOTAL", nullable = false)
    private BigDecimal grandTotal;

    @Column(name = "PLACED_DATE", nullable = false)
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
