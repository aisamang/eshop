package kz.ais.eshop.repositories;

import kz.ais.eshop.models.ProductsLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductsLikeRepository extends JpaRepository<ProductsLike, Long> {

    Optional<ProductsLike> findProductsLikeByUserIdAndDeletedAtIsNull(Long userId);

}
