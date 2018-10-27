package com.dfusiontech.server.api.controller.rest;

import com.dfusiontech.server.model.dto.role.RoleListDTO;
import com.dfusiontech.server.model.jpa.entity.Roles;
import com.dfusiontech.server.repository.jpa.RoleRepository;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * Simple Roles controller. Used for Roles Listing
 *
 * @author   Eugene A. Kalosha <ekalosha@dfusiontech.com>
 * @version  1.11.1
 * @since    2018-10-27
 */
@RestController
@RequestMapping(
	value = RoleController.CONTROLLER_URI,
	produces = MediaType.APPLICATION_JSON,
	name = "Roles Management Controller"
)
public class RoleController {

	static final String CONTROLLER_URI = "/api/rest/roles";

	@Autowired
	private RoleRepository roleRepository;


	/**
	 * Get Roles List
	 *
	 * @return Roles List
	 */
	@Produces(MediaType.APPLICATION_JSON)
	@RequestMapping(method = RequestMethod.GET, value = "", name = "Get Roles List")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "authorization", value = "oAuth Access token for API calls", defaultValue = "Bearer DF0310", required = true, dataType = "string", paramType = "header")
	})
	public List<RoleListDTO> getList() {
		List<Roles> items = roleRepository.findAll();

		List<RoleListDTO> roleListDTOs = RoleListDTO.fromEntitiesList(items, RoleListDTO.class);

		return roleListDTOs;
	}

}
