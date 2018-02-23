package com.payc.stocks;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.payc.stocks.model.Stock;
import com.payc.stocks.service.StockService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class StocksServiceTests {

	@Autowired
	private StockService stockService;

	@Test
	public void createAndGetTest(){
		Stock newItem = new Stock("testCreate", new BigDecimal(1234));
		Stock newItemAdded = stockService.create(newItem);
		assertThat(newItemAdded.getName()).isEqualTo(newItem.getName());
		assertThat(newItemAdded.getAmount()).isEqualTo(newItem.getAmount());

		Stock newItemGet = stockService.getById(newItemAdded.getId());
		assertThat(newItemAdded).isEqualTo(newItemGet);
	}

	@Test
	public void updateTest(){
		Stock newItem = new Stock("testUpdate", new BigDecimal(1234));
		Stock newItemAdded = stockService.create(newItem);
		assertThat(stockService.getAll()).isNotEmpty().contains(newItemAdded);

		newItemAdded.setAmount(BigDecimal.ZERO);
		boolean updateBool = stockService.update(newItemAdded.getId(), newItemAdded);
		assertThat(updateBool).isTrue();
		assertThat(stockService.getById(newItemAdded.getId()).getAmount()).isEqualTo(BigDecimal.ZERO);

		updateBool = stockService.updatePrice(newItemAdded.getId(), BigDecimal.TEN);
		assertThat(updateBool).isTrue();
		assertThat(stockService.getById(newItemAdded.getId()).getAmount()).isEqualTo(BigDecimal.TEN);
	}

	@Test
	public void delete(){
		Stock newItemAdded = stockService.create(new Stock("testUpdate", new BigDecimal(1234)));
		stockService.delete(newItemAdded.getId());
		assertThat(stockService.getById(newItemAdded.getId()).getDeleted()).isTrue();

	}

}
