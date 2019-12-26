package kz.ais.eshop.controllers;

import kz.ais.eshop.models.Delivery;
import kz.ais.eshop.services.DeliveryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api")
public class DeliveryController {

    private DeliveryService deliveryService;

    @Autowired
    public DeliveryController(DeliveryService deliveryService) {
        this.deliveryService = deliveryService;
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping(path = "delivery/all")
    public ResponseEntity<List<Delivery>> getAll(){
        return new ResponseEntity<List<Delivery>>(deliveryService.getAllTrashed(), HttpStatus.OK);
    }

    @GetMapping(path = "delivery/{id}")
    public ResponseEntity get(@PathVariable Long id){
        Delivery delivery = deliveryService.getById(id);

        return delivery != null ? new ResponseEntity<Delivery>(delivery, HttpStatus.OK) :
                ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping(path = "delivery/{id}")
    public ResponseEntity realDelete(@PathVariable Long id){
        Delivery delivery = deliveryService.getById(id);
        return deliveryService.realDelete(delivery) ? ResponseEntity.status(HttpStatus.OK).build() :
                ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @RequestMapping(path = "delivery", method = {RequestMethod.PATCH, RequestMethod.PUT})
    public ResponseEntity update(@RequestBody Delivery delivery){
        return deliveryService.update(delivery) ? new ResponseEntity<Delivery>(delivery, HttpStatus.OK) :
                ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping(path = "delivery/add")
    public ResponseEntity add(@RequestBody Delivery delivery){
        return deliveryService.add(delivery) ? new ResponseEntity<Delivery>(delivery, HttpStatus.ACCEPTED) :
                ResponseEntity.status(HttpStatus.NOT_FOUND).build();

    }
}
