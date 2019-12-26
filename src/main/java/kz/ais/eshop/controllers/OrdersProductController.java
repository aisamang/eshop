package kz.ais.eshop.controllers;

import kz.ais.eshop.models.Cart;
import kz.ais.eshop.models.OrdersProduct;
import kz.ais.eshop.services.CartService;
import kz.ais.eshop.services.OrdersProductService;
import kz.ais.eshop.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api")
public class OrdersProductController {

    private OrdersProductService ordersProductService;
    private ProductService productService;
    private CartService cartService;

    @Autowired
    public OrdersProductController(OrdersProductService ordersProductService,
                                   ProductService productService,
                                   CartService cartService){
        this.ordersProductService = ordersProductService;
        this.productService = productService;
        this.cartService = cartService;
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping(path = "orders_product/all")
    public ResponseEntity<List<OrdersProduct>> index(){
        return new ResponseEntity<List<OrdersProduct>>(ordersProductService.getAll(), HttpStatus.OK);
    }

    @GetMapping(path = "orders_product/{id}")
    public ResponseEntity get(@PathVariable Long id){
        OrdersProduct ordersProduct = ordersProductService.getById(id);

        return ordersProduct!=null ? new ResponseEntity<OrdersProduct>(ordersProduct, HttpStatus.OK) :
                ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @DeleteMapping(path = "orders_product/{id}")
    public ResponseEntity delete(@PathVariable Long id){
        OrdersProduct ordersProduct = ordersProductService.getById(id);

        return ordersProductService.delete(ordersProduct) ? ResponseEntity.status(HttpStatus.OK).build() :
                ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @RequestMapping(path = "orders_product", method = {RequestMethod.PATCH , RequestMethod.PUT})
    public ResponseEntity update(@RequestBody OrdersProduct ordersProduct){
        return ordersProductService.update(ordersProduct) ? new ResponseEntity<OrdersProduct>(ordersProduct, HttpStatus.OK) :
                ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @PostMapping(path = "orders_product/add")
    public ResponseEntity add(@RequestBody OrdersProduct ordersProduct){
        double price = productService.getById(ordersProduct.getProducts().getId()).getPrice();
        ordersProduct.setPrice(ordersProduct.getQuantity() * price);
        boolean o = ordersProductService.insert(ordersProduct);
        Long cartId = ordersProduct.getCart().getId();
        Cart cart = cartService.getById(cartId);
        cart.setTotalPrice(cartService.getPrice(cartId));
        cartService.update(cart);
        return o ? new ResponseEntity<OrdersProduct>(ordersProduct, HttpStatus.ACCEPTED) :
                ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}
