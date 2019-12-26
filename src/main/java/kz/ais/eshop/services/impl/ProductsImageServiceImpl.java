package kz.ais.eshop.services.impl;

import kz.ais.eshop.models.ProductsImage;
import kz.ais.eshop.repositories.ProductsImageRepository;
import kz.ais.eshop.services.ProductsImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ProductsImageServiceImpl implements ProductsImageService {

    private ProductsImageRepository productsImageRepository;

    @Autowired
    public ProductsImageServiceImpl(ProductsImageRepository productsImageRepository){
        this.productsImageRepository=productsImageRepository;
    }

    public List<ProductsImage> getAllWithTrashed() {
        return productsImageRepository.findAll();
    }

    public List<ProductsImage> getAll() {
        return productsImageRepository.findAllByDeletedAtIsNull();
    }

    @Override
    public ProductsImage getById(Long id) {
        Optional<ProductsImage> imageOptional = productsImageRepository.findById(id);

        if (imageOptional.isPresent()) {
            return imageOptional.get();
        } else {
            return null;
        }
    }

    @Override
    public ProductsImage getByProductId(Long id) {
        Optional<ProductsImage> imageOptional = productsImageRepository.findImageByProductIdAndDeletedAtIsNull(id);
        if (imageOptional.isPresent()) {
            return imageOptional.get();
        } else {
            return null;
        }    }

    @Override
    public boolean add(ProductsImage image) {
        if (image == null || image.getId() != null || image.getProduct() == null) {
            return false;
        } else {
            productsImageRepository.save(image);
            return true;
        }    }

    @Override
    public boolean update(ProductsImage image, String oldImage) {
        if (image == null || image.getId() == null || image.getProduct() == null) {
            return false;
        } else {
            File file = new File(oldImage);
            if(file.exists()){
                file.delete();
            }
            productsImageRepository.save(image);
            return true;
        }    }

    @Override
    public boolean realDelete(ProductsImage image) {
        if (image == null || image.getId() == null) {
            return false;
        } else {
            File file = new File(image.getImagePath());
            if(file.exists()){
                file.delete();
            }
            productsImageRepository.delete(image);
            return true;
        }    }

    @Override
    public boolean delete(ProductsImage image) {
        if (image == null || image.getId() == null) {
            return false;
        } else {
            image.setDeletedAt(new Date());
            productsImageRepository.save(image);
            return true;
        }    }
}
