package kz.ais.eshop.models;

import kz.ais.eshop.models.audit.AuditModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "orders")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order extends AuditModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false, nullable = false)
    private Long id;

    @ManyToOne(cascade=CascadeType.MERGE)
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_order_user"))
    private User user;

    private String comment;

    @OneToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_order_status"))
    private Status statuses;

    @OneToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_order_cart"))
    private Cart cart;

    @OneToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_order_address"))
    @NotNull(message = "address is required")
    private Address address;
    private double overallPrice;

    public Order(User user, String comment, Status statuses, Cart cart, @NotNull(message = "address is required") Address address) {
        this.user = user;
        this.comment = comment;
        this.statuses = statuses;
        this.cart = cart;
        this.address = address;
    }
}
