package in.sparklogic.config;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import in.sparklogic.constants.Constants;
import in.sparklogic.globalexceptions.ErrorCodes;
import in.sparklogic.globalexceptions.GeneralExceptions;
import in.sparklogic.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

@Component
public class RequestInterceptor extends HandlerInterceptorAdapter {

	/**
	 * This is not a good practice to use sysout. Always integrate any logger
	 * with your application. We will discuss about integrating logger with
	 * spring boot application in some later article
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object object) throws Exception {
		validateJWT(request, response);
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object object, ModelAndView model)
			throws Exception {
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object object, Exception arg3)
			throws Exception {
	}

	private void validateJWT(final ServletRequest req, final ServletResponse res)
			throws IOException, ServletException, Exception {

		final HttpServletRequest request = (HttpServletRequest) req;
		final HttpServletResponse response = (HttpServletResponse) res;
		final String authHeader = request.getHeader("Authorization");

		if ("OPTIONS".equals(request.getMethod())) {
			response.setStatus(HttpServletResponse.SC_OK);
			return;
		} else {
			if (authHeader == null) {
				throw GeneralExceptions.MissingToken(Object.class, "missing token",
						ErrorCodes.GeneralErrorCodes.NULL_OR_EMPTY.getErrorCode()).get();
			} else if (!authHeader.startsWith(Constants.PREFIX_KEY)) {
				throw GeneralExceptions.InvalidToken(Object.class, "invalid token",
						ErrorCodes.GeneralErrorCodes.UNKNOWN_ERROR.getErrorCode()).get();
			}

			final String token = authHeader.substring(Constants.PREFIX_KEY.length() + 1);

			try {
				final Claims claims = Jwts.parser().setSigningKey(Constants.SECRET_KEY).parseClaimsJws(token).getBody();
				request.setAttribute("claims", claims);
				request.setAttribute("user", claims.get("user"));
				System.out.println("in interceptor");
				System.out.println("DataHHH:" + (Map<String, Object>) claims);

				Map<String, Object> map = ((Map<String, Object>) claims.get("user"));
				User user = new User(map);

				reAuthenticate(user.getStUserName(), "123", user);

				return;
			} catch (final Exception e) {
				throw GeneralExceptions.InvalidToken(Object.class, "invalid token",
						ErrorCodes.GeneralErrorCodes.UNKNOWN_ERROR.getErrorCode()).get();
			}
		}
	}
	// }

	// https://stackoverflow.com/a/25440389
	// https://programmingmitra.blogspot.in/2017/02/automatic-jpa-auditing-persisting-audit-logs-automatically-using-entityListeners.html

	@Autowired
	static UserDetailsService userDetailsService;

	public static void reAuthenticate(final String username, final String password, User user) {
		UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username, password);
		authToken.setDetails(user);
		SecurityContextHolder.getContext().setAuthentication(authToken);
	}
}