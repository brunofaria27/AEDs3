import java.io.*;
import java.util.Scanner;

public class Main {
    /**
     * Imprime o menu para o usuário
     */
    public static void menu() {
        System.out.println("\n1 - Criar time");
        System.out.println("2 - Criar partida");
        System.out.println("3 - Procurar time");
        System.out.println("4 - Deletar time");
        System.out.println("5 - Atualizar dados do time");
        System.out.println("6 - Listar todos os times");
        System.out.println("7 - Buscar na lista Invertida");
        System.out.println("8 - Rodar simulação");
        System.out.println("0 - Sair");
    }

    /**
     * Faz a leitura do último ID utilizado, se for a primeira vez de uso, o ID está inicializado com -1
     * e contruirá o primeiro objeto com ID = 0
     * @param id
     */
    public static int getLastId(int id) {
        RandomAccessFile arq;
        int idReturn;

        try {
            arq = new RandomAccessFile("dados/clube.db", "rw");
            idReturn = arq.readInt();
            arq.close();

            return idReturn;
        } catch(IOException e) {
            System.out.println("Erro ao ler o último id a ser inserido!");
            return id;
        }
    }

    public static void main(String[] args) {
        CRUD crud = new CRUD();
        IndexDAO index = new IndexDAO();
        ListaInvertida listaInvertida = new ListaInvertida();
        OrdenacaoExterna ordenacao = new OrdenacaoExterna();

        Scanner sc = new Scanner(System.in);

        try {
            byte opcao;
            int id = -1;
            id = getLastId(id);
            
            do {
                menu();
    
                System.out.print("\nDigite sua opção: ");
                opcao = sc.nextByte();
    
                if (opcao != 0) {
                    if (opcao == 1) {
                        String nome;
                        String cnpj;
                        String cidade;
    
                        id++;
    
                        sc.nextLine(); // fazendo a captura do "enter" para não ter problema na leitura dos dados de criação
                        System.out.print("\nNome: ");
                        nome = sc.nextLine();
                        System.out.print("CNPJ: ");
                        cnpj = sc.nextLine();
                        System.out.print("Cidade: ");
                        cidade = sc.nextLine();
    
                        Clube c = new Clube((byte) id, nome, cnpj, cidade);
                        crud.create(c, id);
                        listaInvertida.createArqLista(nome, (byte) id, "dados/listaInvertida/listaInvertidaNome.db");       // Criar lista para o nome
                        listaInvertida.createArqLista(cidade, (byte) id, "dados/listaInvertida/listaInvertidaCidade.db");   // Criar lista para a cidade
                    } else if (opcao == 2) {
                        String time1;
                        String time2;
                        int golsTime1;
                        int golsTime2;
    
                        sc.nextLine();
    
                        System.out.print("\nInsira o nome do primeiro time: ");
                        time1 = sc.nextLine();
                        System.out.print("Insira o nome do segundo time: ");
                        time2 = sc.nextLine();
                        System.out.print("Insira a quantidade de gols que o primeiro time fez: ");
                        golsTime1 = sc.nextInt();
                        System.out.print("Insira a quantidade de gols que o segundo time fez: ");
                        golsTime2 = sc.nextInt();
    
                        if (crud.readByName(time1) != null && crud.readByName(time2) != null) { //Checando se os nomes de time inserido são válidos
                            crud.matchGenerator(time1, time2, golsTime1, golsTime2);
                        } else {
                            System.out.println("\nUm dos times inseridos é inválido!");
                        }
                    } else if(opcao == 3) {
                        byte idSearch;
                        Clube clube;
    
                        System.out.println("\nInsira o ID que deseja pesquisar: ");
                        idSearch = sc.nextByte();
                        
                        clube = crud.readId(idSearch);
                        if(crud.idExists(idSearch)) {
                            System.out.println(clube);
                        } else {
                            System.out.println("O id inserido não existe.");
                        }
                    } else if(opcao == 4) {
                        System.out.println("\nInsira o ID do usuário que deseja deletar: ");
                        byte idDel = sc.nextByte();
                        
                        if (crud.delete(idDel) == true) {
                            index.deleteValue((byte) idDel);
                            System.out.println("Time deletado com sucesso!");
                        } else {
                            System.out.println("Time não foi encontrado!");
                        }
                    } else if (opcao == 5) {
                        byte idUpd;
                        String nameUpd;
                        String cnpjUpd;
                        String cidadeUpd;

                        Clube clubePrint;
                        Clube clubeVazio = new Clube((byte) 0, "", "", "");
                        
                        System.out.print("\nInsira o ID do time que deseja alterar: ");
                        idUpd = sc.nextByte();

                        clubePrint = crud.readId(idUpd);
                        System.out.print(clubePrint);
    
                        if (crud.readId(idUpd) != clubeVazio) {
                            System.out.println("\n\n***Insira a seguir os novos dados***\n");
                            sc.nextLine();  // Pegar enter
                            System.out.print("Nome: ");
                            nameUpd = sc.nextLine();
                            System.out.print("CNPJ: ");
                            cnpjUpd = sc.nextLine();
                            System.out.print("Cidade: ");
                            cidadeUpd = sc.nextLine();
        
                            Clube cUpd = new Clube(idUpd, nameUpd, cnpjUpd, cidadeUpd);
        
                            if (crud.update(cUpd)) {
                                listaInvertida.updateLista(nameUpd, idUpd, "dados/listaInvertida/listaInvertidaNome.db", false);
                                listaInvertida.updateLista(cidadeUpd, idUpd, "dados/listaInvertida/listaInvertidaCidade.db", false);
                                System.out.println("Registro atualizado com sucesso!");
                            } else {
                                System.out.println("Não foi possível atualizar o registro!");
                            }
                        } else {
                            System.out.println("\nID inserido inexistente!");
                        }
                        
    
                    } else if (opcao == 6) {
                        crud.readAll();
                    } else if (opcao == 7) {
                        System.out.println("Deseja busca por nome(1) ou cidade(2)");
                        int buscaOption = sc.nextInt();

                        sc.nextLine();
                        if(buscaOption == 1) {
                            // busca por nome
                            String nome;
                            System.out.println("Digite o nome que deseja: ");
                            nome = sc.nextLine();
                            listaInvertida.searchList(nome, "dados/listaInvertida/listaInvertidaNome.db");
                        } else if(buscaOption == 2) {
                            // busca por cidade
                            String cidade;
                            System.out.println("Digite a cidade que deseja: ");
                            cidade = sc.nextLine();
                            listaInvertida.searchList(cidade, "dados/listaInvertida/listaInvertidaCidade.db");
                        } else {
                            System.out.println("A opcao escolhida nao foi valida");
                        }
                    } else if (opcao == 8) {
                        ordenacao.intercalacaoBalanceada(10, 2);
                    }
                }
            } while (opcao != 0);
            sc.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}