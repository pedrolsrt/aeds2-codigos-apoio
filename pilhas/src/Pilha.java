/*
PILHAS - CODIGO COMPATIVEL COM O TEMPLATE DA PROFESSORA
subPilha(int numItens): retorna os N itens do topo em ordem preservada.
Se N for maior que a pilha, lanca IllegalArgumentException, como pede o template.
Nao altera a pilha original. Para usar, mantenha Celula.java na mesma pasta.
*/
import java.util.NoSuchElementException;

public class Pilha<E> {

	private Celula<E> topo;
	private Celula<E> fundo;

	public Pilha() {

		Celula<E> sentinela = new Celula<E>();
		fundo = sentinela;
		topo = sentinela;

	}

	public boolean vazia() {
		return fundo == topo;
	}

	public void empilhar(E item) {

		topo = new Celula<E>(item, topo);
	}

	public E desempilhar() {

		E desempilhado = consultarTopo();
		topo = topo.getProximo();
		return desempilhado;

	}

	public E consultarTopo() {

		if (vazia()) {
			throw new NoSuchElementException("Nao há nenhum item na pilha!");
		}

		return topo.getItem();

	}

	public Pilha<E> subPilha(int numItens) {
		
		if (numItens < 0) {
			throw new IllegalArgumentException("A quantidade nao pode ser negativa.");
		}

		Pilha<E> auxiliar = new Pilha<>();
		Celula<E> atual = topo;

		for (int i = 0; i < numItens; i++) {
			if (atual == fundo) {
				throw new IllegalArgumentException("A pilha nao possui a quantidade solicitada.");
			}
			auxiliar.empilhar(atual.getItem());
			atual = atual.getProximo();
		}

		Pilha<E> resultado = new Pilha<>();
		while (!auxiliar.vazia()) {
			resultado.empilhar(auxiliar.desempilhar());
		}
		return resultado;
	}
}
