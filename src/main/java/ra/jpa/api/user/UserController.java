package ra.jpa.api.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ra.jpa.service.IProductService;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api.com/v1/user")
public class UserController {
    @Autowired
    private IProductService productService;
    @GetMapping("/products")
    public ResponseEntity<?> productList(){
        Map<String, Object> map = new HashMap<>();
        map.put("data", productService.findAllForUser());
        map.put("code",200);
        map.put("status", HttpStatus.OK);
        return new ResponseEntity<>(map,HttpStatus.OK);
    }
}
