package io.javabrains.moviecatalogservice.resource;


import io.javabrains.moviecatalogservice.models.CatalogItem;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/catalog")
public class MovieCatalogResource {

    @RequestMapping(value = "/{userID}")
    public List<CatalogItem> getCatalog(@PathVariable("userID") String userID) {

        return Collections.singletonList(
                new CatalogItem("Transformers", "Test", 4)
        );
    }
}
