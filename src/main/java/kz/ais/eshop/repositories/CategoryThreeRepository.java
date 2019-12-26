package kz.ais.eshop.repositories;

import kz.ais.eshop.models.CategoryThree;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryThreeRepository extends JpaRepository<CategoryThree, Long> {
}
