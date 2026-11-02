/*
EXEMPLO EXECUTAVEL DE PILHA - BASE DO TEMPLATE DA PROFESSORA
Execute: java -cp bin ExemploPilha
Mostra LIFO, subPilha com os 2 itens do topo, ordem preservada,
pilha original intacta e validacao de quantidades invalidas.
Abaixo deste bloco comeca apenas o codigo Java.
*/
import java.time.LocalDate;

public class ExemploPilha {
    public static void main(String[] args) {
        Pilha<String> pedidos = new Pilha<>();
        pedidos.empilhar("Pedido 1");
        pedidos.empilhar("Pedido 2");
        pedidos.empilhar("Pedido 3");

        Pilha<String> recentes = pedidos.subPilha(2);
        conferir("Pedido 3".equals(recentes.desempilhar()), "Primeiro mais recente");
        conferir("Pedido 2".equals(recentes.desempilhar()), "Segundo mais recente");
        conferir(recentes.vazia(), "A subpilha tem somente dois pedidos");
        conferir("Pedido 3".equals(pedidos.consultarTopo()), "Original permanece intacta");
        conferir(pedidos.subPilha(0).vazia(), "Subpilha vazia para zero itens");

        boolean erroEsperado = false;
        try {
            pedidos.subPilha(4);
        } catch (IllegalArgumentException e) {
            erroEsperado = true;
        }
        conferir(erroEsperado, "Quantidade maior que a pilha gera excecao");
        Produto caderno = new ProdutoNaoPerecivel("Caderno", 10.0, 0.2);
        Pedido antigo = new Pedido(LocalDate.now(), 2);
        Pedido novo = new Pedido(LocalDate.now(), 2);
        antigo.incluirProduto(caderno, 1);
        novo.incluirProduto(caderno, 3);
        Pilha<Pedido> pedidosCompletos = new Pilha<>();
        pedidosCompletos.empilhar(antigo);
        pedidosCompletos.empilhar(novo);
        Pilha<Pedido> ultimoPedido = pedidosCompletos.subPilha(1);
        conferir(ultimoPedido.desempilhar() == novo, "Pedido mais recente na subpilha");
        conferir(pedidosCompletos.consultarTopo() == novo, "Pedidos originais preservados");
        conferir(novo.existeNoPedido(caderno) != null, "Produto associado ao pedido");
        System.out.println("OK - Exemplo de Pilha passou em todas as verificacoes.");
    }

    private static void conferir(boolean condicao, String descricao) {
        if (!condicao) {
            throw new AssertionError(descricao);
        }
    }
}
