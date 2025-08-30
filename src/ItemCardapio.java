public class ItemCardapio {
    private int id;
    private String nome;
    private double preco;

    public ItemCardapio(int id, String nome, double preco) {
        this.id = id;
        this.nome = nome;
        this.preco = preco;
    }

    public int getId() {
        return this.id;
    }

    public String getNome() {
        return this.nome;
    }

    public double getPreco() {
        return this.preco;
    }

    @Override
    public String toString() {
        return "Item [ID=" + id + ", Nome=" + nome + ", Preço=R$" + String.format("%.2f", preco) + "]";
    }
}