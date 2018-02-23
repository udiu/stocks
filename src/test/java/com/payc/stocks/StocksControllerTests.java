package com.payc.stocks;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.payc.stocks.controllers.StocksController;
import com.payc.stocks.model.Stock;
import com.payc.stocks.service.StockService;

@RunWith(SpringRunner.class)
@WebMvcTest(StocksController.class)
public class StocksControllerTests {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private StockService service;

	private Stock stock;

	@Before
	public void initController() {
		stock = new Stock("test", BigDecimal.TEN);
		stock.setId(1l);
	}

	@Test
	public void testGet() throws Exception {
		given(service.getById(1L)).willReturn(stock);
		this.mockMvc.perform(get("http://localhost:8080/api/stocks/1").accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk()).andExpect(content().string("{\"name\":\"test\",\"amount\":10,\"id\":1}"));
	}

	@Test
	public void testGet_NotFound() throws Exception {
		given(service.getById(1L)).willReturn(null);
		this.mockMvc.perform(get("http://localhost:8080/api/stocks/1").accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isNotFound());
	}

	@Test
	public void testPut() throws Exception {
		given(service.getById(1L)).willReturn(stock);
		given(service.update(1L, stock)).willReturn(Boolean.TRUE);
		this.mockMvc
		.perform(put("http://localhost:8080/api/stocks/1").content("{\"name\":\"test\",\"amount\":1}")
				.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON_VALUE))
		.andExpect(status().isOk()).andExpect(content().string("{\"name\":\"test\",\"amount\":1,\"id\":1}"));
	}

	@Test
	public void testDelete() throws Exception {
		given(service.delete(1L)).willReturn(Boolean.TRUE);
		this.mockMvc
		.perform(delete("http://localhost:8080/api/stocks/1")
				.accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isNoContent());
	}


}