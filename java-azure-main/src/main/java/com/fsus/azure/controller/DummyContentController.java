package com.fsus.azure.controller;

import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller to print some dummy content
 * 
 * @author arshad.azeem
 *
 */
@RestController
public class DummyContentController {

	/**
	 * endpoint to return dummy content
	 * 
	 * @return some content</code>
	 */
	@ResponseStatus(OK)
	@RequestMapping(value = "/general/success-msg", method = GET, produces = APPLICATION_JSON_VALUE)
	public String getsuccessMessage() {
		return "Successfully called the dummy content endpoint!";
	}

}
