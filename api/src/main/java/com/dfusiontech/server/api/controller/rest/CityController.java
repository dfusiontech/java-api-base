package com.dfusiontech.server.api.controller.rest;

import com.dfusiontech.server.model.data.FilteredRequest;
import com.dfusiontech.server.model.data.FilteredResponse;
import com.dfusiontech.server.model.data.LocationFilter;
import com.dfusiontech.server.model.dto.location.CityViewDTO;
import com.dfusiontech.server.service.CityService;
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
 * City management controller
 *
 * @author   Eugene A. Kalosha <ekalosha@dfusiontech.com>
 * @version  0.1.1
 * @since    2018-12-27
 */
@RestController
@RequestMapping(
	value = CityController.CONTROLLER_URI,
	produces = MediaType.APPLICATION_JSON,
	name = "City Management Controller"
)
@Api(tags = "City Management")
public class CityController {

	static final String CONTROLLER_URI = "/api/cities";

	@Autowired
	private CityService cityService;

	/**
	 * Get City List for current Filters
	 *
	 * @return City List
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/filter", name = "City List for current Filters")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "authorization", value = "oAuth Access token for API calls", defaultValue = "Bearer DF0310", required = true, dataType = "string", paramType = "header")
	})
	public FilteredResponse<LocationFilter, CityViewDTO> getListFiltered(
		@ApiParam(value = "Item Filtering", required = true) @RequestBody FilteredRequest<LocationFilter> filteredRequest
	) {

		FilteredResponse<LocationFilter, CityViewDTO> result = cityService.getListFiltered(filteredRequest);

		return result;
	}

}
