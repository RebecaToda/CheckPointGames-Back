package com.checkpointgames.app.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "ORDERS")
public class Order {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @ManyToOne(optional = true)
    @JoinColumn(name = "ID_COSTUMER", referencedColumnName = "ID")
    private Users costumer;
    
    @NotNull
    @Column(name = "SALE_VALUE", precision = 15, scale = 3)
    private BigDecimal saleValue;
    
    @NotNull
    @Column(name = "ITENS_VALUE", precision = 15, scale = 3)
    private BigDecimal itensValue;
    
    @Column(name = "DISCOUNT", precision = 15, scale = 3)
    private BigDecimal discount;
    
    @Column(name = "ADDITION", precision = 15, scale = 3)
    private BigDecimal addition;
    
    @NotNull
    @Column(name = "DATE")
    private LocalDate date;
   
    @Column(name = "STATUS", columnDefinition = "integer default 0")
    @NotNull
    private Integer status = 0;

    @Column(name = "paymentLink")
    private String paymentLink;

    public String getPaymentLink() {
        return paymentLink;
    }

    public void setPaymentLink(String paymentLink) {
        this.paymentLink = paymentLink;
    }
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Users getCostumer() {
        return costumer;
    }

    public void setCostumer(Users costumer) {
        this.costumer = costumer;
    }

    public BigDecimal getSaleValue() {
        return saleValue;
    }

    public void setSaleValue(BigDecimal saleValue) {
        this.saleValue = saleValue;
    }

    public BigDecimal getItensValue() {
        return itensValue;
    }

    public void setItensValue(BigDecimal itensValue) {
        this.itensValue = itensValue;
    }

    public BigDecimal getDiscount() {
        return discount;
    }

    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }

    public BigDecimal getAddition() {
        return addition;
    }

    public void setAddition(BigDecimal addition) {
        this.addition = addition;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
    
    
}
    

