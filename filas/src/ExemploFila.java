/*
EXEMPLO EXECUTAVEL DE FILA - BASEADO NO PDF DA PROFESSORA
Execute: java -cp bin ExemploFila
Mostra FIFO, media dos N primeiros, filtro dos N primeiros,
preservacao da fila original e uso com Pedido e Produto.
Abaixo deste bloco comeca apenas o codigo Java.
*/
import java.time.LocalDate;

public class ExemploFila {
    public static void main(String[] args) {
        Fila<Integer> numeros = new Fila<>();
        numeros.enfileirar(10);
        numeros.enfileirar(20);
        numeros.enfileirar(30);

        conferir(Math.abs(numeros.calcularValorMedio(x -> x.doubleValue(), 2) - 15.0) < 0.0001,
                "Media dos dois primeiros");
        Fila<Integer> filtrados = numeros.filtrar(x -> x >= 20, 2);
        conferir(filtrados.tamanho() == 1, "Filtra somente os dois primeiros");
        conferir(filtrados.desenfileirar() == 20, "Item filtrado correto");
        conferir(numeros.tamanho() == 3, "Fila original intacta");
        conferir(numeros.desenfileirar() == 10, "Ordem FIFO");
        conferir(numeros.desenfileirar() == 20, "Segundo da fila");
        conferir(numeros.desenfileirar() == 30, "Terceiro da fila");
        conferir(numeros.vazia(), "Fila vazia apos remocoes");
        numeros.enfileirar(40);
        conferir(numeros.desenfileirar() == 40, "Reutilizacao apos esvaziar");

        Produto caderno = new ProdutoNaoPerecivel("Caderno", 10.0, 0.2);
        Produto lapis = new ProdutoNaoPerecivel("Lapis", 2.0, 0.5);
        Pedido primeiro = new Pedido(LocalDate.now(), 2);
        Pedido segundo = new Pedido(LocalDate.now(), 2);
        primeiro.incluirProduto(caderno, 2);
        segundo.incluirProduto(lapis, 3);

        Fila<Pedido> pedidos = new Fila<>();
        pedidos.enfileirar(primeiro);
        pedidos.enfileirar(segundo);
        conferir(Math.abs(pedidos.calcularValorMedio(p -> p.valorFinal(), 2) - 16.5) < 0.0001,
                "Media do valor dos pedidos");
        Fila<Pedido> comCaderno = pedidos.filtrar(p -> p.existeNoPedido(caderno) != null, 2);
        conferir(comCaderno.tamanho() == 1, "Filtro por produto");
        conferir(comCaderno.desenfileirar() == primeiro, "Pedido com produto correto");
        conferir(pedidos.tamanho() == 2, "Pedidos originais intactos");
        System.out.println("OK - Exemplo de Fila passou em todas as verificacoes.");
    }

    private static void conferir(boolean condicao, String descricao) {
        if (!condicao) {
            throw new AssertionError(descricao);
        }
    }
}
