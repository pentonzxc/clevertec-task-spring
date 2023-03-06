package com.nikolai.entityTests;

import com.nikolai.model.CacheEntity;
import com.nikolai.service.CacheEntityServiceImpl;
import com.nikolai.storage.CacheEntityDao;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

public class CacheEntityServiceTests {

    @InjectMocks
    CacheEntityServiceImpl service;

    @Spy
    CacheEntityDao dao;

    @Captor
    ArgumentCaptor<Integer> captor;



    @BeforeEach
    void init() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    public void whenFind_thenReturnElem(){
        var entity = new CacheEntity(1, "entity", "some");
        Mockito.doReturn(entity).when(dao).find(1);

        Assertions.assertEquals(entity, service.find(entity.getId()));
    }


    @Test
    public void whenFind_expectCallDaoFind(){
        int expectedId = 1;
        service.find(expectedId);

        Mockito.verify(dao).find(captor.capture());

        Assertions.assertEquals(expectedId, captor.getValue());
    }


    @Test
    public void whenSaveElem_thenFindReturnElem(){
        var entity = new CacheEntity(1, "entity", "some");
        service.save(entity);

        Assertions.assertEquals(entity, service.find(entity.getId()));
    }


    @Test
    public void whenSave_expectCallDaoSave(){
        var entity = new CacheEntity(1, "entity", "some");
        var entityCaptor = ArgumentCaptor.forClass(CacheEntity.class);

        service.save(entity);
        Mockito.verify(dao).save(entityCaptor.capture());

        Assertions.assertSame(entity, entityCaptor.getValue());
    }


    @Test
    public void whenRemove_thenFindReturnNull(){
        var entity = new CacheEntity(1, "entity", "some");

        service.save(entity);
        service.remove(entity.getId());

        Assertions.assertNull(service.find(entity.getId()));
    }


    @Test
    public void whenRemove_expectCallDaoRemove(){
        int expectedId = 1;

        service.remove(expectedId);
        Mockito.verify(dao).remove(captor.capture());

        Assertions.assertSame(expectedId, captor.getValue());
    }






}