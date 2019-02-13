package com.sesame.services.appointments;

import java.util.List;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Default (white label) error response.
 * 
 * @author ngaspar
 * @version 1.0
 */
@RestController
public class DefaultErrorController implements ErrorController {

	@RequestMapping("/error")
	public List<String> handleError() {
		return BusinessRules.API_ENDPOINT_PATHS;
	}

	@Override
	public String getErrorPath() {
		return "/error";
	}
}