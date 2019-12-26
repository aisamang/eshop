package kz.ais.eshop.services;

import kz.ais.eshop.models.Delivery;

import java.util.List;

public interface DeliveryService {

    List<Delivery> getAllTrashed();

    Delivery getById(Long id);

    boolean add(Delivery delivery);

    boolean update(Delivery delivery);

    boolean realDelete(Delivery delivery);
}
