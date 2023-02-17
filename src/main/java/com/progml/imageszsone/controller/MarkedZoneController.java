package com.progml.imageszsone.controller;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import com.progml.imageszsone.model.MarkedZone;
import com.progml.imageszsone.model.Point;
import com.progml.imageszsone.repository.MarkedZoneRepository;
import com.progml.imageszsone.repository.PointRepository;
import com.progml.imageszsone.utiliti.Utilities;

import java.util.Set;

@Controller
@RequiredArgsConstructor
public class MarkedZoneController {
    private final MarkedZoneRepository markedZoneRepository;
    private final PointRepository pointRepository;
    private final Utilities utilities;

    @PostMapping("/markedZone/add")
    @ResponseStatus(HttpStatus.OK)
    public void addMarkedZoneToImage(@RequestBody String body) {
        JsonObject jsonObject = new JsonParser().parse(body).getAsJsonObject();
        int imageId = jsonObject.getAsJsonPrimitive("imageId").getAsInt();

        JsonObject coords = jsonObject.getAsJsonObject("coords");
        int leftTopX = coords.getAsJsonPrimitive("leftTopX").getAsInt();
        int leftTopY = coords.getAsJsonPrimitive("leftTopY").getAsInt();
        int rightBottomX = coords.getAsJsonPrimitive("rightBottomX").getAsInt();
        int rightBottomY = coords.getAsJsonPrimitive("rightBottomY").getAsInt();

        Set<String> tags = utilities.extractTagsFromJObj(jsonObject);

        MarkedZone markedZone = new MarkedZone();
        markedZone.setImageId(imageId);
        Point leftTop = new Point(leftTopX, leftTopY);
        markedZone.setLeftTop(leftTop);
        Point rightBottom = new Point(rightBottomX, rightBottomY);
        markedZone.setRightBottom(rightBottom);
        markedZone.setTags(tags);

        pointRepository.save(leftTop);
        pointRepository.save(rightBottom);

        markedZoneRepository.save(markedZone);
    }
}
