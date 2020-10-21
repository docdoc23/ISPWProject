package main.java.bean;

import main.java.entity.Sorting;
import main.java.entity.User;

import java.util.GregorianCalendar;

public abstract class ResearchBean {

    protected String city;
    protected Double priceMin;
    protected Double priceMax;
    protected Double size;
    protected Boolean favorite;
    protected Sorting sorting;

    public ResearchBean(String city, Double priceMin, Double priceMax, Double size, Boolean favorite, Sorting sorting) {
        this.city = city;
        this.priceMin = priceMin;
        this.priceMax = priceMax;
        this.size = size;
        this.favorite = favorite;
        this.sorting = sorting;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Double getPriceMin() {
        return priceMin;
    }

    public void setPriceMin(Double priceMin) {
        this.priceMin = priceMin;
    }

    public Double getPriceMax() {
        return priceMax;
    }

    public void setPriceMax(Double priceMax) {
        this.priceMax = priceMax;
    }

    public Double getSize() {
        return size;
    }

    public void setSize(Double size) {
        this.size = size;
    }

    public Boolean getFavorite() {
        return favorite;
    }

    public void setFavorite(Boolean favorite) {
        this.favorite = favorite;
    }

    public Sorting getSorting() {
        return sorting;
    }

    public void setSorting(Sorting sorting) {
        this.sorting = sorting;
    }
}
