package com.dfusiontech.server.model.data;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Sort;
import springfox.documentation.annotations.ApiIgnore;

import java.io.Serializable;
import java.util.Map;

/**
 * Base Sort Parameter
 *
 * @author   Eugene A. Kalosha <ekalosha@dfusiontech.com>
 * @version  0.1.1
 * @since    2018-12-04
 */
@NoArgsConstructor
@Setter
@Getter
@ToString(of = {"field", "order"})
@EqualsAndHashCode(of = {"field", "order"})
public class BaseSort implements Serializable {

	@ApiModelProperty(example = "id")
	private String field;

	@ApiModelProperty(example = "ASC", allowableValues = "ASC,DESC")
	private SortOrder order;

	/**
	 * Elementary Sort Ordering types
	 */
	enum SortOrder {
		ASC, DESC
	}

	/**
	 * Check is sort set
	 *
	 * @return
	 */
	@ApiIgnore
	@ApiModelProperty(hidden = true)
	public boolean isInitialized() {
		boolean result = false;

		if (StringUtils.isNotEmpty(field)) {
			result = true;
		}

		return result;
	}

	/**
	 * Get Spring Sort
	 *
	 * @return
	 */
	@ApiIgnore
	public Sort toSort() {
		Sort result = null;

		if (StringUtils.isNotEmpty(field)) {
			if (SortOrder.ASC.equals(order)) {
				result = Sort.by(Sort.Order.asc(field));
			} else {
				result = Sort.by(Sort.Order.desc(field));
			}
		}

		return result;
	}

	/**
	 * Get ORDER String
	 *
	 * @return
	 */
	@ApiIgnore
	public String toOrderString() {
		return toOrderString(null);
	}

	/**
	 * Get ORDER String
	 *
	 * @return
	 */
	@ApiIgnore
	public String toOrderString(Map<String, String> sortMapping) {
		String result = "";

		String sortFieldName = field;
		if (sortMapping != null && StringUtils.isNotEmpty(field) && sortMapping.containsKey(field)) {
			sortFieldName = sortMapping.get(field);
		}

		if (StringUtils.isNotEmpty(sortFieldName)) {
			String fieldName = sortFieldName.replaceAll("[^a-zA-Z\\_\\-\\.]", "");
			if (SortOrder.ASC.equals(order)) {
				result = " ORDER BY " + fieldName + " ASC";
			} else {
				result = " ORDER BY " + fieldName + " DESC";
			}
		}

		return result;
	}

}
