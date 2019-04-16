package com.dfusiontech.server.model.data;

import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.core.GenericTypeResolver;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.io.Serializable;

/**
 * Base Filtered Request
 *
 * @author   Eugene A. Kalosha <ekalosha@dfusiontech.com>
 * @version  0.1.1
 * @since    2018-12-04
 */
@Setter
@Getter
@ToString(of = {"page", "size"})
@EqualsAndHashCode(of = {"page", "size"})
public class FilteredRequest<T extends BaseFilter> implements Serializable {

	@ApiModelProperty(example = "0")
	private int page;

	@ApiModelProperty(example = "10")
	private int size;

	private BaseSort sort;

	private T filter;

	/**
	 * Initialize default request parameters
	 */
	public FilteredRequest() {
		page = 0;
		size = 10;
		sort = new BaseSort();
	}

	@SuppressWarnings("unchecked")
	public Class<T> getTypeParameterClass() {

		Class<T> entityClass = (Class<T>) GenericTypeResolver.resolveTypeArgument(getClass(), FilteredRequest.class);

		return entityClass;
	}

	/**
	 * Create Page Request from current page parameters
	 *
	 * @return
	 */
	public Pageable toPageRequest() {
		Pageable result;

		if (sort != null && sort.isInitialized()) {
			result = PageRequest.of(page, size, sort.toSort());
		} else {
			result = PageRequest.of(page, size);
		}

		return result;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public BaseSort getSort() {
		return sort;
	}

	public void setSort(BaseSort sort) {
		this.sort = sort;
	}

	public T getFilter() {
		return filter;
	}

	public void setFilter(T filter) {
		this.filter = filter;
	}
}
