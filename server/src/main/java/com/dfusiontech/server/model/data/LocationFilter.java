package com.dfusiontech.server.model.data;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Implementation of Location Filtering Logic
 *
 * @author   Eugene A. Kalosha <ekalosha@dfusiontech.com>
 * @version  0.1.1
 * @since    2018-12-28
 */
@NoArgsConstructor
@Setter
@Getter
public class LocationFilter extends NameFilter {

	private Long countryId;

	private Long stateId;

	private Long cityId;
}
