package br.com.rodrigo.screenmatch.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record EpisodeData(@JsonAlias("Title") String title,
                          @JsonAlias("Episode") Integer epNumber,
                          @JsonAlias("imdbRating")String rating,
                          @JsonAlias("Released") String released) {
}
