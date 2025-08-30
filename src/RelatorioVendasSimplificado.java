public class RelatorioVendasSimplificado implements RelatorioStrategy {

    @Override
    public void gerar() {
        CentralDeDados dados = CentralDeDados.getInstance();
        System.out.println("\n--- Relatório de Vendas (Simplificado) ---");

        double valorTotalArrecadado = dados.getPedidos().stream()
                .filter(p -> p.getStatus() == StatusPedido.ENTREGUE)
                .mapToDouble(Pedido::getValorTotal)
                .sum();

        System.out.println("Quantidade total de pedidos registrados: " + dados.getPedidos().size());
        System.out.println("Valor total arrecadado (pedidos entregues): R$" + String.format("%.2f", valorTotalArrecadado));
    }
}