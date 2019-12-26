package kz.ais.eshop.services.impl;

import kz.ais.eshop.models.Characteristic;
import kz.ais.eshop.repositories.CharacteristicRepository;
import kz.ais.eshop.services.CharacteristicService;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManagerFactory;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class CharacteristicServiceImpl implements CharacteristicService {

    private CharacteristicRepository characteristicRepository;
    private SessionFactory hibernateFactory;

    @Autowired
    public CharacteristicServiceImpl(EntityManagerFactory factory,
                                     CharacteristicRepository characteristicRepository){
        if (factory.unwrap(SessionFactory.class) == null) {
            throw new NullPointerException("factory is not a hibernate factory");
        }
        this.hibernateFactory = factory.unwrap(SessionFactory.class);
        this.characteristicRepository=characteristicRepository;
    }

    @Override
    public List<Characteristic> getAll() {
        Session session = hibernateFactory.openSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Characteristic> criteriaQuery = criteriaBuilder.createQuery(Characteristic.class);
        Root<Characteristic> root = criteriaQuery.from(Characteristic.class);

        Predicate predicate = criteriaBuilder.isNull(root.get("deletedAt"));
        criteriaQuery.where(predicate);
        criteriaQuery.orderBy(criteriaBuilder.desc(root.get("id")));
        List<Characteristic> characteristics = session.createQuery(criteriaQuery).list();

        session.close();
        return characteristics;
    }

    @Override
    public Characteristic getById(Long id) {
        Optional<Characteristic> characteristicOptional = characteristicRepository.findById(id);

        if (characteristicOptional.isPresent()) {
            return characteristicOptional.get();
        } else {
            return null;
        }
    }

    @Override
    public boolean add(Characteristic characteristic) {
        if (characteristic == null || characteristic.getId() != null) {
            return false;
        } else {
            characteristicRepository.save(characteristic);
            return true;
        }
    }

    @Override
    public boolean update(Characteristic characteristic) {
        if (characteristic == null || characteristic.getId() == null) {
            return false;
        } else {
            characteristicRepository.save(characteristic);
            return true;
        }
    }

    @Override
    public boolean delete(Characteristic characteristic) {
        if (characteristic == null || characteristic.getId() == null) {
            return false;
        } else {
            characteristic.setDeletedAt(new Date());
            characteristicRepository.save(characteristic);
            return true;
        }
    }

    @Override
    public boolean realDelete(Characteristic characteristic) {
        if (characteristic == null || characteristic.getId() == null) {
            return false;
        } else {
            characteristicRepository.delete(characteristic);
            return true;
        }
    }

    @Override
    public Characteristic getByName(String name) {
        Optional<Characteristic> characteristicOptional = characteristicRepository.findByNameAndDeletedAtIsNull(name);

        if (characteristicOptional.isPresent()) {
            return characteristicOptional.get();
        } else {
            return null;
        }
    }
}
