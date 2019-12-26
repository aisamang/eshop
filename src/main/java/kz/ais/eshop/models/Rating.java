package kz.ais.eshop.models;

import kz.ais.eshop.models.audit.AuditModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Entity
@Table(name = "ratings")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Rating extends AuditModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false, nullable = false)
    private Long id;

    @OneToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_rating_product"))
    private Product product;

    @OneToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_rating_user"))
    private User user;

    @Max(value = 5)
    @Min(value = 1)
    private int value;
}
