package t1707m.spring.config;

import org.json.JSONObject;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CaptchaFilter extends OncePerRequestFilter {

    private static Logger LOGGER = Logger.getLogger(CaptchaFilter.class.getSimpleName());
    private static String recaptcha_secrectKey = "6LclSLEUAAAAAMdS1xGcNUQqCA5fMadVE3fmzXyN";
    private SimpleUrlAuthenticationFailureHandler failureHandler = new SimpleUrlAuthenticationFailureHandler();

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        failureHandler.setDefaultFailureUrl("/login?error");
//        filterChain.doFilter(httpServletRequest, httpServletResponse);
        HttpSession session = httpServletRequest.getSession();
        if (session.getAttribute("needCaptcha") == null) {
            filterChain.doFilter(httpServletRequest, httpServletResponse);
            return;
        }
        try {
            int needCaptcha = Integer.parseInt(String.valueOf(session.getAttribute("needCaptcha")));
            if (needCaptcha == 1) {
                String captcha = httpServletRequest.getParameter("g-recaptcha-response");
                if (captcha == null) {
                    filterChain.doFilter(httpServletRequest, httpServletResponse);
                    return;
                }
                LOGGER.log(Level.INFO, String.format("Captcha: %s", captcha));
                if (isCaptchaValid(captcha)) {
                    LOGGER.log(Level.INFO, String.format("Valid captcha!"));
                    filterChain.doFilter(httpServletRequest, httpServletResponse);
                } else {
                    LOGGER.log(Level.INFO, String.format("Invalid captcha!"));
                    failureHandler.onAuthenticationFailure(httpServletRequest, httpServletResponse, new BadCredentialsException("* Sai thông tin reCAPTCHA, vui lòng thử lại!"));
                }
            } else {
                filterChain.doFilter(httpServletRequest, httpServletResponse);
            }
        } catch (NumberFormatException ex) {
            session.removeAttribute("needCaptcha");
            LOGGER.log(Level.SEVERE, "NumberFormatException!");
            filterChain.doFilter(httpServletRequest, httpServletResponse);
        } catch (Exception ex) {
            failureHandler.onAuthenticationFailure(httpServletRequest, httpServletResponse, new BadCredentialsException("* Sai thông tin reCAPTCHA, vui lòng thử lại!"));
        }
    }

    /**
     * Validates Google reCAPTCHA V2 or Invisible reCAPTCHA.
     *
     * @param response reCAPTCHA response from client side. (g-recaptcha-response)
     * @return true if validation successful, false otherwise.
     */
    public static boolean isCaptchaValid(String response) {
        try {
            String url = "https://www.google.com/recaptcha/api/siteverify?"
                    + "secret=" + recaptcha_secrectKey
                    + "&response=" + response;
            InputStream res = new URL(url).openStream();
            BufferedReader rd = new BufferedReader(new InputStreamReader(res, Charset.forName("UTF-8")));
            StringBuilder sb = new StringBuilder();
            int cp;
            while ((cp = rd.read()) != -1) {
                sb.append((char) cp);
            }
            String jsonText = sb.toString();
            res.close();
            JSONObject json = new JSONObject(jsonText);
            return json.getBoolean("success");
        } catch (Exception e) {
            return false;
        }
    }
}
