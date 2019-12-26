package kz.ais.eshop.controllers;

import kz.ais.eshop.models.*;
import kz.ais.eshop.services.AddressService;
import kz.ais.eshop.services.CartService;
import kz.ais.eshop.services.DeliveryService;
import kz.ais.eshop.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api")
public class OrderController {

    private OrderService orderService;
    private DeliveryService deliveryService;
    private CartService cartService;
    private AddressService addressService;

    @Autowired
    public OrderController(OrderService orderService,
                           DeliveryService deliveryService,
                           CartService cartService,
                           AddressService addressService){
        this.orderService = orderService;
        this.deliveryService = deliveryService;
        this.cartService = cartService;
        this.addressService = addressService;
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping(path = "order/all")
    public ResponseEntity<List<Order>> getAll(){
        return new ResponseEntity<List<Order>>(orderService.getAll(), HttpStatus.OK);
    }

    @GetMapping(path = "order/user/{id}")
    public ResponseEntity<List<Order>> getAllByUser(@PathVariable Long id){
        List<Order> orders = orderService.getAllByUser(id);

        return orders != null ? new ResponseEntity<List<Order>>(orders, HttpStatus.OK) :
                ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @GetMapping(path = "order/{id}")
    public ResponseEntity get(@PathVariable Long id){
        Order order = orderService.getById(id);

        return order != null ? new ResponseEntity<Order>(order, HttpStatus.OK) :
                ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping(path = "order/{id}")
    public ResponseEntity delete(@PathVariable Long id){
        Order order = orderService.getById(id);

        return orderService.delete(order) ? ResponseEntity.status(HttpStatus.OK).build() :
                ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @RequestMapping(path = "order", method = {RequestMethod.PATCH, RequestMethod.PUT})
    public ResponseEntity update(@RequestBody Order order){
        return orderService.update(order) ? new ResponseEntity<Order>(order, HttpStatus.OK) :
                ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @PostMapping(path = "order/add")
    public ResponseEntity add(@RequestBody Order order){
        Address address = addressService.getById(order.getAddress().getId());
        Delivery delivery = deliveryService.getById(address.getDelivery().getId());
        Cart cart = cartService.getById(order.getCart().getId());
        order.setOverallPrice(delivery.getDeliverCost() + cart.getTotalPrice());
        return orderService.add(order) ? new ResponseEntity<Order>(order, HttpStatus.ACCEPTED) :
                ResponseEntity.status(HttpStatus.NOT_FOUND).build();

    }
}
