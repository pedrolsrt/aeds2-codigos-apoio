import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class ProdutoPerecivel extends Produto{

	private static final double DESCONTO = 0.25;

	private static final int PRAZO_DESCONTO = 7;

	private LocalDate dataDeValidade;

	public ProdutoPerecivel(String desc, double precoCusto, double margemLucro, LocalDate validade) {
		
		super(desc, precoCusto, margemLucro);
		
		if (validade.isBefore(LocalDate.now())) {
			throw new IllegalArgumentException("Data de validade do produto é anterior ao dia de hoje!");
		}
		dataDeValidade = validade;
	}

	public ProdutoPerecivel(String desc, double precoCusto, LocalDate validade) {
		
		super(desc, precoCusto);
		
		if (validade.isBefore(LocalDate.now())) {
			throw new IllegalArgumentException("Data de validade do produto é anterior ao dia de hoje!");
		}
		dataDeValidade = validade;
	}

	@Override
	public double valorDeVenda() {
		
		double precoVenda;
		
		if (dataDeValidade.isBefore(LocalDate.now())) {
			throw new IllegalArgumentException("Data de validade do produto é anterior ao dia de hoje!");
		}
		
		precoVenda = (precoCusto * (1.0 + margemLucro));
				
		if (LocalDate.now().until(dataDeValidade).getDays() <= PRAZO_DESCONTO) {
			precoVenda = precoVenda * (1.0 - DESCONTO);
		}
		
		return precoVenda;
	}

    @Override
    public String toString(){
    	
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        
        String dados = super.toString();
        dados += "\nVálido até " + formato.format(dataDeValidade);
        
        return dados;
    }

	@Override
    public String gerarDadosTexto() {
    
		String precoCustoFormatado = String.format("%.2f", precoCusto).replaceAll(",", ".");
		String margemLucroFormatada = String.format("%.2f", margemLucro).replaceAll(",", ".");
		DateTimeFormatter formatoData = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		String dataFormatada = formatoData.format(dataDeValidade);
		
		return String.format("2;%s;%s;%s;%s", descricao, precoCustoFormatado, margemLucroFormatada, dataFormatada);
		
	}
}
