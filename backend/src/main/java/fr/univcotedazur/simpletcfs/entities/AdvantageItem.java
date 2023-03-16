package fr.univcotedazur.simpletcfs.entities;

import java.util.Objects;
import java.util.UUID;

public class AdvantageItem {
    private UUID id;

    private Status type;

    private String title;

    private String description;
    private Point price;
    private Shop shop;

    public AdvantageItem(Status type, String title, String description, Point price, Shop shop) {
        this.id = UUID.randomUUID();
        this.type = type;
        this.title = title;
        this.description = description;
        this.price = price;
        this.shop = shop;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Status getType() {
        return type;
    }

    public void setType(Status type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Point getPrice() {
        return price;
    }

    public void setPrice(Point price) {
        this.price = price;
    }

    public Shop getShop() {
        return this.shop;
    }

    public void setShop(Shop shop) {
        this.shop = shop;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AdvantageItem that = (AdvantageItem) o;
        return type == that.type && Objects.equals(title, that.title) && Objects.equals(description, that.description) && Objects.equals(price, that.price) && Objects.equals(shop, that.shop);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, title, description, price, shop);
    }
}
