package io.javabrains.moviecatalogservice.resource;


import io.javabrains.moviecatalogservice.models.CatalogItem;
import io.javabrains.moviecatalogservice.models.Movie;
import io.javabrains.moviecatalogservice.models.Rating;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/catalog")
public class MovieCatalogResource {

    @Autowired
    private RestTemplate restTemplate;

    @RequestMapping(value = "/{userID}")
    public List<CatalogItem> getCatalog(@PathVariable("userID") String userID) {

        RestTemplate restTemplate = new RestTemplate();
        Movie movie = restTemplate.getForObject("http://localhost:8082/movies/vibhushit", Movie.class);

        List<Rating> ratings = Arrays.asList(
                new Rating("1234", 4),
                new Rating("5879", 3)
        );

        return ratings.stream().map(rating -> {

            restTemplate.getForObject("http://localhost:8082/movies/" + rating.getMovieId(), Movie.class);
            return new CatalogItem(movie.getName(), "Test", rating.getRating());

        })
                .collect(Collectors.toList());

    }
}
