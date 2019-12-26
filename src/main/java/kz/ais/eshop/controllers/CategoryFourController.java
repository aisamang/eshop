package kz.ais.eshop.controllers;

import kz.ais.eshop.models.CategoryFour;
import kz.ais.eshop.services.CategoryFourService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api")
public class CategoryFourController {

    private CategoryFourService categoryFourService;

    @Autowired
    public CategoryFourController(CategoryFourService categoryFourService){
        this.categoryFourService=categoryFourService;
    }

    @PreAuthorize("permitAll()")
    @GetMapping(path = "categoryfour/all")
    public ResponseEntity<List<CategoryFour>> index(){
        return new ResponseEntity<List<CategoryFour>>(categoryFourService.getAll(), HttpStatus.OK);
    }

    @PreAuthorize("permitAll()")
    @GetMapping(path = "categoryfour/{id}")
    public ResponseEntity get(@PathVariable Long id){
        CategoryFour categoryFour = categoryFourService.getById(id);

        return categoryFour!=null ? new ResponseEntity<CategoryFour>(categoryFour, HttpStatus.OK):
                ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping(path = "categoryfour/add")
    public ResponseEntity add(@RequestBody CategoryFour categoryFour){
        if(categoryFour != null){
            return categoryFourService.insert(categoryFour) ? new ResponseEntity<CategoryFour>(categoryFour, HttpStatus.ACCEPTED) :
                    ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(path = "categoryfour", method = {RequestMethod.PATCH, RequestMethod.PUT})
    public ResponseEntity update(@RequestBody CategoryFour categoryFour){
        return categoryFourService.update(categoryFour) ? new ResponseEntity<CategoryFour>(categoryFour, HttpStatus.OK) :
                ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping(path = "categoryfour/{id}")
    public ResponseEntity delete(@PathVariable Long id){
        CategoryFour categoryFour = categoryFourService.getById(id);
        return categoryFourService.delete(categoryFour) ? ResponseEntity.status(HttpStatus.OK).build() :
                ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}
