package sistemadegerenciamentodebiblioteca;
import livro.Livro;
import biblioteca.Biblioteca;
import usuario.Usuario;
import java.util.Scanner;
public class SistemaDeGerenciamentoDeBiblioteca {                                        

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Biblioteca biblioteca = new Biblioteca();
        
        //Switch externo
        OUTER:
        while (true) {
            System.out.println("\n--- Menu ---");
            System.out.println("1. Adicionar livro");
            System.out.println("2. Listar livros");
            System.out.println("3. Alugar livro");
            System.out.println("4. Devolver livro");
            System.out.println("5. Cadastrar-se");
            System.out.println("6. Listar Usuarios");
            System.out.println("7. Excluir livro");
            System.out.println("8. Excluir Usuario");
            System.out.println("9. Sair");
            System.out.print("Escolha uma opção: ");
            int opcao = scanner.nextInt();
            scanner.nextLine();
            //Switch interno
            switch (opcao) {
                case 1 -> {
                    System.out.print("Digite o título do livro: ");
                    String titulo = scanner.nextLine();
                    System.out.print("Digite o autor do livro: ");
                    String autor = scanner.nextLine();
                    System.out.print("Digite o genero do livro: ");
                    String genero = scanner.nextLine();
                    Livro livro = new Livro();
                    biblioteca.adicionarLivro(livro);
                    
                }
                case 2 -> {
                    biblioteca.listarLivros();
                    
                }
                case 3 -> {
                    System.out.print("Digite o título do livro para alugar: ");
                    String titulo = scanner.nextLine();
                    Livro livro = biblioteca.buscarLivroPorTitulo(titulo);
                    if (livro != null) {
                        biblioteca.aluguelDeLivro(livro);
                    } else {
                        System.out.println("Livro não encontrado.");
                    }                          
                }
                case 4 -> {
                    System.out.print("Digite o título do livro para devolver: ");
                    String titulo = scanner.nextLine();
                    Livro livro = biblioteca.buscarLivroPorTitulo(titulo);
                    if (livro != null) {
                        biblioteca.devolverLivro(livro);
                    } else {
                        System.out.println("Livro não encontrado.");
                    }
                }
                case 5 -> {
                    
                    System.out.println("Digite seu Usuario: ");
                    String nome = scanner.nextLine();
                    System.out.println("Digite seu email: ");
                    String email= scanner.nextLine();
                    System.out.println("Digite seu ID: ");
                    String id = scanner.nextLine();
                    Usuario novoUsuario = new Usuario(id, nome, email);
                    biblioteca.adicionarUsuario(novoUsuario);
                }
                case 6 -> {
                    biblioteca.listarUsuarios();
                }
                
                case 7 -> {
                    System.out.println("Digite o nome do livro");
                    String titulo = scanner.nextLine();
                    System.out.println("Digite o nome do autor");
                    String autor = scanner.nextLine();
                    Livro livro = new Livro();
                    biblioteca.removerLivro(livro);
                }
                
                case 8 -> {
                    System.out.println("Digite seu Usuario: ");
                    String nome = scanner.nextLine();
                    System.out.println("Digite seu email: ");
                    String email= scanner.nextLine();
                    String id = scanner.nextLine();
                    Usuario novoUsuario = new Usuario(id,nome,email);
                    biblioteca.removerUsuario(novoUsuario);
                }
                case 9 -> {
                    System.out.println("Saindo...");
                    break OUTER;
                }
                default -> System.out.println("Opção inválida.");
            }
        }
        
        scanner.close();
    }
}

