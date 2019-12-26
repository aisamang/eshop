package kz.ais.eshop.controllers;

import kz.ais.eshop.models.Product;
import kz.ais.eshop.models.ProductsImage;
import kz.ais.eshop.repositories.ProductRepository;
import kz.ais.eshop.services.ProductsImageService;
import org.apache.commons.io.FileUtils;
import org.apache.tika.Tika;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Date;
import java.util.Optional;

@RestController
@RequestMapping(path = "/api/images")
public class ProductsImageController {

    @Value("${image.not.found.src}")
    String NO_IMAGE;

    private ProductRepository productRepository;
    private ProductsImageService productsImageService;

    @Autowired
    public ProductsImageController(ProductRepository productRepository,
                                   ProductsImageService productsImageService){
        this.productRepository=productRepository;
        this.productsImageService=productsImageService;
    }

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity fileUpload(@RequestParam("file") MultipartFile file, @RequestParam Long productId)
            throws IOException {
        Optional<Product> productOptional = productRepository.findById(productId);
        if (!productOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        Product product = productOptional.get();
        ProductsImage image = productsImageService.getByProductId(productId);
        if (image == null) {
            image = new ProductsImage();
            String folders = "var/tmp/";
            String path = folders + new Date().getTime() + file.getOriginalFilename();
            File convertFile = new File(path);
            new File(folders).mkdirs();
            convertFile.createNewFile();
            FileUtils.writeByteArrayToFile(convertFile, file.getBytes());
            System.out.println(path);
            image.setImagePath(path);
            image.setProduct(product);
            productsImageService.add(image);
        } else {
            String folders = "var/tmp/";
            String path = folders + new Date().getTime() + file.getOriginalFilename();
            File convertFile = new File(path);
            new File(folders).mkdirs();
            if (convertFile.createNewFile()) {
                System.out.println("File created");
            } else {
                System.out.println("File not created");
            }
            FileUtils.writeByteArrayToFile(convertFile, file.getBytes());
            String oldImage = new String(image.getImagePath());
            image.setImagePath(path);
            image.setProduct(product);
            productsImageService.update(image,oldImage);
        }

        return ResponseEntity.status(HttpStatus.OK).body(image);
    }

    @GetMapping(value = "{id}")
    public @ResponseBody
    ResponseEntity<byte[]> getFile(@PathVariable Long id) throws IOException {
        ProductsImage image = productsImageService.getByProductId(id);
        File file = null;
        if (image != null) {
            String filename = image.getImagePath();
            file = new File(filename);
        } else {
            file = new ClassPathResource(NO_IMAGE).getFile();
        }

        Tika tika = new Tika();
        String mimeType = tika.detect(file);
        MediaType mediaType = null;
        if (mimeType.trim().equalsIgnoreCase(MediaType.IMAGE_JPEG_VALUE)) {
            mediaType = MediaType.IMAGE_JPEG;
        } else if (mimeType.trim().equalsIgnoreCase(MediaType.IMAGE_PNG_VALUE)) {
            mediaType = MediaType.IMAGE_PNG;
        }
        return ResponseEntity.ok().contentType(mediaType).body(Files.readAllBytes(file.toPath()));
    }
}
