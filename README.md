# 📚 Sistema de Biblioteca em Java

Este projeto é um sistema simples de gerenciamento de biblioteca desenvolvido em Java, utilizando os princípios da Programação Orientada a Objetos (POO). O sistema permite o cadastro de usuários, o registro de livros, além da funcionalidade de alugar e devolver obras. Também é possível consultar o estado atual do acervo e dos empréstimos.

---

## 🧠 Tecnologias e Conceitos Utilizados

- **Linguagem:** Java  
- **Paradigma:** Programação Orientada a Objetos (Encapsulamento, Abstração, Reuso, Responsabilidade)  
- **CRUD:** Operações básicas de criação, leitura, atualização e exclusão  
- **Organização do Código:** Pacotes por domínio (`biblioteca`, `livro`, `usuario`, `emprestimo`)  
- **Estrutura de Dados:** Uso de `HashMap` e `ArrayList` para armazenamento temporário  
- **Banco de Dados:** PostgreSQL para persistência dos dados (usuários, livros, empréstimos)  
- **JDBC:** Para conexão e execução de comandos SQL  

---

## 📌 Funcionalidades Principais

- Cadastro e listagem de usuários  
- Cadastro e listagem de livros  
- Consulta da disponibilidade dos livros  
- Aluguel (empréstimo) de livros por usuário  
- Registro da devolução dos livros, atualizando o status do empréstimo e do livro  
- Listagem dos livros alugados por usuário  

---

## ⚙️ Detalhes de Implementação

- **Empréstimo de livros:** Ao alugar um livro, o sistema verifica se o livro está disponível. Se sim, registra o empréstimo no banco de dados, atualizando o status do livro para indisponível.  
- **Devolução de livros:** Ao devolver, o sistema atualiza a data de devolução no registro do empréstimo e libera o livro para novos empréstimos, marcando-o como disponível.  
- **Validação:** São feitas verificações para garantir que livros só possam ser alugados se disponíveis e que só possam ser devolvidos se estiverem alugados.  
- **Identificação:** Usuários são identificados por UUID e livros por IDs inteiros.  

---
## 🤝 Contribuições

Contribuições são bem-vindas!  
Sinta-se à vontade para:

- 📌 Abrir uma **issue** se encontrar bugs ou quiser sugerir melhorias;
- 🔧 Criar um **pull request** com correções ou novas funcionalidades;
- 💬 Discutir ideias na aba de discussões (se ativada no repositório).

Vamos construir juntos um sistema cada vez melhor

## 📘 Diagrama UML (Mermaid)

```mermaid
classDiagram

class Usuario {
  UUID id
  String nome
  String email
}

class Livro {
  int id
  String titulo
  String autor
  String genero
  boolean disponivel
}

class Emprestimo {
  int id
  UUID usuarioId
  int livroId
  LocalDateTime dataAluguel
  LocalDateTime dataDevolucao
}

class UsuarioDAO {
  +cadastrarUsuario(Usuario)
  +listarUsuarios(): List<Usuario>
  +buscarPorId(UUID): Usuario
}

class LivroDAO {
  +cadastrarLivro(Livro)
  +atualizarLivro(Livro)
  +buscarLivroPorTitulo(String): Livro
}

class EmprestimoDAO {
  +salvarEmprestimo(Emprestimo)
  +devolverLivro(int)
  +listarEmprestimos(): List<Emprestimo>
  +buscarEmprestimoAtivoPorLivroId(int): Emprestimo
}

Usuario "1" --> "0..*" Emprestimo : realiza
Livro "1" --> "0..*" Emprestimo : é emprestado em

UsuarioDAO --> Usuario
LivroDAO --> Livro
EmprestimoDAO --> Emprestimo
