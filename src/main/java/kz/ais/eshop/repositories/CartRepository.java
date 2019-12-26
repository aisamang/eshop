package kz.ais.eshop.repositories;

import kz.ais.eshop.models.Cart;
import kz.ais.eshop.models.OrdersProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository< Cart, Long > {

    Optional<Cart> findByUserIdAndDeletedAtIsNull(Long userId);

}
