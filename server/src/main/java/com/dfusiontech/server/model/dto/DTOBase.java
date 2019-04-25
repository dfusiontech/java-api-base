package com.dfusiontech.server.model.dto;

import com.dfusiontech.server.util.BeanUtil;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.GenericTypeResolver;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
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
public abstract class DTOBase<ENTITY> implements Serializable {

	/**
	 * Default DTO constructor
	 */
	public DTOBase() {
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
		return toEntity(null);
	}

	/**
	 * Convert current model to Entity Instance
	 *
	 * @return
	 */
	public ENTITY toEntity(ENTITY update) {
		ENTITY result = null;

		Class<ENTITY> entityClass = (Class<ENTITY>) GenericTypeResolver.resolveTypeArgument(getClass(), DTOBase.class);
		ModelMapper modelMapper = BeanUtil.getBean(ModelMapper.class);

		if (update != null) {
			modelMapper.map(this, update);
			result = update;
		} else {
			result = modelMapper.map(this, entityClass);
		}

		return result;
	}

	/**
	 * Convert current model to Entity Instance
	 *
	 * @return
	 */
	public void fromEntity(ENTITY entity) {
		ModelMapper modelMapper = BeanUtil.getBean(ModelMapper.class);
		modelMapper.map(entity,this);
	}

	/**
	 * Get list DTO from the entity
	 *
	 * @param entities
	 * @param dtoClass
	 * @param <E>
	 * @param <D>
	 * @return
	 */
	public static <E, D extends DTOBase> List<D> fromEntitiesList(List<E> entities, Class<D> dtoClass) {

		List<D> result = new ArrayList<>();

		// Initialize items list for ENTITY Element
		Optional.ofNullable(entities).orElse(new ArrayList<>()).forEach(entity -> {
			try {
				D newItem = dtoClass.getDeclaredConstructor().newInstance();
				newItem.fromEntity(entity);
				result.add(newItem);
			} catch (InstantiationException exception) {
				exception.printStackTrace();
			} catch (IllegalAccessException exception) {
				exception.printStackTrace();
			} catch (NoSuchMethodException exception) {
				exception.printStackTrace();
			} catch (InvocationTargetException exception) {
				exception.printStackTrace();
			}
		});

		return result;
	}
}
