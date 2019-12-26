package kz.ais.eshop.models;

import kz.ais.eshop.models.audit.AuditModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "orders_products")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrdersProduct extends AuditModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false, nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_orders_product_product"))
    private Product products;
    private int quantity;
    private double price;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_orders_product_cart"))
    private Cart cart;

    public OrdersProduct(Product products, int quantity, Cart cart) {
        this.products = products;
        this.quantity = quantity;
        this.cart = cart;
    }
}
