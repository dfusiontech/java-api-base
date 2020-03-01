package com.dfusiontech.server.model.dto.location;

import com.dfusiontech.server.model.dto.DTOBase;
import com.dfusiontech.server.model.jpa.entity.City;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * City View Entity Definition
 *
 * @author   Eugene A. Kalosha <ekalosha@dfusiontech.com>
 * @version  0.1.1
 * @since    2018-12-27
 */
@Setter
@Getter
@NoArgsConstructor
@ToString(of = {"id", "name"})
@EqualsAndHashCode(of = {"id"}, callSuper = false)
public class CityViewDTO extends DTOBase<City> {

	@ApiModelProperty(position = 0)
	private Long id;

	@ApiModelProperty(position = 2)
	private String name;

	@ApiModelProperty(position = 4)
	private String code;

	/**
	 * Entity based constructor
	 *
	 * @param entity
	 */
	public CityViewDTO(City entity) {
		super(entity);
	}

}
