package kz.ais.eshop.services;

import kz.ais.eshop.models.Characteristic;

import java.util.List;

public interface CharacteristicService {

    List<Characteristic> getAll();

    Characteristic getById(Long id);

    boolean add(Characteristic characteristic);

    boolean update(Characteristic characteristic);

    boolean delete(Characteristic characteristic);

    boolean realDelete(Characteristic characteristic);

    Characteristic getByName(String name);
}
