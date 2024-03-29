import com.hdu.hdufpga.service.AuthService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
public class TestAuth {
    @Resource
    AuthService authService;
    @Test
    public void test() {
//        System.out.println(authService.loadUserByUsername("1919810"));
    }

}
