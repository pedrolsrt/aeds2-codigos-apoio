public class ProdutoNaoPerecivel extends Produto{

	public ProdutoNaoPerecivel(String desc, double precoCusto, double margemLucro) {
		super(desc, precoCusto, margemLucro);
	}

	public ProdutoNaoPerecivel(String desc, double precoCusto) {
		super(desc, precoCusto);
	}

	@Override
	public double valorDeVenda() {
		return (precoCusto * (1.0 + margemLucro));
	}

	@Override
    public String gerarDadosTexto() {
    	
		String precoCustoFormatado = String.format("%.2f", precoCusto).replaceAll(",", ".");
		String margemLucroFormatada = String.format("%.2f", margemLucro).replaceAll(",", ".");
		
		return String.format("1;%s;%s;%s", descricao, precoCustoFormatado, margemLucroFormatada);
    }
}
