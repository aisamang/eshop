package kz.ais.eshop.services;

import kz.ais.eshop.models.Order;

import java.util.List;

public interface OrderService {

    List<Order> getAllTrashed();

    List<Order> getAll();

    List<Order> getAllByUser(Long userId);

    Order getById(Long id);

    boolean add(Order order);

    boolean update(Order order);

    boolean delete(Order order);

    boolean realDelete(Order order);
}
