package quicklink.mvc;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.SpringBootMockMvcBuilderCustomizer;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ua.patronum.quicklink.QuickLinkApplication;
import ua.patronum.quicklink.restapi.auth.RegistrationRequest;

@SpringBootTest(classes = QuickLinkApplication.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
class MvcAuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
     void testShowRegistrationPage() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/auth/register"))
               .andExpect(MockMvcResultMatchers.status().isOk())
               .andExpect(MockMvcResultMatchers.view().name("registration"));
    }

    @Test
    void testSuccessfulRegistration() throws Exception {
        // Given
        RegistrationRequest registrationRequest = new RegistrationRequest
                ("testUser","testPassword","testPassword");

        // When & Then
        mockMvc.perform(MockMvcRequestBuilders.post("/auth/register")
               .flashAttr("registrationRequest", registrationRequest))
               .andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
    }

    @Test
    void testFailedRegistration() throws Exception {
        // Given
        RegistrationRequest registrationRequest = new RegistrationRequest
                ("testUser","testPassword","testPassword");

        // When & Then
        mockMvc.perform(MockMvcRequestBuilders.post("/auth/register")
               .flashAttr("registrationRequest", registrationRequest))
               .andExpect(MockMvcResultMatchers.status().isOk())
               .andExpect(MockMvcResultMatchers.view().name("registration"))
               .andExpect(MockMvcResultMatchers.model().attributeExists("error"));
    }

    @Test
    void testShowLoginPage() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/auth/login"))
               .andExpect(MockMvcResultMatchers.status().isOk())
               .andExpect(MockMvcResultMatchers.view().name("login"));
    }
}