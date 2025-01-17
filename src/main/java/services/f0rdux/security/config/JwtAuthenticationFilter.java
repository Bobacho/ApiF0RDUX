package services.f0rdux.security.config;

import java.io.IOException;

import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import services.f0rdux.security.services.JwtService;
import services.f0rdux.security.services.UserDetailsServiceImpl;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
	@Lazy
	private final JwtService jwtService;
	private final UserDetailsServiceImpl userDetailsService;
	@Lazy
	private final HandlerExceptionResolver handlerExceptionResolver;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
			FilterChain filterChain) throws ServletException, IOException {
		final String authHeader = request.getHeader("Authorization");

		if (authHeader == null || !authHeader.startsWith("Bearer ")) {
			filterChain.doFilter(request, response);
			return;
		}
		try {
			final String jwt = authHeader.substring(7);
			final String userEmail = jwtService.extractUsername(jwt);

			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			if (userEmail != null && authentication == null) {
				UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);
				if (jwtService.isTokenValid(jwt, userDetails)) {
					UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
							userDetails,
							null,
							userDetails.getAuthorities());

					authToken.setDetails(
							new WebAuthenticationDetailsSource().buildDetails(request));
					SecurityContextHolder.getContext().setAuthentication(authToken);
				} else {
					response.setStatus(401);
				}
			} else {
				response.setStatus(401);
			}

			filterChain.doFilter(request, response);
		} catch (Exception exception) {
			response.setStatus(401);
			System.out.println(exception.getMessage());
			handlerExceptionResolver.resolveException(request, response, null, exception);
		}
	}
}
