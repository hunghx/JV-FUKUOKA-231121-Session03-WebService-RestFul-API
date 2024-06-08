package ra.jpa.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api.com/v1/authenticated")
public class TestController {
    @GetMapping("/admin")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')") // quyền admin
    public ResponseEntity<String> test1(){
        return new ResponseEntity<>("ok", HttpStatus.OK);
    }
    @GetMapping("/user")
    @PreAuthorize("hasAuthority('ROLE_USER')") // quyền user
    public ResponseEntity<String> test2(){
        return new ResponseEntity<>("ok", HttpStatus.OK);
    }
    @GetMapping("/manager")
    @PreAuthorize("hasAuthority('ROLE_MANAGER')") // quyền manager
    public ResponseEntity<String> test3(){
        return new ResponseEntity<>("ok", HttpStatus.OK);
    }
    @GetMapping("/manager-user")
    @PreAuthorize("hasAnyAuthority('ROLE_MANAGER','ROLE_USER')") // quyền manager hoặc user
    public ResponseEntity<String> test4(){
        return new ResponseEntity<>("ok", HttpStatus.OK);
    }

}
