package ra.jpa.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ra.jpa.dto.request.ProductCreateRequest;
import ra.jpa.entity.Product;
import ra.jpa.service.IProductService;

import java.util.List;

@RestController // controller cho dịch vụ web
// qui tắc dặt tên api như sau
// domain : https://www.rikkei.vn
@RequestMapping("/api.rikkei.vn/v1/products") // https://localhost:8080/api.rikkei.vn/v1/products
@RequiredArgsConstructor
@CrossOrigin("*")
public class ApiProductController {
    private final IProductService productService;
    // lấy về danh sách sản phẩm
    @GetMapping
    public ResponseEntity<List<Product>> getProducts() {
        List<Product> list = productService.findAll();
        if (list.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }else {
            return new ResponseEntity<>(list,HttpStatus.OK);
        }

        // tham số thứ nhất là phâ body gửi về
        // tham số thư 2 là 1 HttpStatus
    }
    // thêm mới product
//    @PostMapping
//    public ResponseEntity<Product> doAddJson(@RequestBody Product product){
//        return new ResponseEntity<>(productService.saveJson(product) , HttpStatus.CREATED);
//    }
    // thêm mới product
    @PostMapping
    public ResponseEntity<Product> doAddFormData(@ModelAttribute ProductCreateRequest product){
        return new ResponseEntity<>(productService.saveFormData(product) , HttpStatus.CREATED);
    }
    // lây theo id
    // edit
    @GetMapping("/{id}")
    public  ResponseEntity<Product> findById(@PathVariable Integer id){
        try {
            Product p = productService.findById(id);
            return new ResponseEntity<>(p,HttpStatus.OK);
        }catch (RuntimeException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    // update toàn phần
    @PutMapping("/{id}")
    public  ResponseEntity<Product> doUpdate(@PathVariable Integer id,@ModelAttribute ProductCreateRequest request){
        try {
            return new ResponseEntity<>(productService.updateFormData(request,id), HttpStatus.OK);
        }catch (RuntimeException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @PatchMapping("/{id}")
    public ResponseEntity<Product> doUpdatePath(@PathVariable Integer id,@ModelAttribute ProductCreateRequest request){
        try {
            return new ResponseEntity<>(productService.updateFormDataPath(request,id), HttpStatus.OK);
        }catch (RuntimeException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    // xóa
    @DeleteMapping("/{id}")
    public ResponseEntity<?> doDelete(@PathVariable Integer id){// wildcard
        try {
            productService.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch (RuntimeException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }
}
