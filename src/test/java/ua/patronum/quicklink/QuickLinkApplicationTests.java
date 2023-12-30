package ua.patronum.quicklink;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
class QuickLinkApplicationTests {

    @Test
    void contextLoads() {
    }

    @Test
    void main() {
        QuickLinkApplication.main(new String[] {});
    }
}
