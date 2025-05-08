# 📚 Projeto Sistema de Biblioteca em Java

Este projeto é um sistema simples de gerenciamento de biblioteca, desenvolvido com **Java** utilizando os princípios de **Programação Orientada a Objetos (POO)**. Ele permite adicionar usuários, cadastrar livros, alugar e devolver obras, além de consultar o estado atual do acervo e dos usuários.

## 🧠 Tecnologias e Conceitos Usados

- Linguagem: **Java**
- Paradigma: **POO (Encapsulamento, Abstração, Reuso e Responsabilidade)**
- Pacotes organizados por domínio: `biblioteca`, `livro` e `usuario`
- Estrutura de dados: `HashMap`, `ArrayList`

---

## 📌 Funcionalidades

- ✅ Cadastro de usuários
- ✅ Cadastro de livros
- ✅ Aluguel e devolução de livros
- ✅ Listagem de livros e usuários
- ✅ Consulta de disponibilidade de livros
- ✅ Exibição dos livros alugados por usuário

---

## 📘 Diagrama UML (Mermaid)

```mermaid
classDiagram
    class Biblioteca {
        - Map~String, Livro~ livrosDisponiveis
        - Map~String, Usuario~ usuarios
        + adicionarUsuario(Usuario)
        + buscarUsuarioPornome(String)
        + adicionarLivro(Livro)
        + verificarDisponibilidade(Livro) boolean
        + aluguelDeLivro(Livro)
        + devolverLivro(Livro)
        + buscarLivroPorTitulo(String) Livro
        + listarLivros()
        + listarUsuarios()
    }

    class Livro {
        - String titulo
        - String autor
        - String genero
        - boolean disponivel
        + alugado()
        + devolver()
        + exibirInfo() String
        + getters e setters
    }

    class Usuario {
        - String id
        - String nome
        - String email
        - List~Livro~ livrosAlugados
        + alugarLivro(Livro, Biblioteca)
        + devolverLivro(Livro, Biblioteca)
        + listarLivrosAlugados()
        + getters e setters
    }

    Biblioteca "1" o-- "*" Livro
    Biblioteca "1" o-- "*" Usuario
    Usuario "1" --> "*" Livro : aluga
