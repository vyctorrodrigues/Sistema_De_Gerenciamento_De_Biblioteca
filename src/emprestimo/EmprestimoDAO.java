package emprestimo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import banco.Conexao;

public class EmprestimoDAO {
    
    //Alugando livro
    public void salvarEmprestimo(Emprestimo emprestimo){
        String sql = "INSERT INTO emprestimos (usuario_id, livro_id, data_aluguel) VALUES (?, ?, ?)";

        try (Connection conn = Conexao.conectar();
            PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){

            stmt.setObject(1, emprestimo.getUsuarioId());
            stmt.setInt(2, emprestimo.getLivroId());
            stmt.setTimestamp(3, Timestamp.valueOf(emprestimo.getDataAluguel()));

            int linhasAfetadas = stmt.executeUpdate();

            if (linhasAfetadas > 0) {
                try(ResultSet generatedKeys = stmt.getGeneratedKeys()){
                    if (generatedKeys.next()) {
                        emprestimo.setId(generatedKeys.getInt(1));
                    }
                }
            }
            System.out.println("Empréstimo salvo com sucesso!");
            
        } catch (SQLException e) {
            System.out.println("Erro ao salvar empréstimo: " + e.getMessage());
        }
    }


    //Devolução de livro
    public void devolverLivro (int emprestimoId){
        String sql = "UPDATE emprestimos SET data_devolucao = ? WHERE id = ?";


        try (Connection conn = Conexao.conectar();
            PreparedStatement stmt = conn.prepareStatement(sql)){

            stmt.setTimestamp(1, Timestamp.valueOf(LocalDateTime.now()));
            stmt.setInt(2, emprestimoId);
            
            int linhasAfetadas = stmt.executeUpdate();

            if (linhasAfetadas > 0) {
                System.out.println("Livro devolvido com sucesso!");
            }else {
                System.out.println("Emprestimo não encontrado para devolução.");
            }

        } catch (SQLException e) {
            System.out.println("Erro ao devolver livro: " + e.getMessage());
        }
    }

    //Buscando empréstimos por usuário
    public List<Emprestimo> buscarEmprestimoPorUsuario(UUID usuarioId){
        List<Emprestimo> lista = new ArrayList<>();
        String sql = "SELECT * FROM emprestimos WHERE usuario_id = ?";

        try (Connection conn = Conexao.conectar();
            PreparedStatement stmt = conn.prepareStatement(sql)){

            stmt.setObject(1, usuarioId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Emprestimo e = new Emprestimo(
                    rs.getInt("id"),
                    (UUID) rs.getObject("usuario_id"),
                    rs.getInt("livro_id"),
                    rs.getTimestamp("data_aluguel").toLocalDateTime(),
                    rs.getTimestamp("data_devolucao") != null ? rs.getTimestamp("data_devolucao").toLocalDateTime() : null

                );
                lista.add(e);
            }

            
        } catch (SQLException e) {
           System.out.println("Erro ao buscar empréstimos: " + e.getMessage());
        }
        
        return lista;
    }

    public List<Emprestimo> listarEmprestimos() {
        List<Emprestimo> lista = new ArrayList<>();
        String sql = "SELECT * FROM emprestimos";

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Emprestimo e = new Emprestimo(
                    rs.getInt("id"),
                    (UUID) rs.getObject("usuario_id"),
                    rs.getInt("livro_id"),
                    rs.getTimestamp("data_aluguel").toLocalDateTime(),
                    rs.getTimestamp("data_devolucao") != null ? rs.getTimestamp("data_devolucao").toLocalDateTime() : null
                );
                lista.add(e);
            }
        } catch (SQLException e) {
            System.out.println("Erro ao listar empréstimos: " + e.getMessage());
        }

        return lista;
    }
}

