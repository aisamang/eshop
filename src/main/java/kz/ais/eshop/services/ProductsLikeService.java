package kz.ais.eshop.services;

import kz.ais.eshop.models.Product;
import kz.ais.eshop.models.ProductsLike;

import java.util.List;

public interface ProductsLikeService {

    List<ProductsLike> getAll();

    ProductsLike getById(Long id);

    boolean insert(ProductsLike productsLike);

    boolean update(ProductsLike productsLike);

    boolean delete(ProductsLike productsLike);

    boolean realDelete(ProductsLike productsLike);

    ProductsLike getProductLikeByUserId(Long id);
}
