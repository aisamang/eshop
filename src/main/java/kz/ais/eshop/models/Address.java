package kz.ais.eshop.models;

import kz.ais.eshop.models.audit.AuditModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "address")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Address extends AuditModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false, nullable = false)
    private Long id;

    @NotNull(message = "city is required")
    private String city;

    private float latitude;
    private float longitude;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_address_delivery"))
    private Delivery delivery;

    public Address(@NotNull(message = "city is required") String city, float latitude, float longitude) {
        this.city = city;
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
