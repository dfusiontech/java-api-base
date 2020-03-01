package com.dfusiontech.server.service;

import com.dfusiontech.server.model.data.FilteredRequest;
import com.dfusiontech.server.model.data.FilteredResponse;
import com.dfusiontech.server.model.data.LocationFilter;
import com.dfusiontech.server.model.dto.DTOBase;
import com.dfusiontech.server.model.dto.location.StateViewDTO;
import com.dfusiontech.server.model.jpa.entity.State;
import com.dfusiontech.server.repository.StateRepository;
import com.dfusiontech.server.rest.exception.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * State management Service. Implements basic user CRUD.
 *
 * @author   Eugene A. Kalosha <ekalosha@dfusiontech.com>
 * @version  0.1.1
 * @since    2018-12-27
 */
@Service
public class StateService {

	@Autowired
	private StateRepository stateRepository;

	/**
	 * Get State List
	 *
	 * @return State List
	 */
	public FilteredResponse<LocationFilter, StateViewDTO> getListFiltered(FilteredRequest<LocationFilter> filteredRequest) {
		List<State> items = null;
		Long count = 0l;
		FilteredResponse<LocationFilter, StateViewDTO> filteredResponse = new FilteredResponse<LocationFilter, StateViewDTO>(filteredRequest);

		String namePattern = "";
		if (filteredRequest.getFilter() != null && filteredRequest.getFilter().getName() != null) {
			namePattern = filteredRequest.getFilter().getName();
		}

		Long countryId = null;
		if (filteredRequest.getFilter() != null && filteredRequest.getFilter().getCountryId() != null) {
			countryId = filteredRequest.getFilter().getCountryId();
		} else {
			throw new BadRequestException("Country Id is not specified");
		}

		items = stateRepository.getListByName(countryId, namePattern, filteredRequest.toPageRequest());
		count = stateRepository.getCountByName(countryId, namePattern);

		List<StateViewDTO> itemsDTOList = DTOBase.fromEntitiesList(items, StateViewDTO.class);

		filteredResponse.setItems(itemsDTOList);
		filteredResponse.setTotal(count.intValue());

		return filteredResponse;
	}

}
