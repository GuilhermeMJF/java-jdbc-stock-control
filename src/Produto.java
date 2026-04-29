/**
 * Entidade: Crie a classe Produto com id (int), nome (String),
 * preco (double) e quantidade (int).
 */
public class Produto {
    private int id;
    private String nome;
    private double preco;
    private int quantidade;

    // Getters e Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    // Método toString() para facilitar a impressão na listagem
    @Override
    public String toString() {
        return String.format("ID: %d | Nome: %s | Preço: R$ %.2f | Qtd: %d",
                id, nome, preco, quantidade);
    }
}