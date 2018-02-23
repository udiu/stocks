package com.payc.stocks.model;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

public class StockResource {

	private final Long id;

	private final String name;

	private final BigDecimal amount;

	public StockResource( final Stock stock) {
		this.id = stock.getId();
		this.name = stock.getName();
		this.amount = stock.getAmount();
	}

	@JsonProperty("id")
	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public BigDecimal getAmount() {
		return amount;
	}
}
