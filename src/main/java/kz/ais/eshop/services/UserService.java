package kz.ais.eshop.services;

import kz.ais.eshop.models.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    List<User> getAll();
    User getById(Long id);
    boolean update(User user);
    boolean delete(User user);
    boolean realDelete(User user);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
    User findByUsernameOrEmail(String username, String email);
    User save(User user);
    User findByEmail(String email);
}
