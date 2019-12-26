package kz.ais.eshop.controllers;

import kz.ais.eshop.models.CategoryThree;
import kz.ais.eshop.services.CategoryThreeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api")
public class CategoryThreeController {

    private CategoryThreeService categoryThreeService;

    @Autowired
    public CategoryThreeController(CategoryThreeService categoryThreeService){
        this.categoryThreeService=categoryThreeService;

    }

    @PreAuthorize("permitAll()")
    @GetMapping(path = "categorythree/all")
    public ResponseEntity<List<CategoryThree>> index(){
        return new ResponseEntity<List<CategoryThree>>(categoryThreeService.getAll(), HttpStatus.OK);
    }

    @PreAuthorize("permitAll()")
    @GetMapping(path = "categorythree/{id}")
    public ResponseEntity get(@PathVariable Long id){
        CategoryThree categoryThree = categoryThreeService.getById(id);

        return categoryThree!=null ? new ResponseEntity<CategoryThree>(categoryThree, HttpStatus.OK):
                ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping(path = "categorythree/add")
    public ResponseEntity add(@RequestBody CategoryThree categoryThree){
        if(categoryThree != null){
            return categoryThreeService.insert(categoryThree) ? new ResponseEntity<CategoryThree>(categoryThree, HttpStatus.ACCEPTED) :
                    ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(path = "categorythree", method = {RequestMethod.PATCH, RequestMethod.PUT})
    public ResponseEntity update(@RequestBody CategoryThree categoryThree){
        return categoryThreeService.update(categoryThree) ? new ResponseEntity<CategoryThree>(categoryThree, HttpStatus.OK) :
                ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping(path = "categorythree/{id}")
    public ResponseEntity delete(@PathVariable Long id){
        CategoryThree categoryThree = categoryThreeService.getById(id);
        return categoryThreeService.delete(categoryThree) ? ResponseEntity.status(HttpStatus.OK).build() :
                ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}
