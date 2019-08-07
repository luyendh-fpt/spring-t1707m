package t1707m.spring.config;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MyAuthenticationFailureHandle extends SimpleUrlAuthenticationFailureHandler {

    private static Logger LOGGER = Logger.getLogger(MyAuthenticationFailureHandle.class.getSimpleName());

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException e)
            throws IOException, ServletException {
        LOGGER.log(Level.INFO, String.format("Loggin failure!"));
        HttpSession session = request.getSession();
        int failureCount = 1;
        try {
            failureCount = Integer.parseInt(String.valueOf(session.getAttribute("failureCount")));
            failureCount++;
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, String.format("Error with message: "), ex);
        }
        session.setAttribute("failureCount", failureCount);
        if (failureCount >= 3) {
            session.setAttribute("needCaptcha", 1);
        }
        LOGGER.log(Level.INFO, String.format("Failure count: %d", failureCount));
        super.onAuthenticationFailure(request, response, new BadCredentialsException("* Sai thông tin đăng nhập, vui lòng thử lại!"));
    }
}
