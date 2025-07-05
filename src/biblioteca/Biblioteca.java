package biblioteca;

import usuario.Usuario;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import livro.Livro;

public class Biblioteca {
    
    
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
    
    public void buscarUsuarioPornome(String nome){
        Usuario usuarioEncontrado = usuarios.get(nome);
        
        if(usuarioEncontrado == null){
            System.out.println("Usuário com" + nome + "não encontrado!");
        } else {
            System.out.println("Usuario encontrado " + usuarioEncontrado.getNome());
        }
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
    
    public void aluguelDeLivro(Livro livro){
        if(verificarDisponibilidade(livro)){
            livro.setDisponivel(false);
            System.out.println("Você alugou o livro " + livro.getTitulo());
        }else {
            System.out.println("Livro nao esta disponivel");
        }
    }
    
    public void devolverLivro(Livro livro){
        if(!livro.getDisponivel()){
            livro.setDisponivel(true);
            System.out.println("Livro devolvido com sucesso: " + livro.getTitulo());
        }else {
            System.out.println("Este livro nao foi alugado");
        }
    }
    
    public Livro buscarLivroPorTitulo(String titulo) {
        return livrosDisponiveis.get(titulo);
    }

    
    public void listarLivros(){
        if(livrosDisponiveis.isEmpty()){
            System.out.println("Nenhum livro cadastrado.");
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
            System.out.println("Usuario Não encontrado");
        }
        for(Usuario usuario : usuarios.values()){
            System.out.println(usuario.getNome());
        }
    }
}
