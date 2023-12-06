package br.com.rodrigo.screenmatch.model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

@Entity
@Table(name = "episodes")
public class Episode {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer season;
    private String title;
    private Integer epNumber;
    private double rating;
    private LocalDate releaseDate;
    @ManyToOne
    private Series series;

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

    public Episode() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Series getSeries() {
        return series;
    }

    public void setSeries(Series series) {
        this.series = series;
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
