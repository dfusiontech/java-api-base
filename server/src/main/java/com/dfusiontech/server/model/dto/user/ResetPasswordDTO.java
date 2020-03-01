package com.dfusiontech.server.model.dto.user;

import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Reset password details
 *
 * @author   Eugene A. Kalosha <ekalosha@dfusiontech.com>
 * @version  0.1.1
 * @since    2018-12-25
 */
@Setter
@Getter
@ToString(of = {"code"})
@EqualsAndHashCode(of = {"code"})
public class ResetPasswordDTO {

	@ApiModelProperty(position = 1)
	private String code;

	@ApiModelProperty(position = 2)
	private String password;

	/**
	 * Default constructor
	 */
	public ResetPasswordDTO() {
	}
}
