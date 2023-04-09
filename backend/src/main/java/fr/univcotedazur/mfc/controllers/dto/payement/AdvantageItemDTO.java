package fr.univcotedazur.mfc.controllers.dto.payement;

import fr.univcotedazur.mfc.entities.AdvantageItem;

public class AdvantageItemDTO {
    private final long id;
    private final String type;
    private final String title;
    private final String description;
    private final int price; // in points
    private final String shopName;

    public AdvantageItemDTO(long id, String type, String title, String description, int price, String shopName) {
        this.id = id;
        this.type = type;
        this.title = title;
        this.description = description;
        this.price = price;
        this.shopName = shopName;
    }

    public AdvantageItemDTO(AdvantageItem advantageItem) {
        this.id = advantageItem.getId();
        this.type = advantageItem.getType().toString();
        this.title = advantageItem.getTitle();
        this.description = advantageItem.getDescription();
        this.price = advantageItem.getPrice().getPointAmount();
        this.shopName = (advantageItem.getShop() != null) ? advantageItem.getShop().getName() : "Unknown";
    }

    public long getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getPrice() {
        return price;
    }

    public String getShopName() {
        return shopName;
    }

    @Override
    public String toString() {
        return "AdvantageItemDTO{" +
                "id=" + id +
                ", type='" + type + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", shopName='" + shopName + '\'' +
                '}';
    }
}