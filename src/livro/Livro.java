package livro;


public class Livro {
    private String titulo;
    private String autor;
    private String genero;
    private boolean disponivel;
    
    public Livro() {
        
    }
    
    public void alugado(){
        if(this.getDisponivel()){
            this.setDisponivel(false);
            System.out.println("Livro alugado!");
        }else {
            System.out.println("Livro indisponivel!");
        }
    }
    
    public void devolver(){
        if(!this.getDisponivel()){
            this.setDisponivel(true);
            System.out.println("Livro devolvido!");
        }else {
            System.out.println("Esse Esta disponivel!");
        }
    }
    
    public String getGenero() {
        return genero;
    }
    
    public void setGenero(String genero){
        this.genero = genero;
    }
    
    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public boolean getDisponivel() {
        return disponivel;
    }

    public void setDisponivel(boolean disponivel) {
        this.disponivel = disponivel;
    }

    public String exibirInfo() {
        return "Livro {" + "titulo = " + titulo + ", autor=" + autor + ", isbn=" + ", disponivel=" + disponivel + '}';
    }
    
    
    
    
}
