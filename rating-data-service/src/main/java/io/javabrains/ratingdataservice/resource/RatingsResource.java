package io.javabrains.ratingdataservice.resource;

import io.javabrains.ratingdataservice.models.Rating;
import io.javabrains.ratingdataservice.models.UserRating;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/ratingsdata")
public class RatingsResource {

    @RequestMapping("/{movieId}")
    public Rating getRating(@PathVariable("movieId") String movieId) {
        return new Rating(movieId, 4);
    }

    @RequestMapping("/users/{userId}")
    public UserRating getUserRating(@PathVariable("userId") String userId) {

        List<Rating> ratings = Arrays.asList(
                new Rating("100", 4),
                new Rating("550", 3)
        );

        UserRating userRating = new UserRating();
        userRating.setUserRating(ratings);
        return userRating;
    }
}
