package kz.ais.eshop.services;

import kz.ais.eshop.models.Product;

import java.util.List;

public interface ProductService {

    List<Product> getAll();
    List<Product> findAllByCategoryOneAndDeletedAtIsNull(Long categoryId);
    List<Product> findByCategoryTwoAndDeletedAtIsNull(Long categoryId);
    List<Product> findByCategoryThreeAndDeletedAtIsNull(Long categoryId);
    List<Product> findByCategoryFourAndDeletedAtIsNull(Long categoryId);
    List<Product> orderedByMinPrice();
    List<Product> orderedByMaxPrice();
    Product getById(Long id);
    boolean insert(Product product);
    boolean update(Product product);
    boolean delete(Product product);
    boolean realDelete(Product product);

}
