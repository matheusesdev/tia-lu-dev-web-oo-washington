public class RelatorioVendasDetalhado implements RelatorioStrategy {

    @Override
    public void gerar() {
        CentralDeDados dados = CentralDeDados.getInstance();
        System.out.println("\n--- Relatório de Vendas (Detalhado) ---");

        if (dados.getPedidos().isEmpty()) {
            System.out.println("Nenhum pedido registrado para exibir no relatório.");
            return;
        }

        for (Pedido pedido : dados.getPedidos()) {
            System.out.println("==========================================");
            System.out.println(pedido);
            System.out.println("Itens do Pedido:");
            for (ItemPedido item : pedido.getItens()) {
                System.out.println("  - " + item);
            }
            System.out.println("Valor Total do Pedido: R$" + String.format("%.2f", pedido.getValorTotal()));
        }
        System.out.println("==========================================");
    }
}