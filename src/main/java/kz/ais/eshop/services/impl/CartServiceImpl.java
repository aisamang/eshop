package kz.ais.eshop.services.impl;

import kz.ais.eshop.models.Cart;
import kz.ais.eshop.models.OrdersProduct;
import kz.ais.eshop.repositories.CartRepository;
import kz.ais.eshop.repositories.OrdersProductRepository;
import kz.ais.eshop.services.CartService;
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
public class CartServiceImpl implements CartService {

    private CartRepository cartRepository;
    private OrdersProductRepository ordersProductRepository;
    private SessionFactory hibernateFactory;

    @Autowired
    public CartServiceImpl(EntityManagerFactory factory,
                           OrdersProductRepository ordersProductRepository,
                           CartRepository cartRepository){
        if (factory.unwrap(SessionFactory.class) == null) {
            throw new NullPointerException("factory is not a hibernate factory");
        }
        this.hibernateFactory = factory.unwrap(SessionFactory.class);
        this.ordersProductRepository = ordersProductRepository;
        this.cartRepository=cartRepository;
    }

    @Override
    public List<Cart> getAllWithTrashed() {
        return cartRepository.findAll();
    }

    @Override
    public List<Cart> getAll() {
        Session session = hibernateFactory.openSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Cart> criteriaQuery = criteriaBuilder.createQuery(Cart.class);
        Root<Cart> root = criteriaQuery.from(Cart.class);
        Predicate predicate = criteriaBuilder.isNull(root.get("deletedAt"));
        criteriaQuery.where(predicate);
        criteriaQuery.orderBy(criteriaBuilder.desc(root.get("id")));
        List<Cart> carts = session.createQuery(criteriaQuery).list();
        session.close();
        return carts;
    }

    @Override
    public Cart getByUser(Long userId) {
        Optional<Cart> cartOptional = cartRepository.findByUserIdAndDeletedAtIsNull(userId);

        return cartOptional.orElse(null);
    }

    @Override
    public Cart getById(Long id) {
        Optional<Cart> cartOptional = cartRepository.findById(id);

        return cartOptional.orElse(null);
    }

    @Override
    public double getPrice(Long id) {
        List<OrdersProduct> ordersProducts = ordersProductRepository.findAllByCartIdAndDeletedAtIsNull(id);
        if(!cartRepository.findById(id).isPresent() || id == null || ordersProducts == null){
            return 0;
        } else {
            double price = 0;
            for(OrdersProduct o : ordersProducts){
                price += o.getPrice();
            }
            return price;
        }
    }

    @Override
    public boolean add(Cart cart) {
        if (cart == null || cart.getId() != null) {
            return false;
        } else {
            cartRepository.save(cart);
            return true;
        }
    }

    @Override
    public boolean update(Cart cart) {
        if(cart == null || cart.getId() == null){
            return false;
        } else {
            cartRepository.save(cart);
            return true;
        }
    }

    @Override
    public boolean delete(Cart cart) {
        if (cart == null || cart.getId() == null) {
            return false;
        } else {
            cart.setDeletedAt(new Date());
            cartRepository.save(cart);
            return true;
        }
    }

    @Override
    public boolean realDelete(Cart cart) {
        if(cart == null || cart.getId() == null){
            return false;
        } else {
            cartRepository.delete(cart);
            return true;
        }
    }
}
