package kz.ais.eshop.services.impl;

import kz.ais.eshop.models.OrdersProduct;
import kz.ais.eshop.repositories.CartRepository;
import kz.ais.eshop.repositories.OrdersProductRepository;
import kz.ais.eshop.services.OrdersProductService;
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
public class OrdersProductServiceImpl implements OrdersProductService {

    private OrdersProductRepository ordersProductRepository;
    private CartRepository cartRepository;
    private SessionFactory hibernateFactory;

    @Autowired
    public OrdersProductServiceImpl(EntityManagerFactory factory,
                                    OrdersProductRepository ordersProductRepository,
                                    CartRepository cartRepository){
        if (factory.unwrap(SessionFactory.class) == null) {
            throw new NullPointerException("factory is not a hibernate factory");
        }
        this.hibernateFactory = factory.unwrap(SessionFactory.class);
        this.cartRepository = cartRepository;
        this.ordersProductRepository=ordersProductRepository;
    }

    @Override
    public List<OrdersProduct> getAll() {
        Session session = hibernateFactory.openSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<OrdersProduct> criteriaQuery = criteriaBuilder.createQuery(OrdersProduct.class);
        Root<OrdersProduct> root = criteriaQuery.from(OrdersProduct.class);
        Predicate predicate = criteriaBuilder.isNull(root.get("deletedAt"));
        criteriaQuery.where(predicate);
        criteriaQuery.orderBy(criteriaBuilder.desc(root.get("id")));
        List<OrdersProduct> ordersProducts = session.createQuery(criteriaQuery).list();
        session.close();
        return ordersProducts;
    }

    @Override
    public OrdersProduct getById(Long id) {
        Optional<OrdersProduct> ordersProduct = ordersProductRepository.findById(id);

        return ordersProduct.orElse(null);
    }

    @Override
    public List<OrdersProduct> getAllByCartId(Long cartId) {
        if(cartId == null || !cartRepository.findById(cartId).isPresent()){
            return null;
        } else {
            return ordersProductRepository.findAllByCartIdAndDeletedAtIsNull(cartId);
        }
    }

    @Override
    public boolean insert(OrdersProduct ordersProduct) {
        if (ordersProduct == null || ordersProduct.getId() != null) {
            return false;
        } else {
            ordersProductRepository.save(ordersProduct);
            return true;
        }
    }

    @Override
    public boolean update(OrdersProduct ordersProduct) {
        if (ordersProduct == null || ordersProduct.getId() == null) {
            return false;
        } else {
            ordersProductRepository.save(ordersProduct);
            return true;
        }
    }

    @Override
    public boolean delete(OrdersProduct ordersProduct) {
        if (ordersProduct == null || ordersProduct.getId() == null) {
            return false;
        } else {
            ordersProduct.setDeletedAt(new Date());
            ordersProductRepository.save(ordersProduct);
            return true;
        }
    }

    @Override
    public boolean realDelete(OrdersProduct ordersProduct) {
        if (ordersProduct == null || ordersProduct.getId() == null) {
            return false;
        } else {
            ordersProductRepository.delete(ordersProduct);
            return true;
        }
    }
}
