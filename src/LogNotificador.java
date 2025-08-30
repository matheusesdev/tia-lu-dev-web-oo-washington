import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LogNotificador implements Observer {
    @Override
    public void update(Pedido pedido) {
        DateTimeFormatter formatador = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        String timestamp = LocalDateTime.now().format(formatador);
        System.out.println("[LOG " + timestamp + "]: Status do pedido #" + pedido.getId()
                + " alterado para " + pedido.getStatus());
    }
}