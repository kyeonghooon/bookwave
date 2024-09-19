package com.library.bookwave.handler;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.library.bookwave.dto.PrincipalDTO;
import com.library.bookwave.handler.exception.UnAuthorizedException;
import com.library.bookwave.utils.Define;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Component
public class AuthInterceptor implements HandlerInterceptor {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

		HttpSession session = request.getSession();
		PrincipalDTO principal = (PrincipalDTO) session.getAttribute(Define.PRINCIPAL);
		// TODO 아직 principal이 완벽하지 않아서 주석처리
//		if (principal == null) {
//			throw new UnAuthorizedException("로그인이 필요합니다", HttpStatus.UNAUTHORIZED);
//		}
		return true;
	}

	
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
		HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
		HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
	}

}
