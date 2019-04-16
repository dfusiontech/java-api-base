package com.dfusiontech.server.model.data;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * Implementation of User Filtering Logic
 *
 * @author   Eugene A. Kalosha <ekalosha@dfusiontech.com>
 * @version  0.1.1
 * @since    2018-12-27
 */
@NoArgsConstructor
@Setter
@Getter
public class UsersFilter extends NameFilter {

	@ApiModelProperty(dataType = "array", name = "List of User role codes",
		allowableValues = "USER, ADMIN",
		position = 2)
	private List<String> roles;
}
