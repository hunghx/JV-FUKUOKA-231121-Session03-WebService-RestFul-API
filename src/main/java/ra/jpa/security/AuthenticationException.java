package ra.jpa.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class AuthenticationException implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, org.springframework.security.core.AuthenticationException ex) throws IOException, ServletException {
        log.error("authentication fail cause :",ex.getMessage());
        response.setHeader("error","Authentication");
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(401); // lỗi ko xác minh được người dùng
        Map<String , String> map =new HashMap<>();
        map.put("error",ex.getMessage());
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.writeValue(response.getOutputStream(),map); // giống response Entity
         }
}
