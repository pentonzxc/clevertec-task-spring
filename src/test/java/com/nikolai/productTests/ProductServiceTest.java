package com.nikolai.productTests;

import com.nikolai.model.product.Product;
import com.nikolai.repository.ProductRepository;
import com.nikolai.service.ProductService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.Optional;

public class ProductServiceTest {

    @InjectMocks
    ProductService service;

    @Mock
    ProductRepository repository;

    @Captor
    ArgumentCaptor<Integer> argumentCaptor;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    public void whenFindProductById_thenReturnProduct() {
        var product = new Product(1, 2d);
        Mockito.doReturn(Optional.of(product)).when(repository).findById(product.getId());

        Assertions.assertSame(product, service.findProductById(product.getId()).get());
    }

    @Test
    public void whenFindCardById_expectCallRepositoryFindById(){
        int expectedId = 1;
        service.findProductById(expectedId);

        Mockito.verify(repository).findById(argumentCaptor.capture());

        Assertions.assertEquals(expectedId, argumentCaptor.getValue());
    }


}
