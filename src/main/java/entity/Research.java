package main.java.entity;

//import com.sun.xml.internal.bind.v2.model.core.ID;

import java.util.Date;
import java.util.GregorianCalendar;

public abstract class Research implements Comparable {
    protected String city;
    protected Double priceMin;
    protected Double priceMax;
    protected Double size;
    protected GregorianCalendar date;
    protected Boolean favorite;
    protected User user;
    protected Integer ID;
    protected Sorting sorting;

    public Research(String city, Double priceMin, Double priceMax, Double size, GregorianCalendar date, Boolean favorite,
                    User user, Integer ID) {
        this.city = city;
        this.priceMin = priceMin;
        this.priceMax = priceMax;
        this.size = size;
        this.date = date;
        this.favorite = favorite;
        this.user = user;
        this.ID = ID;
    }

    public Research(String city, Double priceMin, Double priceMax, Double size, GregorianCalendar date,
                    Boolean favorite, User user) {
        this.city = city;
        this.priceMin = priceMin;
        this.priceMax = priceMax;
        this.size = size;
        this.date = date;
        this.favorite = favorite;
        this.user = user;
    }

    public Research() {

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

    public GregorianCalendar getDate() {
        return date;
    }

    public void setDate(GregorianCalendar date) {
        this.date = date;
    }

    public Boolean getFavorite() {
        return favorite;
    }

    public void setFavorite(Boolean favorite) {
        this.favorite = favorite;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public Sorting getSorting() {
        return sorting;
    }

    public void setSorting(String sorting) {
        if (sorting.equals("moreExpensive"))
            this.sorting = Sorting.moreExpensive;
        if (sorting.equals("lessExpensive"))
            this.sorting = Sorting.lessExpensive;
        if (sorting.equals("moreBig"))
            this.sorting = Sorting.moreBig;
        if (sorting.equals("lessBig"))
            this.sorting = Sorting.lessBig;
        if (sorting.equals("moreRecent"))
            this.sorting = Sorting.moreRecent;
        if (sorting.equals("lessRecent"))
            this.sorting = Sorting.lessRecent;
    }

    @Override
    public int compareTo(Object o) {
        return this.date.compareTo(((Research) o).getDate());
    }
}
