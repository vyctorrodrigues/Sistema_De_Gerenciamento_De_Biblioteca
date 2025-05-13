package usuario;

import java.util.ArrayList;
import java.util.List;
import biblioteca.Biblioteca;
import livro.Livro;

public class Usuario {
    private String id;
    private String nome;
    private String email;
    private Biblioteca cadastro;
    private List <Livro> livrosAlugados = new ArrayList<>();

    public Usuario(String id, String nome, String email) {
        this.id = id;
        this.livrosAlugados = new ArrayList<>();
    }
    
    public void devolverLivro(Livro livro, Biblioteca biblioteca){
          biblioteca.devolverLivro(livro);
          livrosAlugados.remove(livro);
       
    }
    public void alugarLivro(Livro livro, Biblioteca biblioteca){
        biblioteca.aluguelDeLivro(livro);
        if(livro.getDisponivel()){
            livrosAlugados.add(livro);
        }
        
    }
    
    public void listarLivrosAlugados(){
        if(livrosAlugados.isEmpty()){
            System.out.println(this.getNome() + "O usuario não tem livros alugados!");
        }else{
            System.out.println(this.getNome()+ "tem seguintes livros alugados: ");
            for(Livro livro : livrosAlugados){
                System.out.println(" - "+ livro.getTitulo());
            }
        }
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Livro> getLivrosAlugados() {
        return livrosAlugados;
    }

    public void setLivrosAlugados(List<Livro> livrosAlugados) {
        this.livrosAlugados = livrosAlugados;
    }

}
 