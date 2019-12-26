package kz.ais.eshop.controllers;

import kz.ais.eshop.models.Cart;
import kz.ais.eshop.models.OrdersProduct;
import kz.ais.eshop.models.User;
import kz.ais.eshop.services.CartService;
import kz.ais.eshop.services.OrdersProductService;
import kz.ais.eshop.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api")
public class CartController {

    private CartService cartService;
    private UserService userService;
    private OrdersProductService ordersProductService;

    @Autowired
    public CartController(CartService cartService,
                          UserService userService,
                          OrdersProductService ordersProductService){
        this.cartService = cartService;
        this.userService=userService;
        this.ordersProductService = ordersProductService;
    }

    @GetMapping(path = "cart/all")
    public ResponseEntity<List<Cart>> index(){
        return new ResponseEntity<List<Cart>>(cartService.getAll(), HttpStatus.OK);
    }

    @GetMapping(path = "cart/{userId}")
    public ResponseEntity getByUser(@PathVariable Long userId) {
        User user = userService.getById(userId);
        Cart cart =cartService.getByUser(userId);
//        cart.setTotalPrice(cartService.getPrice(cart.getId()));
        return user != null ? new ResponseEntity<Cart>(cart, HttpStatus.OK) :
                ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @GetMapping(path = "cart/orders_products/{id}")
    public ResponseEntity<List<OrdersProduct>> getByCart(@PathVariable Long id) {
        List<OrdersProduct> ordersProducts = ordersProductService.getAllByCartId(id);
        return ordersProducts != null ? new ResponseEntity<List<OrdersProduct>>(ordersProducts, HttpStatus.OK) :
                ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @DeleteMapping(path = "cart/{id}")
    public ResponseEntity delete(@PathVariable Long id){
        Cart cart = cartService.getById(id);
        return cartService.delete(cart) ? ResponseEntity.status(HttpStatus.OK).build() :
                ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @RequestMapping(path = "cart", method = {RequestMethod.PATCH, RequestMethod.PUT})
    public ResponseEntity update(@RequestBody Cart cart){
        return cartService.update(cart) ? new ResponseEntity<Cart>(cart, HttpStatus.OK) :
                ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @PostMapping(path = "cart/add")
    public ResponseEntity add(@RequestBody Cart cart){
        boolean cart1 = cartService.add(cart);
        cartService.getById(cart.getId());
        return cart1 ? new ResponseEntity<Cart>(cart, HttpStatus.ACCEPTED) :
                ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

}
