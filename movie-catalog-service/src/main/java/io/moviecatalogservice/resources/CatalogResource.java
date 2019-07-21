package io.moviecatalogservice.resources;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import io.moviecatalogservice.models.CatalogItem;
import io.moviecatalogservice.models.UserRating;
import io.moviecatalogservice.service.MovieInfoService;
import io.moviecatalogservice.service.UserRatingInfoService;

@RestController
@RequestMapping("/catalog")
public class CatalogResource {

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	WebClient.Builder webClientBuilder;

	@Autowired
	MovieInfoService movieInfoService;

	@Autowired
	UserRatingInfoService userRatingInfoService;

	@RequestMapping("/{userId}")
	public List<CatalogItem> getCatalog(@PathVariable("userId") String userId) {
		UserRating userRating = userRatingInfoService.getUserRating(userId);
		return userRating.getRatings().stream().map(rating -> movieInfoService.getCatalogItem(rating))
				.collect(Collectors.toList());
	}

}
/*
 * Alternative WebClient way Movie movie =
 * webClientBuilder.build().get().uri("http://localhost:8082/movies/"+
 * rating.getMovieId()) .retrieve().bodyToMono(Movie.class).block();
 */