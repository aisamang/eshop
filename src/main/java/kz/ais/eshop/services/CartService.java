package kz.ais.eshop.services;

import kz.ais.eshop.models.Cart;

import java.util.List;

public interface CartService {

    List<Cart> getAllWithTrashed();

    List<Cart> getAll();

    double getPrice(Long id);

    Cart getByUser(Long userId);

    Cart getById(Long id);

    boolean add(Cart cart);

    boolean update(Cart cart);

    boolean delete(Cart cart);

    boolean realDelete(Cart cart);
}
