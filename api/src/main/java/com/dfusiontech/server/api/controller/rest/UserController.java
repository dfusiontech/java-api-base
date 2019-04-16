package com.dfusiontech.server.api.controller.rest;

import com.dfusiontech.server.model.auth.UserDetailsImpl;
import com.dfusiontech.server.model.dto.DTOBase;
import com.dfusiontech.server.model.dto.user.UserCreateDTO;
import com.dfusiontech.server.model.dto.user.UserDTO;
import com.dfusiontech.server.model.dto.user.UserListDTO;
import com.dfusiontech.server.model.dto.user.UserUpdateDTO;
import com.dfusiontech.server.model.jpa.entity.Users;
import com.dfusiontech.server.repository.jpa.UserRepository;
import com.dfusiontech.server.rest.exception.ItemNotFoundException;
import com.dfusiontech.server.service.UserService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.NoSuchElementException;


/**
 * User management controller. Basic user CRUD.
 *
 * @author   Eugene A. Kalosha <ekalosha@dfusiontech.com>
 * @version  1.11.1
 * @since    2018-10-27
 */
@RestController
@RequestMapping(
	value = UserController.CONTROLLER_URI,
	produces = MediaType.APPLICATION_JSON,
	name = "Users Management Controller"
)
public class UserController {

	static final String CONTROLLER_URI = "/api/users";

	@Autowired
	private UserService userService;

	/**
	 * Get Users List
	 *
	 * @return Users List
	 */
	@RequestMapping(method = RequestMethod.GET, value = "", name = "Get Users List")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "authorization", value = "oAuth Access token for API calls", defaultValue = "Bearer DF0310", required = true, dataType = "string", paramType = "header")
	})
	public List<UserListDTO> getList() {

		List<UserListDTO> result = userService.getList();

		return result;
	}

	/**
	 * Get User details
	 *
	 * @return User Details
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/self", name = "Get User details")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "authorization", value = "oAuth Access token for API calls", defaultValue = "Bearer DF0310", required = true, dataType = "string", paramType = "header")
	})
	public UserDTO getSelf() {

		UserDetailsImpl user = userService.getCurrentUser();
		UserDTO itemDTO = userService.getDetails(user.getUserId());

		return itemDTO;
	}

	/**
	 * Get User details
	 *
	 * @return User Details
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/{itemId}", name = "Get User details")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "authorization", value = "oAuth Access token for API calls", defaultValue = "Bearer DF0310", required = true, dataType = "string", paramType = "header")
	})
	public UserDTO getDetails(@PathVariable("itemId") @NotNull @Size(min = 1) Long itemId) {

		UserDTO itemDTO = userService.getDetails(itemId);

		return itemDTO;
	}

	/**
	 * Create new User
	 *
	 * @return New User
	 */
	@RequestMapping(method = RequestMethod.POST, value = "", name = "Create new User", consumes = {MediaType.APPLICATION_JSON})
	@ApiImplicitParams({
		@ApiImplicitParam(name = "authorization", value = "oAuth Access token for API calls", defaultValue = "Bearer DF0310", required = true, dataType = "string", paramType = "header")
	})
	public UserDTO create(@ApiParam(value = "User Details", required = true) @RequestBody UserCreateDTO newItemDTO) {

		UserDTO result = userService.create(newItemDTO);

		return result;
	}

	/**
	 * Update User
	 *
	 * @return New User
	 */
	@RequestMapping(method = RequestMethod.PUT, value = "", name = "Update existing User", consumes = {MediaType.APPLICATION_JSON})
	@ApiImplicitParams({
		@ApiImplicitParam(name = "authorization", value = "oAuth Access token for API calls", defaultValue = "Bearer DF0310", required = true, dataType = "string", paramType = "header")
	})
	public UserDTO update(@ApiParam(value = "User update Details", required = true) @RequestBody UserUpdateDTO itemDTO) {

		UserDTO result = userService.update(itemDTO);

		return result;
	}

	/**
	 * Deletes User
	 *
	 * @return ID of removed User
	 */
	@RequestMapping(method = RequestMethod.DELETE, value = "", name = "Delete existing User", consumes = {MediaType.APPLICATION_JSON})
	@ApiImplicitParams({
		@ApiImplicitParam(name = "authorization", value = "oAuth Access token for API calls", defaultValue = "Bearer DF0310", required = true, dataType = "string", paramType = "header")
	})
	public Long delete(@ApiParam(value = "Simple User Details", required = true) @RequestBody UserUpdateDTO itemDTO) {

		Long result = userService.delete(itemDTO);

		return result;
	}

}
