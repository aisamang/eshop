package kz.ais.eshop.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "delivery")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Delivery {

    public final static Long DELIVERY_ID = 1L;
    public final static Long PICKUP_ID = 2L;

    public final static String DELIVERY_NAME = "DELIVERY";
    public final static String PICKUP_NAME = "PICKUP";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false, nullable = false)
    private Long id;
    private String name;
    private double deliverCost;

}
