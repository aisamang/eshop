package kz.ais.eshop.services;

import kz.ais.eshop.models.CategoryOne;

import java.util.List;

public interface CategoryOneService {

    List<CategoryOne> getAll();

    CategoryOne getById(Long id);

    boolean insert(CategoryOne categoryOne);

    boolean update(CategoryOne categoryOne);

    boolean delete(CategoryOne categoryOne);

    boolean realDelete(CategoryOne categoryOne);


}
