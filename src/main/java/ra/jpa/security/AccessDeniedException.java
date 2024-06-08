package ra.jpa.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.web.access.AccessDeniedHandler;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class AccessDeniedException implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, org.springframework.security.access.AccessDeniedException ex) throws IOException, ServletException {
        log.error("Access denied fail cause :",ex.getMessage());
        response.setHeader("error","forbidden");
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(403); // lỗi từ chối quyền truy cap
        Map<String , String> map =new HashMap<>();
        map.put("error",ex.getMessage());
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.writeValue(response.getOutputStream(),map); // giống response Entity
    }
}
