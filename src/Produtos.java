package src;

/**
 * Uma classe que representa um produto de uma loja ou estoque  
 * @author Igor M. Padua e Adriel L. V. Mori
 */

public class Produtos {

	String nomeProduto;
	private int quatidadeEstoque;
	private double precoCusto;
	double precoVenda;
	private String dataUltimaCompraEstoque;
	Empresa fornecedora;

	Produtos(String nomeProduto, int quatidadeEstoque, double precoCusto, double precoVenda,
			String dataUltimaCompraEstoque, Empresa fornecedora) {
		alteracaoCadastrosProduto(nomeProduto, quatidadeEstoque, precoCusto, precoVenda, dataUltimaCompraEstoque, fornecedora);	
	}
	
	public int getQuatidadeEstoque() {
		return quatidadeEstoque;
	}

	public void setQuatidadeEstoque(int quatidadeEstoque) {
		this.quatidadeEstoque = quatidadeEstoque;
	}

	public double getPrecoCusto() {
		return precoCusto;
	}

	public void setPrecoCusto(double precoCusto) {
		this.precoCusto = precoCusto;
	}

	public String getDataUltimaCompraEstoque() {
		return dataUltimaCompraEstoque;
	}

	public void setDataUltimaCompraEstoque(String dataUltimaCompraEstoque) {
		this.dataUltimaCompraEstoque = dataUltimaCompraEstoque;
	}

	public void alteracaoCadastrosProduto(String nomeProduto, int quatidadeEstoque, double precoCusto, double precoVenda,
			String dataUltimaCompraEstoque, Empresa fornecedora) {
		this.nomeProduto = nomeProduto;
		this.quatidadeEstoque = quatidadeEstoque;
		this.precoCusto = precoCusto;
		this.precoVenda = precoVenda;
		this.dataUltimaCompraEstoque = dataUltimaCompraEstoque;
		this.fornecedora = fornecedora;
	}

	public String toString() {

		return "Nome do produto: " + nomeProduto +
			" - Quantidade em estoque: " + quatidadeEstoque +
			" - Preço de custo: " + precoCusto +
			" - Preço de venda: " + precoVenda +
			" - Data da última ocorrência de compra: " + dataUltimaCompraEstoque;
	}
}
