package kz.ais.eshop.repositories;

import kz.ais.eshop.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findAllByCategoryOneIdAndDeletedAtIsNull(Long categoryId);
    List<Product> findByCategoryTwoIdAndDeletedAtIsNull(Long categoryId);
    List<Product> findByCategoryThreeIdAndDeletedAtIsNull(Long categoryId);
    List<Product> findByCategoryFourIdAndDeletedAtIsNull(Long categoryId);

//    @Query(value = "select p from Product p where p.deletedAt is null order by p.price asc")
    List<Product> findByOrderByPriceAsc();
    List<Product> findByOrderByPriceDesc();

}
