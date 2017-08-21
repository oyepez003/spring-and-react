package com.github.services.boot;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.map.repository.config.EnableMapRepositories;
import org.springframework.http.HttpStatus;
/**
 * Basic spring main app.
 * @author omar.yepez
 *
 */
@SpringBootApplication
@ComponentScan(basePackages = {"com.github.services"})
@EnableMapRepositories(basePackages = {"com.github.services.repositories"})
public class App {
	
	/**
	 * App start
	 * @param args
	 */
	public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }
	
	
	/**
	 * CORS DISABLED FOR THE APP (React in domain:port = localhost:3000).
	 * Change it if you change the client domain and port values.
	 * @param origin
	 * @return
	 */
	@Bean
	FilterRegistrationBean corsFilter(
			@Value("${tagit.origin:http://localhost:3000}") String origin) {
		return new FilterRegistrationBean(new Filter() {
			public void doFilter(ServletRequest req, ServletResponse res,
					FilterChain chain) throws IOException, ServletException {
				HttpServletRequest request = (HttpServletRequest) req;
				HttpServletResponse response = (HttpServletResponse) res;
				String method = request.getMethod();
				// this origin value could just as easily have come from a database
				response.setHeader("Access-Control-Allow-Origin", origin);
				response.setHeader("Access-Control-Allow-Methods","POST,GET,OPTIONS,DELETE");
				response.setHeader("Access-Control-Max-Age", Long.toString(60 * 60));
				response.setHeader("Access-Control-Allow-Credentials", "true");
				response.setHeader(
					"Access-Control-Allow-Headers",
					"Origin,Accept,X-Requested-With,Content-Type,Access-Control-Request-Method,Access-Control-Request-Headers,Authorization"
				);
				if ("OPTIONS".equals(method)) {
					response.setStatus(HttpStatus.OK.value());
				}
				else {
					chain.doFilter(req, res);
				}
			}

			public void init(FilterConfig filterConfig) {
			}

			public void destroy() {
			}
		});
	}
	
}
