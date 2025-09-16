package com.neves.status.config;

import feign.RequestInterceptor;
import jakarta.servlet.http.HttpServletRequest;
import java.util.Collections;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Configuration
public class FeignHeaderPropagationConfig {
	private static final String SUFFIX_UP_X = "X-";
	private static final String SUFFIX_LOW_X = "x-";
	private static final String AUTHORIZATION = "authorization";

	@Bean
	public RequestInterceptor requestInterceptor() {
		return template -> {
			ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
			if (attributes == null) return;

			HttpServletRequest request = attributes.getRequest();
			Collections.list(request.getHeaderNames()).forEach(headerName -> {
				if (headerName.startsWith(SUFFIX_LOW_X)
					|| headerName.startsWith(SUFFIX_UP_X)
					|| headerName.equalsIgnoreCase(AUTHORIZATION)) {
					String headerValue = request.getHeader(headerName);
					template.header(headerName, headerValue);
				}
			});
		};
	}

}
