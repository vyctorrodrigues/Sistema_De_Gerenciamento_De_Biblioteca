package usuario;

import java.sql.*;
import java.util.*;
import banco.Conexao;

public class UsuarioDao {
    
    public void salvarUsuario(Usuario usuario){
        
        String sql = "INSERT INTO usuarios (nome, email, data_cadastro) VALUES (?, ?, ?)";

        try (Connection conn = Conexao.conectar();
            PreparedStatement stmt = conn.prepareStatement(sql)){
            
            stmt.setString(1, usuario.getNome());
            stmt.setString(2, usuario.getEmail());
            stmt.setTimestamp(3, Timestamp.valueOf(usuario.getDataCadastro()));
            
           
            stmt.executeUpdate();

            System.out.println("Usuário salvo com sucesso!");
            
        } catch (SQLException e) {
            System.out.println("Erro ao salvar usuário: " + e.getMessage());
        }

    }

    public Usuario buscarUsuarioPorId(UUID id){
        String sql = "Select * FROM usuarios WHERE id = ?";

        try (Connection conn = Conexao.conectar();
            PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setObject(1, id);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String nome = rs.getString("nome");
                String email = rs.getString("email");

                Usuario usuario = new Usuario(nome, email);
                usuario.setId(id);

                return usuario;
            } else {
                System.out.println("Usuário não encontrado com ID: " + id);
            }

        } catch (SQLException e) {
            System.out.println("Erro ao buscar usuário: " + e.getMessage());
        }

        return null;
    }

    public List<Usuario> listarUsuarios(){
        List<Usuario> usuarios = new ArrayList<>();
        String sql = "SELECT * FROM usuarios";

        try (Connection conn = Conexao.conectar();
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                UUID id = UUID.fromString(rs.getString("id"));
                String nome = rs.getString("nome");
                String email = rs.getString("email");

                Usuario usuario = new Usuario(nome, email);
                usuario.setId(id);

                usuarios.add(usuario);  
            }     
            
        } catch (SQLException e) {
            System.out.println("Erro ao listar usuários: " + e.getMessage());
        }

        return usuarios;
    }

    public void removerUsuarioPorId(UUID id){
        String sql = "DELETE FROM usuarios WHERE id = ?";

        try (Connection conn = Conexao.conectar();
            PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setObject(1, id);
            int linhasAfetadas = stmt.executeUpdate();

            if (linhasAfetadas > 0) {
                System.out.println("Usuário removido com sucesso.");
            } else {
                System.out.println("Nenhum usuário encontrado com esse ID.");
            }
            
        } catch (SQLException e) {
            System.out.println("Erro ao remover usuário: " + e.getMessage());
        }
    }
}
