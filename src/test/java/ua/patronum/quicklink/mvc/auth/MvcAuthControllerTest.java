package ua.patronum.quicklink.mvc.auth;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.ui.Model;
import ua.patronum.quicklink.restapi.auth.RegistrationRequest;

import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
class MvcAuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MvcAuthService service;

    @Test
    void showRegistrationPageTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/mvc/auth/register"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("registration"));
    }

    @Test
    void registerTest() throws Exception {
        when(service.registration(Mockito.any(Model.class), Mockito.any(RegistrationRequest.class)))
                .thenReturn("registration");

        mockMvc.perform(MockMvcRequestBuilders.post("/mvc/auth/register"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("registration"));
    }

    @Test
    void showLoginPageTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/mvc/auth/login"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("login"));
    }
}