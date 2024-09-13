package com.library.bookwave.handler;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Component
public class AuthInterceptor implements HandlerInterceptor {

	// preHandle 동작 흐름 (스프링부트 설정파일, 설정 클래스에 등록이 되어야 함 : 특정 URL) 
	// 컨트롤러 들어 오기 전에 동작
	// true --> 컨트롤러 안으로 들여 보낸다. 
	// false --> 컨틀롤러 안으로 못 들어감 
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

		HttpSession session = request.getSession();
		//		User principal = (User) session.getAttribute(Define.PRINCIPAL);
		//		if (principal == null) {
		//			throw new UnAuthorizedException("로그인이 필요합니다", HttpStatus.UNAUTHORIZED);
		//		}
		return true;
	}

	// 뷰가 렌더링 되기 바로전에 콜백 되는 메서드 
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub
		HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
	}

	// 요청 처리가 완료된 후, 즉 뷰가 완전 렌더링이 된 후에 호출
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
		// TODO Auto-generated method stub
		HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
	}

}
