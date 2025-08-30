public class GerenteNotificador implements Observer {
    @Override
    public void update(Pedido pedido) {
        if (pedido.getStatus() == StatusPedido.SAIU_PARA_ENTREGA) {
            System.out.println("\n[NOTIFICAÇÃO PARA GERENTE]: O pedido #" + pedido.getId()
                    + " do cliente " + pedido.getCliente().getNome()
                    + " saiu para entrega.");
        }
    }
}