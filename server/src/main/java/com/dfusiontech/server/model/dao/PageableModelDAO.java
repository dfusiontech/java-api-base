package com.dfusiontech.server.model.dao;

import com.dfusiontech.server.model.data.BaseFilter;
import com.dfusiontech.server.model.data.BaseSort;
import org.springframework.data.domain.Pageable;

public interface PageableModelDAO<ENTITY, F extends BaseFilter> {

	PagedResult<ENTITY> getItemsPageable(F filter, Pageable pageable, BaseSort sort);

}
