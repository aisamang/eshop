package kz.ais.eshop.controllers;

import kz.ais.eshop.models.Rating;
import kz.ais.eshop.services.RatingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api")
public class RatingController {

    private RatingService ratingService;

    public RatingController(RatingService ratingService){
        this.ratingService = ratingService;
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping(path = "rating/all")
    public ResponseEntity<List<Rating>> getAll(){
        return new ResponseEntity<List<Rating>>(ratingService.getAll(), HttpStatus.OK);
    }

    @GetMapping(path = "rating/{id}")
    public ResponseEntity get(@PathVariable Long id){
        Rating rating = ratingService.getById(id);

        return rating != null ? new ResponseEntity<Rating>(rating, HttpStatus.OK) :
                ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping(path = "rating/{id}")
    public ResponseEntity delete(@PathVariable Long id){
        Rating rating = ratingService.getById(id);

        return ratingService.delete(rating) ? ResponseEntity.status(HttpStatus.OK).build() :
                ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @RequestMapping(path = "rating", method = {RequestMethod.PATCH, RequestMethod.PUT})
    public ResponseEntity update(@RequestBody Rating rating){
        return ratingService.update(rating) ? new ResponseEntity<Rating>(rating, HttpStatus.OK) :
                ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @PostMapping(path = "rating/add")
    public ResponseEntity add(@RequestBody Rating rating){
        return ratingService.insert(rating) ? new ResponseEntity<Rating>(rating, HttpStatus.ACCEPTED) :
                ResponseEntity.status(HttpStatus.NOT_FOUND).build();

    }

}
