# Controle de Estoque - Java JDBC & H2 Database 📦

Este projeto é um sistema de gerenciamento de estoque desenvolvido em Java para consolidar conceitos de Programação Orientada a Objetos (POO) e integração com bancos de dados relacionais via JDBC.

## 🏆 Destaque Técnico
O sistema realiza operações completas de CRUD (Create, Read, Update, Delete) diretamente em um banco de dados **H2**, demonstrando domínio em persistência de dados e manipulação de SQL através do Java.

## 🛠️ Tecnologias Utilizadas
- **Java 17+**: Linguagem principal e lógica de negócios.
- **JDBC (Java Database Connectivity)**: Interface para conexão e execução de comandos SQL.
- **H2 Database**: Banco de dados relacional em memória/arquivo para armazenamento dos dados.
- **IntelliJ IDEA**: IDE utilizada para o desenvolvimento e gestão de dependências.

## 🚀 Funcionalidades
- **Criação Automática**: O sistema verifica e cria a tabela de produtos ao ser iniciado.
- **Adicionar Produto**: Cadastro de itens com nome, preço e quantidade.
- **Listagem em Tempo Real**: Consulta de todos os registros persistidos no banco.
- **Atualização de Preço**: Modificação de valores baseada no ID do produto.
- **Exclusão**: Remoção de itens do banco de dados.

## 📂 Estrutura do Projeto
- `Main.java`: Ponto de entrada da aplicação e gerenciamento do menu.
- `Produto.java`: Classe de modelo representando a entidade do banco de dados.
- `exercicio_db`: Arquivo de base de dados gerado pelo H2.

---
Desenvolvido por [Seu Nome] - Estudante de Engenharia de Software na Anhembi Morumbi.
