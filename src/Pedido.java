import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Pedido {
    private int id;
    private LocalDateTime data;
    private StatusPedido status;
    private Cliente cliente;
    private List<ItemPedido> itens;

    private List<Observer> observers = new ArrayList<>();

    public Pedido(int id, Cliente cliente) {
        this.id = id;
        this.cliente = cliente;
        this.itens = new ArrayList<>();
        this.status = null;
    }

    public void addObserver(Observer observer) {
        this.observers.add(observer);
    }

    public void removeObserver(Observer observer) {
        this.observers.remove(observer);
    }

    public void notifyObservers() {
        for (Observer observer : this.observers) {
            observer.update(this);
        }
    }

    public void adicionarItem(ItemPedido item) {
        this.itens.add(item);
    }

    public void confirmar() {
        this.status = StatusPedido.ACEITO;
        this.data = LocalDateTime.now();
        notifyObservers();
    }

    public void avancarStatus() {
        if (this.status != StatusPedido.ENTREGUE) {
            this.status = StatusPedido.values()[this.status.ordinal() + 1];
            System.out.println("Status do Pedido #" + id + " atualizado para: " + this.status);
            notifyObservers();
        } else {
            System.out.println("Pedido #" + id + " já foi entregue e não pode mudar de status.");
        }
    }

    public double getValorTotal() {
        double total = 0.0;
        for (ItemPedido item : itens) {
            total += item.calcularSubtotal();
        }
        return total;
    }

    public int getId() { return id; }
    public Cliente getCliente() { return cliente; }
    public StatusPedido getStatus() { return status; }
    public List<ItemPedido> getItens() { return itens; }

    @Override
    public String toString() {
        DateTimeFormatter formatador = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        String dataFormatada = (data != null) ? data.format(formatador) : "N/A";
        return "Pedido [ID=" + id + ", Data=" + dataFormatada + ", Status=" + status + ", Cliente=" + cliente.getNome() + "]";
    }
}