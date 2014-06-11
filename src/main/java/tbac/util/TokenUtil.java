package tbac.util;

import org.apache.tomcat.util.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.security.GeneralSecurityException;

/**
 * @author kawasima
 */
public class TokenUtil {
    private static final Logger logger = LoggerFactory.getLogger(TokenUtil.class);
    private static final String key = "This is secret!!!";
    private static final SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(), "HmacSHA256");

    public static String createToken(HttpServletRequest request, String resourcePath) throws GeneralSecurityException {
        HttpSession session = request.getSession();

        // This token will be expired after 30 sec.
        long expire = System.currentTimeMillis() + 30 * 1000;
        String token = session.getId() + "$" + resourcePath + "$" + expire;
        Mac mac = Mac.getInstance("HmacSHA256");
        mac.init(secretKey);

        return expire + "$" + Base64.encodeBase64String(mac.doFinal(token.getBytes()));
    }

    public static boolean validToken(HttpServletRequest request) {
        String token = request.getParameter("token");
        HttpSession session = request.getSession(false);
        if (token == null || session == null) {
            logger.error("token or session is null.");
            return false;
        }
        String[] tokens = token.split("\\$", 2);
        try {
            long expires = Long.parseLong(tokens[0]);

            if (System.currentTimeMillis() > expires) {
                logger.error("this token is expired.");
                return false;
            }

            Mac mac = Mac.getInstance("HmacSHA256");
            mac.init(secretKey);
            String hash = Base64.encodeBase64String(
                    mac.doFinal(
                            (session.getId() + "$"
                                    + request.getRequestURI() + "$"
                                    + expires).getBytes()));

            logger.info("expected=" + tokens[1] + ", actual=" + hash);
            return hash.equals(tokens[1]);
        } catch(NumberFormatException ex) {
            logger.error("expires is missing.");
            return false;
        } catch(GeneralSecurityException ex) {
            logger.error("hmac error.");
            return false;
        }

    }

}
