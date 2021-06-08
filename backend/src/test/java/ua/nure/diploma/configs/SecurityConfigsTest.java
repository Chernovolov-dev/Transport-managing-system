package ua.nure.diploma.configs;

import org.junit.Assert;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import ua.nure.diploma.repositories.UserRepository;
import ua.nure.diploma.services.UserService;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class SecurityConfigsTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Test
    @DisplayName("When calling the /deliveryRequest endpoint without authorization we expect to get a 301 Redirection.")
    void testUnauthorizedDeliveryRequestEndpoint() throws Exception{

        mockMvc.perform(get("/deliveryRequest/**"))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    @DisplayName("When calling the /routeSheet** endpoint without authorization we expect to get a 301 Redirection.")
    void testUnauthorizedRouteSheetEndpoint() throws Exception{

        mockMvc.perform(get("/routeSheet/**"))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    @DisplayName("When calling the deliveryRequest/** endpoint with role of the logist")
    @WithMockUser(roles = "LOGIST")
    void testLogistDeliveryRequestEndpoint() throws Exception{

        mockMvc.perform(get("/deliveryRequest/**"))
                .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("When calling the routeSheet/** endpoint with role of the request manager")
    @WithMockUser(roles = "REQUEST_MANAGER")
    void testRequestManagerRouteSheetEndpoint() throws Exception{

        mockMvc.perform(get("/routeSheet/**"))
                .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("When calling the routeSheet/manage endpoint with role of the logist")
    @WithMockUser(roles="LOGIST")
    void testLogistRouteSheetManageEndpoint() throws Exception{

        mockMvc.perform(get("/routeSheet/manage"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("When calling the deliveryRequest/all endpoint with role of the request manager")
    @WithMockUser(roles="REQUEST_MANAGER")
    void testRequestManagerDeliveryRequestAllEndpoint() throws Exception{

        mockMvc.perform(get("/deliveryRequest/all"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("When trying to login with wrong username we expect to get UsernameNotFoundException")
    void testLoginWithWrongUsername(){

        String actualName="Custom";

        Assert.assertThrows(UsernameNotFoundException.class, ()-> userService.loadUserByUsername(actualName));
    }
}
