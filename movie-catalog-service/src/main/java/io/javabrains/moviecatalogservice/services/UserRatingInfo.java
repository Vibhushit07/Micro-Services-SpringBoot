package io.javabrains.moviecatalogservice.services;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import io.javabrains.moviecatalogservice.models.Rating;
import io.javabrains.moviecatalogservice.models.UserRating;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

@Service
public class UserRatingInfo {

    @Autowired
    private RestTemplate restTemplate;

    @HystrixCommand(fallbackMethod = "getFallbackUserRating")
    public UserRating getUserRating(String userID) {
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
