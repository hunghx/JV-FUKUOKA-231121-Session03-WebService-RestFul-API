package ra.jpa.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ra.jpa.exception.UsernamePasswordException;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice // xử lí ngoại l
public class ApiControllerAdvice {
    @ExceptionHandler(UsernamePasswordException.class)
    public ResponseEntity<?> doexceptionLogin(UsernamePasswordException e){
        Map<String, Object> map = new HashMap<>();
        map.put("code", 400);
        map.put("status", HttpStatus.BAD_REQUEST);
        map.put("messagge",e.getMessage());
        return new ResponseEntity<>(map,HttpStatus.BAD_REQUEST);
    }
}
