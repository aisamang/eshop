package kz.ais.eshop.services.impl;

import kz.ais.eshop.models.CategoryOne;
import kz.ais.eshop.repositories.CategoryOneRepository;
import kz.ais.eshop.services.CategoryOneService;
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
public class CategoryOneServiceImpl implements CategoryOneService {

    private CategoryOneRepository categoryOneRepository;
    private SessionFactory hibernateFactory;

    @Autowired
    public CategoryOneServiceImpl(EntityManagerFactory factory,
                              CategoryOneRepository categoryOneRepository) {
        if (factory.unwrap(SessionFactory.class) == null) {
            throw new NullPointerException("factory is not a hibernate factory");
        }
        this.hibernateFactory = factory.unwrap(SessionFactory.class);
        this.categoryOneRepository = categoryOneRepository;
    }

    @Override
    public List<CategoryOne> getAll() {
        Session session = hibernateFactory.openSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<CategoryOne> criteriaQuery = criteriaBuilder.createQuery(CategoryOne.class);
        Root<CategoryOne> root = criteriaQuery.from(CategoryOne.class);
        Predicate predicate = criteriaBuilder.isNull(root.get("deletedAt"));
        criteriaQuery.where(predicate);
        criteriaQuery.orderBy(criteriaBuilder.desc(root.get("id")));
        List<CategoryOne> products = session.createQuery(criteriaQuery).list();
        session.close();
        return products;
    }

    @Override
    public CategoryOne getById(Long id) {
        Optional<CategoryOne> categoryOneOptional = categoryOneRepository.findById(id);

        if (categoryOneOptional.isPresent()){
            return categoryOneOptional.get();
        } else {
            return null;
        }
    }

    @Override
    public boolean insert(CategoryOne categoryOne) {
        if (categoryOne == null || categoryOne.getId() != null) {
            return false;
        } else {
            categoryOneRepository.save(categoryOne);
            return true;
        }
    }

    @Override
    public boolean update(CategoryOne categoryOne) {
        if (categoryOne == null || categoryOne.getId() == null) {
            return false;
        } else {
            categoryOneRepository.save(categoryOne);
            return true;
        }
    }

    @Override
    public boolean delete(CategoryOne categoryOne) {
        if (categoryOne == null || categoryOne.getId() == null){
            return false;
        } else {
            categoryOne.setDeletedAt(new Date());
            categoryOneRepository.save(categoryOne);
            return true;
        }
    }

    @Override
    public boolean realDelete(CategoryOne categoryOne) {
        if (categoryOne == null || categoryOne.getId() == null){
            return false;
        } else {
            categoryOneRepository.delete(categoryOne);
            return true;
        }
    }
}
