package livro;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import banco.Conexao;

public class LivroDAO {

    public void salvarLivro(Livro livro) {
        String sql = "INSERT INTO livros (titulo, autor, genero, disponivel) VALUES (?, ?, ?, ?)";

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, livro.getTitulo());
            stmt.setString(2, livro.getAutor());
            stmt.setString(3, livro.getGenero());
            stmt.setBoolean(4, livro.getDisponivel());

            stmt.executeUpdate();
            System.out.println("Livro salvo no banco com sucesso!");

        } catch (SQLException e) {
            System.out.println("Erro ao salvar livro: " + e.getMessage());
        }
    }

    public Livro buscarLivroPorTitulo(String titulo){
        String sql = "SELECT * FROM livros WHERE titulo = ?";
        
        try(Connection conn = Conexao.conectar();
        PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, titulo);
            ResultSet rs = stmt.executeQuery();

            if(rs.next()){
                Livro livro = new Livro(
                    rs.getString("titulo"),
                    rs.getString("autor"),
                    rs.getString("genero")
                );
                livro.setDisponivel(rs.getBoolean("disponivel"));
                return livro;
            }
        }catch (SQLException e){
            System.out.println("Erro ao buscar livro: " + e.getMessage());
        }
        return null;
    }
}