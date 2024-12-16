package br.com.alura.TabelaFlipe.principal;

import br.com.alura.TabelaFlipe.models.Models;
import br.com.alura.TabelaFlipe.models.Dados;
import br.com.alura.TabelaFlipe.models.Veiculo;
import br.com.alura.TabelaFlipe.services.ConsumoApi;
import br.com.alura.TabelaFlipe.services.ConverteDados;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Principal {
    private final String URL = "https://parallelum.com.br/fipe/api/v1/";

    private Scanner read = new Scanner(System.in);
    private ConsumoApi consumo = new ConsumoApi();
    private ConverteDados conversor = new ConverteDados();

    public void showMenu(){
        System.out.println("""
                ***** Opções *****
                
                [1] - Carros
                [2] - Motos
                [3] - Caminhões
                
                """);

        // Coleta a opção do user
        var search = read.nextLine().toLowerCase();
        var json = "";


        // Busca a API dependendo da opção do user
        switch (search) {
            case ("carros"), ("motos"), ("caminhoes"):
                json = consumo.buscaApi(URL + search + "/marcas");
                break;
        }


        // Exibe a lista de marcas
        var marcas = conversor.obterLista(json, Dados.class);
        marcas.stream()
                .sorted(Comparator.comparing(Dados::cod))
                .forEach(System.out::println);

        System.out.println("Informe o código da marca para mais detalhes");

        // busca os modelos
        var codMarca = read.nextLine();


        json = consumo.buscaApi(URL +search+"/marcas/"+codMarca+"/modelos");
        var modeloLista  = conversor.obterDados(json, Models.class);


        System.out.println("Modelos dessa marca: ");

        // Pega o list que vem em models + compara e organiza em ordem a partir do cód + Exibe na tela todos os modelos
        modeloLista.modelos().stream()
                .sorted(Comparator.comparing(Dados::cod))
                .forEach(System.out::println);


        System.out.println("\nDigite uma parte do nome do carro para ser buscado:");
        var nomeVeiculo = read.nextLine();

        List<Dados> modelosFiltrados = modeloLista.modelos().stream()
                .filter(m -> m.name().toLowerCase().contains(nomeVeiculo.toLowerCase()))
                .collect(Collectors.toList());

        System.out.println("Modelos Filtrados: ");
        modelosFiltrados.forEach(System.out::println);

        System.out.println("\nDigite o código do modelo para detalhes de avaliação");

        var codigoModelo = read.nextLine();
        json = consumo.buscaApi(URL +search+"/marcas/"+codMarca+"/modelos/"+codigoModelo+"/anos/");

        List<Dados> anos = conversor.obterLista(json, Dados.class);

        List<Veiculo> veiculos = new ArrayList<>();

        for (int i = 0; i < anos.size(); i++) {
            json = consumo.buscaApi(URL +search+"/marcas/"+codMarca+"/modelos/"+codigoModelo+"/anos/"+anos.get(i).cod());
            Veiculo veiculo = conversor.obterDados(json, Veiculo.class);
            veiculos.add(veiculo);
        }

        System.out.println("\nTodos os veiculos filtrados com avaliações por ano: ");
        veiculos.forEach(System.out::println);


    }
}
