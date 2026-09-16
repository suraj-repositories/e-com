package com.oranbyte.ecom.config;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.oranbyte.ecom.util.ApiResponse;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class JwtFilter extends OncePerRequestFilter {

	@Autowired
	private JwtUtil jwtUtil;

	@Autowired
	private CustomerUserDetailService customerUserDetailService;

	@Autowired
	private ObjectMapper objectMapper;

	private String userName = null;
	private Claims claims;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		if (request.getServletPath().matches("/user/login|/user/forgotPassword|/user/signup")) {

			filterChain.doFilter(request, response);
			return;
		}

		String authorizationHeader = request.getHeader("Authorization");

		if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {

			filterChain.doFilter(request, response);
			return;
		}

		String token = authorizationHeader.substring(7);

		try {

			String userName = jwtUtil.extractUsername(token);

			if (userName != null && SecurityContextHolder.getContext().getAuthentication() == null) {

				UserDetails userDetails = customerUserDetailService.loadUserByUsername(userName);

				if (jwtUtil.validateToken(token, userDetails)) {

					UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
							userDetails, null, userDetails.getAuthorities());

					authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

					SecurityContextHolder.getContext().setAuthentication(authentication);
				}
			}

			filterChain.doFilter(request, response);

		} catch (ExpiredJwtException ex) {
			log.warn("JWT token expired for request: {}", request.getRequestURI());
			sendErrorResponse(response, HttpServletResponse.SC_UNAUTHORIZED,
					"JWT token has expired. Please login again.");
		} catch (JwtException ex) {
			log.warn("Invalid JWT token: {}", ex.getMessage());
			sendErrorResponse(response, HttpServletResponse.SC_UNAUTHORIZED, "Invalid JWT token. Please login again.");
		} catch (Exception ex) {
			log.error("JWT authentication error", ex);
			sendErrorResponse(response, HttpServletResponse.SC_UNAUTHORIZED, "Authentication failed.");
		}
	}

	private void sendErrorResponse(HttpServletResponse response, int status, String message) throws IOException {

		response.setStatus(status);
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");

		ApiResponse<?> apiResponse = new ApiResponse<>(false, message, null);

		objectMapper.writeValue(response.getWriter(), apiResponse);
	}

	public boolean isAdmin() throws Exception {
		log.info("Claims : {} | {}", claims, (String) claims.get("role"));
		return "admin".equalsIgnoreCase((String) claims.get("role"));
	}

	public boolean isUser() throws Exception {
		return "user".equalsIgnoreCase((String) claims.get("role"));
	}

	public String getCurrentUser() {
		return userName;
	}

}
