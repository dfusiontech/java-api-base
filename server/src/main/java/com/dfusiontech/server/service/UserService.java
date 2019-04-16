package com.dfusiontech.server.service;

import com.dfusiontech.server.model.auth.UserDetailsImpl;
import com.dfusiontech.server.model.dao.PagedResult;
import com.dfusiontech.server.model.dao.UserModelDAO;
import com.dfusiontech.server.model.data.FilteredRequest;
import com.dfusiontech.server.model.data.FilteredResponse;
import com.dfusiontech.server.model.data.UsersFilter;
import com.dfusiontech.server.model.dto.DTOBase;
import com.dfusiontech.server.model.dto.user.*;
import com.dfusiontech.server.model.jpa.domains.RoleType;
import com.dfusiontech.server.model.jpa.entity.Users;
import com.dfusiontech.server.repository.jpa.RoleRepository;
import com.dfusiontech.server.repository.jpa.UserRepository;
import com.dfusiontech.server.rest.exception.ApplicationExceptionCodes;
import com.dfusiontech.server.rest.exception.ConflictException;
import com.dfusiontech.server.rest.exception.ItemNotFoundException;
import com.dfusiontech.server.rest.exception.NotAuthenticatedException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.*;

/**
 * User management Service. Implements basic user CRUD.
 *
 * @author   Eugene A. Kalosha <ekalosha@dfusiontech.com>
 * @version  1.11.1
 * @since    2018-10-27
 */
@Service
public class UserService {


	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private EmailService emailService;

	@Autowired
	PasswordEncoder passwordEncoder;

	@Autowired
	UserModelDAO userModelDAO;

	/**
	 * Get Users List
	 *
	 * @return Users List
	 */
	public List<UserListDTO> getList() {
		List<Users> items;

		items = userRepository.findAll();

		List<UserListDTO> usersDTOList = DTOBase.fromEntitiesList(items, UserListDTO.class);

		return usersDTOList;
	}

	/**
	 * Get Users List Filtered
	 *
	 * @return Users List
	 */
	public FilteredResponse<UsersFilter, UserListDTO> getListFiltered(FilteredRequest<UsersFilter> filteredRequest) {

		PagedResult<Users> pagedResult = userModelDAO.getItemsPageable(filteredRequest.getFilter(), filteredRequest.toPageRequest(), filteredRequest.getSort());

		// Convert to DTOs
		List<UserListDTO> usersDTOList = DTOBase.fromEntitiesList(pagedResult.getItems(), UserListDTO.class);

		FilteredResponse<UsersFilter, UserListDTO> filteredResponse = new FilteredResponse<UsersFilter, UserListDTO>(filteredRequest);
		filteredResponse.setItems(usersDTOList);
		filteredResponse.setTotal(pagedResult.getCount().intValue());

		return filteredResponse;
	}

	/**
	 * Get User details
	 *
	 * @return User Details
	 */
	public UserDTO getDetails(Long itemId) {

		Users itemDetails = getUser(itemId);
		UserDTO itemDTO = new UserDTO(itemDetails);

		return itemDTO;
	}

	/**
	 * Get User details
	 *
	 * @return User Details
	 */
	public Users getUser(Long itemId) {
		Users itemDetails;

		try {
			itemDetails = userRepository.findById(itemId).get();
		} catch (NoSuchElementException exception) {
			throw new ItemNotFoundException(MessageFormat.format("User not found in the database [{0}]", itemId), ApplicationExceptionCodes.USER_NOT_EXISTS);
		}

		return itemDetails;
	}

	/**
	 * Create new User
	 *
	 * @return New User
	 */
	public UserDTO create(UserCreateDTO newItemDTO) {

		// Verify user with such email exists
		if (userRepository.findFirstByEmailAndIdIsNotIn(newItemDTO.getEmail(), Arrays.asList(0l)).isPresent()) {
			throw new ConflictException(MessageFormat.format("User with this email already registered in the system [{0}]", newItemDTO.getEmail()), ApplicationExceptionCodes.USER_WITH_EMAIL_ALREADY_EXISTS);
		}

		Users newItem = new Users();

		// newItem.setCreatedBy(getCurrentUserEntity());
		newItem.setCreatedAt(new Date());

		applyEntityChanges(newItemDTO, newItem);

		Users saveResult = userRepository.save(newItem);

		UserDTO result = new UserDTO(saveResult);

		return result;
	}

	/**
	 * Update User
	 *
	 * @return New User
	 */
	public UserDTO update(UserUpdateDTO itemDTO) {

		UserDTO result;

		try {

			// Verify user with such email exists
			if (userRepository.findFirstByEmailAndIdIsNotIn(itemDTO.getEmail(), Arrays.asList(itemDTO.getId())).isPresent()) {
				throw new ConflictException(MessageFormat.format("User with this email already registered in the system [{0}]", itemDTO.getEmail()), ApplicationExceptionCodes.USER_WITH_EMAIL_ALREADY_EXISTS);
			}

			// Get Existing item from the database
			Users existingItem = getUser(itemDTO.getId());

			// Update item details
			Users updatedItem = existingItem;
			applyEntityChanges(itemDTO, updatedItem);

			// Save to the database
			Users saveResult = userRepository.save(updatedItem);

			result = new UserDTO(saveResult);

		} catch (NoSuchElementException exception) {
			throw new ItemNotFoundException(MessageFormat.format("User not found in the system [{0}]", itemDTO.getId()), ApplicationExceptionCodes.USER_NOT_EXISTS);
		}

		return result;
	}

	/**
	 * Update User Profile
	 *
	 * @return Updated User
	 */
	public UserDTO updateProfile(UserUpdateDTO itemDTO) {

		UserDTO result;

		// Verify user with such email exists
		if (userRepository.findFirstByEmailAndIdIsNotIn(itemDTO.getEmail(), Arrays.asList(itemDTO.getId())).isPresent()) {
			throw new ConflictException(MessageFormat.format("User with this email already registered in the system [{0}]", itemDTO.getEmail()), ApplicationExceptionCodes.USER_WITH_EMAIL_ALREADY_EXISTS);
		}

		// Get Existing item from the database
		Users currentUser = getCurrentUserEntity();

		// Update item details
		Users updatedItem = currentUser;
		updatedItem.setFirstName(itemDTO.getFirstName());
		updatedItem.setLastName(itemDTO.getLastName());

		// updatedItem.setUpdatedBy(getCurrentUserEntity());
		updatedItem.setUpdatedAt(new Date());

		// Save to the database
		Users saveResult = userRepository.save(updatedItem);

		result = new UserDTO(saveResult);

		return result;
	}


	/**
	 * Apply entity changes and linkages
	 *
	 * @param itemDTO
	 * @param entity
	 */
	protected void applyEntityChanges(UserCreateDTO itemDTO, Users entity) {

		entity.setFirstName(itemDTO.getFirstName());
		entity.setLastName(itemDTO.getLastName());
		entity.setEmail(itemDTO.getEmail());
		// entity.setExpired(itemDTO.getExpired());
		// entity.setEnabled(itemDTO.getEnabled());

		// Set Encoded Password if it is defined
		if (StringUtils.isNotEmpty(itemDTO.getPasswordPlain())) {
			entity.setPassword(passwordEncoder.encode(itemDTO.getPasswordPlain()));
		}

		/*
		// Set Roles List from objects or names
		if (itemDTO.getRoles() != null) {
			Optional.ofNullable(itemDTO.getRoles()).ifPresent(rolesList -> {
				entity.setRoles(new HashSet<>());
				itemDTO.getRoles().stream().forEach(role -> {
					entity.getRoles().add(roleRepository.findById(role.getId()).get());
				});
			});
		} else {
			Optional.ofNullable(itemDTO.getRoleNames()).ifPresent(rolesList -> {
				entity.setRoles(new HashSet<>());
				itemDTO.getRoleNames().stream().forEach(roleName -> {
					entity.getRoles().add(roleRepository.findOneByName(roleName));
				});
			});
		}
		*/

		// entity.setUpdatedBy(getCurrentUserEntity());
		entity.setUpdatedAt(new Date());
	}


	/**
	 * Send forget password email
	 *
	 * @param forgetPasswordDTO
	 * @return
	 */
	public UserDTO sendResetPasswordEmail(ForgetPasswordDTO forgetPasswordDTO) {
		Users user = userRepository.findFirstByEmail(forgetPasswordDTO.getEmail())
			.orElseThrow(() -> new ItemNotFoundException(MessageFormat.format("User with this email is not found [{0}]", forgetPasswordDTO.getEmail()), ApplicationExceptionCodes.USER_WITH_EMAIL_NOT_EXISTS));
		emailService.sendResetPasswordEmail(user);

		return new UserDTO(user);
	}

	/**
	 * Change password for user
	 *
	 * @param user
	 * @param password
	 * @return
	 */
	public Users changePassword(Users user, String password) {
		user.setPassword(passwordEncoder.encode(password));
		Users result = userRepository.save(user);

		return result;
	}

	/**
	 * Verify passwords
	 *
	 * @param rawPassword
	 * @param encodedPassword
	 * @return
	 */
	public boolean comparePasswords(String rawPassword, String encodedPassword) {
		return passwordEncoder.matches(rawPassword, encodedPassword);
	}

	/**
	 * Deletes User
	 *
	 * @return ID of removed User
	 */
	public Long delete(UserUpdateDTO itemDTO) {

		Long result = delete(itemDTO.getId());

		return result;
	}

	/**
	 * Deletes User
	 *
	 * @return ID of removed User
	 */
	public Long delete(Long itemId) {

		Long result;

		try {
			// Get Existing item from the database
			Users existingItem = getUser(itemId);

			existingItem.setDeleted(true);

			// Delete item details
			userRepository.save(existingItem);

			result = itemId;

		} catch (NoSuchElementException exception) {
			throw new ItemNotFoundException(MessageFormat.format("User not found in the system [{0}]", itemId), ApplicationExceptionCodes.USER_NOT_EXISTS);
		}

		return result;
	}

	/**
	 * Check is current user Authorized
	 *
	 * @return
	 */
	public boolean isAuthorized() {
		Object user = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		Boolean result = false;

		// Verify current User Details
		if (user != null && user instanceof UserDetailsImpl) {
			result = true;
		}

		return result;
	}

	/**
	 * Check is current user has role
	 *
	 * @return
	 */
	public boolean hasRole(String roleName) {
		UserDetailsImpl user = getCurrentUser();

		boolean result = user.getAuthorities().contains(new SimpleGrantedAuthority(roleName));

		return result;
	}

	/**
	 * Check is current user has role
	 *
	 * @return
	 */
	public boolean hasRole(RoleType role) {
		return hasRole(role.role());
	}

	/**
	 * Get Current Security User
	 *
	 * @return
	 */
	public UserDetailsImpl getCurrentUser() {
		Object securityUser = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		UserDetailsImpl user = null;

		// Initialize current User Details
		if (securityUser != null && securityUser instanceof UserDetailsImpl) {
			user = (UserDetailsImpl) securityUser;
		}

		if (user == null) {
			throw new NotAuthenticatedException("User is not Authorized on this server.");
		}

		return user;
	}

	/**
	 * Get Current JPA User Entity
	 *
	 * @return
	 */
	public Users getCurrentUserEntity() {
		UserDetailsImpl user = getCurrentUser();

		return getUser(user.getUserId());
	}

}
