package ra.jpa.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ra.jpa.dto.response.ErrorResponse;
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
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleFormRegister(MethodArgumentNotValidException e){
        System.out.println(e);
        Map<String, String> map = new HashMap<>();
        e.getFieldErrors().forEach(fieldError ->{
            map.put(fieldError.getField(), fieldError.getDefaultMessage());
        });
        Map<String , Object> error = new HashMap<>();
        error.put("errors",map);
        error.put("code", 400);
        error.put("message", HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(new ErrorResponse(error),HttpStatus.BAD_REQUEST);
    }
}
