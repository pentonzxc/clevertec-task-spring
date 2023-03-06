package com.nikolai.model;

public class CacheEntity {
    private Integer id;

    private String name;

    private String whatever;

    public CacheEntity(Integer id, String name, String whatever) {
        this.id = id;
        this.name = name;
        this.whatever = whatever;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWhatever() {
        return whatever;
    }

    public void setWhatever(String whatever) {
        this.whatever = whatever;
    }

    @Override
    public String toString() {
        return "CacheEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", whatever='" + whatever + '\'' +
                '}';
    }
}
