package ua.nure.diploma.handlers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CustomSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    private static final Logger LOGGER= LoggerFactory.getLogger(CustomSuccessHandler.class);
    private final GrantedAuthority requestManagerAuthority=new SimpleGrantedAuthority("ROLE_REQUEST_MANAGER");

    private RedirectStrategy redirectStrategy=new DefaultRedirectStrategy();

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {

        if(!isRequestManagerAuthority(authentication)){

            final String targetUrl="/routeSheet/manage";

            clearAuthenticationAttributes(request);
            LOGGER.info("Redirecting user to the following location {}",targetUrl);
            redirectStrategy.sendRedirect(request,response,targetUrl);
        }
        else{

            final String targetUrl="/deliveryRequest/all";

            clearAuthenticationAttributes(request);
            LOGGER.info("Redirecting user to the following location {}",targetUrl);
            redirectStrategy.sendRedirect(request,response,targetUrl);
        }

    }

    protected boolean isRequestManagerAuthority(final Authentication authentication){

        return !authentication.getAuthorities().isEmpty()
                &&authentication.getAuthorities().contains(requestManagerAuthority);
    }
}
