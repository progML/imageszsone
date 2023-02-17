package com.progml.imageszsone.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;
import com.progml.imageszsone.model.Image;
import com.progml.imageszsone.model.MarkedZone;
import com.progml.imageszsone.repository.ImageRepository;
import com.progml.imageszsone.repository.MarkedZoneRepository;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class GalleryController {
    private final ImageRepository imageRepository;
    private final MarkedZoneRepository markedZoneRepository;


    @GetMapping("/")
    public String getAllImages(Model model, @RequestParam(required = false) String tag) {
        List<Image> list;
        if (tag != null) {
            list = imageRepository.findAllByTags(tag);
            model.addAttribute("search", tag);

            List<MarkedZone> markedZones = markedZoneRepository.findAllByTags(tag);
            model.addAttribute("markedZones", markedZones);
        } else {
            list = imageRepository.findAll();
        }
        model.addAttribute("images", list);
        return "gallery";
    }

    @GetMapping("/{id}")
    public String getImage(Model model, @PathVariable Long id) {
        Image image = imageRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Can't find image by id"));
        model.addAttribute("image", image);

        List<MarkedZone> markedZones = markedZoneRepository.getAllByImageId(image.getId());
        model.addAttribute("markedZones", markedZones);

        return "gallerySingleItem";
    }
}
