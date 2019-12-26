package kz.ais.eshop.repositories;

import kz.ais.eshop.models.ProductsCharacteristic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductsCharacteristicRepository extends JpaRepository<ProductsCharacteristic, Long> {

    List<ProductsCharacteristic> findAllByProductIdAndDeletedAtIsNull(Long productId);
}
