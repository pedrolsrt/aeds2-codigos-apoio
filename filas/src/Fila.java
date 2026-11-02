/*
FILAS - MODELO ENCADEADO COMPATIVEL COM Celula.java DA PROFESSORA
FIFO: enfileirar no fim e desenfileirar no inicio.
calcularValorMedio(Function<E, Double>, int): media dos N primeiros.
filtrar(Predicate<E>, int): nova fila com os aprovados dentre os N primeiros.
N maior que o tamanho considera os elementos existentes; N negativo gera erro.
Este modelo foi criado a partir do PDF; o template oficial de Fila nao foi fornecido.
*/
import java.util.NoSuchElementException;
import java.util.function.Function;
import java.util.function.Predicate;

public class Fila<E> {
    private Celula<E> frente;
    private Celula<E> tras;
    private int tamanho;

    public Fila() {
        frente = new Celula<E>();
        tras = frente;
        tamanho = 0;
    }

    public boolean vazia() {
        return frente == tras;
    }

    public int tamanho() {
        return tamanho;
    }

    public void enfileirar(E item) {
        Celula<E> nova = new Celula<E>(item);
        tras.setProximo(nova);
        tras = nova;
        tamanho++;
    }

    public E consultarPrimeiro() {
        if (vazia()) {
            throw new NoSuchElementException("Nao ha itens na fila.");
        }
        return frente.getProximo().getItem();
    }

    public E desenfileirar() {
        E item = consultarPrimeiro();
        frente = frente.getProximo();
        tamanho--;
        if (vazia()) {
            tras = frente;
        }
        return item;
    }

    public double calcularValorMedio(Function<E, Double> extrator, int quantidade) {
        if (extrator == null || quantidade < 0) {
            throw new IllegalArgumentException("Extrator nulo ou quantidade negativa.");
        }
        double soma = 0.0;
        int contados = 0;
        Celula<E> atual = frente.getProximo();
        while (atual != null && contados < quantidade) {
            Double valor = extrator.apply(atual.getItem());
            if (valor == null) {
                throw new IllegalArgumentException("O extrator retornou null.");
            }
            soma += valor;
            contados++;
            atual = atual.getProximo();
        }
        return contados == 0 ? 0.0 : soma / contados;
    }

    public Fila<E> filtrar(Predicate<E> condicional, int quantidade) {
        if (condicional == null || quantidade < 0) {
            throw new IllegalArgumentException("Condicao nula ou quantidade negativa.");
        }
        Fila<E> resultado = new Fila<E>();
        Celula<E> atual = frente.getProximo();
        int examinados = 0;
        while (atual != null && examinados < quantidade) {
            E item = atual.getItem();
            if (condicional.test(item)) {
                resultado.enfileirar(item);
            }
            atual = atual.getProximo();
            examinados++;
        }
        return resultado;
    }
}
