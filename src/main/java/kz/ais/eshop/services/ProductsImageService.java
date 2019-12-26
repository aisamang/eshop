package kz.ais.eshop.services;

import kz.ais.eshop.models.ProductsImage;

public interface ProductsImageService {

    ProductsImage getById(Long id);

    ProductsImage getByProductId(Long imageId);

    boolean add(ProductsImage product);

    boolean update(ProductsImage product, String oldImage);

    boolean realDelete(ProductsImage product);

    boolean delete(ProductsImage products);
}
