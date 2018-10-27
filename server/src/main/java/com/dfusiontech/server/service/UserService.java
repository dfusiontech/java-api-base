package com.dfusiontech.server.service;

import com.dfusiontech.server.model.dto.DTOBase;
import com.dfusiontech.server.model.dto.user.UserCreateDTO;
import com.dfusiontech.server.model.dto.user.UserDTO;
import com.dfusiontech.server.model.dto.user.UserListDTO;
import com.dfusiontech.server.model.dto.user.UserUpdateDTO;
import com.dfusiontech.server.model.jpa.entity.Users;
import com.dfusiontech.server.repository.jpa.UserRepository;
import com.dfusiontech.server.rest.exception.ItemNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

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
	private UserRepository userRepository;

	/**
	 * Get Users List
	 *
	 * @return Users List
	 */
	public List<UserListDTO> getList() {
		List<Users> items = userRepository.findAll();

		List<UserListDTO> usersDTOList = DTOBase.fromEntitiesList(items, UserListDTO.class);

		return usersDTOList;
	}

	/**
	 * Get User details
	 *
	 * @return User Details
	 */
	public UserDTO getDetails(Long itemId) {
		UserDTO itemDTO;

		try {
			Users itemDetails = userRepository.findById(itemId).get();
			itemDTO = new UserDTO(itemDetails);
		} catch (NoSuchElementException exception) {
			throw new ItemNotFoundException("Item not found in the database [" + itemId + "]");
		}

		return itemDTO;
	}

	/**
	 * Create new User
	 *
	 * @return New User
	 */
	public UserDTO create(UserCreateDTO newItemDTO) {

		Users newItem = newItemDTO.toEntity();
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
			// Get Existing item from the database
			Users existingItem = userRepository.findById(itemDTO.getId()).get();

			// Update item details
			Users updatedItem = itemDTO.toEntity(existingItem);

			// Save to the database
			Users saveResult = userRepository.save(updatedItem);

			result = new UserDTO(saveResult);

		} catch (NoSuchElementException exception) {
			throw new ItemNotFoundException("Item not found in the database [" + itemDTO.getId() + "]");
		}

		return result;
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
			Users existingItem = userRepository.findById(itemId).get();

			// Delete item details
			userRepository.delete(existingItem);

			result = itemId;

		} catch (NoSuchElementException exception) {
			throw new ItemNotFoundException("Item not found in the database [" + itemId + "]");
		}

		return result;
	}

}
