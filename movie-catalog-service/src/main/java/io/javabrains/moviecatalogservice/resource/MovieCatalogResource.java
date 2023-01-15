package io.javabrains.moviecatalogservice.resource;


import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import io.javabrains.moviecatalogservice.models.CatalogItem;
import io.javabrains.moviecatalogservice.models.Movie;
import io.javabrains.moviecatalogservice.models.Rating;
import io.javabrains.moviecatalogservice.models.UserRating;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/catalog")
public class MovieCatalogResource {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private WebClient.Builder webClientBuilder;

    @RequestMapping(value = "/{userID}")
    public List<CatalogItem> getCatalog(@PathVariable("userID") String userID) {

        UserRating userRating = getUserRating(userID);

        return userRating.getUserRating().stream().map(rating -> getCatalogItem(rating))
                .collect(Collectors.toList());
    }

    @HystrixCommand(fallbackMethod = "getFallbackUserRating")
    private CatalogItem getCatalogItem(Rating rating) {
        Movie movie = restTemplate.getForObject("http://movie-info-service/movies/" + rating.getMovieId(), Movie.class);

        return new CatalogItem(movie.getName(), "Test", rating.getRating());
    }

    public CatalogItem getFallbackCatalogItem(Rating rating) {

        return new CatalogItem("Movie name not found", "", rating.getRating());
    }

    @HystrixCommand(fallbackMethod = "getFallbackUserRating")
    private UserRating getUserRating(String userID) {
        return restTemplate.getForObject("http://ratings-data-service/ratingsdata/users/" + userID, UserRating.class);
    }

    public UserRating getFallbackUserRating(@PathVariable("userID") String userID) {
        UserRating userRating = new UserRating();
        userRating.setUserId(userID);
        userRating.setUserRating(Arrays.asList(
                new Rating("0", 0)
        ));

        return userRating;
    }
}

/*
Line number 35 can be made asynchronous with this code
        Movie movie = webClientBuilder.build()
                            .get()
                            .uri("http://localhost:8082/movies/" + rating.getMovieId())
                            .retrieve()
                            .bodyToMono(Movie.class)
                            .block();

*/
