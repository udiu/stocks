package com.payc.stocks.controllers;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.payc.stocks.model.Stock;
import com.payc.stocks.model.StockResource;
import com.payc.stocks.service.StockService;

@RestController
@ExposesResourceFor(Stock.class)
@RequestMapping(value = "/api/stocks", produces = "application/json")
public class StocksController {

	@Autowired
	private StockService stockService;

	@PostConstruct
	public void init() {
		// Add some mock data
		stockService.create(new Stock("PROD 1", BigDecimal.ONE));
		stockService.create(new Stock("PROD 2", BigDecimal.TEN));
		stockService.create(new Stock("PROD 3", new BigDecimal(12.123)));
	}

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<Collection<StockResource>> getStocks() {
		return new ResponseEntity<>(stockService.getAll().stream()
				.map(StockResource::new).collect(Collectors.toList()), HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST, consumes = "application/json")
	public ResponseEntity<StockResource> createStock(@RequestBody Stock newStock) {
		if(StringUtils.isEmpty(newStock.getName()) || newStock.getAmount() == null ) {
			throw new IllegalArgumentException("{\"error\":\"At least one parameter is invalid or not supplied\"}");
		}
		Stock createdStock = stockService.create(newStock);
		return new ResponseEntity<>(new StockResource(createdStock), HttpStatus.CREATED);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<StockResource> getStockById(@PathVariable Long id) {
		Stock stock = stockService.getById(id);
		if( stock != null ){
			return new ResponseEntity<>(new StockResource(stock), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> deleteStock(@PathVariable Long id) {
		boolean wasDeleted = stockService.delete(id);
		HttpStatus responseStatus = wasDeleted ? HttpStatus.NO_CONTENT : HttpStatus.NOT_FOUND;
		return new ResponseEntity<>(responseStatus);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = "application/json")
	public ResponseEntity<StockResource> updateStock(@PathVariable Long id, @RequestBody Stock updatedStock) {
		Stock stock = stockService.getById(id);
		if( stock == null ){
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		stock.setAmount(updatedStock.getAmount());
		boolean wasUpdated = stockService.update(id, stock);
		if (wasUpdated) {
			return new ResponseEntity<>(new StockResource(stock), HttpStatus.OK);
		}
		else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@ExceptionHandler(IllegalArgumentException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public final String exceptionHandlerIllegalArgumentException(final IllegalArgumentException e) {
		return e.getMessage();
	}
}
