package kz.ais.eshop.services.impl;

import kz.ais.eshop.models.ProductsCharacteristic;
import kz.ais.eshop.repositories.ProductsCharacteristicRepository;
import kz.ais.eshop.services.ProductsCharacteristicService;
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
public class ProductsCharacteristicServiceImpl implements ProductsCharacteristicService {

    private ProductsCharacteristicRepository productsCharacteristicRepository;
    private SessionFactory hibernateFactory;

    @Autowired
    public ProductsCharacteristicServiceImpl(EntityManagerFactory factory,
                                             ProductsCharacteristicRepository productsCharacteristicRepository){
        if (factory.unwrap(SessionFactory.class) == null) {
            throw new NullPointerException("factory is not a hibernate factory");
        }
        this.hibernateFactory = factory.unwrap(SessionFactory.class);
        this.productsCharacteristicRepository = productsCharacteristicRepository;
    }

    @Override
    public List<ProductsCharacteristic> getAll() {
        Session session = hibernateFactory.openSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<ProductsCharacteristic> criteriaQuery = criteriaBuilder.createQuery(ProductsCharacteristic.class);
        Root<ProductsCharacteristic> root = criteriaQuery.from(ProductsCharacteristic.class);
        Predicate predicate = criteriaBuilder.isNull(root.get("deletedAt"));
        criteriaQuery.where(predicate);
        criteriaQuery.orderBy(criteriaBuilder.desc(root.get("id")));
        List<ProductsCharacteristic> productsCharacteristics = session.createQuery(criteriaQuery).list();
        session.close();
        return productsCharacteristics;
    }

    @Override
    public ProductsCharacteristic getById(Long id) {
        Optional<ProductsCharacteristic> productsCharacteristicOptional = productsCharacteristicRepository.findById(id);

        if (productsCharacteristicOptional.isPresent()){
            return productsCharacteristicOptional.get();
        } else {
            return null;
        }
    }

    @Override
    public boolean insert(ProductsCharacteristic productsCharacteristic) {
        if (productsCharacteristic == null || productsCharacteristic.getId() != null) {
            return false;
        } else {
            productsCharacteristicRepository.save(productsCharacteristic);
            return true;
        }
    }

    @Override
    public boolean update(ProductsCharacteristic productsCharacteristic) {
        if (productsCharacteristic == null || productsCharacteristic.getId() == null) {
            return false;
        } else {
            productsCharacteristicRepository.save(productsCharacteristic);
            return true;
        }
    }

    @Override
    public boolean delete(ProductsCharacteristic productsCharacteristic) {
        if (productsCharacteristic == null || productsCharacteristic.getId() == null){
            return false;
        } else {
            productsCharacteristic.setDeletedAt(new Date());
            productsCharacteristicRepository.save(productsCharacteristic);
            return true;
        }
    }

    @Override
    public boolean realDelete(ProductsCharacteristic productsCharacteristic) {
        if (productsCharacteristic == null || productsCharacteristic.getId() == null){
            return false;
        } else {
            productsCharacteristicRepository.delete(productsCharacteristic);
            return true;
        }
    }

    @Override
    public List<ProductsCharacteristic> getByProduct(Long productId) {
        if( productId == null ) {
            return null;
        } else {
            return productsCharacteristicRepository.findAllByProductIdAndDeletedAtIsNull(productId);
        }
    }
}
