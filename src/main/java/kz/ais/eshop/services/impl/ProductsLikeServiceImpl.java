package kz.ais.eshop.services.impl;

import kz.ais.eshop.models.ProductsLike;
import kz.ais.eshop.repositories.ProductsLikeRepository;
import kz.ais.eshop.services.ProductsLikeService;
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
public class ProductsLikeServiceImpl implements ProductsLikeService {

    private ProductsLikeRepository productsLikeRepository;
    private SessionFactory hibernateFactory;

    @Autowired
    public ProductsLikeServiceImpl(EntityManagerFactory factory,
                              ProductsLikeRepository productsLikeRepository) {
        if (factory.unwrap(SessionFactory.class) == null) {
            throw new NullPointerException("factory is not a hibernate factory");
        }
        this.hibernateFactory = factory.unwrap(SessionFactory.class);
        this.productsLikeRepository = productsLikeRepository;
    }

    @Override
    public List<ProductsLike> getAll() {
        Session session = hibernateFactory.openSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<ProductsLike> criteriaQuery = criteriaBuilder.createQuery(ProductsLike.class);
        Root<ProductsLike> root = criteriaQuery.from(ProductsLike.class);
        Predicate predicate = criteriaBuilder.isNull(root.get("deletedAt"));
        criteriaQuery.where(predicate);
        criteriaQuery.orderBy(criteriaBuilder.desc(root.get("id")));
        List<ProductsLike> productsLikes = session.createQuery(criteriaQuery).list();
        session.close();
        return productsLikes;
    }

    @Override
    public ProductsLike getProductLikeByUserId(Long id) {
        Optional<ProductsLike> productsLikeOptional = productsLikeRepository.findProductsLikeByUserIdAndDeletedAtIsNull(id);

        return productsLikeOptional.orElse(null);
    }

    @Override
    public ProductsLike getById(Long id) {
        Optional<ProductsLike> productsLikeOptional = productsLikeRepository.findById(id);

        return productsLikeOptional.orElse(null);
    }

    @Override
    public boolean insert(ProductsLike productsLike) {
        if (productsLike == null || productsLike.getId() != null) {
            return false;
        } else {
            productsLikeRepository.save(productsLike);
            return true;
        }     }

    @Override
    public boolean update(ProductsLike productsLike) {
        if (productsLike == null || productsLike.getId() == null) {
            return false;
        } else {
            productsLikeRepository.save(productsLike);
            return true;
        }
    }

    @Override
    public boolean delete(ProductsLike productsLike) {
        if (productsLike == null || productsLike.getId() == null){
            return false;
        } else {
            productsLike.setDeletedAt(new Date());
            productsLikeRepository.save(productsLike);
            return true;
        }     }

    @Override
    public boolean realDelete(ProductsLike productsLike) {
        if (productsLike == null || productsLike.getId() == null){
            return false;
        } else {
            productsLikeRepository.delete(productsLike);
            return true;
        }
    }
}
