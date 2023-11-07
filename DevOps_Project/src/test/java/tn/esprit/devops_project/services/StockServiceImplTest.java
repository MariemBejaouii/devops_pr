package tn.esprit.devops_project.services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension; // Use SpringExtension instead of SpringRunner
import tn.esprit.devops_project.entities.Stock;
import tn.esprit.devops_project.repositories.StockRepository;

import java.util.List;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
/// test avec JUnit
@ExtendWith(SpringExtension.class)
@SpringBootTest
public class StockServiceImplTest {


    @Autowired
    private StockRepository stockRepository;

    @Autowired
    private StockServiceImpl stockService;

    @Test
    public void testAddStockWithSucces() {
        Stock stockToSave = new Stock();
        stockToSave.setTitle("Test Stock");
        Stock savedStock = stockService.addStock(stockToSave);

        assertEquals(stockToSave.getTitle(), savedStock.getTitle());
    }

   /* @Test
    public void testAddStockWithFailure() {
        Stock stockToSave = new Stock();
        stockToSave.setTitle("Test Stock");
        Stock savedStock = stockService.addStock(stockToSave);

        assertNotEquals("Test Stock", savedStock.getTitle());
    }*/

    @Test
    public void testRetrieveStock() {
        Stock stock = new Stock();
        stock.setTitle("Test Stock");
        stockRepository.save(stock);

        Stock retrievedStock = stockService.retrieveStock(stock.getIdStock());

        assertEquals(stock.getTitle(), retrievedStock.getTitle());
    }

    @Test
    public void testRetrieveStockNotFound() {

        assertThrows(NullPointerException.class, () -> stockService.retrieveStock(999L));
    }

/*    @Test
    public void testRetrieveAllStock() {
        Stock stock1 = new Stock();
        stock1.setTitle("Stock 1");
        Stock stock2 = new Stock();
        stock2.setTitle("Stock 2");
        stockRepository.save(stock1);
        stockRepository.save(stock2);

        List<Stock> retrievedStocks = stockService.retrieveAllStock();

        assertEquals(29, retrievedStocks.size());
    }*/
}




