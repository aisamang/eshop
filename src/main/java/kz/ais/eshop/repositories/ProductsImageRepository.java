package kz.ais.eshop.repositories;

import kz.ais.eshop.models.ProductsImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductsImageRepository extends JpaRepository<ProductsImage, Long> {

    Optional<ProductsImage> findImageByProductIdAndDeletedAtIsNull(Long productId);

    List<ProductsImage> findAllByDeletedAtIsNull();

}
