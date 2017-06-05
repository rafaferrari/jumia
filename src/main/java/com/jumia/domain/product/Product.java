package com.jumia.domain.product;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import javax.persistence.*;

/**
 * @author rafael.ferrari
 */
@Entity(name = "PRODUCT")
@Table(name = "PRODUCT")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID", nullable = false)
    private Long id;

    @Column(name = "NAME", nullable = false)
    private String name;

    @Column(name = "CATEGORY", nullable = false)
    private String category;

    @Column(name = "WEIGHT", nullable = false)
    private String weight;

    @Column(name = "PRICE", nullable = false)
    private BigDecimal price;

    @Column(name = "CREATION_DATE", nullable = false)
    private LocalDateTime creationDate;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(final String category) {
        this.category = category;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(final String weight) {
        this.weight = weight;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(final BigDecimal price) {
        this.price = price;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(final LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

}
