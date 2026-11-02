/*
APP - PILHAS / TEMPLATE DA PROFESSORA
Menu 6: lista produtos dos N pedidos mais recentes.
Metodos principais: finalizarPedido, listarProdutosPedidosRecentes, Pilha.subPilha.
Execute a partir da pasta pilhas (onde esta produtos.txt).
*/
import java.nio.charset.Charset;
import java.time.LocalDate;
import java.util.Scanner;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

public class App {

    static String nomeArquivoDados;

    static Scanner teclado;

    static Produto[] produtosCadastrados;

    static int quantosProdutos = 0;

    static Pilha<Pedido> pilhaPedidos = new Pilha<>();
        
    static void limparTela() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    static void pausa() {
        System.out.println("Digite enter para continuar...");
        teclado.nextLine();
    }

    static void cabecalho() {
        System.out.println("AEDs II COMÉRCIO DE COISINHAS");
        System.out.println("=============================");
    }
   
    static <T extends Number> T lerOpcao(String mensagem, Class<T> classe) {
        
    	T valor;
        
    	System.out.println(mensagem);
    	try {
            valor = classe.getConstructor(String.class).newInstance(teclado.nextLine());
        } catch (InstantiationException | IllegalAccessException | IllegalArgumentException 
        		| InvocationTargetException | NoSuchMethodException | SecurityException e) {
            return null;
        }
        return valor;
    }

    static int menu() {
        cabecalho();
        System.out.println("1 - Listar todos os produtos");
        System.out.println("2 - Procurar por um produto, por código");
        System.out.println("3 - Procurar por um produto, por nome");
        System.out.println("4 - Iniciar novo pedido");
        System.out.println("5 - Fechar pedido");
        System.out.println("6 - Listar produtos dos pedidos mais recentes");
        System.out.println("0 - Sair");
        System.out.print("Digite sua opção: ");
        return Integer.parseInt(teclado.nextLine());
    }

    static Produto[] lerProdutos(String nomeArquivoDados) {
    	
    	Scanner arquivo = null;
    	int numProdutos;
    	String linha;
    	Produto produto;
    	Produto[] produtosCadastrados;
    	
    	try {
    		arquivo = new Scanner(new File(nomeArquivoDados), "UTF-8");
    		
    		numProdutos = Integer.parseInt(arquivo.nextLine());
    		produtosCadastrados = new Produto[numProdutos];
    		
    		for (int i = 0; i < numProdutos; i++) {
    			linha = arquivo.nextLine();
    			produto = Produto.criarDoTexto(linha);
    			produtosCadastrados[i] = produto;
    		}
    		quantosProdutos = numProdutos;
    		
    	} catch (IOException excecaoArquivo) {
    		produtosCadastrados = null;
    	} finally {
    		if (arquivo != null) arquivo.close();
    	}
    	
    	return produtosCadastrados;
    }

    static Produto localizarProduto() {
        
    	Produto produto = null;
    	Boolean localizado = false;
    	
    	cabecalho();
    	System.out.println("Localizando um produto...");
        int idProduto = lerOpcao("Digite o código identificador do produto desejado: ", Integer.class);
        for (int i = 0; (i < quantosProdutos && !localizado); i++) {
        	if (produtosCadastrados[i].hashCode() == idProduto) {
        		produto = produtosCadastrados[i];
        		localizado = true;
        	}
        }
        
        return produto;   
    }

    static Produto localizarProdutoDescricao() {
        
    	Produto produto = null;
    	Boolean localizado = false;
    	String descricao;
    	
    	cabecalho();
    	System.out.println("Localizando um produto...");
    	System.out.println("Digite o nome ou a descrição do produto desejado:");
        descricao = teclado.nextLine();
        for (int i = 0; (i < quantosProdutos && !localizado); i++) {
        	if (produtosCadastrados[i].descricao.toLowerCase().equals(descricao.toLowerCase())) {
        		produto = produtosCadastrados[i];
        		localizado = true;
    		}
        }
        
        return produto;
    }
    
    private static void mostrarProduto(Produto produto) {
    	
        cabecalho();
        String mensagem = "Dados inválidos para o produto!";
        
        if (produto != null){
            mensagem = String.format("Dados do produto:\n%s", produto);
        }
        
        System.out.println(mensagem);
    }

    static void listarTodosOsProdutos() {
    	
        cabecalho();
        System.out.println("\nPRODUTOS CADASTRADOS:");
        for (int i = 0; i < quantosProdutos; i++) {
        	System.out.println(String.format("%02d - %s", (i + 1), produtosCadastrados[i].toString()));
        }
    }

    public static Pedido iniciarPedido() {
    	
    	int formaPagamento = lerOpcao("Digite a forma de pagamento do pedido, sendo 1 para pagamento à vista e 2 para pagamento a prazo", Integer.class);
    	Pedido pedido = new Pedido(LocalDate.now(), formaPagamento);
    	Produto produto;
    	int numProdutos;
    	int quantidade;
    	
    	listarTodosOsProdutos();
    	System.out.println("Incluindo produtos no pedido...");
    	numProdutos = lerOpcao("Quantos produtos serão incluídos no pedido?", Integer.class);
        for (int i = 0; i < numProdutos; i++) {
        	produto = localizarProdutoDescricao();
        	if (produto == null) {
        		System.out.println("Produto não encontrado");
        		i--;
        	} else {
        		quantidade = lerOpcao("Quantos itens desse produto serão incluídos no pedido?", Integer.class);
        		pedido.incluirProduto(produto, quantidade);
        	}
        }
    	
        return pedido;
    }

    public static void finalizarPedido(Pedido pedido) {
        if (pedido == null) {
            System.out.println("Nenhum pedido foi iniciado.");
            return;
        }
        pilhaPedidos.empilhar(pedido);
        System.out.println("Pedido finalizado e armazenado com sucesso.");
    }

    public static void listarProdutosPedidosRecentes() {
        if (pilhaPedidos.vazia()) {
            System.out.println("Nao existem pedidos finalizados.");
            return;
        }

        Integer quantidade = lerOpcao("Quantos pedidos recentes deseja consultar?", Integer.class);
        if (quantidade == null || quantidade < 0) {
            System.out.println("Quantidade invalida.");
            return;
        }

        Pilha<Pedido> recentes;
        try {
            recentes = pilhaPedidos.subPilha(quantidade);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return;
        }

        if (recentes.vazia()) {
            System.out.println("Nenhum pedido selecionado.");
            return;
        }

        int numero = 1;
        while (!recentes.vazia()) {
            Pedido pedido = recentes.desempilhar();
            System.out.println("PEDIDO RECENTE " + numero++);
            boolean encontrouProduto = false;
            for (ItemDePedido item : pedido.getItensDoPedido()) {
                if (item != null) {
                    System.out.println(item);
                    encontrouProduto = true;
                }
            }
            if (!encontrouProduto) {
                System.out.println("Pedido sem produtos.");
            }
        }
    }

	public static void main(String[] args) {
		
		teclado = new Scanner(System.in, "UTF-8");
        
		nomeArquivoDados = "produtos.txt";
        produtosCadastrados = lerProdutos(nomeArquivoDados);
        if (produtosCadastrados == null) {
            System.out.println("Nao foi possivel carregar produtos.txt. Execute na raiz do projeto.");
            teclado.close();
            return;
        }
        
        Pedido pedido = null;
        
        int opcao = -1;
      
        do{
            opcao = menu();
            switch (opcao) {
                case 1: listarTodosOsProdutos(); break;
                case 2: mostrarProduto(localizarProduto()); break;
                case 3: mostrarProduto(localizarProdutoDescricao()); break;
                case 4: pedido = iniciarPedido(); break;
                case 5: finalizarPedido(pedido); pedido = null; break;
                case 6: listarProdutosPedidosRecentes(); break;
            }
            pausa();
        }while(opcao != 0);       

        teclado.close();    
    }
}
