import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    // Requisitos Chave: Banco de Dados H2 [cite: 8]
    private static final String JDBC_URL = "jdbc:h2:./exercicio_db"; //
    private static final String JDBC_USER = "sa";
    private static final String JDBC_PASSWORD = "";

    static {
        try {
            Class.forName("org.h2.Driver");
        } catch (ClassNotFoundException e) {
            throw new IllegalStateException("Driver H2 não encontrado!", e);
        }
    }

    private static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
    }

    public static void main(String[] args) {
        // Garante que a tabela exista [cite: 13]
        criarTabela();

        // Interação com o Usuário: menu no console com Scanner [cite: 5]
        Scanner scanner = new Scanner(System.in);
        int opcao = 0;

        // Loop while com switch [cite: 5]
        while (opcao != 5) {
            System.out.println("\n--- Controle de Estoque (Exercício 1) ---");
            System.out.println("1. Adicionar Produto"); // [cite: 21]
            System.out.println("2. Listar Todos os Produtos"); // [cite: 22]
            System.out.println("3. Atualizar Preço"); // [cite: 23]
            System.out.println("4. Deletar Produto"); // [cite: 25]
            System.out.println("5. Sair"); // [cite: 25]
            System.out.print("Escolha uma opção: ");

            try {
                opcao = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Opção inválida. Digite um número.");
                continue;
            }

            switch (opcao) {
                case 1:
                    adicionarProduto(scanner); // Create
                    break;
                case 2:
                    listarProdutos(); // Read
                    break;
                case 3:
                    atualizarPreco(scanner); // Update
                    break;
                case 4:
                    deletarProduto(scanner); // Delete
                    break;
                case 5:
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Opção inválida.");
            }
        }
        scanner.close();
    }

    public static void criarTabela() {
        String sql = "CREATE TABLE IF NOT EXISTS produto (" + // [cite: 14]
                "id INT AUTO_INCREMENT PRIMARY KEY," + // [cite: 15]
                "nome VARCHAR(255) NOT NULL," + // [cite: 16]
                "preco DOUBLE," + // [cite: 17]
                "quantidade INT" + // [cite: 18]
                ");"; // [cite: 19]

        // Gerenciamento de Recursos: try-with-resources [cite: 7]
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println("Erro ao criar tabela: " + e.getMessage());
        }
    }

    private static void adicionarProduto(Scanner scanner) {
        try {
            System.out.print("Digite o nome: ");
            String nome = scanner.nextLine();
            System.out.print("Digite o preço: ");
            double preco = Double.parseDouble(scanner.nextLine());
            System.out.print("Digite a quantidade: ");
            int quantidade = Integer.parseInt(scanner.nextLine());

            String sql = "INSERT INTO produto (nome, preco, quantidade) VALUES (?, ?, ?)";

            // Segurança: PreparedStatement [cite: 6]
            // Gerenciamento: try-with-resources [cite: 7]
            try (Connection conn = getConnection();
                 PreparedStatement pstmt = conn.prepareStatement(sql)) {

                pstmt.setString(1, nome);
                pstmt.setDouble(2, preco);
                pstmt.setInt(3, quantidade);

                pstmt.executeUpdate();
                System.out.println("Produto salvo no banco!");
            }
        } catch (NumberFormatException e) {
            System.out.println("Erro de formato: Preço ou quantidade inválidos.");
        } catch (SQLException e) {
            System.out.println("Erro ao adicionar produto: " + e.getMessage());
        }
    }

    private static void listarProdutos() {
        String sql = "SELECT * FROM produto"; // [cite: 22]
        List<Produto> produtos = new ArrayList<>();

        // try-with-resources para Connection, PreparedStatement e ResultSet [cite: 7]
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                // Mapeando o ResultSet para o objeto Produto
                Produto p = new Produto();
                p.setId(rs.getInt("id"));
                p.setNome(rs.getString("nome"));
                p.setPreco(rs.getDouble("preco"));
                p.setQuantidade(rs.getInt("quantidade"));
                produtos.add(p);
            }
        } catch (SQLException e) {
            System.out.println("Erro ao listar produtos: " + e.getMessage());
        }

        System.out.println("\n--- Lista de Produtos Cadastrados ---");
        if (produtos.isEmpty()) {
            System.out.println("Nenhum produto cadastrado.");
        } else {
            for (Produto p : produtos) {
                System.out.println(p); // Usa o toString() da classe Produto
            }
        }
    }

    private static void atualizarPreco(Scanner scanner) {
        try {
            System.out.print("Digite o ID do produto para atualizar: ");
            int id = Integer.parseInt(scanner.nextLine()); // [cite: 23]
            System.out.print("Digite o NOVO preço: ");
            double novoPreco = Double.parseDouble(scanner.nextLine()); // [cite: 23]

            String sql = "UPDATE produto SET preco = ? WHERE id = ?"; // [cite: 24]

            // Segurança: PreparedStatement [cite: 6]
            try (Connection conn = getConnection();
                 PreparedStatement pstmt = conn.prepareStatement(sql)) {

                pstmt.setDouble(1, novoPreco);
                pstmt.setInt(2, id);

                int afetados = pstmt.executeUpdate();
                if (afetados > 0) {
                    System.out.println("Preço atualizado com sucesso!");
                } else {
                    System.out.println("Produto não encontrado (ID: " + id + ").");
                }
            }
        } catch (NumberFormatException e) {
            System.out.println("Erro de formato: ID ou Preço inválidos.");
        } catch (SQLException e) {
            System.out.println("Erro ao atualizar preço: " + e.getMessage());
        }
    }

    private static void deletarProduto(Scanner scanner) {
        try {
            System.out.print("Digite o ID do produto para deletar: ");
            int id = Integer.parseInt(scanner.nextLine()); // [cite: 25]

            String sql = "DELETE FROM produto WHERE id = ?";

            // Segurança: PreparedStatement [cite: 6]
            try (Connection conn = getConnection();
                 PreparedStatement pstmt = conn.prepareStatement(sql)) {

                pstmt.setInt(1, id);

                int afetados = pstmt.executeUpdate();
                if (afetados > 0) {
                    System.out.println("Produto removido do banco.");
                } else {
                    System.out.println("Produto não encontrado (ID: " + id + ").");
                }
            }
        } catch (NumberFormatException e) {
            System.out.println("Erro de formato: ID inválido.");
        } catch (SQLException e) {
            System.out.println("Erro ao deletar produto: " + e.getMessage());
        }
    }
}