import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class AplicacaoCLI {
    private static List<Cliente> clientes = new ArrayList<>();
    private static List<ItemCardapio> cardapio = new ArrayList<>();
    private static List<Pedido> pedidos = new ArrayList<>();

    private static int proximoClienteId = 1;
    private static int proximoItemId = 1;
    private static int proximoPedidoId = 1;

    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        popularDadosIniciais();
        int opcao = -1;
        while (opcao != 0) {
            exibirMenuPrincipal();
            try {
                opcao = Integer.parseInt(scanner.nextLine());
                switch (opcao) {
                    case 1:
                        gerenciarCardapio();
                        break;
                    case 2:
                        gerenciarClientes();
                        break;
                    case 3:
                        gerenciarPedidos();
                        break;
                    case 4:
                        gerenciarRelatorios(); // O método modificado é chamado aqui
                        break;
                    case 0:
                        System.out.println("Saindo do sistema... Até logo!");
                        break;
                    default:
                        System.out.println("Opção inválida. Tente novamente.");
                        break;
                }
            } catch (NumberFormatException e) {
                System.out.println("Erro: por favor, digite um número válido.");
                opcao = -1;
            }
        }
        scanner.close();
    }

    public static void exibirMenuPrincipal() {
        System.out.println("\n--- Sistema de Pedidos FoodDelivery ---");
        System.out.println("1. Gerenciar Cardápio");
        System.out.println("2. Gerenciar Clientes");
        System.out.println("3. Gerenciar Pedidos");
        System.out.println("4. Gerar Relatórios");
        System.out.println("0. Sair");
        System.out.print("Escolha uma opção: ");
    }

    public static void gerenciarCardapio() {
        System.out.println("\n--- Gerenciar Cardápio ---");
        System.out.println("1. Cadastrar Novo Item");
        System.out.println("2. Listar Itens");
        System.out.print("Opção: ");
        int opcao = Integer.parseInt(scanner.nextLine());
        if (opcao == 1) cadastrarNovoItem();
        else if (opcao == 2) listarItensCardapio();
    }

    public static void gerenciarClientes() {
        System.out.println("\n--- Gerenciar Clientes ---");
        System.out.println("1. Cadastrar Novo Cliente");
        System.out.println("2. Listar Clientes");
        System.out.print("Opção: ");
        int opcao = Integer.parseInt(scanner.nextLine());
        if (opcao == 1) cadastrarNovoCliente();
        else if (opcao == 2) listarClientes();
    }

    public static void gerenciarPedidos() {
        System.out.println("\n--- Gerenciar Pedidos ---");
        System.out.println("1. Registrar Novo Pedido");
        System.out.println("2. Atualizar Status de um Pedido (Avançar)");
        System.out.println("3. Consultar Pedidos por Status");
        System.out.print("Opção: ");
        try {
            int opcao = Integer.parseInt(scanner.nextLine());
            switch (opcao) {
                case 1:
                    registrarNovoPedido();
                    break;
                case 2:
                    atualizarStatusPedido();
                    break;
                case 3:
                    consultarPedidosPorStatus();
                    break;
                default:
                    System.out.println("Opção inválida.");
                    break;
            }
        } catch (NumberFormatException e) {
            System.out.println("Erro: Entrada inválida. Tente novamente.");
        }
    }

    public static void gerenciarRelatorios() {

        System.out.println("\n--- Relatório de Vendas (Simplificado) ---");

        double valorTotalArrecadado = pedidos.stream()
                .filter(p -> p.getStatus() == StatusPedido.ENTREGUE)
                .mapToDouble(Pedido::getValorTotal)
                .sum();

        System.out.println("Quantidade total de pedidos registrados: " + pedidos.size());
        System.out.println("Valor total arrecadado (pedidos entregues): R$" + String.format("%.2f", valorTotalArrecadado));


        System.out.println("\n==========================================\n");

        System.out.println("--- Relatório de Vendas (Detalhado) ---");
        if (pedidos.isEmpty()) {
            System.out.println("Nenhum pedido registrado para exibir no relatório.");
        } else {
            for (Pedido pedido : pedidos) {
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
        System.out.println("\nPressione Enter para voltar ao menu principal...");
        scanner.nextLine();
    }



    private static void cadastrarNovoCliente() {
        System.out.print("Nome do cliente: ");
        String nome = scanner.nextLine();
        System.out.print("Telefone do cliente: ");
        String telefone = scanner.nextLine();
        Cliente novoCliente = new Cliente(proximoClienteId++, nome, telefone);
        clientes.add(novoCliente);
        System.out.println("Cliente cadastrado com sucesso! " + novoCliente);
    }

    private static void listarClientes() {
        System.out.println("\n--- Lista de Clientes ---");
        if (clientes.isEmpty()) {
            System.out.println("Nenhum cliente cadastrado.");
        } else {
            clientes.forEach(System.out::println);
        }
    }

    private static void cadastrarNovoItem() {
        System.out.print("Nome do item: ");
        String nome = scanner.nextLine();
        System.out.print("Preço do item (ex: 12.50): ");
        double preco = Double.parseDouble(scanner.nextLine());
        ItemCardapio novoItem = new ItemCardapio(proximoItemId++, nome, preco);
        cardapio.add(novoItem);
        System.out.println("Item cadastrado com sucesso! " + novoItem);
    }

    private static void listarItensCardapio() {
        System.out.println("\n--- Cardápio do Restaurante ---");
        if (cardapio.isEmpty()) {
            System.out.println("Nenhum item no cardápio.");
        } else {
            cardapio.forEach(System.out::println);
        }
    }

    private static void registrarNovoPedido() {
        System.out.println("\n--- Registrar Novo Pedido ---");
        if (clientes.isEmpty()){
            System.out.println("ERRO: Cadastre um cliente antes de registrar um pedido.");
            return;
        }
        listarClientes();
        System.out.print("Digite o ID do cliente para o pedido: ");
        int clienteId = Integer.parseInt(scanner.nextLine());
        Cliente clienteDoPedido = clientes.stream().filter(c -> c.getId() == clienteId).findFirst().orElse(null);
        if (clienteDoPedido == null) {
            System.out.println("Cliente não encontrado.");
            return;
        }
        Pedido novoPedido = new Pedido(proximoPedidoId++, clienteDoPedido);
        while(true) {
            listarItensCardapio();
            System.out.print("Digite o ID do item para adicionar (ou 0 para finalizar o pedido): ");
            int itemId = Integer.parseInt(scanner.nextLine());
            if (itemId == 0) break;
            ItemCardapio itemSelecionado = cardapio.stream().filter(item -> item.getId() == itemId).findFirst().orElse(null);
            if (itemSelecionado != null) {
                System.out.print("Digite a quantidade: ");
                int quantidade = Integer.parseInt(scanner.nextLine());
                novoPedido.adicionarItem(new ItemPedido(itemSelecionado, quantidade));
                System.out.println("Item '" + itemSelecionado.getNome() + "' adicionado!");
            } else {
                System.out.println("ID do item inválido.");
            }
        }
        if (!novoPedido.getItens().isEmpty()) {
            novoPedido.confirmar();
            pedidos.add(novoPedido);
            System.out.println("Pedido registrado e ACEITO com sucesso! " + novoPedido);
        } else {
            System.out.println("Pedido cancelado pois nenhum item foi adicionado.");
            proximoPedidoId--;
        }
    }

    private static void atualizarStatusPedido() {
        System.out.println("\n--- Pedidos com Status para Avançar ---");
        pedidos.stream()
                .filter(p -> p.getStatus() != StatusPedido.ENTREGUE)
                .forEach(System.out::println);
        System.out.print("Digite o ID do pedido para avançar o status: ");
        int pedidoId = Integer.parseInt(scanner.nextLine());
        Pedido pedidoParaAtualizar = pedidos.stream().filter(p -> p.getId() == pedidoId).findFirst().orElse(null);
        if (pedidoParaAtualizar != null) {
            pedidoParaAtualizar.avancarStatus();
        } else {
            System.out.println("Pedido não encontrado ou já finalizado.");
        }
    }

    private static void consultarPedidosPorStatus() {
        System.out.println("\n--- Consultar Pedidos por Status ---");
        System.out.println("Selecione um status para filtrar:");
        int i = 0;
        for (StatusPedido s : StatusPedido.values()) {
            System.out.println(++i + ". " + s);
        }
        System.out.print("Digite o número do status: ");
        try {
            int opcaoStatus = Integer.parseInt(scanner.nextLine());
            if (opcaoStatus < 1 || opcaoStatus > StatusPedido.values().length) {
                System.out.println("Opção inválida.");
                return;
            }
            StatusPedido statusSelecionado = StatusPedido.values()[opcaoStatus - 1];
            System.out.println("\n--- Pedidos com status '" + statusSelecionado + "' ---");
            long count = pedidos.stream()
                    .filter(p -> p.getStatus() == statusSelecionado)
                    .peek(p -> {
                        System.out.println(p);
                        p.getItens().forEach(item -> System.out.println("  - " + item));
                    })
                    .count();
            if (count == 0) {
                System.out.println("Nenhum pedido encontrado com este status.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Entrada inválida. Por favor, digite um número.");
        }
    }

    private static void popularDadosIniciais() {
        clientes.add(new Cliente(proximoClienteId++, "Joao Silva", "9999-8888"));
        clientes.add(new Cliente(proximoClienteId++, "Maria Souza", "7777-6666"));
        cardapio.add(new ItemCardapio(proximoItemId++, "Pizza Margherita", 45.00));
        cardapio.add(new ItemCardapio(proximoItemId++, "Refrigerante Lata", 5.50));
        cardapio.add(new ItemCardapio(proximoItemId++, "Hamburguer Duplo", 32.75));
    }
}