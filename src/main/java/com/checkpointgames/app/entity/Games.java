package com.checkpointgames.app.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.math.RoundingMode;

@Entity
@Table(name = "GAMES")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Games {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank
    @Size(max = 255)
    @Column(name = "NAME")
    @JsonProperty("title")
    private String name;

    @NotBlank
    @Column(name = "DESCRIPTION", columnDefinition = "TEXT")
    private String description;

    @Column(name = "CATEGORY")
    private String category;

    @NotNull
    @Column(name = "INVENTORY")
    private Integer inventory;

    @NotNull
    @Column(name = "VALUE", precision = 15, scale = 3)
    @JsonProperty("price")
    private BigDecimal value;

    @Column(name = "LINK_IMAGE", columnDefinition = "TEXT")
    @JsonProperty("coverImage")
    private String linkImage;

    @Column(name = "DISCOUNT", columnDefinition = "integer default 0")
    private Integer discount = 0; // Inicializa com 0 se vier vazio

    @NotNull
    @Column(name = "STATUS", columnDefinition = "integer default 0")
    private Integer status = 0; // <--- A CORREÇÃO ESTÁ AQUI (Inicializa com 0)

    // --- MÉTODOS ESPECIAIS ---

    @JsonProperty("finalPrice")
    public BigDecimal getFinalPrice() {
        if (value == null) return BigDecimal.ZERO;
        if (discount == null || discount == 0) return value;

        BigDecimal discountFactor = BigDecimal.valueOf(100 - discount).divide(BigDecimal.valueOf(100));
        return value.multiply(discountFactor).setScale(2, RoundingMode.HALF_UP);
    }

    // --- GETTERS E SETTERS ---

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public Integer getInventory() { return inventory; }
    public void setInventory(Integer inventory) { this.inventory = inventory; }

    public BigDecimal getValue() { return value; }
    public void setValue(BigDecimal value) { this.value = value; }

    public String getLinkImage() { return linkImage; }
    public void setLinkImage(String linkImage) { this.linkImage = linkImage; }

    public Integer getDiscount() { return discount; }
    public void setDiscount(Integer discount) { this.discount = discount; }

    public Integer getStatus() { return status; }
    public void setStatus(Integer status) { this.status = status; }
}