package kz.ais.eshop.controllers;

import kz.ais.eshop.models.Address;
import kz.ais.eshop.models.Delivery;
import kz.ais.eshop.services.AddressService;
import kz.ais.eshop.services.DeliveryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api")
public class AddressController {

    private AddressService addressService;
    private DeliveryService deliveryService;

    @Autowired
    public AddressController(AddressService addressService,
                             DeliveryService deliveryService){
        this.addressService = addressService;
        this.deliveryService = deliveryService;
    }

    @GetMapping(path = "address/all")
    public ResponseEntity<List<Address>> getAll(){
        return new ResponseEntity<List<Address>>(addressService.getAll(), HttpStatus.OK);
    }

    @GetMapping(path = "address/all/{id}")
    public ResponseEntity<List<Address>> getAllById(@PathVariable Long id) {
        List<Address> addresses = addressService.getAllByDelivery(id);
        return addresses != null ? new ResponseEntity<List<Address>>(addresses, HttpStatus.OK) :
                ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @GetMapping(path = "address/{id}")
    public ResponseEntity get(@PathVariable Long id){
        Address address = addressService.getById(id);

        return address != null ? new ResponseEntity<Address>(address, HttpStatus.OK) :
            ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @DeleteMapping(path = "address/{id}")
    public ResponseEntity delete(@PathVariable Long id){
        Address address = addressService.getById(id);

        return addressService.delete(address) ? ResponseEntity.status(HttpStatus.OK).build() :
                ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @RequestMapping(path = "address", method = {RequestMethod.PATCH , RequestMethod.PUT})
    public ResponseEntity update(@RequestBody Address address){

        return addressService.update(address) ? new ResponseEntity<Address>(address, HttpStatus.OK) :
                ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @PostMapping(path = "address/add")
    public ResponseEntity add(@RequestBody Address address){
        address.setDelivery(deliveryService.getById(Delivery.DELIVERY_ID));
        return addressService.insert(address) ? new ResponseEntity<Address>(address, HttpStatus.ACCEPTED) :
                ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping(path = "address/pickup/add")
    public ResponseEntity addPickup(@RequestBody Address address){
        address.setDelivery(deliveryService.getById(Delivery.PICKUP_ID));
        return addressService.insert(address) ? new ResponseEntity<Address>(address, HttpStatus.ACCEPTED) :
                ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}
