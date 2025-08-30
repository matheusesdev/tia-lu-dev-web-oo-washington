public class ItemPedido {
    private int quantidade;
    private double precoUnitario;
    private ItemCardapio itemCardapio;

    public ItemPedido(ItemCardapio itemCardapio, int quantidade) {
        this.itemCardapio = itemCardapio;
        this.quantidade = quantidade;
        this.precoUnitario = itemCardapio.getPreco();
    }

    public ItemCardapio getItemCardapio() {
        return this.itemCardapio;
    }

    public int getQuantidade() {
        return this.quantidade;
    }

    public double calcularSubtotal() {
        return this.quantidade * this.precoUnitario;
    }

    @Override
    public String toString() {
        return this.itemCardapio.getNome() + " (Qtd: " + quantidade + ", Preço Unit: R$" + String.format("%.2f", precoUnitario) + ")";
    }
}