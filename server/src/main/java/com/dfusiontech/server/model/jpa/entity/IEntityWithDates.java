package com.dfusiontech.server.model.jpa.entity;

import java.util.Date;

public interface IEntityWithDates {
	Date getCreatedAt();
	Date getUpdatedAt();
}
