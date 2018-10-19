package com.dfusiontech.server.model.dto;

import com.dfusiontech.server.util.BeanUtil;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.GenericTypeResolver;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Base DTO class
 *
 * @ENTITY Base entity to use in modeler
 *
 * @author Eugene A. Kalosha <ekalosha@dfusiontech.com>
 * @version  1.11.1
 * @since    2018-10-17
 */
public abstract class DTOBase<ENTITY> {

	@JsonIgnore
	final Class<ENTITY> entityClass;

	/**
	 * Model Mapper to use to map DTO to Entity
	 */
	// @Autowired
	@Getter
	@JsonIgnore
	private ModelMapper modelMapper;

	/**
	 * Default DTO constructor
	 */
	public DTOBase() {
		this.entityClass = (Class<ENTITY>) GenericTypeResolver.resolveTypeArgument(getClass(), DTOBase.class);

		this.modelMapper = BeanUtil.getBean(ModelMapper.class);
	}

	/**
	 * Entity DTO constructor
	 */
	public DTOBase(ENTITY entity) {
		this();

		// Init from Entity
		if (entity != null) {
			fromEntity(entity);
		}
	}

	/**
	 * Convert current model to Entity Instance
	 *
	 * @return
	 */
	public ENTITY toEntity() {
		ENTITY result = null;

		result = modelMapper.map(this, this.entityClass);

		return result;
	}

	/**
	 * Convert current model to Entity Instance
	 *
	 * @return
	 */
	public void fromEntity(ENTITY entity) {
		modelMapper.map(entity,this);
	}

	/**
	 * Get list DTO from the entity
	 *
	 * @param entities
	 * @return
	 */
	public List<?> fromEntitiesList(List<ENTITY> entities) {
		List<DTOBase> result = new ArrayList<>();

		Optional.ofNullable(entities).orElse(new ArrayList<>()).forEach(entity -> {
			try {
				DTOBase newItem = getClass().newInstance();
				newItem.fromEntity(entity);
				result.add(newItem);
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		});

		return result;
	}
}
