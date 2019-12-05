package com.dfusiontech.server.model.dto.vocabulary;

import com.dfusiontech.server.model.dto.DTOBase;
import com.dfusiontech.server.model.jpa.entity.Language;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * Language View Entity Definition
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
public class LanguageViewDTO extends DTOBase<Language> {

	@ApiModelProperty(position = 0)
	private Long id;

	@ApiModelProperty(position = 2)
	private String name;

	@ApiModelProperty(position = 4)
	private String code;

	@ApiModelProperty(position = 6)
	private String charset;

	@ApiModelProperty(position = 8)
	private String locale;

	/**
	 * Entity based constructor
	 *
	 * @param entity
	 */
	public LanguageViewDTO(Language entity) {
		super(entity);
	}

}
