package fr.univcotedazur.simpletcfs.entities;

import java.util.Objects;

public class Item {

    private Cookies cookie;
    private int quantity;

    public Item() {
    }

    public Item(Cookies cookie, int quantity) {
        this.cookie = cookie;
        this.quantity = quantity;
    }

    public Cookies getCookie() {
        return cookie;
    }

    public void setCookie(Cookies cookie) {
        this.cookie = cookie;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return quantity + "x" + cookie.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Item)) return false;
        Item item = (Item) o;
        return quantity == item.quantity && cookie == item.cookie;
    }

    @Override
    public int hashCode() {
        return Objects.hash(cookie, quantity);
    }

}
