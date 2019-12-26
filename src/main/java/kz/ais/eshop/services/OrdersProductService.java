package kz.ais.eshop.services;

import kz.ais.eshop.models.OrdersProduct;

import java.util.List;

public interface OrdersProductService {

    List<OrdersProduct> getAll();
    OrdersProduct getById(Long id);
    List<OrdersProduct> getAllByCartId(Long cartId);
    boolean insert(OrdersProduct ordersProduct);
    boolean update(OrdersProduct ordersProduct);
    boolean delete(OrdersProduct ordersProduct);
    boolean realDelete(OrdersProduct ordersProduct);
}
