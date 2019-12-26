package kz.ais.eshop.models;

import kz.ais.eshop.models.audit.AuditModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "categories_three")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryThree extends AuditModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false, nullable = false)
    private Long id;

    @NotNull(message = "name is required")
    private String name;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_category_three_category_two"))
    @NotNull(message = "category two is required")
    private CategoryTwo categoryTwo;
}
