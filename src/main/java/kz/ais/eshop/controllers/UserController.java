package kz.ais.eshop.controllers;

import kz.ais.eshop.models.User;
import kz.ais.eshop.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping(path = "api")
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping(path = "user/all")
    public ResponseEntity<List<User>> index(){
        return new ResponseEntity<List<User>>(userService.getAll(), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping(path = "user/{id}")
    public ResponseEntity get(@PathVariable Long id){
        User user = userService.getById(id);

        return user != null ? new ResponseEntity<User>(user, HttpStatus.OK) :
                ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }


    @PreAuthorize("isAuthenticated()")
    @GetMapping(path = "user")
    public ResponseEntity getCurrentUser(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        User user = userService.findByUsernameOrEmail(username, username);

        return user != null ? new ResponseEntity<User>(user, HttpStatus.OK) :
                ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @RequestMapping(path = "user", method = {RequestMethod.PATCH, RequestMethod.PUT})
    public ResponseEntity update(@RequestBody User user){
        return userService.update(user) ? new ResponseEntity<User>(user, HttpStatus.OK) :
                ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping(path = "user/{id}")
    public ResponseEntity delete(@PathVariable Long id){
        User user = userService.getById(id);
        return userService.delete(user) ? ResponseEntity.status(HttpStatus.OK).build() :
                ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

}
