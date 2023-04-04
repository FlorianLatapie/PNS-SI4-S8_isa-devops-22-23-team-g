package fr.univcotedazur.simpletcfs.cli.model;

public class AdvantageItemDTO {
    private final Long id;
    private final String type;
    private final String title;
    private final String description;
    private final int price; // in points
    private final String shopName;

    public AdvantageItemDTO(Long id, String type, String title, String description, int price, String shopName) {
        this.id = id;
        this.type = type;
        this.title = title;
        this.description = description;
        this.price = price;
        this.shopName = shopName;
    }

    public Long getId() {
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
        return "name: " + title + "  price: " + price + "  description: " + description + "  shop: " + shopName + "  type: " + type +'\n';
    }
}