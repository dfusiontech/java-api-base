package com.dfusiontech.server.model.jpa.entity;

public interface IEntityWithMetadata extends IEntityWithDates {
	Users getCreatedBy();
	Users getUpdatedBy();
}
