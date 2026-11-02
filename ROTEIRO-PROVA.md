# ROTEIRO DE PROVA — Pilhas e Filas (AEDs II)

**Comece por aqui durante a atividade.** Este arquivo é um mapa de consulta e adaptação dos códigos do repositório. Os trechos Java abaixo foram copiados diretamente dos arquivos compilados do projeto; a explicação fica fora do código para facilitar copiar e colar.

**Atenção:** as oficinas indicam assuntos e métodos, mas **não revelam o enunciado da prova**. Este material não garante que a prova seja idêntica nem que copiar um trecho sem adaptá-lo resolva uma questão diferente. O código-base oficial disponível é o de **Pilhas**; a implementação de **Filas** foi construída a partir do PDF da oficina.

## 1. Decisão em 15 segundos

| O enunciado mencionar... | Abra primeiro | Localize |
| --- | --- | --- |
| Pilha, LIFO, topo, últimos pedidos | `pilhas/src/Pilha.java` | `subPilha(int)` |
| Salvar pedido na pilha | `pilhas/src/App.java` | `finalizarPedido(Pedido)` |
| Produtos de pedidos recentes | `pilhas/src/App.java` | `listarProdutosPedidosRecentes()` |
| Fila, FIFO, ordem de chegada | `filas/src/Fila.java` | `enfileirar`, `desenfileirar` |
| Média dos primeiros N elementos | `filas/src/Fila.java` | `calcularValorMedio(...)` |
| Filtrar os primeiros N elementos | `filas/src/Fila.java` | `filtrar(...)` |
| Média dos pedidos / pedidos acima de valor / por produto | `filas/src/App.java` | `exibirValorTotalMedio`, `exibirPedidosAcimaValor`, `exibirPedidosComProduto` |
| Como usar com objetos reais | `pilhas/src/ExemploPilha.java` ou `filas/src/ExemploFila.java` | `main` |

## 2. Ordem correta para resolver a avaliação

1. Leia **qual classe e qual método** o enunciado manda implementar. Se houver um `TODO`, substitua **somente o `TODO`** ou o método correspondente; não cole outra classe `App` inteira sobre a fornecida.
2. Confira **assinatura exata** (nome, parâmetros, tipo de retorno e modificador). Se o código da professora for diferente, **a assinatura exigida na prova prevalece**.
3. Copie o trecho equivalente da seção 4 (Pilhas) ou 5 (Filas), mantendo as classes auxiliares necessárias.
4. Adapte **nomes das variáveis e das classes**, se o enunciado usar outros. Não misture classes de `pilhas/src` e `filas/src` no mesmo projeto: elas têm nomes repetidos.
5. Verifique se o menu precisa de nova opção e de um `case` no `switch`; não basta implementar o método se o enunciado também exigir interação pelo menu.
6. Compile, execute com os seis produtos de teste, confira a ordem dos resultados e só então entregue.

## 3. Nomes do código-base que você deve preservar

- `Pedido.valorFinal()` retorna o valor do pedido. **Não substitua por `getValorTotal()`**, que não existe nesse template.
- `Pedido.existeNoPedido(produto) != null` significa que o pedido contém o produto.
- `Pedido.getItensDoPedido()` retorna um vetor que pode conter posições `null`.
- `Pilha<Pedido>`: `empilhar`, `desempilhar`, `consultarTopo`, `vazia`, `subPilha`.
- `Fila<Pedido>`: `enfileirar`, `desenfileirar`, `consultarPrimeiro`, `vazia`, `tamanho`, `calcularValorMedio`, `filtrar`.
- `Pilha` e `Fila` dependem de `Celula.java` no mesmo projeto.
- `calcularValorMedio` usa `java.util.function.Function`; `filtrar` usa `java.util.function.Predicate` (Java 8).

## 4. Pilhas — trechos prontos

### 4.1 Guardar pedido finalizado

**Arquivo:** `pilhas/src/App.java` — use dentro da classe `App`. O projeto completo já declara `static Pilha<Pedido> pilhaPedidos = new Pilha<>();`. Se estiver trabalhando no template da professora, confira se esse atributo já existe antes de criar outro.


```java
public static void finalizarPedido(Pedido pedido) {
        if (pedido == null) {
            System.out.println("Nenhum pedido foi iniciado.");
            return;
        }
        pilhaPedidos.empilhar(pedido);
        System.out.println("Pedido finalizado e armazenado com sucesso.");
    }
```


**Quando copiar:** quando o exercício pedir que os pedidos concluídos sejam armazenados em pilha. A pilha coloca o último pedido no topo.

### 4.2 Copiar os N elementos do topo sem mexer na pilha original

**Arquivo:** `pilhas/src/Pilha.java` — substitua somente o método `subPilha(int numItens)` que estiver incompleto no código-base.


```java
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
```


**Exemplo mental:** se empilhou 1, 2 e 3, `subPilha(2)` retorna 3 no topo e 2 abaixo; a original continua com 3, 2, 1. Se pedir mais itens do que existem, o método lança `IllegalArgumentException`, conforme o contrato do template.

### 4.3 Mostrar produtos dos pedidos mais recentes

**Arquivo:** `pilhas/src/App.java` — usa a pilha declarada na classe e `Pedido.getItensDoPedido()`.


```java
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
```


**Menu:** em `App.java`, o menu já tem a opção 6 e o `switch` chama `listarProdutosPedidosRecentes()`. Se a prova fornecer um menu diferente, adicione a chamada no número solicitado.

## 5. Filas — trechos prontos

### 5.1 Guardar pedido na ordem de chegada

**Arquivo:** `filas/src/App.java`. O projeto completo já declara `static Fila<Pedido> filaPedidos = new Fila<>();`.


```java
public static void finalizarPedido(Pedido pedido) {
        if (pedido == null) {
            System.out.println("Nenhum pedido foi iniciado.");
            return;
        }
        filaPedidos.enfileirar(pedido);
        System.out.println("Pedido armazenado na fila.");
    }
```


**Diferença principal:** em fila, o primeiro pedido armazenado será o primeiro a sair (FIFO). Não use `empilhar` aqui.

### 5.2 Calcular média de um atributo dos primeiros N elementos

**Arquivo:** `filas/src/Fila.java`. Mantenha `import java.util.function.Function;`.


```java
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
```


**Como chamar com pedidos:** `filaPedidos.calcularValorMedio(p -> p.valorFinal(), n)`. A expressão `p -> p.valorFinal()` extrai o número de cada `Pedido`. Não desenfileira os pedidos. Neste modelo, N maior que o tamanho usa os elementos disponíveis; zero retorna `0.0`.

### 5.3 Filtrar os primeiros N elementos

**Arquivo:** `filas/src/Fila.java`. Mantenha `import java.util.function.Predicate;`.


```java
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
```


**Como chamar com pedidos:** `filaPedidos.filtrar(p -> p.valorFinal() > minimo, n)` filtra por valor; `filaPedidos.filtrar(p -> p.existeNoPedido(procurado) != null, n)` filtra por produto. A nova fila mantém a ordem de chegada e a original fica intacta.

### 5.4 Opções do menu de Filas — funções completas

**Arquivo:** `filas/src/App.java`. Esses métodos dependem dos atributos `filaPedidos`, `teclado`, `produtosCadastrados` e dos auxiliares `lerOpcao`, `localizarProduto`, `exibirFila` já presentes no projeto. **Não copie isoladamente** para uma classe que não tenha essas dependências.

**Média dos N primeiros pedidos:**


```java
static void exibirValorTotalMedio() {
        Integer n = lerOpcao("Quantos primeiros pedidos considerar?", Integer.class);
        if (n == null || n < 0) {
            System.out.println("Quantidade invalida.");
            return;
        }
        double media = filaPedidos.calcularValorMedio(p -> p.valorFinal(), n);
        System.out.printf("Valor total medio: R$ %.2f%n", media);
    }
```


**Pedidos acima de determinado valor:**


```java
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
```


**Pedidos que contêm determinado produto:**


```java
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
```


**Auxiliar que exibe uma fila de pedidos:**


```java
static void exibirFila(Fila<Pedido> fila) {
        if (fila.vazia()) {
            System.out.println("Nenhum pedido encontrado.");
        }
        while (!fila.vazia()) {
            System.out.println(fila.desenfileirar());
        }
    }
```


**Menu:** no projeto completo, as opções 6, 7 e 8 chamam esses métodos. Se o template da prova já tiver um `switch`, coloque os `case` equivalentes nele, sem duplicar o menu inteiro.

## 6. Testar sem perder tempo (PowerShell)

Abra um terminal **na raiz do repositório**. Execute Pilhas e Filas **separadamente**:

**Pilhas:**

```powershell
cd .\pilhas
New-Item -ItemType Directory -Force bin | Out-Null
javac -encoding UTF-8 -source 8 -target 8 -d bin (Get-ChildItem .\src\*.java).FullName
java -cp bin ExemploPilha
java -cp bin App produtos-exemplo.txt
```

**Filas:** volte à raiz e execute:

```powershell
cd ..
cd .\filas
New-Item -ItemType Directory -Force bin | Out-Null
javac -encoding UTF-8 -source 8 -target 8 -d bin (Get-ChildItem .\src\*.java).FullName
java -cp bin ExemploFila
java -cp bin App produtos-exemplo.txt
```

**Saída esperada dos exemplos:** `OK - Exemplo de Pilha passou em todas as verificacoes.` e `OK - Exemplo de Fila passou em todas as verificacoes.`. Os exemplos validam o comportamento com `AssertionError` se houver falha; **não exibem todos os pedidos no console**. Para interagir com o menu, execute `App`.

**Sequência de menu para conferir:** iniciar pedido (4), escolher produto e quantidade, finalizar (5), repetir com outro pedido e usar a opção 6 em Pilhas; em Filas, após finalizar dois pedidos, testar as opções 6, 7 e 8. No arquivo `produtos-exemplo.txt` há somente seis produtos, facilitando localizar um pelo código ou nome.

## 7. Checklist de entrega

- [ ] A solução usa **Pilha (LIFO)** ou **Fila (FIFO)** conforme o enunciado.
- [ ] A **assinatura do método** é exatamente a exigida.
- [ ] O código foi colocado **na classe certa**, sem duplicar `App`, `Celula`, `Pedido` ou outros nomes.
- [ ] Os imports e atributos usados pelo trecho existem.
- [ ] Os N elementos selecionados estão na ordem correta.
- [ ] A estrutura original foi preservada quando solicitado.
- [ ] O menu chama os métodos pedidos.
- [ ] A compilação terminou sem erros e pelo menos um teste funcional passou.
- [ ] O projeto foi enviado ao **repositório de entrega indicado pela professora**, não apenas ao repositório de apoio.

**Observação final:** o tratamento de valores inválidos (quantidade negativa, maior que o tamanho, zero) pode variar no enunciado da prova. Em caso de divergência, siga a regra escrita na avaliação. Este roteiro é um material de consulta, não um substituto para ler o enunciado.

