package kz.ais.eshop.controllers;

import kz.ais.eshop.models.ProductsCharacteristic;
import kz.ais.eshop.services.ProductsCharacteristicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api")
public class ProductsCharacteristicController {

    private ProductsCharacteristicService productsCharacteristicService;

    @Autowired
    public ProductsCharacteristicController(ProductsCharacteristicService productsCharacteristicService){
        this.productsCharacteristicService=productsCharacteristicService;
    }

    @GetMapping(path = "products_characteristic/all")
    public ResponseEntity<List<ProductsCharacteristic>> index(){
        return new ResponseEntity<List<ProductsCharacteristic>>(productsCharacteristicService.getAll(), HttpStatus.OK);
    }

    @GetMapping(path = "products_characteristic/{id}")
    public ResponseEntity get(@PathVariable Long id){
        ProductsCharacteristic productsCharacteristic = productsCharacteristicService.getById(id);

        return productsCharacteristic!=null ? new ResponseEntity<ProductsCharacteristic>(productsCharacteristic, HttpStatus.OK):
                ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @GetMapping(path = "products_characteristic/get/{id}")
    public ResponseEntity<List<ProductsCharacteristic>> getByProductId(@PathVariable Long id){
        List<ProductsCharacteristic> productsCharacteristic = productsCharacteristicService.getByProduct(id);

        return productsCharacteristic!=null ? new ResponseEntity<List<ProductsCharacteristic>>(productsCharacteristic, HttpStatus.OK):
                ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping(path = "products_characteristic/add")
    public ResponseEntity add(@RequestBody ProductsCharacteristic productsCharacteristic){
        return productsCharacteristicService.insert(productsCharacteristic) ? new ResponseEntity<ProductsCharacteristic>(productsCharacteristic,HttpStatus.ACCEPTED):
                ResponseEntity.status(HttpStatus.NOT_FOUND).build();

    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(path = "products_characteristic", method = {RequestMethod.PATCH, RequestMethod.PUT})
    public ResponseEntity update(@RequestBody ProductsCharacteristic productsCharacteristic){
        return productsCharacteristicService.update(productsCharacteristic) ? new ResponseEntity<ProductsCharacteristic>(productsCharacteristic, HttpStatus.OK) :
                ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping(path = "products_characteristic/{id}")
    public ResponseEntity delete(@PathVariable Long id){
        ProductsCharacteristic productsCharacteristic = productsCharacteristicService.getById(id);
        return productsCharacteristicService.delete(productsCharacteristic) ? ResponseEntity.status(HttpStatus.OK).build() :
                ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}
