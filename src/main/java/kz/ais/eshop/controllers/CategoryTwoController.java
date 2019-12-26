package kz.ais.eshop.controllers;

import kz.ais.eshop.models.CategoryTwo;
import kz.ais.eshop.services.CategoryTwoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api")
public class CategoryTwoController {

    private CategoryTwoService categoryTwoService;

    @Autowired
    public CategoryTwoController(CategoryTwoService categoryTwoService){
        this.categoryTwoService=categoryTwoService;
    }

    @PreAuthorize("permitAll()")
    @GetMapping(path = "categorytwo/all")
    public ResponseEntity<List<CategoryTwo>> index(){
        return new ResponseEntity<List<CategoryTwo>>(categoryTwoService.getAll(), HttpStatus.OK);
    }

    @PreAuthorize("permitAll()")
    @GetMapping(path = "categorytwo/{id}")
    public ResponseEntity get(@PathVariable Long id){
        CategoryTwo categoryTwo = categoryTwoService.getById(id);

        return categoryTwo!=null ? new ResponseEntity<CategoryTwo>(categoryTwo, HttpStatus.OK):
                ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping(path = "categorytwo/add")
    public ResponseEntity add(@RequestBody CategoryTwo categoryTwo){
        if(categoryTwo != null){
            return categoryTwoService.insert(categoryTwo) ? new ResponseEntity<CategoryTwo>(categoryTwo, HttpStatus.ACCEPTED) :
                    ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(path = "categorytwo", method = {RequestMethod.PATCH, RequestMethod.PUT})
    public ResponseEntity update(@RequestBody CategoryTwo categoryTwo){
        return categoryTwoService.update(categoryTwo) ? new ResponseEntity<CategoryTwo>(categoryTwo, HttpStatus.OK) :
                ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping(path = "categorytwo/{id}")
    public ResponseEntity delete(@PathVariable Long id){
        CategoryTwo categoryTwo = categoryTwoService.getById(id);
        return categoryTwoService.delete(categoryTwo) ? ResponseEntity.status(HttpStatus.OK).build() :
                ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}
