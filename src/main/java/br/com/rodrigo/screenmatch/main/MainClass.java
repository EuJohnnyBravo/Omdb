package br.com.rodrigo.screenmatch.main;

import br.com.rodrigo.screenmatch.model.*;
import br.com.rodrigo.screenmatch.repository.ISeriesRepository;
import br.com.rodrigo.screenmatch.service.ConsumeApi;
import br.com.rodrigo.screenmatch.service.ConvertData;

import java.util.*;
import java.util.stream.Collectors;

public class MainClass {

    private final Scanner input = new Scanner(System.in);
    private final ConsumeApi consumeApi = new ConsumeApi();
    private final ConvertData converse = new ConvertData();
    private final String address = "https://www.omdbapi.com/?t=";
    private final String API_KEY = "&apikey=4cfea020";
    private final List<SeriesData> seriesData = new ArrayList<>();
    private final ISeriesRepository repository;
    private List<Series> series = new ArrayList<>();

    public MainClass(ISeriesRepository repository){
        this.repository = repository;
    }

    public void showMenu(){
        var option = -1;

        while (option != 0){
            var menu = """
                    ╔═══════════════════════════════╗
                    1 - Buscar série
                    2 - Buscar episódios
                    3 - Listar séries buscadas
                    4 - Buscar séries por titulo
                    5 - Buscar séries por ator
                    6 - Top 5 séries
                    7 - Buscar séries por categoria
                    
                    0 - sair
                    ╚═══════════════════════════════╝
                    """;

            System.out.println(menu);
            option = input.nextInt();
            input.nextLine();

            switch (option){
                case 1:
                    searchWebSeries();
                    break;
                case 2:
                    searchEpisodeBySeries();
                    break;
                case 3:
                    listSearchedSeries();
                    break;
                case 4:
                    searchSeriesByTitle();
                    break;
                case 5:
                    searchSeriesByActor();
                    break;
                case 6:
                    searchTop5Series();
                    break;
                case 7:
                    searchByCategory();
                    break;
                case 0:
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Opção inválida");
            }
        }
    }

    private SeriesData getSeriesData(){
        System.out.println("Digite o nome da Série para a busca");
        var name = input.nextLine();
        var json = consumeApi.obterDados(address + name.replace(" ", "+") + API_KEY);
        return converse.getData(json, SeriesData.class);
    }

    private void searchWebSeries(){
        SeriesData data = getSeriesData();
        Series series = new Series(data);
        repository.save(series);
        System.out.println(data);
    }

    private void searchEpisodeBySeries(){

        listSearchedSeries();

        System.out.println("Escolha uma serie pelo nome");
        var name = input.nextLine();

        Optional<Series> series = repository.findByTitleContainingIgnoreCase(name);

        if(series.isPresent()){
            var foundSeries = series.get();
            List<SeasonData> seasons = new ArrayList<>();

            for(int i = 1; i <= foundSeries.getTotalSeasons(); i++){
                var json = consumeApi.obterDados(address +foundSeries.getTitle().replace(" ", "+")+"&season="+i+API_KEY);
                SeasonData seasonData = converse.getData(json, SeasonData.class);
                seasons.add(seasonData);
            }
            seasons.forEach(System.out::println);

            List<Episode> episodes = seasons.stream()
                    .flatMap(d -> d.episodes().stream()
                            .map(e -> new Episode(d.seasonNumber(), e)))
                    .collect(Collectors.toList());
            foundSeries.setEpisodeList(episodes);
            repository.save(foundSeries);
        } else {
            System.out.println("Serie não encontrada...");
        }
    }

    private void listSearchedSeries(){
        series = repository.findAll();
        series.stream()
                .sorted(Comparator.comparing(Series::getGenre))
                .forEach(System.out::println);
    }

    private void searchSeriesByTitle(){
        System.out.println("Escolha uma serie pelo nome");
        var name = input.nextLine();
        Optional<Series> searchedSeries = repository.findByTitleContainingIgnoreCase(name);

        if(searchedSeries.isPresent()){
            System.out.println("Dados da série: " + searchedSeries.get());
        } else {
            System.out.println("Série não encontrada");
        }
    }

    private void searchSeriesByActor(){
        System.out.println("Nome do ator para a busca");
        var actorName = input.nextLine();
        System.out.println("Avaliação minima:");
        var rating = input.nextDouble();
        input.nextLine();
        List<Series> foundSeries = repository.findByActorsContainingIgnoreCaseAndRatingGreaterThanEqual(actorName, rating);
        System.out.println("Series em que o/a " + actorName + " trabalhou:");
        foundSeries.forEach(s ->
                System.out.println(s.getTitle() + " | avaliação: " + s.getRating()));
    }

    private void searchTop5Series(){
        List<Series> topSeries = repository.findTop5ByOrderByRatingDesc();
        topSeries.forEach(s ->
                System.out.println(s.getTitle() + " | avaliação: " + s.getRating()));
    }

    private void searchByCategory(){
        System.out.println("Digite uma categoria/genero:");
        var categoryName = input.nextLine();
        Category category = Category.fromPortugese(categoryName);
        List<Series> seriesByCategory = repository.findByGenre(category);
        System.out.println("Series da categoria: " + categoryName);
        seriesByCategory.forEach(System.out::println);
    }
}
