package ra.jpa.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ra.jpa.entity.Product;
@Repository
public interface ProductRepository extends JpaRepository<Product,Integer> {

}
