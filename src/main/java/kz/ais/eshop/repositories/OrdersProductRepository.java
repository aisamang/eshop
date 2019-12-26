package kz.ais.eshop.repositories;

import kz.ais.eshop.models.OrdersProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrdersProductRepository extends JpaRepository<OrdersProduct, Long> {

    List<OrdersProduct> findAllByCartIdAndDeletedAtIsNull(Long cartId);

}
