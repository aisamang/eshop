package kz.ais.eshop.repositories;

import kz.ais.eshop.models.Characteristic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CharacteristicRepository extends JpaRepository<Characteristic, Long> {

    Optional<Characteristic> findByNameAndDeletedAtIsNull(String name);
}
