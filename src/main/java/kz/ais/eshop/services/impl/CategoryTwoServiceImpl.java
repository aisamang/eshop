package kz.ais.eshop.services.impl;

import kz.ais.eshop.models.CategoryTwo;
import kz.ais.eshop.repositories.CategoryTwoRepository;
import kz.ais.eshop.services.CategoryTwoService;
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
public class CategoryTwoServiceImpl implements CategoryTwoService {

    private CategoryTwoRepository categoryTwoRepository;
    private SessionFactory hibernateFactory;

    @Autowired
    public CategoryTwoServiceImpl(EntityManagerFactory factory,
                              CategoryTwoRepository categoryTwoRepository) {
        if (factory.unwrap(SessionFactory.class) == null) {
            throw new NullPointerException("factory is not a hibernate factory");
        }
        this.hibernateFactory = factory.unwrap(SessionFactory.class);
        this.categoryTwoRepository = categoryTwoRepository;
    }

    @Override
    public List<CategoryTwo> getAll() {
        Session session = hibernateFactory.openSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<CategoryTwo> criteriaQuery = criteriaBuilder.createQuery(CategoryTwo.class);
        Root<CategoryTwo> root = criteriaQuery.from(CategoryTwo.class);
        Predicate predicate = criteriaBuilder.isNull(root.get("deletedAt"));
        criteriaQuery.where(predicate);
        criteriaQuery.orderBy(criteriaBuilder.desc(root.get("id")));
        List<CategoryTwo> categoryTwo = session.createQuery(criteriaQuery).list();
        session.close();
        return categoryTwo;
    }

    @Override
    public CategoryTwo getById(Long id) {
        Optional<CategoryTwo> categoryTwoOptional = categoryTwoRepository.findById(id);

        if (categoryTwoOptional.isPresent()){
            return categoryTwoOptional.get();
        } else {
            return null;
        }
    }

    @Override
    public boolean insert(CategoryTwo categoryTwo) {
        if (categoryTwo == null || categoryTwo.getId() != null) {
            return false;
        } else {
            categoryTwoRepository.save(categoryTwo);
            return true;
        }
    }

    @Override
    public boolean update(CategoryTwo categoryTwo) {
        if (categoryTwo == null || categoryTwo.getId() == null) {
            return false;
        } else {
            categoryTwoRepository.save(categoryTwo);
            return true;
        }
    }

    @Override
    public boolean delete(CategoryTwo categoryTwo) {
        if (categoryTwo == null || categoryTwo.getId() == null){
            return false;
        } else {
            categoryTwo.setDeletedAt(new Date());
            categoryTwoRepository.save(categoryTwo);
            return true;
        }
    }

    @Override
    public boolean realDelete(CategoryTwo categoryTwo) {
        if (categoryTwo == null || categoryTwo.getId() == null){
            return false;
        } else {
            categoryTwoRepository.delete(categoryTwo);
            return true;
        }
    }
}
