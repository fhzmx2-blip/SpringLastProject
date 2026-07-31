package com.sist.aop;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.*;

import javax.servlet.http.HttpServletRequest;

import com.sist.service.*;
import com.sist.vo.*;

import lombok.RequiredArgsConstructor;

@Aspect
@Component
@RequiredArgsConstructor

public class FooterCommonsAspect {
	private final FoodService fService;

	@After("execution(* com.sist.web.*Controller.*(..))")
	public void sendData() {

		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
				.getRequest();
		List<FoodVO> fList = fService.foodHit7Data();
		request.setAttribute("fList", fList);
	}
}