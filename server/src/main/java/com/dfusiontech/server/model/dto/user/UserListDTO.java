package com.dfusiontech.server.model.dto.user;

import com.dfusiontech.server.model.dto.DTOBase;
import com.dfusiontech.server.model.jpa.entity.Users;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

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
public class UserListDTO extends DTOBase<Users> {
	@ApiModelProperty(position = 0)
	private Long id;

	@ApiModelProperty(position = 1)
	private String email;

	@ApiModelProperty(position = 2)
	private Boolean enabled;
}
