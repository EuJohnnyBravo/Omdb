package br.com.rodrigo.screenmatch.model;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class Episode {

    private Integer season;
    private String title;
    private Integer epNumber;
    private double rating;
    private LocalDate releaseDate;

    public Episode(Integer seasonNumber, EpisodeData episodeData) {
        this.season = seasonNumber;
        this.title = episodeData.title();
        this.epNumber = episodeData.epNumber();

        try{
            this.rating = Double.valueOf(episodeData.rating());
        } catch (NumberFormatException e){
            this.rating = 0.0;
        }

        try {
            this.releaseDate = LocalDate.parse(episodeData.released());
        } catch (DateTimeParseException e){
            this.releaseDate = null;
        }
    }

    public Integer getSeason() {
        return season;
    }

    public void setSeason(Integer season) {
        this.season = season;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getEpNumber() {
        return epNumber;
    }

    public void setEpNumber(Integer epNumber) {
        this.epNumber = epNumber;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    @Override
    public String toString() {
        return  "temporada=" + season +
                ", title='" + title + '\'' +
                ", numeroEpisodio=" + epNumber +
                ", rating=" + rating +
                ", dataLancamento=" + releaseDate;
    }
}
