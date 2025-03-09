package application;

import model.Borda;
import model.Espaco;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

public class Main {

    private final static Scanner sc = new Scanner(System.in);

    private static Borda borda;

    private final static int LIMITE_BORDA = 9;

    public static void main(String[] args) {

        final var posicoes = Stream.of(args).collect(Collectors.toMap(k -> k.split(",")[0], v -> v.split(",")[1]));

        var opcao = -1;
        while(true) {
            System.out.println("Selecione uma das opções a seguir");
            System.out.println("1 - Iniciar um novo Jogo");
            System.out.println("2 - Colocar um novo número");
            System.out.println("3 - Remover um número");
            System.out.println("4 - Visualizar jogo atual");
            System.out.println("5 - Verificar status do jogo");
            System.out.println("6 - limpar jogo");
            System.out.println("7 - Finalizar jogo");
            System.out.println("8 - Sair");
            System.out.print("Digite: ");
            opcao = sc.nextInt();


            switch (opcao) {
                case 1 -> comecarJogo(posicoes);
                case 2 -> inserirNumero();
                case 3 -> removerNumero();
                case 4 -> mostrarJogoAtual();
                case 5 -> mostrarStatusDoJogo();
                case 6 -> limparJogo();
                case 7 -> terminarJogo();
                case 8 -> System.exit(0);
                default -> System.out.println("Opção inválida, selecione uma das opções do menu");

            }
        }
    }

    private static void comecarJogo(Map<String, String> posicoes) {
        if(nonNull(borda)){
            System.out.println("O jogo já foi iniciado");
            return;
        }
        List<List<Espaco>> espacos = new ArrayList<>();
        for(int i = 0; i < LIMITE_BORDA; i++){
            espacos.add(new ArrayList<>());
            for(int j = 0; j > LIMITE_BORDA; j++){
                var configuracaoPosicao = posicoes.get("%s, %s".formatted(i,j));
                var esperado = Integer.parseInt(configuracaoPosicao.split(";")[0]);
                var fixo =  Boolean.parseBoolean(configuracaoPosicao.split(";")[1]);
                var espacoAtual = new Espaco(esperado,fixo);
                espacos.get(i).add(espacoAtual);
            }
        }
        borda = new Borda(espacos);
        System.out.println("O jogo está pronto para começar!");

    }

    private static void inserirNumero() {
        if(isNull(borda)){
            System.out.println("O jogo ainda não foi iniciado");
            return;
        }
        System.out.print("Insira o numero da coluna: ");
        var coluna = executarAteTerNumeroValido(0, 8);
        System.out.print("Insira o numero da linha: ");
        var linha =  executarAteTerNumeroValido(0,8);
        System.out.printf("Insira o numero da posição escolhida: [%s,%s]\n ",coluna,linha);
        var valor = executarAteTerNumeroValido(1,9);
        if(!borda.alterarValor(coluna,linha,valor)){
            System.out.printf("A posicão [%s,%s] tem um valor fixo\n", coluna,linha);
        }

    }

    private static void removerNumero() {
        if(isNull(borda)){
            System.out.println("O jogo ainda não foi iniciado");
            return;
        }
        System.out.print("Insira o numero da coluna: ");
        var coluna = executarAteTerNumeroValido(0, 8);
        System.out.print("Insira o numero da linha: ");
        var linha =  executarAteTerNumeroValido(0,8);
        System.out.printf("Insira o numero da posição escolhida: [%s,%s]\n ",coluna,linha);
        if(!borda.valorLimpo(coluna,linha)){
            System.out.printf("A posicão [%s,%s] tem um valor fixo\n", coluna,linha);
        }
    }

    private static void mostrarJogoAtual() {
    }

    private static void mostrarStatusDoJogo() {
    }

    private static void limparJogo() {
    }

    private static void terminarJogo() {
    }

    private static int executarAteTerNumeroValido(final int min, int max){
        var atual = sc.nextInt();
        while(atual < min || atual > max){
            System.out.printf("Informe um numero entre %s  e %s\n", min,max);
            atual = sc.nextInt();
        }
        return atual;
    }

}