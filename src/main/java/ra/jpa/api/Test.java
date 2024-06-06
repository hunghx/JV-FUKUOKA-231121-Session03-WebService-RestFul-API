package ra.jpa.api;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class Test {
    public static void main(String[] args) {
        PasswordEncoder p = new BCryptPasswordEncoder();
        String pass = p.encode("123456");
        String pass2 = p.encode("123456");
        System.out.println(pass);
        System.out.println(pass2);

        boolean check1 = p.matches("123456", pass);
        boolean check2 = p.matches("123456", pass2);
        System.out.println(check1);
        System.out.println(check2);
    }
}
