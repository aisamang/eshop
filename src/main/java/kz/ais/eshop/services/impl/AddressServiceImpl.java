package kz.ais.eshop.services.impl;

import kz.ais.eshop.models.Address;
import kz.ais.eshop.repositories.AddressRepository;
import kz.ais.eshop.services.AddressService;
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
public class AddressServiceImpl implements AddressService {

    private AddressRepository addressRepository;
    private SessionFactory hibernateFactory;

    @Autowired
    public AddressServiceImpl(EntityManagerFactory factory,
                              AddressRepository addressRepository){
        if (factory.unwrap(SessionFactory.class) == null) {
            throw new NullPointerException("factory is not a hibernate factory");
        }
        this.hibernateFactory = factory.unwrap(SessionFactory.class);
        this.addressRepository = addressRepository;
    }

    @Override
    public List<Address> getAll() {
        Session session = hibernateFactory.openSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Address> criteriaQuery = criteriaBuilder.createQuery(Address.class);
        Root<Address> root = criteriaQuery.from(Address.class);
        Predicate predicate = criteriaBuilder.isNull(root.get("deletedAt"));
        criteriaQuery.where(predicate);
        criteriaQuery.orderBy(criteriaBuilder.desc(root.get("id")));
        List<Address> addresses = session.createQuery(criteriaQuery).list();
        session.close();
        return addresses;
    }

    @Override
    public List<Address> getAllByDelivery(Long id) {
        return addressRepository.findAllByDeliveryIdAndDeletedAtIsNull(id);
    }

    @Override
    public Address getById(Long id) {
        Optional<Address> addressOptional = addressRepository.findById(id);

        return addressOptional.orElse(null);
    }

    @Override
    public boolean insert(Address address) {
        if (address == null || address.getId() != null) {
            return false;
        } else {
            addressRepository.save(address);
            return true;
        }
    }

    @Override
    public boolean update(Address address) {
        if (address == null || address.getId() == null) {
            return false;
        } else {
            addressRepository.save(address);
            return true;
        }
    }

    @Override
    public boolean delete(Address address) {
        if (address == null || address.getId() == null){
            return false;
        } else {
            address.setDeletedAt(new Date());
            addressRepository.save(address);
            return true;
        }
    }

    @Override
    public boolean realDelete(Address address) {
        if (address == null || address.getId() == null){
            return false;
        } else {
            addressRepository.delete(address);
            return true;
        }
    }
}
