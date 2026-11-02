/*
APP - FILAS / ADAPTADO DO SISTEMA DE COMERCIO DA PROFESSORA
Menu 6: media do valor final dos N primeiros pedidos.
Menu 7: primeiros N pedidos com valor acima do informado.
Menu 8: primeiros N pedidos contendo um produto por codigo.
Usa Pedido.valorFinal() e Pedido.existeNoPedido(Produto) do template.
Execute a partir da pasta filas (onde esta produtos.txt).
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

    static Fila<Pedido> filaPedidos = new Fila<>();
        
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
        System.out.println("6 - Exibir valor total medio dos N primeiros pedidos");
        System.out.println("7 - Exibir primeiros pedidos com valor total acima de um valor");
        System.out.println("8 - Exibir primeiros pedidos com determinado produto");
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
        filaPedidos.enfileirar(pedido);
        System.out.println("Pedido armazenado na fila.");
    }

    static void exibirValorTotalMedio() {
        Integer n = lerOpcao("Quantos primeiros pedidos considerar?", Integer.class);
        if (n == null || n < 0) {
            System.out.println("Quantidade invalida.");
            return;
        }
        double media = filaPedidos.calcularValorMedio(p -> p.valorFinal(), n);
        System.out.printf("Valor total medio: R$ %.2f%n", media);
    }

    static void exibirPedidosAcimaValor() {
        Integer n = lerOpcao("Quantos primeiros pedidos considerar?", Integer.class);
        Double minimo = lerOpcao("Valor minimo (exclusivo):", Double.class);
        if (n == null || n < 0 || minimo == null) {
            System.out.println("Dados invalidos.");
            return;
        }
        Fila<Pedido> encontrados = filaPedidos.filtrar(p -> p.valorFinal() > minimo, n);
        exibirFila(encontrados);
    }

    static void exibirPedidosComProduto() {
        Integer n = lerOpcao("Quantos primeiros pedidos considerar?", Integer.class);
        if (n == null || n < 0) {
            System.out.println("Quantidade invalida.");
            return;
        }
        Produto procurado = localizarProduto();
        if (procurado == null) {
            System.out.println("Produto nao encontrado.");
            return;
        }
        Fila<Pedido> encontrados = filaPedidos.filtrar(p -> p.existeNoPedido(procurado) != null, n);
        exibirFila(encontrados);
    }

    static void exibirFila(Fila<Pedido> fila) {
        if (fila.vazia()) {
            System.out.println("Nenhum pedido encontrado.");
        }
        while (!fila.vazia()) {
            System.out.println(fila.desenfileirar());
        }
    }

	public static void main(String[] args) {
		
		teclado = new Scanner(System.in, "UTF-8");
        
		nomeArquivoDados = args.length > 0 ? args[0] : "produtos.txt";
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
                case 6: exibirValorTotalMedio(); break;
                case 7: exibirPedidosAcimaValor(); break;
                case 8: exibirPedidosComProduto(); break;
            }
            pausa();
        }while(opcao != 0);       

        teclado.close();    
    }
}
