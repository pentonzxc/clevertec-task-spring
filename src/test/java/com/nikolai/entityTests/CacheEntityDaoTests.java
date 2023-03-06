package com.nikolai.entityTests;

import com.nikolai.model.CacheEntity;
import com.nikolai.storage.CacheEntityDao;
import com.nikolai.storage.Dao;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CacheEntityDaoTests {
    Dao dao;

    @BeforeEach
    void setUp() {
        dao = new CacheEntityDao();
    }


    @Test
    public void whenEmptyFind_thenReturnNull() {
        Assertions.assertNull(dao.find(1));
    }

    @Test
    public void whenSaveAndFind_thenReturnElem() {
        CacheEntity entity = new CacheEntity(1, "2123", "sada");
        dao.save(entity);

        Assertions.assertEquals(entity, dao.find(entity.getId()));
    }


    @Test
    public void whenRemove_thenFindReturnNull() {
        CacheEntity entity = new CacheEntity(1, "2123", "sada");
        dao.save(entity);
        dao.remove(entity.getId());

        Assertions.assertNull(dao.find(entity.getId()));
    }


    @Test
    public void whenEmptyRemove_thenNotThrowException() {
        Assertions.assertDoesNotThrow(() -> dao.remove(1));
    }

    @Test
    public void whenSaveAndIdNull_thenThrowNullPointerException() {
        Assertions.assertThrows(NullPointerException.class, () -> dao.save(new CacheEntity(null, "sad", "sadas")));
    }


}
