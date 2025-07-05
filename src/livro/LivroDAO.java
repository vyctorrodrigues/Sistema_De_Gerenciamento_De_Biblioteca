package livro;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

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

    public void atualizarLivro(Livro livro){
        String sql = "UPDATE livros SET autor = ?, genero = ?, disponivel = ? WHERE titulo = ?";

        try (Connection conn = Conexao.conectar();
            PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setString(1, livro.getAutor());
            stmt.setString(2, livro.getGenero());
            stmt.setBoolean(3, livro.getDisponivel());
            stmt.setString(4, livro.getTitulo());

            stmt.executeUpdate();
            System.out.println("Livro atualizado com sucesso!");

            
        } catch (SQLException e) {
            System.out.println("Erro ao atualizar livro: " + e.getMessage());
        }
    }

    public List<Livro> listarLivros(){
        List<Livro> livros = new ArrayList<>();
        String sql = "SELECT * FROM livros";

        try (Connection conn = Conexao.conectar();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Livro livro = new Livro(
                    rs.getString("titulo"), 
                    rs.getString("autor"), 
                    rs.getString("genero")
                );
                livro.setDisponivel(rs.getBoolean("disponivel"));   
                livros.add(livro);
            }

        } catch (SQLException e) {
            System.out.println("Erro ao listar livros: " + e.getMessage());
        }

        return livros;
    }

    public Livro buscarLivroPorID(int id){
        Livro livro = null;
        String sql = "SELECT * FROM livros WHERE id = ?";
    
        try (Connection conn = Conexao.conectar();
            PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String titulo = rs.getString("titulo");
                String autor = rs.getString("autor");
                String genero = rs.getString("genero");
                boolean disponivel = rs.getBoolean("disponivel");

                livro = new Livro(titulo, autor, genero);
                livro.setDisponivel(disponivel);
                livro.setId(id);
            }
            
        } catch (SQLException e) {
            System.out.println("Erro ao buscar livro por ID: " + e.getMessage());
        }

        return livro;
    }

    public boolean removerLivro(String titulo){
        String sql = "DELETE FROM livros WHERE titulo = ?";
    
        try (Connection conn = Conexao.conectar();
            PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, titulo);
            int linhasAfetadas = stmt.executeUpdate();
        
            return linhasAfetadas > 0;
        } catch (SQLException e) {
            System.out.println("Erro ao remover livro: " + e.getMessage());
            return false;
        }
    }

    public boolean removerLivroPorId(int id){
        String sql = "DELETE FROM livros WHERE id = ?";

        try (Connection conn = Conexao.conectar();
            PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            int linhasAfetadas = stmt.executeUpdate();
            return linhasAfetadas > 0;
            
        } catch (SQLException e) {
            System.out.println("Erro ao remover livro: " + e.getMessage());
            return false;
        }
    }
}