package sistemadegerenciamentodebiblioteca;
import livro.Livro;
import livro.LivroDAO;
import usuario.Usuario;
import usuario.UsuarioDao;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

import emprestimo.Emprestimo;
import emprestimo.EmprestimoDAO;


public class SistemaDeGerenciamentoDeBiblioteca {                                        
    
    public static String formatarNome(String nome){
        //Remove os espaços e coloca em minúsculo
        nome =  nome.trim().toLowerCase();
        //Divide por espaços múltiplos
        String[] palavras = nome.split("\\s+");

        StringBuilder nomeFormatado = new StringBuilder();

        for (String palavra : palavras) {
            if(!palavra.isEmpty()){
                //Primeira letra
                nomeFormatado.append(Character.toUpperCase(palavra.charAt(0)))
                //resto da palavra como foi digitado
                .append(palavra.substring(1))
                .append(" ");
            }
        }
        //remove espaços extras no final
        return nomeFormatado.toString().trim();
    }
    //validando email
    public static boolean validarEmail(String email){
        String regex = "^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$";
        return email.matches(regex);
    }


    public static void separador(){
        System.out.println("\n---------------------------------------------\n");
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        UsuarioDao usuarioDao = new UsuarioDao();
        LivroDAO livroDAO = new LivroDAO();
        
       
        
        //Switch externo
        OUTER:
        while (true) {
            System.out.println("\n=== Menu ===");
            System.out.println("1 - Adicionar livro");
            System.out.println("2 - Listar livros");
            System.out.println("3 - Alugar livro");
            System.out.println("4 - Devolver livro");
            System.out.println("5 - Cadastro do usuário");
            System.out.println("6 - Listar usuários");
            System.out.println("7 - Buscar usuários por ID");
            System.out.println("8 - Remover livro por ID");
            System.out.println("9 - Remover livro por titulo");
            System.out.println("10 - Remover usuário por ID");
            System.out.println("11 - Sair");
            System.out.print(">> Escolha uma opção: ");
            if (!scanner.hasNextInt()) {
                System.out.println("Digite apenas números!");
                //descarta entrda inválida
                scanner.next();
                continue;
            }
            int opcao = scanner.nextInt();
            scanner.nextLine();
            //Switch interno
            switch (opcao) {
                case 1 -> {
                    
                    System.out.print("Digite o título do livro: ");
                    String titulo = scanner.nextLine();

                    System.out.print("Digite o autor do livro: ");
                    String autor = scanner.nextLine();

                    System.out.print("Digite o gênero do livro: ");
                    String genero = scanner.nextLine();

                    Livro livro = new Livro(titulo, autor, genero);
                    livro.setDisponivel(true);
                    livroDAO.salvarLivro(livro);
                    
                }

                case 2 -> {

                    List<Livro> livros = livroDAO.listarLivros();

                    if (livros.isEmpty()) {
                        System.out.println("Nenhum livro cadastrado.");
                    }else {
                        for (Livro livro : livros) {
                            System.out.println(livro.exibirInfo());
                        }
                    }
                    
                }

                case 3 -> {
                    
                    List<Usuario> usuarios = usuarioDao.listarUsuarios();
                    if (usuarios.isEmpty()) {
                        System.out.println("Nenhum usuário cadastrado.");
                        break;
                    }
                    System.out.println("Usuários cadastrados: ");
                    
                    for (Usuario usuario : usuarios) {
                        System.out.println("ID: " + usuario.getId() + " - Nome: " + usuario.getNome());
                    }

                    System.out.print("Digite o ID do usuário que vai alugar o livro: ");
                    String usuarioIdTexto = scanner.nextLine();

                    UUID usuarioId;
                    try {
                        usuarioId = UUID.fromString(usuarioIdTexto);
                    } catch (IllegalArgumentException e) {
                        System.out.println("ID inválido.");
                        break;
                    }

                    System.out.print("Digite o título do livro para alugar: ");
                    String titulo = scanner.nextLine();

                    Livro livro = livroDAO.buscarLivroPorTitulo(titulo);

                    if (livro != null && livro.getDisponivel()) {
                        livro.setDisponivel(false); 
                        livroDAO.atualizarLivro(livro);

                        Emprestimo emprestimo = new Emprestimo();
                        emprestimo.setUsuarioId(usuarioId);
                        emprestimo.setLivroId(livro.getId());
                        emprestimo.setDataAluguel(LocalDateTime.now());

                        EmprestimoDAO emprestimoDAO = new EmprestimoDAO();
                        emprestimoDAO.salvarEmprestimo(emprestimo);

                        
                        System.out.println("Livro " + livro.getTitulo() + " alugado com sucesso!");
                        separador();
                    } else {
                        System.out.println("Livro não disponivel ou não encontrado.");
                    }
                    separador();
                }

                case 4 -> {
                    EmprestimoDAO emprestimoDAO = new EmprestimoDAO();
                    System.out.print("Digite o título do livro para devolver: ");
                    String titulo = scanner.nextLine();

                    Livro livro = livroDAO.buscarLivroPorTitulo(titulo);

                    if (livro != null && !livro.getDisponivel()) {

                        Emprestimo emprestimo = emprestimoDAO.buscarEmprestimoAtivoPorLivroId(livro.getId());
                        if (emprestimo != null) {
                            emprestimoDAO.devolverLivro(emprestimo.getId());
                            livro.setDisponivel(true);
                            livroDAO.atualizarLivro(livro);
                        }else {
                            System.out.println("Nenhum empréstimo ativo encontrado para este livro.");
                        }
                        System.out.println("Livro " + livro.getTitulo() + " devolvido com sucesso!");
                        separador();
                    } else {
                        System.out.println("Livro não esta alugado ou não encontrado.");
                    }
                }

                case 5 -> {
                    
                    System.out.println("Digite o nome do usuário: ");
                    String nome = scanner.nextLine();
                    //aplicando formatação
                    nome = formatarNome(nome);


                    System.out.println("Digite o email: ");
                    String email= scanner.nextLine().trim().toLowerCase();

                    if (!validarEmail(email)) {
                        System.out.println("Email inválido! Tente novamente.");
                        break;
                    }

                    Usuario novoUsuario = new Usuario(nome, email);

                    
                    usuarioDao.salvarUsuario(novoUsuario);

                    System.out.println("Usuário criado com ID: " + novoUsuario.getId());
                    separador();
                    
                }

                case 6 -> {
                    List<Usuario> usuarios = usuarioDao.listarUsuarios();

                    if (usuarios.isEmpty()) {
                        System.out.println("Nenhum usuário cadastrado.");
                    } else {
                        System.out.println("\n--- Lista de Usuários ---");

                        for (Usuario usuario : usuarios) {
                            System.out.println("ID: " + usuario.getId());
                            System.out.println("Nome: " + usuario.getNome());
                            System.out.println("Email: " + usuario.getEmail());
                            separador();
                        }
                    }
                }
                
                case 7 -> {
                    System.out.println("Digite o ID do Usuário");
                    String idTexto = scanner.nextLine();

                    try {
                        UUID id = UUID.fromString(idTexto); //converte String para UUID

                        
                        Usuario usuario = usuarioDao.buscarUsuarioPorId(id);

                        if (usuario != null) {
                            System.out.println("Usuário encontrado: ");
                            System.out.println("Nome: " + usuario.getNome());
                            System.out.println("Email: " + usuario.getEmail());
                            separador();
                        }else {
                            System.out.println("Usuário não encontrado");
                        }

                    } catch (IllegalArgumentException e) {
                        System.out.println("Formato do ID inválido. Use um ID válido.");
                    }
                    
                }
                
                case 8 -> {
                   System.out.println("Digite o ID do livro que deseja remover: ");
                   
                   if (!scanner.hasNextInt()) {
                        System.out.println("ID inválido! Digite um número inteiro.");
                        scanner.nextLine();//descarta entrada invalida
                        break;
                   }
                   int idLivro = scanner.nextInt();
                   scanner.nextLine();//limpa Buffer

                   boolean sucesso = livroDAO.removerLivroPorId(idLivro);
                   if (sucesso) {
                        System.out.println("Livro removido com sucesso!");
                   }else {
                        System.out.println("Livro com ID " + idLivro + " não encontrado.");
                   }
                   separador();
                   
                }

                case 9 -> {
                    System.out.print("Digite o título do livro que deseja remover: ");
                    String titulo = scanner.nextLine();

                    boolean sucesso = livroDAO.removerLivro(titulo);
                    if (sucesso) {
                        System.out.println("Livro removido com sucesso!");
                    } else {
                        System.out.println("Livro com o título \"" + titulo + "\" não encontrado.");
                    }
                    separador();
                }
                
                
                case 10 -> {
                    List<Usuario> usuarios = usuarioDao.listarUsuarios();

                    if (usuarios.isEmpty()) {
                        System.out.println("Nenhum usuário cadastrado.");
                        break;
                    }

                    //Exibe os Usuários antes de pedir o ID
                    System.out.println("\n--- Lista de Usuários ---");
                    for (Usuario usuario : usuarios) {
                        System.out.println("ID: " + usuario.getId());
                        System.out.println("Nome: " + usuario.getNome());
                        System.out.println("Email: " + usuario.getEmail());
                        separador();
                    }

                    System.out.println("Digite o ID do usuário que deseja remover: ");
                    String idTexto = scanner.nextLine();


                    try {
                        UUID id = UUID.fromString(idTexto);

                        //Busca o usuário antes de excluir
                        Usuario usuarioParaRemover = usuarioDao.buscarUsuarioPorId(id);

                        if(usuarioParaRemover == null){
                            System.out.println("Usuário não encontrado.");
                            break;
                        }

                        //Confirmando se realmente deseja remover o usuário
                        System.out.println("Tem certeza que deseja remover o usuário: " + 
                            usuarioParaRemover.getNome() + " (" + usuarioParaRemover.getEmail() + ")? [s/n]");
                        
                        String confirmacao = scanner.nextLine();

                        if (confirmacao.equalsIgnoreCase("s")) {
                            usuarioDao.removerUsuarioPorId(id);
                            System.out.println("Retornando ao menu principal...");
                        } else {
                            System.out.println("Remoção cancelada");
                        }
                    } catch (IllegalArgumentException e) {
                        System.out.println("Formato de ID inválido. Use um ID válido.");
                    }
                    separador();
                }
                
                case 11 -> {
                    System.out.println("\nObrigado por usar o sistema. Até a próxima!");
                    break OUTER;
                }
                default -> System.out.println("Opção inválida.");
            }
        }
        
        scanner.close();

       
    }
}


