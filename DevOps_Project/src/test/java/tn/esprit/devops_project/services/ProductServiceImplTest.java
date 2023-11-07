package tn.esprit.devops_project.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import tn.esprit.devops_project.entities.Product;
import tn.esprit.devops_project.entities.ProductCategory;
import tn.esprit.devops_project.entities.Stock;
import tn.esprit.devops_project.repositories.ProductRepository;
import tn.esprit.devops_project.repositories.StockRepository;
import tn.esprit.devops_project.services.ProductServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
///test avec mockito
public class ProductServiceImplTest {

    @Mock
    private StockRepository stockRepository;

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductServiceImpl productService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testAddProduct() {
        // Create a simulated Stock object
        Stock stock = new Stock();
        stock.setIdStock(1L);

        // Define the behavior of the mock stockRepository to return the stock when findById is called with 1L
        when(stockRepository.findById(1L)).thenReturn(Optional.of(stock));

        // Create a simulated Product object to add
        Product productToAdd = new Product();
        productToAdd.setTitle("Test Product");

        // Define the behavior of the mock productRepository to return the productToAdd when save is called
        when(productRepository.save(productToAdd)).thenReturn(productToAdd);

        // Call the method under test
        Product savedProduct = productService.addProduct(productToAdd, 1L);

        // Verify that the productRepository's save method was called
        verify(productRepository).save(productToAdd);

        // Check if the method returned a valid Product object
        assertNotNull(savedProduct);
        assertEquals("Test Product", savedProduct.getTitle());
    }



    @Test
    public void testRetrieveAllProducts() {
        List<Product> simulatedProducts = new ArrayList<>();
        simulatedProducts.add(new Product());
        simulatedProducts.add(new Product());
        simulatedProducts.add(new Product());

        when(productRepository.findAll()).thenReturn(simulatedProducts);

        List<Product> retrievedProducts = productService.retreiveAllProduct();

        assertNotNull(retrievedProducts);
        assertEquals(3, retrievedProducts.size()); // Verify that the list contains 3 products (as in our simulation)


    }


    @Test
    public void testRetrieveProductByCategory() {
        ProductCategory category = ProductCategory.ELECTRONICS;

        List<Product> products = new ArrayList<>();
        Product product1 = new Product();
        product1.setTitle("Product1");
        product1.setCategory(category);

        Product product2 = new Product();
        product2.setTitle("Product2");
        product2.setCategory(category);

        Product product3 = new Product();
        product3.setTitle("Product3");
        product3.setCategory(category);

        products.add(product1);
        products.add(product2);
        products.add(product3);

        when(productRepository.findByCategory(category)).thenReturn(products);

        List<Product> retrievedProducts = productService.retrieveProductByCategory(category);

        assertEquals(3, retrievedProducts.size());
    }


    @Test
    public void testDeleteProduct() {
        Long productId = 1L;

        productService.deleteProduct(productId);

        // Verify that the deleteById method of the mock productRepository was called with the simulated product ID
        verify(productRepository).deleteById(productId);
    }



    @Test
    public void testRetrieveProductStock() {
        Long stockId = 1L;

        // Create a simulated list of products for this stock
        List<Product> products = new ArrayList<>();
        Product product1 = new Product();
        product1.setTitle("Product1");
        Stock stock1 = new Stock();
        stock1.setIdStock(stockId);
        product1.setStock(stock1);

        Product product2 = new Product();
        product2.setTitle("Product2");
        Stock stock2 = new Stock();
        stock2.setIdStock(stockId);
        product2.setStock(stock2);

        Product product3 = new Product();
        product3.setTitle("Product3");
        Stock stock3 = new Stock();
        stock3.setIdStock(stockId);
        product3.setStock(stock3);

        products.add(product1);
        products.add(product2);
        products.add(product3);

        // Define the behavior of the mock productRepository to return the simulated list when findByStockIdStock is called with the simulated stock ID
        when(productRepository.findByStockIdStock(stockId)).thenReturn(products);

        List<Product> retrievedProducts = productService.retreiveProductStock(stockId);

        assertEquals(3, retrievedProducts.size());
    }



    @Test
    public void testRetrieveProduct() {
        Long productId = 1L;
        Product simulatedProduct = new Product();
        simulatedProduct.setIdProduct(productId);

        when(productRepository.findById(productId)).thenReturn(Optional.of(simulatedProduct));

        Product retrievedProduct = productService.retrieveProduct(productId);

        assertNotNull(retrievedProduct);
        assertEquals(productId, retrievedProduct.getIdProduct());
    }

    @Test
    public void testRetrieveProductWhenNotFound() {
        // Define the behavior of the mock productRepository to return an empty Optional when findById is called
        when(productRepository.findById(anyLong())).thenReturn(Optional.empty());

        Long unknownProductId = 999L;

        // Verify that the method throws an exception when the product is not found
        assertThrows(NullPointerException.class, () -> productService.retrieveProduct(unknownProductId));
    }
}





