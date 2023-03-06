package com.nikolai.productTests;

import com.nikolai.model.product.Product;
import com.nikolai.repository.ProductRepository;
import com.nikolai.service.ProductService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @InjectMocks
    private ProductService service;

    @Mock
    private ProductRepository repository;

    @Captor
    private ArgumentCaptor<Integer> argumentCaptor;


    @Test
    void whenFindProductById_thenReturnProduct() {
        var product = new Product(1, 2d);
        Mockito.doReturn(Optional.of(product)).when(repository).findById(product.getId());

        Assertions.assertSame(product, service.findProductById(product.getId()).get());
    }

    @Test
    void whenFindCardById_expectCallRepositoryFindById() {
        int expectedId = 1;
        service.findProductById(expectedId);

        Mockito.verify(repository).findById(argumentCaptor.capture());

        Assertions.assertEquals(expectedId, argumentCaptor.getValue());
    }


}
