package com.payc.stocks.service;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;

import com.payc.stocks.model.Stock;

@Service
public class StockServiceImpl extends GenericServiceImpl<Stock> implements StockService {

	@Override
	public boolean updatePrice(Long id, BigDecimal newPrice) {
		if( !store.containsKey(id) ) {
			return false;
		}
		store.get(id).setAmount(newPrice);
		return true;
	}

}
