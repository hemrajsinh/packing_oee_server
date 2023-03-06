package in.sparklogic.util;

import java.util.Date;

import org.springframework.stereotype.Component;

import in.sparklogic.constants.Constants;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JWTHelper {
	
	public String generateJWT(String subject, Object claim){
		return  Jwts.builder().setSubject(subject).claim("user", claim)
		.setIssuedAt(new Date())
		.setExpiration(null)
        .signWith(SignatureAlgorithm.HS256, Constants.SECRET_KEY).compact();
	}
	

}
