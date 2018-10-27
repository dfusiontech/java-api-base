package com.dfusiontech.server.model.dto.user;

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
 * @since    2018-10-17
 */
@Setter
@Getter
@ToString(of = {"id", "email"})
@EqualsAndHashCode(of = {"id", "email"})
public class UserUpdateDTO extends UserCreateDTO {

	@ApiModelProperty(position = 0)
	private Long id;

}
