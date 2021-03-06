package src;

/**
 * Uma classe que representa uma loja  
 * @author Igor M. Padua e Adriel L. V. Mori
 */

public class Loja extends Empresa {

	Loja(String nome, String telefone, String cnpj, String razaoSocial) {
		super(nome, telefone, cnpj, razaoSocial);	
	}

	public String toString() {
		return "Loja: " + super.toString() + " - " + estoque;
	}
}
