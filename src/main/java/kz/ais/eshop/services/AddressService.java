package kz.ais.eshop.services;

import kz.ais.eshop.models.Address;

import java.util.List;

public interface AddressService {

    List<Address> getAll();
    List<Address> getAllByDelivery(Long id);
    Address getById(Long id);
    boolean insert(Address address);
    boolean update(Address address);
    boolean delete(Address address);
    boolean realDelete(Address address);
}
