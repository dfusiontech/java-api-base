package com.dfusiontech.server.service;

import com.dfusiontech.server.model.data.FilteredRequest;
import com.dfusiontech.server.model.data.FilteredResponse;
import com.dfusiontech.server.model.data.LocationFilter;
import com.dfusiontech.server.model.dto.DTOBase;
import com.dfusiontech.server.model.dto.location.CityViewDTO;
import com.dfusiontech.server.model.jpa.entity.City;
import com.dfusiontech.server.repository.CityRepository;
import com.dfusiontech.server.rest.exception.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * City management Service. Implements basic user CRUD.
 *
 * @author   Eugene A. Kalosha <ekalosha@dfusiontech.com>
 * @version  0.1.1
 * @since    2018-12-27
 */
@Service
public class CityService {

	@Autowired
	private CityRepository cityRepository;

	/**
	 * Get City List
	 *
	 * @return City List
	 */
	public FilteredResponse<LocationFilter, CityViewDTO> getListFiltered(FilteredRequest<LocationFilter> filteredRequest) {
		List<City> items = null;
		Long count = 0l;
		FilteredResponse<LocationFilter, CityViewDTO> filteredResponse = new FilteredResponse<LocationFilter, CityViewDTO>(filteredRequest);

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

		Long stateId = null;
		if (filteredRequest.getFilter() != null && filteredRequest.getFilter().getStateId() != null) {
			stateId = filteredRequest.getFilter().getStateId();
		}

		if (countryId == null && stateId == null) {
			throw new BadRequestException("Country or State must be specified to search a City");
		}

		if (stateId != null) {
			items = cityRepository.getListByStateAndName(stateId, namePattern, filteredRequest.toPageRequest());
			count = cityRepository.getCountByStateAndName(stateId, namePattern);
		} else {
			items = cityRepository.getListByCountryAndName(countryId, namePattern, filteredRequest.toPageRequest());
			count = cityRepository.getCountByCountryAndName(countryId, namePattern);
		}

		List<CityViewDTO> itemsDTOList = DTOBase.fromEntitiesList(items, CityViewDTO.class);

		filteredResponse.setItems(itemsDTOList);
		filteredResponse.setTotal(count.intValue());

		return filteredResponse;
	}

}
