package ra.jpa.service;

import jakarta.servlet.ServletContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import ra.jpa.dto.request.ProductCreateRequest;
import ra.jpa.entity.Product;
import ra.jpa.repository.ProductRepository;

import java.io.File;
import java.io.IOException;
import java.util.Date;


@Service
public class ProductServiceImpl implements IProductService{
    @Value("${uploadPath}")
    private String uploadPath;
    @Autowired
    private ServletContext servletContext;
    @Autowired
    public ProductRepository productRepository;
    @Override
    public Page<Product> findAll(Pageable pageable) {
        // PageAble
        return productRepository.findAll(pageable);
    }


    @Override
    public Product findById(Integer id) {
        return productRepository.findById(id).orElseThrow(() -> new RuntimeException("id not found"));
    }

    @Override
    public void save(ProductCreateRequest request) {
        // xử lí upload
        // chuyển đổi DTO -> entity
        String uploadFolder = servletContext.getRealPath("/uploads");
        File folderUpload = new File(uploadFolder);
        if (!folderUpload.exists()){
            folderUpload.mkdirs();
        }
        Product product = Product.builder()
                .name(request.getName())
                .price(request.getPrice())
                .stock(request.getStock())
                .description(request.getDescription())
                .createdAt(new Date())
                .isHidden(false)
                .build();
        if (request.getFile().getSize() !=0){
            String fileName = request.getFile().getOriginalFilename();
            try {
                FileCopyUtils.copy(request.getFile().getBytes(),new File(uploadPath+File.separator+fileName));
                FileCopyUtils.copy(request.getFile().getBytes(),new File(uploadFolder+File.separator+fileName));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            product.setImageUrl(fileName);
        }
        //luwuu lại
        productRepository.save(product);
    }

    @Override
    public void save(ProductCreateRequest request, Integer id) {
        // xử lí upload
        // chuyển đổi DTO -> entity
        String uploadFolder = servletContext.getRealPath("/uploads");
        File folderUpload = new File(uploadFolder);
        if (!folderUpload.exists()){
            folderUpload.mkdirs();
        }
        Product product = productRepository.findById(id).orElseThrow(() -> new RuntimeException("id not found"));
        product.setName(request.getName());
        product.setPrice(request.getPrice());
        product.setStock(request.getStock());
        product.setDescription(request.getDescription());
        if (request.getFile().getSize() !=0){
            String fileName = request.getFile().getOriginalFilename();
            try {
                FileCopyUtils.copy(request.getFile().getBytes(),new File(uploadPath+File.separator+fileName));
                FileCopyUtils.copy(request.getFile().getBytes(),new File(uploadFolder+File.separator+fileName));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            product.setImageUrl(fileName);
        }
        //luwuu lại
        productRepository.save(product);
    }

    @Override
    public void deleteById(Integer id) {
        productRepository.deleteById(id);
    }
}
