package ra.jpa.api.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ra.jpa.dto.request.FormLogin;
import ra.jpa.dto.request.FormRegister;
import ra.jpa.service.AuthenticationService;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api.com/v1/auth")
public class AuthController {
    // đăng nhập đăng kí : ko cần quyền
    // đăng nhập : trả về 1 Response : (token, id, fullname, typeToken)
    @Autowired
    private AuthenticationService authenticationService;
    @PostMapping("/sign-in")
    public ResponseEntity<?> doLogin(@RequestBody FormLogin formLogin){
        // kiểm tra thông tin có hợp lệ không
        Map<String , Object> map = new HashMap<>();
        map.put("data",authenticationService.doLogin(formLogin));
        map.put("code",200);
        map.put("status", HttpStatus.OK);
        return new ResponseEntity<>(map,HttpStatus.OK);
    }
    @PostMapping("/sign-up")
    public ResponseEntity<?> doRegister(@RequestBody FormRegister request){
        // kiểm tra thông tin có hợp lệ không

        authenticationService.register(request);
        Map<String , Object> map = new HashMap<>();
        map.put("message","Register successfully");
        map.put("code",201);
        map.put("status", HttpStatus.CREATED);
        return new ResponseEntity<>(map,HttpStatus.CREATED);
    }

}
