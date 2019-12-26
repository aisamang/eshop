package kz.ais.eshop.repositories;

import kz.ais.eshop.models.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AddressRepository extends JpaRepository< Address, Long > {

    public Optional<Address> findByCity(String name);

    List<Address> findAllByDeliveryIdAndDeletedAtIsNull(Long id);
}
