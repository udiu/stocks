package com.payc.stocks.service;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;

import com.payc.stocks.model.Stock;

@Service
public interface StockService extends GenericService<Stock> {

	boolean updatePrice(final Long id, final BigDecimal newPrice);

}
