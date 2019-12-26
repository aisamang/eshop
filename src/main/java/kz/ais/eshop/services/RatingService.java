package kz.ais.eshop.services;

import kz.ais.eshop.models.Rating;

import java.util.List;

public interface RatingService {

    List<Rating> getAll();

    Rating getById(Long id);

    boolean insert(Rating rating);

    boolean update(Rating rating);

    boolean delete(Rating rating);

    boolean realDelete(Rating rating);
}
