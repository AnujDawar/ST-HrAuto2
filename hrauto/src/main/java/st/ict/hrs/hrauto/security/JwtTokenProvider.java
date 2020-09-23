package st.ict.hrs.hrauto.security;

import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import st.ict.hrs.hrauto.entity.CustomLdapLoginUserDetails;

@CrossOrigin(origins = "http://localhost:4200")
@Component
public class JwtTokenProvider {

    //private static final Logger logger = LoggerFactory.getLogger(JwtTokenProvider.class);
    private static Logger logger = LogManager.getLogger(JwtTokenProvider.class.getName());

    @Value("${jwtSecret}")
    private String jwtSecret;

    @Value("${jwtExpirationInMs}")
    private int jwtExpirationInMs;

    public String generateToken(Authentication authentication) {

    	//LdapUserDetailsImpl userPrincipal = (LdapUserDetailsImpl) authentication.getPrincipal();
    	
		//Authentication auth = SecurityContextHolder.getContext().getAuthentication();    	    
		CustomLdapLoginUserDetails userPrincipal = (CustomLdapLoginUserDetails)authentication.getPrincipal();
    	 
        Date now = new Date();
        System.out.println(now);
        Date expiryDate = new Date(now.getTime() + jwtExpirationInMs);
        System.out.println(expiryDate);
        return Jwts.builder()
                .setSubject(userPrincipal.getUsername() +"#_#"+ userPrincipal.getEmployeeNumber())
                .setIssuedAt(new Date())
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    public String getUsernameEmployeeNumberFromJWT(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }

    public boolean validateToken(String authToken) 
    {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
            return true;
        } catch (SignatureException ex) {
            logger.error("Invalid JWT signature");
        } catch (MalformedJwtException ex) {
            logger.error("Invalid JWT token");
        } catch (ExpiredJwtException ex) {
            logger.error("Expired JWT token");
        } catch (UnsupportedJwtException ex) {
            logger.error("Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            logger.error("JWT claims string is empty.");
        }
        return false;
    }
}
