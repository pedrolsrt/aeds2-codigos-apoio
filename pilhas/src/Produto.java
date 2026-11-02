import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public abstract class Produto implements Comparable<Produto>{
	
	private static final double MARGEM_PADRAO = 0.2;
	private static int ultimoID = 10_000;
	
	protected int idProduto;
	protected String descricao;
	protected double precoCusto;
	protected double margemLucro;

	private void init(String desc, double precoCusto, double margemLucro) {
		
		if ((desc.length() >= 3) && (precoCusto > 0.0) && (margemLucro > 0.0)) {
			descricao = desc;
			this.precoCusto = precoCusto;
			this.margemLucro = margemLucro;
			idProduto = ultimoID++;
		} else {
			throw new IllegalArgumentException("Valores inválidos para os dados do produto.");
		}
	}

	protected Produto(String desc, double precoCusto, double margemLucro) {
		init(desc, precoCusto, margemLucro);
	}

	protected Produto(String desc, double precoCusto) {
		init(desc, precoCusto, MARGEM_PADRAO);
	}

	public abstract double valorDeVenda();

    @Override
	public String toString() {
    	
    	NumberFormat moeda = NumberFormat.getCurrencyInstance();
    	
		return String.format("NOME: " + descricao + ": " + moeda.format(valorDeVenda()));
	}
    
    @Override

    public int hashCode(){
        return idProduto;
    }

    @Override
    public boolean equals(Object obj){
    	
    	if (this == obj) {
    		return true;
    	}
    	if ((obj == null) || (getClass() != obj.getClass())) {
    		return false;
    	}
    	Produto outroProduto = (Produto) obj;
    	return (this.hashCode() == outroProduto.hashCode());
    }
    
    @Override

    public int compareTo(Produto outro){
    	
    	if (this.idProduto == outro.idProduto)
    		return 0;
    	else if (this.idProduto < outro.idProduto)
    		return -1;
    	else
    		return 1;
    }

    static Produto criarDoTexto(String linha) {
     
    	String[] dadosLinha;
    	int tipo;
    	String descricao;
    	double precoCusto, margemLucro;
    	LocalDate dataDeValidade;
    	Produto produto;
    	
    	dadosLinha = linha.split(";");
    	tipo = Integer.parseInt(dadosLinha[0]);
    	descricao = dadosLinha[1];
    	precoCusto = Double.parseDouble(dadosLinha[2].replace(",", "."));
        margemLucro = Double.parseDouble(dadosLinha[3].replace(",", "."));
        if (tipo == 2) {
    		DateTimeFormatter formatoData = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    		dataDeValidade = LocalDate.parse(dadosLinha[4], formatoData);
    		produto = new ProdutoPerecivel(descricao, precoCusto, margemLucro, dataDeValidade);
    	} else {
    		produto = new ProdutoNaoPerecivel(descricao, precoCusto, margemLucro);
    	}
    	
    	return produto;
    }

    public abstract String gerarDadosTexto();
}
