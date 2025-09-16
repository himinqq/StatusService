package com.neves.status.config;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

import feign.RequestTemplate;
import jakarta.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@SpringBootTest(classes = FeignHeaderPropagationConfig.class)
class FeignHeaderPropagationConfigTest {
	@Autowired
	private FeignHeaderPropagationConfig feignHeaderPropagationConfig;
	@Mock
	private HttpServletRequest mockRequest;
	private AutoCloseable closeable;

	private static final String SUFFIX_X = "X-Custom-Header";
	private static final String SUFFIX_x = "x-request-id";
	private static final String AUTHORIZATION = "authorization";
	private static final String NOT_PROPAGATE_HEADER = "content-type";
	private static final Map<String, String> headers = Map.of(
			AUTHORIZATION, "Bearer test-token",
			SUFFIX_x, "test-request-id-123",
			SUFFIX_X, "custom-value",
			NOT_PROPAGATE_HEADER, "application/json"
	);

	@BeforeEach
	void setUp() {
		// Mockito 초기화
		closeable = openMocks(this);

		// Mock HttpServletRequest를 RequestContextHolder에 설정
		RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(mockRequest));
	}

	@AfterEach
	void tearDown() throws Exception {
		// 테스트 후 컨텍스트 정리
		RequestContextHolder.resetRequestAttributes();
		closeable.close();
	}

	@Test
	@DisplayName("x-로 시작하는 헤더와 authorization 헤더가 Feign 요청으로 전파되어야 한다")
	void shouldPropagateMatchingHeaders() {
		// Mock HttpServletRequest 설정
		when(mockRequest.getHeaderNames()).thenReturn(Collections.enumeration(headers.keySet()));
		headers.forEach((key, value) -> when(mockRequest.getHeader(key)).thenReturn(value));

		// Feign 요청 템플릿 준비
		RequestTemplate requestTemplate = new RequestTemplate();

		// when: 인터셉터 실행
		feignHeaderPropagationConfig.requestInterceptor().apply(requestTemplate);

		// then: 결과 검증
		assertThat(requestTemplate.headers()).isNotNull();
		// 1. 전파되어야 하는 헤더 검증
		assertThat(requestTemplate.headers().get(AUTHORIZATION)).containsExactly(headers.get(AUTHORIZATION));
		assertThat(requestTemplate.headers().get(SUFFIX_x)).containsExactly(headers.get(SUFFIX_x));
		assertThat(requestTemplate.headers().get(SUFFIX_X)).containsExactly(headers.get(SUFFIX_X));

		// 2. 전파되지 않아야 하는 헤더 검증
		assertThat(requestTemplate.headers()).doesNotContainKey(NOT_PROPAGATE_HEADER);
	}

	@Test
	@DisplayName("HTTP 요청 컨텍스트가 없을 때 NullPointerException이 발생하지 않아야 한다")
	void shouldNotThrowExceptionWhenNoRequestContext() {
		// given: HTTP 요청 컨텍스트를 null로 설정
		RequestContextHolder.setRequestAttributes(null);
		RequestTemplate requestTemplate = new RequestTemplate();

		// when & then: 예외 없이 정상적으로 실행되는지 검증
		feignHeaderPropagationConfig.requestInterceptor().apply(requestTemplate);

		// 헤더가 비어있는지 확인
		assertThat(requestTemplate.headers()).isEmpty();
	}
}