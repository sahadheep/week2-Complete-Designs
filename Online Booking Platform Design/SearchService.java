import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SearchService {
    private final List<Resource> resources = new ArrayList<>();
    private final Map<String, List<Resource>> cache = new HashMap<>();

    public void addResource(Resource resource) {
        resources.add(resource);
        cache.clear();
    }

    public List<Resource> search(String location, LocalDate date, double minPrice, double maxPrice, String category) {
        String cacheKey = (location + "|" + date + "|" + minPrice + "|" + maxPrice + "|" + category).toLowerCase();
        if (cache.containsKey(cacheKey)) {
            System.out.println("  [CACHE] Search cache hit for key: " + cacheKey);
            return cache.get(cacheKey);
        }

        List<Resource> result = new ArrayList<>();
        for (Resource resource : resources) {
            boolean locationMatch = resource.getLocation().equalsIgnoreCase(location);
            boolean categoryMatch = category == null || category.isEmpty() || resource.getCategory().equalsIgnoreCase(category);
            boolean priceMatch = resource.getPrice() >= minPrice && resource.getPrice() <= maxPrice;

            if (locationMatch && categoryMatch && priceMatch) {
                result.add(resource);
            }
        }

        cache.put(cacheKey, result);
        return result;
    }
}
