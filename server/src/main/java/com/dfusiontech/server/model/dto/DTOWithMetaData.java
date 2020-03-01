package com.dfusiontech.server.model.dto;

import com.dfusiontech.server.model.jpa.entity.IEntityWithDates;
import com.dfusiontech.server.model.jpa.entity.IEntityWithMetadata;
import com.dfusiontech.server.model.jpa.entity.Users;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 * Risk Model Domain View Entity Definition
 *
 * @author   Eugene A. Kalosha <ekalosha@dfusiontech.com>
 * @version  0.1.1
 * @since    2018-11-08
 */
public abstract class DTOWithMetaData<ENTITY> extends DTOBase<ENTITY> {

	@Getter
	@Setter
	@NoArgsConstructor
	public static class ItemMetadata implements Serializable {
		@ApiModelProperty(position = 16, readOnly = true)
		private Date createdAt;

		@ApiModelProperty(position = 17, readOnly = true)
		private Date updatedAt;

		@ApiModelProperty(position = 18, readOnly = true)
		private IDTitle createdBy;

		@ApiModelProperty(position = 19, readOnly = true)
		private IDTitle updatedBy;
	}

	@Getter
	@Setter
	@NoArgsConstructor
	@AllArgsConstructor
	public static class IDTitle implements Serializable {
		@ApiModelProperty(position = 19, readOnly = true)
		private Long id;

		@ApiModelProperty(position = 20, readOnly = true)
		private String title;
	}

	@Getter
	@Setter
	@ApiModelProperty(position = 256, readOnly = true)
	private ItemMetadata metadata;

	/**
	 * Default constructor
	 */
	public DTOWithMetaData() {
		super();
	}

	/**
	 * Entity based constructor
	 *
	 * @param entity
	 */
	public DTOWithMetaData(ENTITY entity) {
		super(entity);
	}

	@Override
	public void fromEntity(ENTITY entity) {
		super.fromEntity(entity);

		// Set Metadata
		metadata = new ItemMetadata();
		if (entity instanceof IEntityWithDates) {
			metadata.setCreatedAt(((IEntityWithDates) entity).getCreatedAt());
			metadata.setUpdatedAt(((IEntityWithDates) entity).getUpdatedAt());
		}

		if (entity instanceof IEntityWithMetadata) {
			Users createdBy = ((IEntityWithMetadata) entity).getCreatedBy();
			if (createdBy != null) {
				metadata.setCreatedBy(new IDTitle(createdBy.getId(), createdBy.getFullName()));
			}

			Users updatedBy = ((IEntityWithMetadata) entity).getUpdatedBy();
			if (updatedBy != null) {
				metadata.setUpdatedBy(new IDTitle(updatedBy.getId(), updatedBy.getFullName()));
			}
		}
	}
}
