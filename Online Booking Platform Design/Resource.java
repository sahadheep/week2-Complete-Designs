public class Resource {
    private final String id;
    private final String type;
    private final String location;
    private final String category;
    private final double price;
    private final String description;

    public Resource(String id, String type, String location, String category, double price, String description) {
        this.id = id;
        this.type = type;
        this.location = location;
        this.category = category;
        this.price = price;
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public String getLocation() {
        return location;
    }

    public String getCategory() {
        return category;
    }

    public double getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return "Resource{" +
                "id='" + id + '\'' +
                ", type='" + type + '\'' +
                ", location='" + location + '\'' +
                ", category='" + category + '\'' +
                ", price=" + price +
                '}';
    }
}
