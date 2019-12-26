package kz.ais.eshop.repositories;

import kz.ais.eshop.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findAllByUserIdAndDeletedAtIsNull(Long userId);
}
