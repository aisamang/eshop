package kz.ais.eshop.services.impl;

import kz.ais.eshop.models.Delivery;
import kz.ais.eshop.repositories.DeliveryRepository;
import kz.ais.eshop.services.DeliveryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DeliveryServiceImpl implements DeliveryService {

    private DeliveryRepository deliveryRepository;

    @Autowired
    public DeliveryServiceImpl(DeliveryRepository deliveryRepository){
        this.deliveryRepository = deliveryRepository;
    }

    @Override
    public List<Delivery> getAllTrashed() {
        return deliveryRepository.findAll();
    }

    @Override
    public Delivery getById(Long id) {
        Optional<Delivery> delivery = deliveryRepository.findById(id);

        return delivery.orElse(null);
    }

    @Override
    public boolean add(Delivery delivery) {
        if (delivery == null || delivery.getId() != null ) {
            return false;
        } else {
            deliveryRepository.save(delivery);
            return true;
        }
    }

    @Override
    public boolean update(Delivery delivery) {
        if (delivery == null || delivery.getId() == null) {
            return false;
        } else {
            deliveryRepository.save(delivery);
            return true;
        }
    }

    @Override
    public boolean realDelete(Delivery delivery) {
        if (delivery == null || delivery.getId() == null) {
            return false;
        } else {
            deliveryRepository.delete(delivery);
            return true;
        }
    }
}
