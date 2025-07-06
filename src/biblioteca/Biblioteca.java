package biblioteca;

import usuario.Usuario;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import emprestimo.Emprestimo;
import emprestimo.EmprestimoDAO;
import livro.Livro;

public class Biblioteca {
    private EmprestimoDAO emprestimoDao = new EmprestimoDAO();
    
    private Map<String, Livro> livrosDisponiveis = new HashMap<>();
    private Map<UUID, Usuario> usuarios = new HashMap<>();
    
    public void adicionarUsuario(Usuario usuario){
        if(!usuarios.containsKey(usuario.getId())){
            usuarios.put(usuario.getId(), usuario);
            System.out.println("Usuario adicionado com sucesso!");
        }else {
            System.out.println("Usuario ja Cadastrado!");
        }
    }
    
    public void removerUsuario(Usuario usuario){
        if(usuarios.containsKey(usuario.getId())){
            usuarios.remove(usuario.getId());
            System.out.println("Usuario Excluido!");
        }else {
            System.out.println("Usuario não existe!");
        }
    }

    public void removerUsuarioPorId(UUID id){
        if (usuarios.containsKey(id)) {
            usuarios.remove(id);
            System.out.println("Usuário removido com sucesso!");
        }else {
            System.out.println("Usuário com ID " + id + " não encontrado.");
        }
    }
    
    public Usuario buscarUsuarioPornome(String nome){
        for (Usuario usuario : usuarios.values()) {
            if (usuario.getNome().equalsIgnoreCase(nome)) {
                return usuario;
            }
        }
        return null; // não encontrou
    }   
    
    public Usuario buscarUsuarioPorId(UUID id){
        return usuarios.get(id);
    }

    public void adicionarLivro(Livro livro){
        if(!livrosDisponiveis.containsKey(livro.getTitulo())){
            livrosDisponiveis.put(livro.getTitulo(), livro);
            System.out.println("Livro adicionado com sucesso");
        }else {
            System.out.println("O livro ja esta na biblioteca.");
        }
    }
    
    public void removerLivro(String titulo){
        if(livrosDisponiveis.containsKey(titulo)){
            livrosDisponiveis.remove(titulo);
            System.out.println("Livro removido com sucesso!");
        }else {
            System.out.println("Esse tituto não existe para ser removido!");
        }
    }
    
    public boolean verificarDisponibilidade(Livro livro){
        return livrosDisponiveis.containsKey(livro.getTitulo()) && livro.getDisponivel();
    }
    
    public void aluguelDeLivro(UUID usuarioId, Livro livro){
        Usuario usuario = usuarios.get(usuarioId);

        if (usuario == null) {
            System.out.println("Usuário não encontrado");
            return;
        }

        if (!verificarDisponibilidade(livro)) {
            System.out.println("Livro não disponível.");
            return;
        }

        //Atualiza status em memoria
        livro.setDisponivel(false);
        usuario.getLivrosAlugados().add(livro);

        //Cria e salva o emprestimo dentro do banco

        Emprestimo emprestimo = new Emprestimo(0, usuarioId, livro.getId(), LocalDateTime.now(), null);

        emprestimoDao.salvarEmprestimo(emprestimo);
        System.out.println("Livro alugado com sucesso: " + livro.getTitulo());
    }
    
    public void devolverLivro(UUID usuarioId, Livro livro){
        Usuario usuario = usuarios.get(usuarioId);

        if (usuario ==  null) {
            System.out.println("Usuário não encontrado");
            return;
        }

        if (livro.getDisponivel()) {
            System.out.println("Este Livro já está disponível");
            return;
        }

        //Atualiza em memoria
        livro.setDisponivel(true);
        usuario.getLivrosAlugados().remove(livro);

        //Atualiza no banco: marcar devolução no empréstimo

        List<Emprestimo> emprestimos = emprestimoDao.buscarEmprestimoPorUsuario(usuarioId);

        for (Emprestimo emprestimo : emprestimos) {
            if (emprestimo.getLivroId() == livro.getId() && emprestimo.getDataDevolucao() == null) {
                emprestimoDao.devolverLivro(emprestimo.getId()); //Método atualiza data_devolucao
                break;
            }
        }

        System.out.println("Livro devolvido com sucesso: " + livro.getTitulo());
    }
    
    public Livro buscarLivroPorTitulo(String titulo) {
        return livrosDisponiveis.get(titulo);
    }

    
    public void listarLivros(){
        if(livrosDisponiveis.isEmpty()){
            System.out.println("Nenhum livro cadastrado.");
            return;
        }

        for(Livro livro : livrosDisponiveis.values()){
            System.out.println("Título: " + livro.getTitulo());
            System.out.println("Autor: " + livro.getAutor());
            System.out.println("Gêneros: " + String.join(", ", livro.getGenero()));
            System.out.println("-------------------------");
        }
    }
    
    public void listarUsuarios(){
        if (usuarios.isEmpty()) {
            System.out.println("Nenhum usuário cadastrado");
            return;
        }
        for(Usuario usuario : usuarios.values()){
            System.out.println(usuario.getNome());
        }
    }
}
