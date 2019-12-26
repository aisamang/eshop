package kz.ais.eshop.models;

import kz.ais.eshop.models.audit.AuditModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "products_likes")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductsLike extends AuditModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false, nullable = false)
    private Long id;

    @OneToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_products_like_user"))
    private User user;

    @OneToMany
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_products_like_product"))
    private List<Product> product = new ArrayList<>();
}
