package kz.ais.eshop.models;

import kz.ais.eshop.models.audit.AuditModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "products_characteristics")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductsCharacteristic extends AuditModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false, nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_products_characteristic_product"))
    @NotNull(message = "name is required")
    private Product product;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_products_characteristic_characteristic"))
    private Characteristic characteristic;

    @NotNull(message = "value is required")
    private String value;

}
