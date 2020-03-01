package com.dfusiontech.server.service;

import com.dfusiontech.server.model.data.FilteredRequest;
import com.dfusiontech.server.model.data.FilteredResponse;
import com.dfusiontech.server.model.data.NameFilter;
import com.dfusiontech.server.model.dto.DTOBase;
import com.dfusiontech.server.model.dto.vocabulary.CurrencyViewDTO;
import com.dfusiontech.server.model.jpa.entity.Currency;
import com.dfusiontech.server.repository.CurrencyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Currency management Service. Implements basic user CRUD.
 *
 * @author   Eugene A. Kalosha <ekalosha@dfusiontech.com>
 * @version  0.1.1
 * @since    2018-12-27
 */
@Service
public class CurrencyService {

	@Autowired
	private CurrencyRepository currencyRepository;

	/**
	 * Get Currency List
	 *
	 * @return Currency List
	 */
	public FilteredResponse<NameFilter, CurrencyViewDTO> getListFiltered(FilteredRequest<NameFilter> filteredRequest) {
		List<Currency> items = null;
		Long count = 0l;
		FilteredResponse<NameFilter, CurrencyViewDTO> filteredResponse = new FilteredResponse<NameFilter, CurrencyViewDTO>(filteredRequest);

		String namePattern = "";
		if (filteredRequest.getFilter() != null && filteredRequest.getFilter().getName() != null) {
			namePattern = filteredRequest.getFilter().getName();
		}

		items = currencyRepository.getListByName(namePattern, filteredRequest.toPageRequest());
		count = currencyRepository.getCountByName(namePattern);

		List<CurrencyViewDTO> itemsDTOList = DTOBase.fromEntitiesList(items, CurrencyViewDTO.class);

		filteredResponse.setItems(itemsDTOList);
		filteredResponse.setTotal(count.intValue());

		return filteredResponse;
	}

}
