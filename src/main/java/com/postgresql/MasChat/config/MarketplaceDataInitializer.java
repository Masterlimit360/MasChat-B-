package com.postgresql.MasChat.config;

import com.postgresql.MasChat.model.MarketplaceCategory;
import com.postgresql.MasChat.repository.MarketplaceCategoryRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class MarketplaceDataInitializer {
    @Bean
    CommandLineRunner initMarketplaceCategories(MarketplaceCategoryRepository categoryRepository) {
        return args -> {
            if (categoryRepository.count() == 0) {
                List<MarketplaceCategory> categories = List.of(
                    create("Electronics", "phone-portrait"),
                    create("Vehicles", "car"),
                    create("Fashion", "shirt"),
                    create("Home & Garden", "home"),
                    create("Real Estate", "business"),
                    create("Baby & Kids", "happy"),
                    create("Sports & Outdoors", "bicycle"),
                    create("Health & Beauty", "heart"),
                    create("Services", "construct"),
                    create("Jobs", "briefcase"),
                    create("Pets", "paw"),
                    create("Books, Music & Media", "book"),
                    create("Food & Agriculture", "nutrition"),
                    create("Industrial & Business", "build"),
                    create("Others", "apps")
                );
                categoryRepository.saveAll(categories);
            }
        };
    }

    private MarketplaceCategory create(String name, String icon) {
        MarketplaceCategory cat = new MarketplaceCategory();
        cat.setName(name);
        cat.setIcon(icon);
        return cat;
    }
} 