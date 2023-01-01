package io.javabrains.moviecatalogservice.resource;


import io.javabrains.moviecatalogservice.models.CatalogItem;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class MovieCatalogResource {

    public List<CatalogItem> getCatalog(String userId) {

    }
}
