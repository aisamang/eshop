package kz.ais.eshop.services;

import kz.ais.eshop.models.CategoryFour;

import java.util.List;

public interface CategoryFourService {

    List<CategoryFour> getAll();

    CategoryFour getById(Long id);

    boolean insert(CategoryFour categoryFour);

    boolean update(CategoryFour categoryFour);

    boolean delete(CategoryFour categoryFour);

    boolean realDelete(CategoryFour categoryFour);
}
