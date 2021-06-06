package ua.nure.diploma.configs;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@SpringBootTest
@AutoConfigureMockMvc
class SecurityConfigsTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("When calling the /deliveryRequest endpoint without authorization we expect to get a 301 Redirection.")
    void testUnauthorizedDeliveryRequestEndpoint() throws Exception{

        mockMvc.perform(get("/deliveryRequest"))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    @DisplayName("When calling the /routeSheet endpoint without authorization we expect to get a 301 Redirection.")
    void testUnauthorizedRouteSheetEndpoint() throws Exception{

        mockMvc.perform(get("/routeSheet"))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    @DisplayName("When calling the deliveryRequest/all endpoint with role of the logist")
    @WithMockUser(roles = "LOGIST")
    void testLogistDeliveryRequestAllEndpoint() throws Exception{

        mockMvc.perform(get("/deliveryRequest/all"))
                .andExpect(status().isForbidden());
    }
}
