import java.util.Optional;
import java.util.Scanner;

public class AplicacaoCLI {

    private static CentralDeDados dados = CentralDeDados.getInstance();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        int opcao = -1;
        while (opcao != 0) {
            exibirMenuPrincipal();
            try {
                opcao = Integer.parseInt(scanner.nextLine());
                switch (opcao) {
                    case 1: gerenciarCardapio(); break;
                    case 2: gerenciarClientes(); break;
                    case 3: gerenciarPedidos(); break;
                    case 4: gerenciarRelatorios(); break;
                    case 0: System.out.println("Saindo do sistema... Até logo!"); break;
                    default: System.out.println("Opção inválida. Tente novamente."); break;
                }
            } catch (NumberFormatException e) {
                System.out.println("Erro: por favor, digite um número válido.");
                opcao = -1;
            }
        }
        scanner.close();
    }

    // --- Módulo de Menus e Navegação ---
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
        int opcao = -1;
        while (opcao != 0) {
            System.out.println("\n--- Gerenciar Cardápio ---");
            System.out.println("1. Cadastrar Novo Item");
            System.out.println("2. Listar Itens");
            System.out.println("0. Voltar ao Menu Principal");
            System.out.print("Opção: ");
            try {
                opcao = Integer.parseInt(scanner.nextLine());
                switch (opcao) {
                    case 1: cadastrarNovoItem(); break;
                    case 2:
                        listarItensCardapio();
                        System.out.println("\nPressione Enter para continuar...");
                        scanner.nextLine();
                        break;
                    case 0: break;
                    default: System.out.println("Opção inválida."); break;
                }
            } catch (NumberFormatException e) {
                System.out.println("Erro: por favor, digite um número válido.");
            }
        }
    }

    public static void gerenciarClientes() {
        int opcao = -1;
        while (opcao != 0) {
            System.out.println("\n--- Gerenciar Clientes ---");
            System.out.println("1. Cadastrar Novo Cliente");
            System.out.println("2. Listar Clientes");
            System.out.println("0. Voltar ao Menu Principal");
            System.out.print("Opção: ");
            try {
                opcao = Integer.parseInt(scanner.nextLine());
                switch (opcao) {
                    case 1: cadastrarNovoCliente(); break;
                    case 2:
                        listarClientes();
                        System.out.println("\nPressione Enter para continuar...");
                        scanner.nextLine();
                        break;
                    case 0: break;
                    default: System.out.println("Opção inválida."); break;
                }
            } catch (NumberFormatException e) {
                System.out.println("Erro: por favor, digite um número válido.");
            }
        }
    }

    public static void gerenciarPedidos() {
        int opcao = -1;
        while (opcao != 0) {
            System.out.println("\n--- Gerenciar Pedidos ---");
            System.out.println("1. Registrar Novo Pedido");
            System.out.println("2. Atualizar Status de um Pedido");
            System.out.println("3. Consultar Pedidos por Status");
            System.out.println("0. Voltar ao Menu Principal");
            System.out.print("Opção: ");
            try {
                opcao = Integer.parseInt(scanner.nextLine());
                switch (opcao) {
                    case 1: registrarNovoPedido(); break;
                    case 2: atualizarStatusPedido(); break;
                    case 3: consultarPedidosPorStatus(); break;
                    case 0: break;
                    default: System.out.println("Opção inválida."); break;
                }
            } catch (NumberFormatException e) {
                System.out.println("Erro: por favor, digite um número válido.");
            }
        }
    }

    public static void gerenciarRelatorios() {
        System.out.println("\n--- Gerar Relatórios ---");
        System.out.println("1. Relatório de Vendas (Simplificado)");
        System.out.println("2. Relatório de Vendas (Detalhado)");
        System.out.print("Escolha o tipo de relatório (ou 0 para voltar): ");
        try {
            int tipoRelatorio = Integer.parseInt(scanner.nextLine());
            RelatorioStrategy relatorio = null;

            if (tipoRelatorio == 1) {
                relatorio = new RelatorioVendasSimplificado();
            } else if (tipoRelatorio == 2) {
                relatorio = new RelatorioVendasDetalhado();
            } else if (tipoRelatorio == 0) {
                return;
            } else {
                System.out.println("Opção de relatório inválida.");
                return;
            }
            relatorio.gerar();
            System.out.println("\nPressione Enter para voltar ao menu principal...");
            scanner.nextLine();
        } catch (NumberFormatException e) {
            System.out.println("Erro: por favor, digite um número válido.");
        }
    }

    // --- Módulo de Operações (Clientes) ---
    private static void cadastrarNovoCliente() {
        System.out.print("Nome do cliente: ");
        String nome = scanner.nextLine();
        System.out.print("Telefone do cliente: ");
        String telefone = scanner.nextLine();
        Cliente novoCliente = new Cliente(dados.getProximoClienteId(), nome, telefone);
        dados.adicionarCliente(novoCliente);
        System.out.println("Cliente cadastrado com sucesso! " + novoCliente);
    }

    private static void listarClientes() {
        System.out.println("\n--- Lista de Clientes ---");
        if (dados.getClientes().isEmpty()) {
            System.out.println("Nenhum cliente cadastrado.");
        } else {
            dados.getClientes().forEach(System.out::println);
        }
    }

    // --- Módulo de Operações (Cardápio) ---
    private static void cadastrarNovoItem() {
        System.out.print("Nome do item: ");
        String nome = scanner.nextLine();
        System.out.print("Preço do item (ex: 12.50): ");
        double preco = Double.parseDouble(scanner.nextLine());
        ItemCardapio novoItem = new ItemCardapio(dados.getProximoItemId(), nome, preco);
        dados.adicionarItemCardapio(novoItem);
        System.out.println("Item cadastrado com sucesso! " + novoItem);
    }

    private static void listarItensCardapio() {
        System.out.println("\n--- Cardápio do Restaurante ---");
        if (dados.getCardapio().isEmpty()) {
            System.out.println("Nenhum item no cardápio.");
        } else {
            dados.getCardapio().forEach(System.out::println);
        }
    }

    // --- Módulo de Operações (Pedidos) ---
    private static void registrarNovoPedido() {
        System.out.println("\n--- Registrar Novo Pedido ---");
        if (dados.getClientes().isEmpty()){
            System.out.println("ERRO: Cadastre um cliente antes de registrar um pedido.");
            return;
        }
        listarClientes();
        System.out.print("Digite o ID do cliente para o pedido: ");
        int clienteId = Integer.parseInt(scanner.nextLine());

        Optional<Cliente> clienteOpt = dados.buscarClientePorId(clienteId);
        if (!clienteOpt.isPresent()) {
            System.out.println("Cliente não encontrado.");
            return;
        }

        Pedido novoPedido = new Pedido(dados.getProximoPedidoId(), clienteOpt.get());

        while(true) {
            listarItensCardapio();
            System.out.print("Digite o ID do item para adicionar (ou 0 para finalizar): ");
            int itemId = Integer.parseInt(scanner.nextLine());
            if (itemId == 0) break;

            Optional<ItemCardapio> itemOpt = dados.buscarItemCardapioPorId(itemId);
            if (itemOpt.isPresent()) {
                System.out.print("Digite a quantidade: ");
                int quantidade = Integer.parseInt(scanner.nextLine());
                novoPedido.adicionarItem(new ItemPedido(itemOpt.get(), quantidade));
                System.out.println("Item '" + itemOpt.get().getNome() + "' adicionado!");
            } else {
                System.out.println("ID do item inválido.");
            }
        }

        if (!novoPedido.getItens().isEmpty()) {
            novoPedido.confirmar();
            dados.adicionarPedido(novoPedido);
            System.out.println("Pedido registrado e ACEITO com sucesso! " + novoPedido);
        } else {
            System.out.println("Pedido cancelado pois nenhum item foi adicionado.");
        }
    }

    private static void atualizarStatusPedido() {
        System.out.println("\n--- Pedidos com Status para Avançar ---");
        boolean haPedidosParaAvancar = dados.getPedidos().stream()
                .anyMatch(p -> p.getStatus() != StatusPedido.ENTREGUE);

        if (!haPedidosParaAvancar) {
            System.out.println("Nenhum pedido para avançar o status.");
            return;
        }

        dados.getPedidos().stream()
                .filter(p -> p.getStatus() != StatusPedido.ENTREGUE)
                .forEach(System.out::println);

        System.out.print("Digite o ID do pedido para avançar o status: ");
        int pedidoId = Integer.parseInt(scanner.nextLine());

        Optional<Pedido> pedidoOpt = dados.buscarPedidoPorId(pedidoId);
        if (pedidoOpt.isPresent()) {
            pedidoOpt.get().avancarStatus();
        } else {
            System.out.println("Pedido não encontrado.");
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
            long count = dados.getPedidos().stream()
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
}