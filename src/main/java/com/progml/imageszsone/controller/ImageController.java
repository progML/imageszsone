package com.progml.imageszsone.controller;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import com.progml.imageszsone.model.Image;
import com.progml.imageszsone.repository.ImageRepository;
import com.progml.imageszsone.utiliti.Utilities;

import java.util.Set;

@Controller
@RequiredArgsConstructor
public class ImageController {
    private final ImageRepository imageRepository;
    private final Utilities utilities;

    @GetMapping("/addImage")
    public String addImage() {
        return "addImage";
    }


    @PostMapping("/addImage")
    @ResponseStatus(HttpStatus.OK)
    public String addImage(@RequestBody String body) {
        JsonObject jObj = new JsonParser().parse(body).getAsJsonObject();

        Set<String> set = utilities.extractTagsFromJObj(jObj);

        Image image = new Image();
        image.setImgLink(jObj.getAsJsonPrimitive("link").getAsString());
        image.setTags(set);
        imageRepository.saveAndFlush(image);

        return "redirect:/" + image.getId();
    }


}
