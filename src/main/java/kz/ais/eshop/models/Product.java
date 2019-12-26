package kz.ais.eshop.models;

import kz.ais.eshop.models.audit.AuditModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "products")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product extends AuditModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false, nullable = false)
    private Long id;

    @NotNull(message = "name is required")
    private String name;
    private String description;
    private String fullDescription;
    private String title;
    @NotNull(message = "vendor code is required")
    private int vendorCode;
    @NotNull(message = "price is required")
    private double price;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_product_category_one"))
    @NotNull
    private CategoryOne categoryOne;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_product_category_two"))
    private CategoryTwo categoryTwo;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_product_category_three"))
    private CategoryThree categoryThree;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_product_category_four"))
    private CategoryFour categoryFour;

    private Boolean isNew;
    private Boolean isHit;
}
