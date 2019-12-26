package kz.ais.eshop.services;

import kz.ais.eshop.models.CategoryTwo;

import java.util.List;

public interface CategoryTwoService {

    List<CategoryTwo> getAll();

    CategoryTwo getById(Long id);

    boolean insert(CategoryTwo categoryTwo);

    boolean update(CategoryTwo categoryTwo);

    boolean delete(CategoryTwo categoryTwo);

    boolean realDelete(CategoryTwo categoryTwo);
}
