package br.com.rodrigo.screenmatch.main;

import br.com.rodrigo.screenmatch.model.SeasonData;
import br.com.rodrigo.screenmatch.model.Series;
import br.com.rodrigo.screenmatch.model.SeriesData;
import br.com.rodrigo.screenmatch.service.ConsumeApi;
import br.com.rodrigo.screenmatch.service.ConvertData;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class MainClass {

    private final Scanner input = new Scanner(System.in);
    private final ConsumeApi consumeApi = new ConsumeApi();
    private final ConvertData converse = new ConvertData();
    private final String address = "https://www.omdbapi.com/?t=";
    private final String API_KEY = "&apikey=4cfea020";
    private final List<SeriesData> seriesData = new ArrayList<>();

    public void showMenu(){
        var option = -1;

        while (option != 0){
            var menu = """
                    ╔═══════════════════════════╗
                    1 - Buscar série
                    2 - Buscar episódios
                    3 - Listar séries buscadas
                    
                    0 - sair
                    ╚═══════════════════════════╝
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
        seriesData.add(data);
        System.out.println(data);
    }

    private void searchEpisodeBySeries(){
        SeriesData data = getSeriesData();
        List<SeasonData> seasons = new ArrayList<>();

        for(int i = 1; i <= data.totalSeasons(); i++){
            var json = consumeApi.obterDados(address +data.title().replace(" ", "+")+"&season="+i+API_KEY);
            SeasonData seasonData = converse.getData(json, SeasonData.class);
            seasons.add(seasonData);
        }
        seasons.forEach(System.out::println);
    }

    private void listSearchedSeries(){
        List<Series> series = new ArrayList<>();
        series = seriesData.stream()
                        .map(d -> new Series(d))
                        .collect(Collectors.toList());

        series.stream()
                .sorted(Comparator.comparing(Series::getGenre))
                .forEach(System.out::println);
    }
}
