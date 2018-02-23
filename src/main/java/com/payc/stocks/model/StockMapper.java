package com.payc.stocks.model;

public class StockMapper {

	public static StockResource toResource(final Stock stock){
		return new StockResource(stock);
	}
}
