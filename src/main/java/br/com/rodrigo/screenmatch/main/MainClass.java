package br.com.rodrigo.screenmatch.main;

import br.com.rodrigo.screenmatch.ScreenmatchApplication;
import br.com.rodrigo.screenmatch.model.DadosEpisodio;
import br.com.rodrigo.screenmatch.model.DadosSerie;
import br.com.rodrigo.screenmatch.model.DadosTemporada;
import br.com.rodrigo.screenmatch.service.ConsumoApi;
import br.com.rodrigo.screenmatch.service.ConverteDados;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MainClass {

    private Scanner input = new Scanner(System.in);
    private ConsumoApi consumoApi = new ConsumoApi();
    private ConverteDados conversor = new ConverteDados();

    private final String ENDERECO = "https://www.omdbapi.com/?t=";
    private final String API_KEY = "&apikey=4cfea020";

    public void exibeMenu(){
        System.out.println("Digite o nome da Série para a busca");
        var nomeSerie = input.nextLine();
        var json = consumoApi.obterDados(ENDERECO + nomeSerie.replace(" ", "+") + API_KEY);
        DadosSerie dados = conversor.obterDados(json, DadosSerie.class);
        System.out.println(dados);

		List<DadosTemporada> temporadas = new ArrayList<>();

		for(int i = 1; i <= dados.totalTemporadas(); i++){
			json = consumoApi.obterDados(ENDERECO+nomeSerie.replace(" ", "+")+"&season="+i+API_KEY);
			DadosTemporada dadosTemporada = conversor.obterDados(json, DadosTemporada.class);
			temporadas.add(dadosTemporada);
		}

		temporadas.forEach(System.out::println);
        temporadas.forEach(t -> t.episodios().forEach(e -> System.out.println(e.titulo())));
    }
}
