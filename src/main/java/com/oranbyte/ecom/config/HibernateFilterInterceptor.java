package com.oranbyte.ecom.config;

import org.hibernate.Session;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.persistence.EntityManager;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class HibernateFilterInterceptor implements HandlerInterceptor {

	private final EntityManager entityManager;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		Session session = entityManager.unwrap(Session.class);

		if (authentication != null
				&& authentication.getAuthorities().stream().noneMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {

			session.enableFilter("activeFilter");
		}

		return true;
	}
}