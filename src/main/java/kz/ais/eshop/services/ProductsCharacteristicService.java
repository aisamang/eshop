package kz.ais.eshop.services;

import kz.ais.eshop.models.ProductsCharacteristic;

import java.util.List;

public interface ProductsCharacteristicService {

    List<ProductsCharacteristic> getAll();

    ProductsCharacteristic getById(Long id);

    List<ProductsCharacteristic> getByProduct(Long productId);

    boolean insert(ProductsCharacteristic productsCharacteristic);

    boolean update(ProductsCharacteristic productsCharacteristic);

    boolean delete(ProductsCharacteristic productsCharacteristic);

    boolean realDelete(ProductsCharacteristic productsCharacteristic);
}
