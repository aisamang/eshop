package kz.ais.eshop.controllers;

import kz.ais.eshop.models.Product;
import kz.ais.eshop.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api")
public class ProductController {

    private ProductService productService;

    @Autowired
    public ProductController(ProductService productService){
        this.productService=productService;
    }

    @PreAuthorize("permitAll()")
    @GetMapping(path = "products/all")
    public ResponseEntity<List<Product>> index(){
        return new ResponseEntity<List<Product>>(productService.getAll(), HttpStatus.OK);
    }

    @PreAuthorize("permitAll()")
    @GetMapping(path = "products/category_one/{id}")
    public ResponseEntity<List<Product>> getAllByCategoryOne(@PathVariable Long id){
        List<Product> products = productService.findAllByCategoryOneAndDeletedAtIsNull(id);
        if(products != null){
            return new ResponseEntity<List<Product>>(products, HttpStatus.OK);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PreAuthorize("permitAll()")
    @GetMapping(path = "products/category_two/{id}")
    public ResponseEntity<List<Product>> getAllByCategoryTwo(@PathVariable Long id){
        List<Product> products = productService.findByCategoryTwoAndDeletedAtIsNull(id);
        if(products != null){
            return new ResponseEntity<List<Product>>(products, HttpStatus.OK);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PreAuthorize("permitAll()")
    @GetMapping(path = "products/category_three/{id}")
    public ResponseEntity<List<Product>> getAllByCategoryThree(@PathVariable Long id){
        List<Product> products = productService.findByCategoryThreeAndDeletedAtIsNull(id);
        if(products != null){
            return new ResponseEntity<List<Product>>(products, HttpStatus.OK);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PreAuthorize("permitAll()")
    @GetMapping(path = "products/category_four/{id}")
    public ResponseEntity<List<Product>> getAllByCategoryFour(@PathVariable Long id){
        List<Product> products = productService.findByCategoryFourAndDeletedAtIsNull(id);
        if(products != null){
            return new ResponseEntity<List<Product>>(products, HttpStatus.OK);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PreAuthorize("permitAll()")
    @GetMapping(path = "products/order_price_min")
    public ResponseEntity<List<Product>> orderedByPriceMin(){
        return new ResponseEntity<List<Product>>(productService.orderedByMinPrice(), HttpStatus.OK);
    }

    @PreAuthorize("permitAll()")
    @GetMapping(path = "products/order_price_max")
    public ResponseEntity<List<Product>> orderByPriceMax(){
        return new ResponseEntity<List<Product>>(productService.orderedByMaxPrice(), HttpStatus.OK);
    }

    @PreAuthorize("permitAll()")
    @GetMapping(path = "products/{id}")
    public ResponseEntity get(@PathVariable Long id){
        Product product = productService.getById(id);

        return product!=null ? new ResponseEntity<Product>(product, HttpStatus.OK):
                ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping(path = "products/add")
    public ResponseEntity add(@RequestBody Product product){
        if(product != null){
            return productService.insert(product) ? new ResponseEntity<Product>(product, HttpStatus.ACCEPTED) :
                    ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(path = "products", method = {RequestMethod.PATCH, RequestMethod.PUT})
    public ResponseEntity update(@RequestBody Product product){
        return productService.update(product) ? new ResponseEntity<Product>(product, HttpStatus.OK) :
                ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }


    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping(path = "products/{id}")
    public ResponseEntity delete(@PathVariable Long id){
        Product product = productService.getById(id);
        return productService.delete(product) ? ResponseEntity.status(HttpStatus.OK).build() :
                ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

}
