package com.ubiqube.etsi.mano.filter;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

class VersionFilterTest {

	@InjectMocks
	private VersionFilter versionFilter;

	@Mock
	private HttpServletRequest request;

	@Mock
	private HttpServletResponse response;

	@Mock
	private FilterChain chain;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testDoFilter_withVersionHeader() throws IOException, ServletException {
		when(request.getHeader("Version")).thenReturn("1.0");

		versionFilter.doFilter(request, response, chain);

		verify(chain).doFilter(request, response);
		verify(response).setHeader("Version", "1.0");
	}

	@Test
	void testDoFilter_withoutVersionHeader() throws IOException, ServletException {
		when(request.getHeader("Version")).thenReturn(null);

		versionFilter.doFilter(request, response, chain);

		verify(chain).doFilter(request, response);
		verify(response, never()).setHeader(anyString(), anyString());
	}
}
