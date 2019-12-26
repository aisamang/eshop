package kz.ais.eshop.services.impl;

import kz.ais.eshop.models.CategoryThree;
import kz.ais.eshop.repositories.CategoryFourRepository;
import kz.ais.eshop.repositories.CategoryThreeRepository;
import kz.ais.eshop.services.CategoryThreeService;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManagerFactory;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryThreeServiceImpl implements CategoryThreeService {

    private CategoryThreeRepository categoryThreeRepository;
    private SessionFactory hibernateFactory;

    @Autowired
    public CategoryThreeServiceImpl(EntityManagerFactory factory,
                                     CategoryThreeRepository categoryThreeRepository) {
        if (factory.unwrap(SessionFactory.class) == null) {
            throw new NullPointerException("factory is not a hibernate factory");
        }
        this.hibernateFactory = factory.unwrap(SessionFactory.class);
        this.categoryThreeRepository = categoryThreeRepository;
    }

    @Override
    public List<CategoryThree> getAll() {
        Session session = hibernateFactory.openSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<CategoryThree> criteriaQuery = criteriaBuilder.createQuery(CategoryThree.class);
        Root<CategoryThree> root = criteriaQuery.from(CategoryThree.class);
        Predicate predicate = criteriaBuilder.isNull(root.get("deletedAt"));
        criteriaQuery.where(predicate);
        criteriaQuery.orderBy(criteriaBuilder.desc(root.get("id")));
        List<CategoryThree> categoryThree = session.createQuery(criteriaQuery).list();
        session.close();
        return categoryThree;
    }

    @Override
    public CategoryThree getById(Long id) {
        Optional<CategoryThree> categoryThreeOptional = categoryThreeRepository.findById(id);

        if (categoryThreeOptional.isPresent()){
            return categoryThreeOptional.get();
        } else {
            return null;
        }
    }

    @Override
    public boolean insert(CategoryThree categoryThree) {
        if (categoryThree == null || categoryThree.getId() != null) {
            return false;
        } else {
            categoryThreeRepository.save(categoryThree);
            return true;
        }
    }

    @Override
    public boolean update(CategoryThree categoryThree) {
        if (categoryThree == null || categoryThree.getId() == null) {
            return false;
        } else {
            categoryThreeRepository.save(categoryThree);
            return true;
        }
    }

    @Override
    public boolean delete(CategoryThree categoryThree) {
        if (categoryThree == null || categoryThree.getId() == null){
            return false;
        } else {
            categoryThree.setDeletedAt(new Date());
            categoryThreeRepository.save(categoryThree);
            return true;
        }
    }

    @Override
    public boolean realDelete(CategoryThree categoryThree) {
        if (categoryThree == null || categoryThree.getId() == null){
            return false;
        } else {
            categoryThreeRepository.delete(categoryThree);
            return true;
        }
    }
}
