package com.dfusiontech.server.model.dto.user;

import com.dfusiontech.server.model.dto.DTOBase;
import com.dfusiontech.server.model.jpa.entity.Users;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.util.*;

/**
 * User Entity Definition
 *
 * @author   Eugene A. Kalosha <ekalosha@dfusiontech.com>
 * @version  1.11.1
 * @since    2018-10-27
 */
@Setter
@Getter
@ToString(of = {"id", "email"})
@EqualsAndHashCode(of = {"id", "email"})
@NoArgsConstructor
public class UserDTO extends DTOBase<Users> {

	@ApiModelProperty(position = 0)
	private Long id;

	@ApiModelProperty(position = 1)
	private String email;

	// private String password;

	@ApiModelProperty(position = 2)
	private Boolean expired;

	/**
	 * User role codes list
	 */
	@ApiModelProperty(dataType = "array", name = "List of User role codes", allowableValues = "USER, ADMIN", position = 2)
	private List<String> roleNames;

	@ApiModelProperty(position = 3)
	private Boolean credentialsExpired;

	@ApiModelProperty(position = 4)
	private Boolean locked;

	@ApiModelProperty(position = 5)
	private Date createdAt;

	@ApiModelProperty(position = 6)
	private Date updatedAt;

	@ApiModelProperty(position = 7)
	private Date expirationDate;

	@ApiModelProperty(position = 8)
	private Date credentialsExpirationDate;

	/**
	 * Entity based constructor
	 *
	 * @param users
	 */
	public UserDTO(Users users) {
		super(users);
	}

	/**
	 * Initialize current DTO object from Users entity
	 *
	 * @param users
	 */
	@Override
	public void fromEntity(Users users) {
		super.fromEntity(users);

		// Filling User Roles List
		roleNames = new ArrayList<>();
		Optional.ofNullable(users.getRoles()).orElse(new HashSet<>()).stream().forEach(role -> {
			roleNames.add(role.getName());
		});
	}
}
