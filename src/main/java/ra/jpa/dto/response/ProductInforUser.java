package ra.jpa.dto.response;

import lombok.Builder;
import lombok.Data;
import ra.jpa.entity.Product;

@Data
@Builder
public class ProductInforUser {
    private Integer id;
    private String name;
    private Double price;
    private Integer stock;
    private String imageUrl;
    private String description;
    public static ProductInforUser map(Product product){
        return ProductInforUser
                .builder()
                .id(product.getId())
                .name(product.getName())
                .price(product.getPrice())
                .stock(product.getStock())
                .description(product.getDescription())
                .imageUrl(product.getImageUrl())
                .build();
    }
}
