package kz.ais.eshop.services.impl;

import kz.ais.eshop.models.Order;
import kz.ais.eshop.repositories.OrderRepository;
import kz.ais.eshop.services.OrderService;
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
public class OrderServiceImpl implements OrderService {

    private OrderRepository orderRepository;
    private SessionFactory hibernateFactory;

    @Autowired
    public OrderServiceImpl(EntityManagerFactory factory,
                                    OrderRepository orderRepository){
        if (factory.unwrap(SessionFactory.class) == null) {
            throw new NullPointerException("factory is not a hibernate factory");
        }
        this.hibernateFactory = factory.unwrap(SessionFactory.class);
        this.orderRepository=orderRepository;
    }

    @Override
    public List<Order> getAllTrashed() {
        return orderRepository.findAll();
    }

    @Override
    public List<Order> getAll() {
        Session session = hibernateFactory.openSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Order> criteriaQuery = criteriaBuilder.createQuery(Order.class);
        Root<Order> root = criteriaQuery.from(Order.class);
        Predicate predicate = criteriaBuilder.isNull(root.get("deletedAt"));
        criteriaQuery.where(predicate);
        criteriaQuery.orderBy(criteriaBuilder.desc(root.get("id")));
        List<Order> orders = session.createQuery(criteriaQuery).list();
        session.close();
        return orders;
    }

    @Override
    public List<Order> getAllByUser(Long userId) {
        return orderRepository.findAllByUserIdAndDeletedAtIsNull(userId);
    }

    @Override
    public Order getById(Long id) {
        Optional<Order> order = orderRepository.findById(id);

        return order.orElse(null);
    }

    @Override
    public boolean add(Order order) {
        if (order == null || order.getId() != null) {
            return false;
        } else {
            orderRepository.save(order);
            return true;
        }
    }

    @Override
    public boolean update(Order order) {
        if (order == null || order.getId() == null) {
            return false;
        } else {
            orderRepository.save(order);
            return true;
        }
    }

    @Override
    public boolean delete(Order order) {
        if (order == null || order.getId() == null) {
            return false;
        } else {
            order.setDeletedAt(new Date());
            orderRepository.save(order);
            return true;
        }
    }

    @Override
    public boolean realDelete(Order order) {
        if (order == null || order.getId() == null) {
            return false;
        } else {
            orderRepository.delete(order);
            return true;
        }
    }
}
