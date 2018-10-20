package com.dfusiontech.server.api.controller.rest;

import com.dfusiontech.server.model.dto.user.UserDTO;
import com.dfusiontech.server.model.dto.user.UserSmallDTO;
import com.dfusiontech.server.model.jpa.entity.UsersEntity;
import com.dfusiontech.server.repository.jpa.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

@RestController
@RequestMapping(
	value = UserController.CONTROLLER_URI,
	produces = MediaType.APPLICATION_JSON,
	name = "Users Management Controller"
)
// @Api(value = "Info", description = "Info about the platform.")
public class UserController {

	static final String CONTROLLER_URI = "/api/rest/users";

	@Autowired
	private UserRepository userRepository;


	/**
	 * Get Users List
	 *
	 * @return Users List
	 */
	@Produces(MediaType.APPLICATION_JSON)
	@RequestMapping(method = RequestMethod.GET, value = "/list", name = "Get Users List")
	public List<UserSmallDTO> getUsersList() {
		List<UsersEntity> items = userRepository.findAll();

		List<UserSmallDTO> usersDTOList = (List<UserSmallDTO>) (new UserSmallDTO()).fromEntitiesList(items);

		return usersDTOList;
	}

}
