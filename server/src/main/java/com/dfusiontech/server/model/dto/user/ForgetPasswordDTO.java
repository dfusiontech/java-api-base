package com.dfusiontech.server.model.dto.user;

import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Forget password details
 *
 * @author   Eugene A. Kalosha <ekalosha@dfusiontech.com>
 * @version  0.1.1
 * @since    2018-12-25
 */
@Setter
@Getter
@ToString(of = {"email"})
@EqualsAndHashCode(of = {"email"}, callSuper = false)
public class ForgetPasswordDTO {

	@ApiModelProperty(position = 3)
	private String email;

	/**
	 * Default constructor
	 */
	public ForgetPasswordDTO() {
	}
}
