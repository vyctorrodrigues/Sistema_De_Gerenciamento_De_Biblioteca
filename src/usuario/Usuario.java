package usuario;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import biblioteca.Biblioteca;

import livro.Livro;

public class Usuario {
    private UUID id;
    private String nome;
    private String email;
    private List <Livro> livrosAlugados = new ArrayList<>();
    private LocalDateTime dataCadastro;

    public Usuario(String nome, String email) {
        this.dataCadastro = LocalDateTime.now();
        this.id = UUID.randomUUID(); 
        this.nome = nome;
        this.email = email;
        this.livrosAlugados = new ArrayList<>();
    }
    
    public void devolverLivro(Livro livro, Biblioteca biblioteca){
        biblioteca.devolverLivro(this.id, livro);
        livrosAlugados.remove(livro);
       
    }
    public void alugarLivro(Livro livro, Biblioteca biblioteca){
        biblioteca.aluguelDeLivro(this.id, livro);
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
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
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

    public LocalDateTime getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(LocalDateTime dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

}
 