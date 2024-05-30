package ra.jpa.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ra.jpa.dto.request.ProductCreateRequest;
import ra.jpa.entity.Product;

import java.util.List;

public interface IProductService {
    Page<Product> findAll(Pageable pageable);
    Product findById(Integer id);
    void save(ProductCreateRequest request);
    void save(ProductCreateRequest request,Integer id);
    void deleteById(Integer id);
}
