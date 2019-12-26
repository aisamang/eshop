package kz.ais.eshop.services;

import kz.ais.eshop.models.CategoryThree;

import java.util.List;

public interface CategoryThreeService {

    List<CategoryThree> getAll();

    CategoryThree getById(Long id);

    boolean insert(CategoryThree categoryThree);

    boolean update(CategoryThree categoryThree);

    boolean delete(CategoryThree categoryThree);

    boolean realDelete(CategoryThree categoryThree);
}
