package emprestimo;

import java.time.LocalDateTime;
import java.util.UUID;

public class Emprestimo {
    private int id;
    private UUID usuarioId;
    private int livroId;
    private LocalDateTime dataAluguel;
    private LocalDateTime dataDevolucao;
    
    public Emprestimo(int id, UUID usuarioId, int livroId, LocalDateTime dataAluguel, LocalDateTime dataDevolucao) {
        this.id = id;
        this.usuarioId = usuarioId;
        this.livroId = livroId;
        this.dataAluguel = dataAluguel;
        this.dataDevolucao = dataDevolucao;
    
    }

    public Emprestimo(){
        
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public UUID getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(UUID usuarioId) {
        this.usuarioId = usuarioId;
    }

    public int getLivroId() {
        return livroId;
    }

    public void setLivroId(int livroId) {
        this.livroId = livroId;
    }

    public LocalDateTime getDataAluguel() {
        return dataAluguel;
    }

    public void setDataAluguel(LocalDateTime dataAluguel) {
        this.dataAluguel = dataAluguel;
    }

    public LocalDateTime getDataDevolucao() {
        return dataDevolucao;
    }
    
    public void setDataDevolucao(LocalDateTime dataDevolucao) {
        this.dataDevolucao = dataDevolucao;
    }
    
}
