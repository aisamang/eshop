package kz.ais.eshop.repositories;

import kz.ais.eshop.models.CategoryTwo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryTwoRepository extends JpaRepository<CategoryTwo, Long> {
}
