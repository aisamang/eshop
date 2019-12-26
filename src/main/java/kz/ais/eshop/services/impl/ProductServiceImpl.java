package kz.ais.eshop.services.impl;

import kz.ais.eshop.models.Product;
import kz.ais.eshop.repositories.ProductRepository;
import kz.ais.eshop.services.ProductService;
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
public class ProductServiceImpl implements ProductService {

    private ProductRepository productRepository;
    private SessionFactory hibernateFactory;

    @Autowired
    public ProductServiceImpl(EntityManagerFactory factory,
                             ProductRepository productRepository) {
        if (factory.unwrap(SessionFactory.class) == null) {
            throw new NullPointerException("factory is not a hibernate factory");
        }
        this.hibernateFactory = factory.unwrap(SessionFactory.class);
        this.productRepository = productRepository;
    }

    @Override
    public List<Product> getAll() {
        Session session = hibernateFactory.openSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Product> criteriaQuery = criteriaBuilder.createQuery(Product.class);
        Root<Product> root = criteriaQuery.from(Product.class);
        Predicate predicate = criteriaBuilder.isNull(root.get("deletedAt"));
        criteriaQuery.where(predicate);
        criteriaQuery.orderBy(criteriaBuilder.desc(root.get("id")));
        List<Product> products = session.createQuery(criteriaQuery).list();
        session.close();
        return products;
    }

    @Override
    public boolean insert(Product product) {
        if (product == null || product.getId() != null) {
            return false;
        } else {
            productRepository.save(product);
            return true;
        }
    }

    @Override
    public boolean update(Product product) {
        if (product == null || product.getId() == null) {
            return false;
        } else {
            productRepository.save(product);
            return true;
        }
    }

    @Override
    public boolean delete(Product product) {
        if (product == null || product.getId() == null){
            return false;
        } else {
            product.setDeletedAt(new Date());
            productRepository.save(product);
            return true;
        }
    }

    @Override
    public boolean realDelete(Product product) {
        if (product == null || product.getId() == null){
            return false;
        } else {
            productRepository.delete(product);
            return true;
        }
    }

    @Override
    public Product getById(Long id) {
        Optional<Product> productOptional = productRepository.findById(id);

        return productOptional.orElse(null);
    }

    @Override
    public List<Product> findAllByCategoryOneAndDeletedAtIsNull(Long categoryId) {
        return productRepository.findAllByCategoryOneIdAndDeletedAtIsNull(categoryId);
    }

    @Override
    public List<Product> findByCategoryTwoAndDeletedAtIsNull(Long categoryId) {
        return productRepository.findByCategoryTwoIdAndDeletedAtIsNull(categoryId);
    }

    @Override
    public List<Product> findByCategoryThreeAndDeletedAtIsNull(Long categoryId) {
        return productRepository.findByCategoryThreeIdAndDeletedAtIsNull(categoryId);
    }

    @Override
    public List<Product> findByCategoryFourAndDeletedAtIsNull(Long categoryId) {
        return productRepository.findByCategoryFourIdAndDeletedAtIsNull(categoryId);
    }

    @Override
    public List<Product> orderedByMinPrice() {
        return productRepository.findByOrderByPriceAsc();
    }

    @Override
    public List<Product> orderedByMaxPrice() {
        return productRepository.findByOrderByPriceDesc();
    }
}
