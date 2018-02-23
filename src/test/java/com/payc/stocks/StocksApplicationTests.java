package com.payc.stocks;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.payc.stocks.service.StockService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class StocksApplicationTests {

	@Autowired
	private StockService stockService;

	@Test
	public void contextLoads() throws Exception {
		assertThat(stockService).isNotNull();
	}
}
