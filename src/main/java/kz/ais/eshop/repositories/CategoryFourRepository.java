package kz.ais.eshop.repositories;

import kz.ais.eshop.models.CategoryFour;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryFourRepository extends JpaRepository<CategoryFour, Long> {
}
