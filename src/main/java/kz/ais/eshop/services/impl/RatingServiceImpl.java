package kz.ais.eshop.services.impl;

import kz.ais.eshop.models.Rating;
import kz.ais.eshop.repositories.RatingRepository;
import kz.ais.eshop.services.RatingService;
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
public class RatingServiceImpl implements RatingService {

    private RatingRepository ratingRepository;
    private SessionFactory hibernateFactory;

    @Autowired
    public RatingServiceImpl(EntityManagerFactory factory,
                             RatingRepository ratingRepository){
        if (factory.unwrap(SessionFactory.class) == null) {
            throw new NullPointerException("factory is not a hibernate factory");
        }
        this.hibernateFactory = factory.unwrap(SessionFactory.class);
        this.ratingRepository = ratingRepository;
    }

    @Override
    public List<Rating> getAll() {
        Session session = hibernateFactory.openSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Rating> criteriaQuery = criteriaBuilder.createQuery(Rating.class);
        Root<Rating> root = criteriaQuery.from(Rating.class);
        Predicate predicate = criteriaBuilder.isNull(root.get("deletedAt"));
        criteriaQuery.where(predicate);
        criteriaQuery.orderBy(criteriaBuilder.desc(root.get("id")));
        List<Rating> ratings = session.createQuery(criteriaQuery).list();
        session.close();
        return ratings;
    }

    @Override
    public Rating getById(Long id) {
        Optional<Rating> ratingOptional = ratingRepository.findById(id);

        return ratingOptional.orElse(null);
    }

    @Override
    public boolean insert(Rating rating) {
        if (rating == null || rating.getId() != null) {
            return false;
        } else {
            ratingRepository.save(rating);
            return true;
        }
    }

    @Override
    public boolean update(Rating rating) {
        if (rating == null || rating.getId() == null) {
            return false;
        } else {
            ratingRepository.save(rating);
            return true;
        }
    }

    @Override
    public boolean delete(Rating rating) {
        if (rating == null || rating.getId() == null){
            return false;
        } else {
            rating.setDeletedAt(new Date());
            ratingRepository.save(rating);
            return true;
        }
    }

    @Override
    public boolean realDelete(Rating rating) {
        if (rating == null || rating.getId() == null){
            return false;
        } else {
            ratingRepository.delete(rating);
            return true;
        }
    }
}
