package kz.ais.eshop.controllers;

import kz.ais.eshop.models.ProductsLike;
import kz.ais.eshop.models.User;
import kz.ais.eshop.services.ProductsLikeService;
import kz.ais.eshop.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api")
public class ProductLikeController {

    private ProductsLikeService productsLikeService;
    private UserService userService;

    @Autowired
    public ProductLikeController(ProductsLikeService productsLikeService,
                                 UserService userService){
        this.productsLikeService=productsLikeService;
        this.userService=userService;
    }

    @GetMapping(path = "product_likes/{id}")
    public ResponseEntity get(@PathVariable Long id) {
        ProductsLike productsLike = productsLikeService.getById(id);

        return productsLike != null ? new ResponseEntity<ProductsLike>(productsLike, HttpStatus.OK) :
                ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @GetMapping(path = "product_likes/get/{userId}")
    public ResponseEntity<ProductsLike> getAll(@PathVariable Long userId) {
        if(userId!=null){
            User user = userService.getById(userId);
            return user != null ? new ResponseEntity<ProductsLike>(productsLikeService.getProductLikeByUserId(userId), HttpStatus.OK)
                    : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @DeleteMapping(path = "product_likes/{id}")
    public ResponseEntity delete(@PathVariable Long id) {
        ProductsLike productsLike = productsLikeService.getById(id);
        return productsLikeService.delete(productsLike) ? ResponseEntity.status(HttpStatus.OK).build() :
                ResponseEntity.status(HttpStatus.NOT_FOUND).build();

    }

    @PostMapping(path = "product_likes/add")
    public ResponseEntity add(@RequestBody ProductsLike productsLike){
        if(productsLike != null){
            return productsLikeService.insert(productsLike) ? new ResponseEntity<ProductsLike>(productsLike, HttpStatus.ACCEPTED) :
                    ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @RequestMapping(path = "product_likes/{id}", method = {RequestMethod.PATCH, RequestMethod.PUT})
    public ResponseEntity update(@RequestBody ProductsLike productsLike){
        return productsLikeService.update(productsLike) ? new ResponseEntity<ProductsLike>(productsLike, HttpStatus.OK) :
                ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

}
