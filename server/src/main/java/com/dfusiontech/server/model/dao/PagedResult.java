package com.dfusiontech.server.model.dao;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PagedResult<ENTITY> {

	private List<ENTITY> items;

	private Long count;

	public PagedResult() {
		;
	}

	public PagedResult(List<ENTITY> items, Long count) {
		this.items = items;
		this.count = count;
	}
}
