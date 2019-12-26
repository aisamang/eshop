package kz.ais.eshop.services.impl;

import kz.ais.eshop.models.User;
import kz.ais.eshop.repositories.UserRepository;
import kz.ais.eshop.services.UserService;
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
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private SessionFactory hibernateFactory;

    @Autowired
    public UserServiceImpl(EntityManagerFactory factory,
                           UserRepository userRepository){
        if (factory.unwrap(SessionFactory.class) == null) {
            throw new NullPointerException("factory is not a hibernate factory");
        }
        this.hibernateFactory = factory.unwrap(SessionFactory.class);
        this.userRepository=userRepository;
    }

    @Override
    public List<User> getAll() {
        Session session = hibernateFactory.openSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);
        Root<User> root = criteriaQuery.from(User.class);

        Predicate predicate = criteriaBuilder.isNull(root.get("deletedAt"));
        criteriaQuery.where(predicate);
        criteriaQuery.orderBy(criteriaBuilder.desc(root.get("id")));
        List<User> users = session.createQuery(criteriaQuery).list();

        session.close();
        return users;
    }

    @Override
    public User getById(Long id) {
        Optional<User> userOptional = userRepository.findById(id);

        if (userOptional.isPresent()) {
            return userOptional.get();
        } else {
            return null;
        }    }

    @Override
    public User save(User user) {
        if(user == null || user.getId() != null) {
            return userRepository.save(user);
        } else {
            return null;
        }
    }

    @Override
    public boolean update(User user) {
        if (user == null || user.getId() == null) {
            return false;
        } else {
            userRepository.save(user);
            return true;
        }
    }

    @Override
    public boolean delete(User user) {
        if (user == null || user.getId() == null) {
            return false;
        } else {
            user.setDeletedAt(new Date());
            userRepository.save(user);
            return true;
        }
    }

    @Override
    public boolean realDelete(User user) {
        if (user == null || user.getId() == null) {
            return false;
        } else {
            userRepository.delete(user);
            return true;
        }
    }

    @Override
    public boolean existsByUsername(String username) {
        if(userRepository.existsByUsername(username)){
            return false;
        } else {
            return true;
        }
    }

    @Override
    public boolean existsByEmail(String email) {
        if(userRepository.existsByEmail(email)){
            return false;
        } else {
            return true;
        }
    }

    @Override
    public User findByUsernameOrEmail(String username, String email) {

        Optional<User> userOptional = userRepository.findByUsernameOrEmail(username, email);
        return userOptional.orElse(null);
    }

    @Override
    public User findByEmail(String email) {

        Optional<User> userOptional = userRepository.findByEmail(email);
        return userOptional.orElse(null);
    }
}
