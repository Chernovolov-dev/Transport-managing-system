package ua.nure.diploma.configs;

import org.junit.Assert;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import ua.nure.diploma.models.User;
import ua.nure.diploma.services.UserService;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.logout;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class SecurityConfigsTest {

    @Autowired
    private MockMvc mockMvc;

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

    @Test
    @DisplayName("When trying to login with correct username, with role of logist, but wrong password")
    void testLoginWithWrongPassword() throws Exception{

        User user=new User();

        //Data we want to enter
        user.setUserName("Vladislav");
        user.setUserPassword("Custom");

        UserDetails userDetails=userService.loadUserByUsername(user.getUserName());

        Authentication authentication=new UsernamePasswordAuthenticationToken(user.getUserName(),
                "logist",userDetails.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(authentication);

        Assert.assertNotEquals(SecurityContextHolder.getContext().getAuthentication().getCredentials(),
                user.getUserPassword());
    }

    @Test
    @DisplayName("When trying to login with correct data")
    void testLoginWithCorrectData(){

        User user=new User();
        user.setUserName("Dmitriy");
        user.setUserPassword("manager");

        UserDetails userDetails=userService.loadUserByUsername(user.getUserName());

        Authentication authentication=new UsernamePasswordAuthenticationToken(user.getUserName(),
                "manager",userDetails.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(authentication);

        Assert.assertEquals(SecurityContextHolder.getContext().getAuthentication().getCredentials(),
                user.getUserPassword());
    }

    @Test
    @DisplayName("When trying to logout no matter from which endpoint")
    void testLogout() throws Exception{

        mockMvc.perform(logout());
    }

}
