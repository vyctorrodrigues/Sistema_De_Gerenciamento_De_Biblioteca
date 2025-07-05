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
            System.out.println("7. Listar Usuario por ID");
            System.out.println("8. Excluir livro");
            System.out.println("9. Excluir Usuario por nome");
            System.out.println("10. Excluir Usuário por ID");
            System.out.println("11. Sair");
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
                    Livro livro = new Livro(titulo, autor, genero);
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
                    
                    System.out.println("Digite o nome Usuario: ");
                    String nome = scanner.nextLine();
                    System.out.println("Digite o email: ");
                    String email= scanner.nextLine();
                    Usuario novoUsuario = new Usuario(nome, email);
                    biblioteca.adicionarUsuario(novoUsuario);
                    System.out.println("Usuário criado com ID: " + novoUsuario.getId());
                    
                }
                case 6 -> {
                    biblioteca.listarUsuarios();
                }
                
                case 7 -> {
                    System.out.println("Digite o ID do Usuário");
                    String id = scanner.nextLine();
                    Usuario usuario = biblioteca.buscarUsuarioPorId(id);
                    if (usuario != null) {
                        System.out.println("Usuário encontrado: " + usuario.getNome() + ", Email: " + usuario.getEmail());
                    }else {
                        System.out.println("Usuário não encontrado");
                    }
                }
                
                case 8 -> {
                   System.out.println("Digite o livro que quer remover: ");
                   String titulo =scanner.nextLine();
                   biblioteca.removerLivro(titulo);
                }
                
                case 9 -> {
                    System.out.println("Digite o Usuário que deseja excluir: ");
                    String nome = scanner.nextLine();
                    System.out.println("Digite o email para remover: ");
                    String email = scanner.nextLine();
                    Usuario novoUsuario = new Usuario(nome,email);
                    biblioteca.removerUsuario(novoUsuario);
                }

                case 10 -> {
                    System.out.println("Digite o ID do usuário para excluir: ");
                    String id = scanner.nextLine();
                    biblioteca.removerUsuarioPorId(id);
                }

                case 11 -> {
                    System.out.println("Saindo...");
                    break OUTER;
                }
                default -> System.out.println("Opção inválida.");
            }
        }
        
        scanner.close();

       
    }
}


