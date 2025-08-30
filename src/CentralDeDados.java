import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CentralDeDados {

    // --- Módulo Singleton ---
    private static CentralDeDados instance;

    private CentralDeDados() {
        this.clientes = new ArrayList<>();
        this.cardapio = new ArrayList<>();
        this.pedidos = new ArrayList<>();
        popularDadosIniciais();
    }

    public static CentralDeDados getInstance() {
        if (instance == null) {
            instance = new CentralDeDados();
        }
        return instance;
    }

    // --- Módulo de Armazenamento de Dados ---
    private List<Cliente> clientes;
    private List<ItemCardapio> cardapio;
    private List<Pedido> pedidos;

    private int proximoClienteId = 1;
    private int proximoItemId = 1;
    private int proximoPedidoId = 1;

    private void popularDadosIniciais() {
        adicionarCliente(new Cliente(getProximoClienteId(), "Joao Silva", "9999-8888"));
        adicionarCliente(new Cliente(getProximoClienteId(), "Maria Souza", "7777-6666"));
        adicionarItemCardapio(new ItemCardapio(getProximoItemId(), "Pizza Margherita", 45.00));
        adicionarItemCardapio(new ItemCardapio(getProximoItemId(), "Refrigerante Lata", 5.50));
        adicionarItemCardapio(new ItemCardapio(getProximoItemId(), "Hamburguer Duplo", 32.75));
    }

    // --- Módulo de Acesso e Manipulação de Dados (Clientes) ---
    public List<Cliente> getClientes() { return clientes; }
    public void adicionarCliente(Cliente cliente) { this.clientes.add(cliente); }
    public Optional<Cliente> buscarClientePorId(int id) {
        return clientes.stream().filter(c -> c.getId() == id).findFirst();
    }
    public int getProximoClienteId() { return proximoClienteId++; }

    // --- Módulo de Acesso e Manipulação de Dados (Cardápio) ---
    public List<ItemCardapio> getCardapio() { return cardapio; }
    public void adicionarItemCardapio(ItemCardapio item) { this.cardapio.add(item); }
    public Optional<ItemCardapio> buscarItemCardapioPorId(int id) {
        return cardapio.stream().filter(i -> i.getId() == id).findFirst();
    }
    public int getProximoItemId() { return proximoItemId++; }

    // --- Módulo de Acesso e Manipulação de Dados (Pedidos) ---
    public List<Pedido> getPedidos() { return pedidos; }
    public void adicionarPedido(Pedido pedido) { this.pedidos.add(pedido); }
    public Optional<Pedido> buscarPedidoPorId(int id) {
        return pedidos.stream().filter(p -> p.getId() == id).findFirst();
    }
    public int getProximoPedidoId() { return proximoPedidoId++; }
}