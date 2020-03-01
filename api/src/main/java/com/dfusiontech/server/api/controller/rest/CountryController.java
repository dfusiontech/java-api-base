package com.dfusiontech.server.api.controller.rest;

import com.dfusiontech.server.model.data.FilteredRequest;
import com.dfusiontech.server.model.data.FilteredResponse;
import com.dfusiontech.server.model.data.NameFilter;
import com.dfusiontech.server.model.dto.location.CountryViewDTO;
import com.dfusiontech.server.service.CountryService;
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
 * Country management controller
 *
 * @author   Eugene A. Kalosha <ekalosha@dfusiontech.com>
 * @version  0.1.1
 * @since    2018-12-27
 */
@RestController
@RequestMapping(
	value = CountryController.CONTROLLER_URI,
	produces = MediaType.APPLICATION_JSON,
	name = "Country Management Controller"
)
@Api(tags = "Country Management")
public class CountryController {

	static final String CONTROLLER_URI = "/api/countries";

	@Autowired
	private CountryService countryService;

	/**
	 * Get Country List for current Filters
	 *
	 * @return Country List
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/filter", name = "Country List for current Filters")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "authorization", value = "oAuth Access token for API calls", defaultValue = "Bearer DF0310", required = true, dataType = "string", paramType = "header")
	})
	public FilteredResponse<NameFilter, CountryViewDTO> getListFiltered(
		@ApiParam(value = "Item Filtering", required = true) @RequestBody FilteredRequest<NameFilter> filteredRequest
	) {

		FilteredResponse<NameFilter, CountryViewDTO> result = countryService.getListFiltered(filteredRequest);

		return result;
	}

}
