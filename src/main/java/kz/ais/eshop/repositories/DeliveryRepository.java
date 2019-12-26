package kz.ais.eshop.repositories;

import kz.ais.eshop.models.Delivery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeliveryRepository extends JpaRepository< Delivery, Long > {
}
