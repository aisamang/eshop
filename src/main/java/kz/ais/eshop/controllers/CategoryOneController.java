package kz.ais.eshop.controllers;

import kz.ais.eshop.models.CategoryOne;
import kz.ais.eshop.services.CategoryOneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api")
public class CategoryOneController {

    private CategoryOneService categoryOneService;

    @Autowired
    public CategoryOneController(CategoryOneService categoryOneService){
        this.categoryOneService=categoryOneService;
    }

    @PreAuthorize("permitAll()")
    @GetMapping(path = "categoryone/all")
    public ResponseEntity<List<CategoryOne>> index(){
        return new ResponseEntity<List<CategoryOne>>(categoryOneService.getAll(), HttpStatus.OK);
    }

    @PreAuthorize("permitAll()")
    @GetMapping(path = "categoryone/{id}")
    public ResponseEntity get(@PathVariable Long id){
        CategoryOne categoryOne = categoryOneService.getById(id);

        return categoryOne!=null ? new ResponseEntity<CategoryOne>(categoryOne, HttpStatus.OK):
                ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping(path = "categoryone/add")
    public ResponseEntity add(@RequestBody CategoryOne categoryOne){
        if(categoryOne != null){
            return categoryOneService.insert(categoryOne) ? new ResponseEntity<CategoryOne>(categoryOne, HttpStatus.ACCEPTED) :
                    ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(path = "categoryone", method = {RequestMethod.PATCH, RequestMethod.PUT})
    public ResponseEntity update(@RequestBody CategoryOne categoryOne){
        return categoryOneService.update(categoryOne) ? new ResponseEntity<CategoryOne>(categoryOne, HttpStatus.OK) :
                ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping(path = "categoryone/{id}")
    public ResponseEntity delete(@PathVariable Long id){
        CategoryOne categoryOne = categoryOneService.getById(id);
        return categoryOneService.delete(categoryOne) ? ResponseEntity.status(HttpStatus.OK).build() :
                ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}
