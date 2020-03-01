package com.dfusiontech.server.api.controller.rest;

import com.dfusiontech.server.model.data.FilteredRequest;
import com.dfusiontech.server.model.data.FilteredResponse;
import com.dfusiontech.server.model.data.NameFilter;
import com.dfusiontech.server.model.dto.vocabulary.LanguageViewDTO;
import com.dfusiontech.server.service.LanguageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.ws.rs.core.MediaType;

/**
 * Language management controller
 *
 * @author   Eugene A. Kalosha <ekalosha@dfusiontech.com>
 * @version  0.1.1
 * @since    2018-12-27
 */
@RestController
@RequestMapping(
	value = LanguageController.CONTROLLER_URI,
	produces = MediaType.APPLICATION_JSON,
	name = "Language Management Controller"
)
@Api(tags = "Language Management")
public class LanguageController {

	static final String CONTROLLER_URI = "/api/languages";

	@Autowired
	private LanguageService languageService;

	/**
	 * Get Language List for current Filters
	 *
	 * @return Language List
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/filter", name = "Language List for current Filters")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "authorization", value = "oAuth Access token for API calls", defaultValue = "Bearer DF0310", required = true, dataType = "string", paramType = "header")
	})
	public FilteredResponse<NameFilter, LanguageViewDTO> getListFiltered(
		@ApiParam(value = "Item Filtering", required = true) @RequestBody FilteredRequest<NameFilter> filteredRequest
	) {

		FilteredResponse<NameFilter, LanguageViewDTO> result = languageService.getListFiltered(filteredRequest);

		return result;
	}

}
