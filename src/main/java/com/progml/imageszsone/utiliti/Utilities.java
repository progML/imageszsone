package com.progml.imageszsone.utiliti;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import com.progml.imageszsone.repository.ImageRepository;

import javax.annotation.PostConstruct;
import java.util.HashSet;
import java.util.Set;

@RequiredArgsConstructor
@PropertySource("classpath:constants.yml")
@Service
public class Utilities {
    private final ImageRepository imageRepository;

    @Value("${clearDbOnStart}")
    private boolean clearDb;

    @PostConstruct
    public void removeAll() {
        if (clearDb) imageRepository.deleteAll();
    }


    public Set<String> extractTagsFromJObj(JsonObject jObj) {
        JsonArray tags = jObj.getAsJsonArray("tags");
        Set<String> set = new HashSet<>();
        for (JsonElement jElTag : tags) {
            String tag = jElTag.getAsString();
            if (!tag.isBlank())
                set.add(tag);
        }
        return set;
    }
}
