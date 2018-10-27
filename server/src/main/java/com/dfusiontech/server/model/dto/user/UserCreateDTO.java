package com.dfusiontech.server.model.dto.user;

import com.dfusiontech.server.model.dto.DTOBase;
import com.dfusiontech.server.model.jpa.entity.Users;
import com.dfusiontech.server.repository.jpa.RoleRepository;
import com.dfusiontech.server.util.BeanUtil;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.crypto.password.PasswordEncoder;

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
@ToString(of = {"email"})
@EqualsAndHashCode(of = {"email"})
public class UserCreateDTO extends DTOBase<Users> {

	@ApiModelProperty(position = 1)
	private String email;

	@ApiModelProperty(position = 2)
	private String passwordPlain;

	/**
	 * User role codes list
	 */
	@ApiModelProperty(dataType = "array", name = "List of User role codes", allowableValues = "USER, ADMIN", position = 3)
	private List<String> roleNames = new ArrayList<>();

	/**
	 * Convert User Details DTO to Entity
	 *
	 * @return
	 */
	@Override
	public Users toEntity(Users update) {
		Users result = super.toEntity(update);

		RoleRepository roleRepository = BeanUtil.getBean(RoleRepository.class);
		PasswordEncoder passwordEncoder = BeanUtil.getBean(PasswordEncoder.class);

		// Set Encoded Password if it is defined
		if (StringUtils.isNotEmpty(passwordPlain)) {
			result.setPassword(passwordEncoder.encode(passwordPlain));
		}

		// Set Roles List from names
		Optional.ofNullable(roleNames).ifPresent(rolesList -> {
			result.setRoles(new HashSet<>());
			roleNames.stream().forEach(roleName -> {
				result.getRoles().add(roleRepository.findOneByName(roleName));
			});
		});

		// Set Create/Update Dates
		if (result.getCreatedAt() == null) {
			result.setCreatedAt(new Date());
		}
		result.setUpdatedAt(new Date());

		return result;
	}

}
