package kz.ais.eshop.repositories;

import kz.ais.eshop.models.CategoryOne;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryOneRepository extends JpaRepository<CategoryOne, Long> {
}
