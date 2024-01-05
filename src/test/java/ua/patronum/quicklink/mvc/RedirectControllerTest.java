package ua.patronum.quicklink.mvc;

import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

class RedirectControllerTest {

    private final RedirectController controller = new RedirectController();
    private final MockMvc mockMvc = MockMvcBuilders.standaloneSetup(controller).build();

    @Test
    void testRedirectToHomePage() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/"))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/mvc/home"));
    }
}
