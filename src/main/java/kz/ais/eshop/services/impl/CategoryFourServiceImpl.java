package kz.ais.eshop.services.impl;

import kz.ais.eshop.models.CategoryFour;
import kz.ais.eshop.repositories.CategoryFourRepository;
import kz.ais.eshop.services.CategoryFourService;
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
public class CategoryFourServiceImpl implements CategoryFourService {

    private CategoryFourRepository categoryFourRepository;
    private SessionFactory hibernateFactory;

    @Autowired
    public CategoryFourServiceImpl(EntityManagerFactory factory,
                                    CategoryFourRepository categoryFourRepository) {
        if (factory.unwrap(SessionFactory.class) == null) {
            throw new NullPointerException("factory is not a hibernate factory");
        }
        this.hibernateFactory = factory.unwrap(SessionFactory.class);
        this.categoryFourRepository = categoryFourRepository;
    }

    @Override
    public List<CategoryFour> getAll() {
        Session session = hibernateFactory.openSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<CategoryFour> criteriaQuery = criteriaBuilder.createQuery(CategoryFour.class);
        Root<CategoryFour> root = criteriaQuery.from(CategoryFour.class);
        Predicate predicate = criteriaBuilder.isNull(root.get("deletedAt"));
        criteriaQuery.where(predicate);
        criteriaQuery.orderBy(criteriaBuilder.desc(root.get("id")));
        List<CategoryFour> categoryFour = session.createQuery(criteriaQuery).list();
        session.close();
        return categoryFour;
    }

    @Override
    public CategoryFour getById(Long id) {
        Optional<CategoryFour> categoryFourOptional = categoryFourRepository.findById(id);

        if (categoryFourOptional.isPresent()){
            return categoryFourOptional.get();
        } else {
            return null;
        }
    }

    @Override
    public boolean insert(CategoryFour categoryFour) {
        if (categoryFour == null || categoryFour.getId() != null) {
            return false;
        } else {
            categoryFourRepository.save(categoryFour);
            return true;
        }
    }

    @Override
    public boolean update(CategoryFour categoryFour) {
        if (categoryFour == null || categoryFour.getId() == null) {
            return false;
        } else {
            categoryFourRepository.save(categoryFour);
            return true;
        }
    }

    @Override
    public boolean delete(CategoryFour categoryFour) {
        if (categoryFour == null || categoryFour.getId() == null){
            return false;
        } else {
            categoryFour.setDeletedAt(new Date());
            categoryFourRepository.save(categoryFour);
            return true;
        }
    }

    @Override
    public boolean realDelete(CategoryFour categoryFour) {
        if (categoryFour == null || categoryFour.getId() == null){
            return false;
        } else {
            categoryFourRepository.delete(categoryFour);
            return true;
        }
    }
}
