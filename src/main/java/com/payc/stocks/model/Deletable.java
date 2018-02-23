package com.payc.stocks.model;

public interface Deletable extends Identifiable {

	Boolean getDeleted();

	void setDeleted(final Boolean deleted);
}
