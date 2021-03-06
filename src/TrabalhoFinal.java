package src;

import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.JFrame;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;
import java.util.Scanner;
import java.io.*;

/**
 * Classe principal do programa  
 * @author Igor M. Padua e Adriel L. V. Mori
 */

public class TrabalhoFinal {
	public static void main(String[] args) throws Exception {
		ArrayList<Empresa> empresas = new ArrayList<Empresa>();
		Scanner input = new Scanner(System.in);
		int i, aux = -1;
		Boolean sair = true;
		String escolha;
		// Usado para criar uma Empresa
		String nome, telefone, cnpj, razaoSocial;
		String bairro, cidade, estado;
		int capacidadeMax, id=0;

		// Ler o arquivo
		try {
			InputStream arquivo = new FileInputStream("Dados.txt");
			InputStreamReader in = new InputStreamReader(arquivo);
			BufferedReader br = new BufferedReader(in);

			String linha = br.readLine();
			int contador=-1;

			while (linha != null) {
				String[] campos = linha.split(";");

					if (campos[0].equals("fornecedor")) {

					// Dados principais
					nome = campos[1];
					telefone = campos[2];
					cnpj = campos[3];
					razaoSocial = campos[4];
					Fornecedor fornecedor = new Fornecedor(nome, telefone, cnpj, razaoSocial);
					// Estoque
					capacidadeMax = Integer.parseInt(campos[5]);
					Estoque estoque = new Estoque(capacidadeMax);
					fornecedor.estoque = estoque;
					// Endereço
					estado = campos[6];
					cidade = campos[7];
					bairro = campos[8];
					Endereco endereco = new Endereco(bairro, cidade, estado);
					fornecedor.endereco = endereco;

					empresas.add(fornecedor); contador++;
					
				} else if (campos[0].equals("loja")) {

					// Dados principais
					nome = campos[1];
					telefone = campos[2];
					cnpj = campos[3];
					razaoSocial = campos[4];
					Loja loja = new Loja(nome, telefone, cnpj, razaoSocial);
					// Estoque
					capacidadeMax = Integer.parseInt(campos[5]);
					Estoque estoque = new Estoque(capacidadeMax);
					loja.estoque = estoque;
					// Endereço
					estado = campos[6];
					cidade = campos[7];
					bairro = campos[8];
					Endereco endereco = new Endereco(bairro, cidade, estado);
					loja.endereco = endereco;
					
					empresas.add(loja); contador++;
					
				} if(campos[0].equals("produto")) {
						
					// Dados principais
					String nomeProduto= campos[1];
					int quatidadeEstoque= Integer.parseInt(campos[2]);
					double precoCusto= Double.parseDouble(campos[3]);
					double precoVenda= Double.parseDouble(campos[4]);
					String dataUltimaCompraEstoque= campos[5];
					String fornecedorAux = campos[6];

					for(int p=0; p<empresas.size();p++) {
						if(empresas.get(p).nome.equals(fornecedorAux)) {

							id=p;
							break;
						} 			
					} 

					Produtos produto = new Produtos(nomeProduto, quatidadeEstoque, 
							precoCusto, precoVenda, dataUltimaCompraEstoque, empresas.get(id));

					empresas.get(contador).estoque.produtosEmEstoque.add(produto);

				} 
			
				linha = br.readLine();
			}

			br.close();
			in.close();
			arquivo.close();

		} catch(Exception e){
			throw new IllegalAccessError("Arquivo não existe");
		}

		while (sair == true) {
			escolha = JOptionPane.showInputDialog(null, 
					"1 - Adicionar uma empresa\n" + 
					"2 - Administra produtos\n" +
					"3 - Procurar um produto por endereço\n" +
					"4 - Modificar algum dado\n" +
					"5 - Mostrar dados das empresas\n" +
					"6 - Remover uma Empresa ou produto\n" +
					"7 - Gráfico de ocupação do estoque\n"+
					"8 - Sair", 
					"TrabalhoFinal", JOptionPane.QUESTION_MESSAGE);
			switch (escolha) {
				case "1":
					/*
					 * Aqui o usuário poderá criar uma loja ou um fornecedor
					 */
					System.out.println("\n-------ADICIONANDO EMPRESA--------");
					System.out.println("1 - Adicionar uma loja\n" +
							"2 - Adicionar um Fornecedor");
					escolha = input.next();
					input = new Scanner(System.in);
					if (escolha.equals("1")) {

						// Criação da loja
						System.out.println("Qual o nome da loja?");
						nome = input.nextLine();
						System.out.println("Qual o número de telefone da loja?");
						telefone = input.next();
						System.out.println("Qual o CNPJ da loja?");
						cnpj = input.next();
						input = new Scanner(System.in);
						System.out.println("Qual a razão social da loja?");
						razaoSocial = input.nextLine();
						Loja loja = new Loja(nome, telefone, cnpj, razaoSocial);

						// Criação do estoque da loja
						System.out.println("Qual a capacidade máxima do estoque da loja?");
						capacidadeMax = input.nextInt();
						input = new Scanner(System.in);
						Estoque estoque = new Estoque(capacidadeMax);
						loja.estoque = estoque;

						// Endereço da loja
						System.out.println("Qual o estado da loja?");		
						estado = input.nextLine();
						System.out.println("Qual a cidade da loja?");		
						cidade = input.nextLine();
						System.out.println("Qual o bairro da loja?");		
						bairro = input.nextLine();
						Endereco endereco = new Endereco(bairro, cidade, estado);
						loja.endereco = endereco;

						empresas.add(loja);
			
					} else if (escolha.equals("2")) { 

						// Criação do fornecedor
						System.out.println("Qual o nome do fornecedor?");
						nome = input.nextLine();
						System.out.println("Qual o número de telefone do fornecedor?");
						telefone = input.next();
						System.out.println("Qual o CNPJ do fornecedor?");
						cnpj = input.next();
						input = new Scanner(System.in);
						System.out.println("Qual a razão social do fornecedor?");
						razaoSocial = input.nextLine();
						Fornecedor fornecedor = new Fornecedor(nome, telefone, cnpj, razaoSocial);

						// Criação do estoque do fornecedor
						System.out.println("Qual a capacidade máxima do estoque do fornecedor?");
						capacidadeMax = input.nextInt();
						Estoque estoque = new Estoque(capacidadeMax);
						input = new Scanner(System.in);
						fornecedor.estoque = estoque;

						// Endereço da loja
						System.out.println("Qual o estado da loja?");		
						cidade = input.nextLine();
						System.out.println("Qual a cidade da loja?");		
						estado = input.nextLine();
						System.out.println("Qual o bairro da loja?");		
						bairro = input.nextLine();
						Endereco endereco = new Endereco(bairro, cidade, estado);
						fornecedor.endereco = endereco;

						empresas.add(fornecedor);

					} else {
						System.out.println("Comando errado");
					} 	

					break;
				case "2":
					/*
					 * Aqui o usuário poderá adicionar um produto a alguma empresa
					 */
					System.out.println("\n-------ADMINISTRANDO PRODUTOS--------");
					
					if(empresas.isEmpty()) {
						
						System.out.println("Nenhuma empresa no sistema");
						break;
						
					}else {
						//Aqui o usuário irá adicionar produtos a loja
						
						System.out.println("Qual o nome da empresa a se adicionar o(s) produto(s) ?");
						String verificaLoja = input.next();
						input = new Scanner(System.in);
						int cont=-1;
						//Para caso não exista empresas dentro do sistema
						for (i = 0; i < empresas.size(); i++) {
							if (verificaLoja.equals(empresas.get(i).nome)) {
								cont = i;
							}
						} 
						
						if(cont==-1) {
							System.out.println("Nenhuma empresa no sistema");
							break;
							
						}
						
						/*
						 * Caso de entrada produtos para Fornecedor
						 */
						if(empresas.get(cont) instanceof Fornecedor) {
							
							System.out.println("Adicionando Produtos ao fornecedor -"+empresas.get(cont).nome+"-");
							input = new Scanner(System.in);
							
							System.out.println("1 - Adicionar novo produto\n"
									+ "2 - Aumentar estoque do produto");
							
							int opcaoAdicionar = input.nextInt();
							
							switch(opcaoAdicionar) {
								
							case 1:
								//Adicionando Produtos ainda não listados no sistema
								System.out.println("Quantos produtos serão adicionados ?");
								int num = input.nextInt();
								int identificaExistente=0;
								
								for(int p=0;p<num;p++) {
									
									System.out.println("---Produto "+ (p+1) +" ---");
									
									input = new Scanner(System.in);
									System.out.println("Qual o nome do produto ?");
									String nomeProduto = input.nextLine();
									
									for(int a=0; a<empresas.get(cont).estoque.produtosEmEstoque.size();a++) {
										if(empresas.get(cont).estoque.produtosEmEstoque.get(a).nomeProduto.equals(nomeProduto)) {
											identificaExistente++;
										}		
									}
									
									if(identificaExistente!=0) {
										System.out.println("Produto já existe no sistema em seu estoque");
										
										break;
									}
									
									input = new Scanner(System.in);
									System.out.println("Qual a quantidade do produto ?");
									int quantidadeEstoque = input.nextInt();
									int total=0;
									total=quantidadeEstoque;
									
									//Calculando estoque total antes de adicionar produto
									for(int s=0;s<empresas.get(cont).estoque.produtosEmEstoque.size(); s++) {
										total+=empresas.get(cont).estoque.produtosEmEstoque.get(s).getQuatidadeEstoque();
									}
									
									if (empresas.get(cont).estoque.verificaEstoque(total)==false) {
										
										System.out.println("O estoque da loja -" + empresas.get(cont).nome + "- está cheio!");
										break;
									} else
										
										System.out.println("Qual o preço de custo ?");
										double precoCusto = input.nextDouble();
										System.out.println("Qual o preço de venda ?");
										double precoVenda = input.nextDouble();
										input = new Scanner(System.in);
										System.out.println("Qual a data de produção ? (formatação: XX/YY/ZZZZ)");
										String dataUltimaCompraEstoque = input.nextLine();
										
										Produtos produto = new Produtos(nomeProduto, quantidadeEstoque, 
												precoCusto, precoVenda, dataUltimaCompraEstoque, empresas.get(cont));
										
										empresas.get(cont).estoque.produtosEmEstoque.add(produto);
								}
									break;
								
							
							case 2:
								
								//Adicionado unidades a um produto existente
								int numProdutos=0;
								
								System.out.println("--Lista de produtos no sistema--");
								
								for(int s=0;s<empresas.get(cont).estoque.produtosEmEstoque.size(); s++) {
									System.out.println("Nome: "+empresas.get(cont).estoque.produtosEmEstoque.get(s).nomeProduto+
											" - Quantidade em estoque: " + empresas.get(cont).estoque.produtosEmEstoque.get(s).getQuatidadeEstoque());
									numProdutos++;
								}
								
								if(numProdutos==0) {
									System.out.println("Esta empresa não possui produtos");
									break;
								}
								
								//Caso o usuário queira adicionar mais estoque do produto
								input = new Scanner(System.in);
								System.out.println("\nQual o nome do produto ?");
								String nomeProduto = input.nextLine();
								int idAdd=-1;
								
								//Pega a posicao do produto
								for(int s=0;s<empresas.get(cont).estoque.produtosEmEstoque.size(); s++) {	
									if(empresas.get(cont).estoque.produtosEmEstoque.get(s).nomeProduto.equals(nomeProduto)) {
										idAdd=s;
										continue;
									} 
								}
								
								if(idAdd==-1) {
									System.out.println("Este produto não consta no sistema");
									break;
								}
							
								input = new Scanner(System.in);
								System.out.println("Qual a quantidade a se adicionar deste produto no estoque ?");
								int quantidadeEstoque = input.nextInt();
								int total=0, acumulado=0;
								
								total= quantidadeEstoque;
								acumulado=quantidadeEstoque+empresas.get(cont).estoque.produtosEmEstoque.get(idAdd).getQuatidadeEstoque();
								
								//Calculando estoque total antes de adicionar produto
								for(int a=0;a<empresas.get(cont).estoque.produtosEmEstoque.size(); a++) {
									total+=empresas.get(cont).estoque.produtosEmEstoque.get(a).getQuatidadeEstoque();
								}
								
								if (empresas.get(cont).estoque.verificaEstoque(total)==false) {
									
									System.out.println("O estoque do fornecedor -" + empresas.get(cont).nome + "- está cheio!");
									break;
								} else
									
									System.out.println("Qual o novo preço de custo ? Atual: R$"+empresas.get(cont).estoque.produtosEmEstoque.get(idAdd).getPrecoCusto());
									double precoCusto = input.nextDouble();
									System.out.println("Qual o novo preço de venda ? Atual: R$"+empresas.get(cont).estoque.produtosEmEstoque.get(idAdd).precoVenda);
									double precoVenda = input.nextDouble();
									input = new Scanner(System.in);
									System.out.println("Qual a data de produção ? (formatação: XX/YY/ZZZZ)");
									String dataUltimaCompraEstoque = input.nextLine();
									
									empresas.get(cont).estoque.produtosEmEstoque.get(idAdd).alteracaoCadastrosProduto(nomeProduto,
											acumulado, precoCusto, precoVenda, dataUltimaCompraEstoque, empresas.get(cont));
					
								break;
								
							default:
								System.out.println("Opcao Inválida");
								break;
							}
							
							break;
							
						/*
						 * Caso de entrada produtos para Loja
						 */
						} else if(empresas.get(cont) instanceof Loja){
							int cont2=-1;//Identificador do fornecedor para a loja
							
							//caixa de mensagem com todos os produtos do fornecedor escolhido
							String lista="";
							
							for(i=0;i<empresas.size();i++) {
								if(empresas.get(i) instanceof Fornecedor) {
									lista+="FORNECEDOR --"+ empresas.get(i).nome +"--\n";
									
									if(empresas.get(i).estoque.produtosEmEstoque.isEmpty()) {
										lista+="Nenhum produto no sistema\n";
									} else
										for(int j=0; j<empresas.get(i).estoque.produtosEmEstoque.size();j++) {
											lista+=empresas.get(i).estoque.produtosEmEstoque.get(j).nomeProduto+
													"\n";
									}
								}
							}
							
							JOptionPane.showMessageDialog(null, lista);
							
							System.out.println("Qual o nome do fornecedor dos produtos ?");
							String nomeFornecedor = input.nextLine();
							
							//Verifica se existe esse fornecedor
							for (int j = 0; j < empresas.size(); j++) {
								if (nomeFornecedor.equals(empresas.get(j).nome)) {			
									cont2 = j;
								}
							}
							
							if(cont2==-1) {
								System.out.println("Nenhum fornecedor com esse nome no sistema");
								break;
							}
							
							System.out.println("Adicionando Produtos a Loja -"+empresas.get(cont).nome+"-");
							input = new Scanner(System.in);
							
							System.out.println("1 - Adicionar novo produto\n"
									+ "2 - Aumentar estoque do produto");
							
							int opcaoAdicionar = input.nextInt();
							
							switch(opcaoAdicionar) {
							
							case 1://Adicionado produto ao sistema da Loja
							
								//caixa de mensagem com todos os produtos do fornecedor escolhido
								lista="Produtos em estoque na " + empresas.get(cont2).nome +":\n";
								
								for(int j=0; j<empresas.get(cont2).estoque.produtosEmEstoque.size();j++) {
									lista+="-" +empresas.get(cont2).estoque.produtosEmEstoque.get(j).nomeProduto
											+ "- Unidades disponívies: " + empresas.get(cont2).estoque.produtosEmEstoque.get(j).getQuatidadeEstoque()+"\n";
								}
								
								System.out.println(lista);
							
								System.out.println("Quantos produtos serão adicionados ?");
								int num = input.nextInt();
								
								for(int p=0;p<num;p++) {
									
									System.out.println("---Produto "+ (p+1) +" ---");
											
										input = new Scanner(System.in);
										System.out.println("Qual o nome do produto ?");
										String nomeProdutoTransferido = input.nextLine();
										int posicaoProdutoTransferido=-1;
										int posicaoProdutoNaLoja=-1;
										int identificaExistente=0;
										
										//Verifica se existe de fato o produto no estoque da Loja
										for(int a=0; a<empresas.get(cont).estoque.produtosEmEstoque.size();a++) {
											if(empresas.get(cont).estoque.produtosEmEstoque.get(a).nomeProduto.equals(nomeProdutoTransferido)) {
												posicaoProdutoNaLoja=a;
												identificaExistente++;
											}		
										}
										
										if(identificaExistente!=0) {
											System.out.println("Produto já existe no sistema em seu estoque");
											
											break;
										}
											
										//verifica se o produto esta no Fornecedor 
										for(int s=0;s<empresas.get(cont2).estoque.produtosEmEstoque.size(); s++) {
											if(empresas.get(cont2).estoque.produtosEmEstoque.get(s).nomeProduto.equals(nomeProdutoTransferido)) {
												posicaoProdutoTransferido=s;
											} 
										}
											
										if(posicaoProdutoTransferido==-1) {
											System.out.println("Prouduto não listado no sistema");
											break;
										}
											
										input = new Scanner(System.in);
										System.out.println("Qual a quantidade de estoque ?");
										int quantidadeEstoque = input.nextInt();
										int total=0, subtracao=empresas.get(cont2).estoque.produtosEmEstoque.get(posicaoProdutoTransferido).getQuatidadeEstoque()-quantidadeEstoque;
											
										total=quantidadeEstoque;
											
										//Calculando estoque total antes de adicionar produto
										for(int s=0;s<empresas.get(cont).estoque.produtosEmEstoque.size(); s++) {
											total+=empresas.get(cont).estoque.produtosEmEstoque.get(s).getQuatidadeEstoque();
										}
											
										if (empresas.get(cont).estoque.verificaEstoque(total)==false) {
												
											System.out.println("O estoque da loja -" + empresas.get(cont).nome + "- está cheio!");
											break;
										} else 
											
										/*
										 * Verifica se o forncedor tem a quantidade de 
										 * produtos, se sim, subtrai o estoque do fornecedor para adicionar na loja
										*/
												
										if(empresas.get(cont).estoque.verificaCompra(quantidadeEstoque, empresas.get(cont2).estoque.produtosEmEstoque.get(posicaoProdutoTransferido).getQuatidadeEstoque())==true) {
											empresas.get(cont2).estoque.produtosEmEstoque.get(posicaoProdutoTransferido).setQuatidadeEstoque(subtracao);
										} else {
											System.out.println("Fornecedora não possui estoque suficiente deste produto"
													+ "\nEstoque atual em unidades: " + empresas.get(cont2).estoque.produtosEmEstoque.get(posicaoProdutoTransferido).getQuatidadeEstoque()); 
											break;
										}
											
										System.out.println("O valor de compra da unidade é de: R$" + empresas.get(cont2).estoque.produtosEmEstoque.get(posicaoProdutoTransferido).precoVenda);
										System.out.println("Qual o preço de venda ?");
										double precoVenda = input.nextDouble();
										input = new Scanner(System.in);
										System.out.println("Qual a data de compra ? (formatação: XX/YY/ZZZZ)");
										String dataUltimaCompraEstoque = input.nextLine();
											
										empresas.get(cont2).estoque.produtosEmEstoque.get(posicaoProdutoTransferido).setDataUltimaCompraEstoque("Comprado "+dataUltimaCompraEstoque + " por " + empresas.get(cont).nome);
												
										Produtos produto = new Produtos(nomeProdutoTransferido, quantidadeEstoque, empresas.get(cont2).estoque.produtosEmEstoque.get(posicaoProdutoTransferido).precoVenda, 
												precoVenda, dataUltimaCompraEstoque, empresas.get(cont2));
											
										empresas.get(cont).estoque.produtosEmEstoque.add(produto);
									
								}
							break; //Fim da função
								
							case 2:
								//adicionando unidades a um produto já existente
								
								int numProdutos=0, produtoTransferido=0;
								System.out.println("--Lista de produtos no sistema da Loja--");
								
								for(int s=0;s<empresas.get(cont).estoque.produtosEmEstoque.size(); s++) {
									System.out.println("Nome: "+empresas.get(cont).estoque.produtosEmEstoque.get(s).nomeProduto+
											" - Quantidade em estoque: " + empresas.get(cont).estoque.produtosEmEstoque.get(s).getQuatidadeEstoque());
									numProdutos++;
								}
								
								if(numProdutos==0) {
									System.out.println("Esta empresa não possui produtos");
									break;
								}
								
								input = new Scanner(System.in);
								System.out.println("\nQual o nome do produto ?");
								String nomeProdutoTransferido= input.nextLine();
								int posicaoProdutoNoFornecedor=-1;
								int posicaoProdutoAdicionado=-1;//Posicao do meu produto na Loja
								
								//Pega a posicao do produto na Loja
								for(int s=0;s<empresas.get(cont).estoque.produtosEmEstoque.size(); s++) {	
									if(empresas.get(cont).estoque.produtosEmEstoque.get(s).nomeProduto.equals(nomeProdutoTransferido)) {
										posicaoProdutoAdicionado=s;
										continue;
									} 
								}
								
								if(posicaoProdutoAdicionado==-1) {
									System.out.println("Este produto não consta no sistema");
									break;
								}
								//Procura o Produto no estoque do fornecedor, para poder decrementar
								for(int s=0;s<empresas.get(cont2).estoque.produtosEmEstoque.size(); s++) {
									if(empresas.get(cont2).estoque.produtosEmEstoque.get(s).nomeProduto.equals(nomeProdutoTransferido)) {
										posicaoProdutoNoFornecedor=s;
									} 
								}
									
								if(posicaoProdutoNoFornecedor==-1) {
									System.out.println("Prouduto não listado no sistema do Fornecedor");
									break;
								}
								
								//Produto já existe no estoque, então iremos adicionar
								System.out.println("Estoque atual do produto: " + empresas.get(cont).estoque.produtosEmEstoque.get(posicaoProdutoAdicionado).getQuatidadeEstoque());
								
								input = new Scanner(System.in);
								System.out.println("Qual a quantidade a se adicionar deste produto no estoque ?");
								int quantidadeEstoque = input.nextInt();
								int total=0, acumulado=0, subtracao=empresas.get(cont2).estoque.produtosEmEstoque.get(posicaoProdutoNoFornecedor).getQuatidadeEstoque()-quantidadeEstoque;
									
								total= quantidadeEstoque;
								acumulado=quantidadeEstoque+empresas.get(cont).estoque.produtosEmEstoque.get(posicaoProdutoAdicionado).getQuatidadeEstoque();
									
								//Calculando estoque total antes de adicionar produto
								for(int a=0;a<empresas.get(cont).estoque.produtosEmEstoque.size(); a++) {
									total+=empresas.get(cont).estoque.produtosEmEstoque.get(a).getQuatidadeEstoque();
								}
									
								if (empresas.get(cont).estoque.verificaEstoque(total)==false) {
										
									System.out.println("O estoque da loja -" + empresas.get(cont).nome + "- está cheio!");
									break;
								} else 
									
								/*Verifica se o forncedor tem a quantidade de 
								produtos, se sim, subtrai o estoque do fornecedor para adicionar na loja
								*/
										
								if(empresas.get(cont).estoque.verificaCompra(quantidadeEstoque, empresas.get(cont2).estoque.produtosEmEstoque.get(posicaoProdutoNoFornecedor).getQuatidadeEstoque())==true) {
									empresas.get(cont2).estoque.produtosEmEstoque.get(posicaoProdutoNoFornecedor).setQuatidadeEstoque(subtracao);
								} else {
									System.out.println("Fornecedora não possui estoque suficiente deste produto"
											+ "\nEstoque atual em unidades: " + empresas.get(cont2).estoque.produtosEmEstoque.get(posicaoProdutoNoFornecedor).getQuatidadeEstoque()); 
									break;
								}
								
								System.out.println("O valor de compra da unidade é de: R$" + empresas.get(cont2).estoque.produtosEmEstoque.get(posicaoProdutoNoFornecedor).precoVenda);
								System.out.println("Qual o novo preço de venda ?");
								double precoVenda = input.nextDouble();
								input = new Scanner(System.in);
								System.out.println("Qual a data de compra ? (formatação: XX/YY/ZZZZ)");
								String dataUltimaCompraEstoque = input.nextLine();
									
								//Atualizando data no produto do fornecedor
								empresas.get(cont2).estoque.produtosEmEstoque.get(posicaoProdutoNoFornecedor).setDataUltimaCompraEstoque("Comprado "+dataUltimaCompraEstoque + " por " + empresas.get(cont).nome);
									
								empresas.get(cont).estoque.produtosEmEstoque.get(posicaoProdutoAdicionado).alteracaoCadastrosProduto(nomeProdutoTransferido, acumulado,  empresas.get(cont2).estoque.produtosEmEstoque.get(posicaoProdutoNoFornecedor).precoVenda
										, precoVenda, dataUltimaCompraEstoque, empresas.get(cont2));
								
								break; //Fim da função
								
							default:
								System.out.println("Opção inválida");
							
							break;
							}
						} else  
							System.out.println("Nenhuma loja com esse nome no sistema");
					}
					break;
					
				case "3":
					/*
					 * Aqui o usuário poderá pesquisar um endereço para encontrar lojas 
					 */
					
					System.out.println("----BUSCANDO LOJAS E SEUS PRODUTOS POR LOCALIZAÇÃO----"
							+ "\nInforme a cidade:");
					
					String localCidade = input.nextLine();
					input = new Scanner(System.in);
					int contadorLoja=0;
					
					System.out.println("Informe o bairro:");
					String localBairro = input.nextLine();
					input = new Scanner(System.in);
					
					for(i=0; i<empresas.size();i++) {
						if(empresas.get(i).endereco.cidade.equals(localCidade) && empresas.get(i).endereco.bairro.equals(localBairro)) {
							contadorLoja++;
							
							System.out.println("------------------------------\n");
							
							if(empresas.get(i) instanceof Loja) {
								System.out.print("Loja: ");
							} else System.out.print("Fornecedor: ");
							
							System.out.println(empresas.get(i).nome + "\nProdutos:");
							if(empresas.get(i).estoque.produtosEmEstoque.isEmpty()) {
								System.out.println("Não consta produtos nesta loja!");
							} else
								
								for(int j=0; j<empresas.get(i).estoque.produtosEmEstoque.size();j++) {
									System.out.println(empresas.get(i).estoque.produtosEmEstoque.get(j).nomeProduto + 
											"- Valor unidade: R$" + empresas.get(i).estoque.produtosEmEstoque.get(j).precoVenda);
							}
						} 
					}
					
					System.out.println("------------------------------\n");
					
					if(contadorLoja==0) System.out.println("Não existe lojas na sua localização!");
					
					System.out.println("\n\nPressione ENTER para continuar");
					
					String time = input.nextLine();//mostrar o gráfico
					
					break;
				case "4":
					/*
					 * Aqui o usuário poderá modificar todos os dados do programa
					 */

					System.out.println("\n-------MUDA DADOS--------");
					System.out.println("Qual o nome da empresa?");
					nome = input.nextLine();
					
					//Procurando se a empresa existe
					aux = -1;
					for (i = 0; i < empresas.size(); i++) {
						if (nome.equals(empresas.get(i).nome)) {
							aux = i;
							break;
						}
					}

					if (aux == i) {

						System.out.println("1 - Mudar dados principais\n" +
								"2 - Mudar o endereço\n" +
								"3 - Mudar dados do estoque");

						escolha = input.next();
						input = new Scanner(System.in);
						if (escolha.equals("1")) {

							// Aqui se troca os dados principais da Empresa
							System.out.println("Trocar nome: (Atual = " + empresas.get(i).nome + ")");
							nome = input.nextLine();
							System.out.println("Trocar telefone: (Atual = " + empresas.get(i).telefone + ")");
							telefone = input.next();
							System.out.println("Trocar CNPJ: (Atual = " + empresas.get(i).cnpj + ")");
							cnpj = input.next();
							input = new Scanner(System.in);
							System.out.println("Trocar a razão social: (Atual = " + empresas.get(i).razaoSocial + ")");
							razaoSocial = input.nextLine();

							empresas.get(i).alteracaoCadastros(nome, telefone, cnpj, razaoSocial);
						} else if (escolha.equals("2")) {

							// Aqui se troca o endereço da empresa
							System.out.println("Trocar estado: (Atual = " + empresas.get(i).endereco.estado + ")");		
							estado = input.nextLine();
							System.out.println("Trocar cidade: (Atual = " + empresas.get(i).endereco.cidade + ")");		
							cidade = input.nextLine();
							System.out.println("Trocar bairro: (Atual = " + empresas.get(i).endereco.bairro + ")");		
							bairro = input.nextLine();

							empresas.get(i).endereco.alteracaoCadastros(bairro, cidade, estado);

						} else if (escolha.equals("3")) {
							// Aqui troca a capacidade máxima do estoque e os dados do produto
				
							System.out.println("1 - Mudar Capacidade total\n" +
									"2 - Alterar dados de um produto");

							escolha = input.next();
							input = new Scanner(System.in);
							
							if (escolha.equals("1")) {
							
								int total=0;
								for(int s=0;s<empresas.get(i).estoque.produtosEmEstoque.size(); s++) {
									total+=empresas.get(i).estoque.produtosEmEstoque.get(s).getQuatidadeEstoque();
								}
								
								System.out.println("Estoque em uso: "  +total
										+ "\nTrocar a capacidade máxima: (Atual = " + empresas.get(i).estoque.getCapacidadeMax() + ")");	
								capacidadeMax = input.nextInt();
								
								if(total>capacidadeMax) {
									System.out.println("Capicidade inferior ao nível de estoque!");
									break;
								} else {
									empresas.get(i).estoque.setCapacidadeMax(capacidadeMax);
									System.out.println("Capicidade alterada!");
								}
							
							}else if (escolha.equals("2")) {
							
								if(empresas.get(i).estoque.produtosEmEstoque.isEmpty()) {
									
									System.out.println("Nenhum pruduto listado na empresa --" + empresas.get(i).nome + "--");
									
								}else {
									
									System.out.println("---LISTA DE PRODUTOS---");
									for(int x=0; x<empresas.get(i).estoque.produtosEmEstoque.size(); x++) {
										System.out.println(x + "-" + empresas.get(i).estoque.produtosEmEstoque.get(x).nomeProduto);
									}
									
									System.out.println("Escolha o número refente ao produto a ser alterado");
									int escolhaProduto = input.nextInt();
									
									empresas.get(i).estoque.produtosEmEstoque.get(escolhaProduto).setQuatidadeEstoque(0);
									
									System.out.println("Novos dados:");
									
									System.out.println("Qual a quantidade do produto ?");
									int quantidadeEstoque = input.nextInt();
									int total=0;
									total=quantidadeEstoque;
									
									//Calculando estoque total antes de adicionar produto
									for(int s=0;s<empresas.get(i).estoque.produtosEmEstoque.size(); s++) {
										total+=empresas.get(i).estoque.produtosEmEstoque.get(s).getQuatidadeEstoque();
									}
									
									if (empresas.get(i).estoque.verificaEstoque(total)==false) {
										
										System.out.println("O estoque da loja -" + empresas.get(i).nome + "- está cheio!");
										break;
									} else {

										input = new Scanner(System.in);
										int novoFornecedor = i;

										System.out.println("Qual o novo preço de custo ?");
										double precoCusto = input.nextDouble();
										System.out.println("Qual o novo preço de venda ?");
										double precoVenda = input.nextDouble();
										input = new Scanner(System.in);
										System.out.println("Qual a nova data da compra ? (formatação: XX/YY/ZZZZ)");
										String dataUltimaCompraEstoque = input.nextLine();

										// Apenas lojas podem trocar fornecedor
										if (empresas.get(i) instanceof Loja) {
											System.out.println("Qual o novo Fornecedor ?");

											for(int j=0; j<empresas.size(); j++) {
												if(empresas.get(j) instanceof Fornecedor) {
													System.out.println(j+" - " + empresas.get(j).nome);
												} else continue;
											}
											novoFornecedor = input.nextInt();
										}
										empresas.get(i).estoque.produtosEmEstoque.get(escolhaProduto).alteracaoCadastrosProduto(empresas.get(i).estoque.produtosEmEstoque.get(escolhaProduto).nomeProduto,
												quantidadeEstoque, precoCusto, precoVenda, dataUltimaCompraEstoque, empresas.get(novoFornecedor));

									}
								}
								
							}

						} else {
							System.out.println("Dado errado");
						}
					} else {
						System.out.println("Não existe essa empresa");
					}
					break;
					
				case "5":
					if (empresas.size() == 0) {
						System.out.println("Não existem empresas");	
					} else {
						System.out.println("-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
						for (i = 0; i < empresas.size(); i++) {
							System.out.println(empresas.get(i));
							for (int j = 0; j < empresas.get(i).estoque.produtosEmEstoque.size(); j++) {
								System.out.print(empresas.get(i).estoque.produtosEmEstoque.get(j));
								if (empresas.get(i) instanceof Loja) {
									System.out.print(" - Fornecedora: " + empresas.get(i).estoque.produtosEmEstoque.get(j).fornecedora.nome);
								}
								System.out.println();
							}
							System.out.println("-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
						}
	
					}
				break;
				case "6":
					/*
					 * Aqui o usario poderá remover uma empresa ou produto
					 */

					System.out.println("\n-------REMOÇÃO--------");
					System.out.println("Qual o nome da empresa?");
					nome = input.nextLine();

					//Procurando se a empresa existe
					aux = -1;
					for (i = 0; i < empresas.size(); i++) {
						if (nome.equals(empresas.get(i).nome)) {
							aux = i;
							break;
						}
					}

					if (aux == i) {
						System.out.println("1 - Remover empresa\n" + 
								"2 - Remover um produto");
						escolha = input.next();
						if (escolha.equals("1")) {
							// Aqui remove uma empresa	
							System.out.println("Tem certeza que quer remover a empresa " + empresas.get(i).nome + "? (\"s\" para sim)");
							escolha = input.next();
							if (escolha.equals("s")) {
								empresas.remove(i);
								 System.out.println("Empresa Removida!");
							} 
						} else if (escolha.equals("2")) {
							// Aqui remove um produto de uma empresa
							
							if(empresas.get(i).estoque.produtosEmEstoque.isEmpty()) {
								
								System.out.println("Nenhum pruduto listado na empresa --" + empresas.get(i).nome + "--");
								
							}else {
								
								System.out.println("---LISTA DE PRODUTOS---");
								for(int x=0; x<empresas.get(i).estoque.produtosEmEstoque.size(); x++) {
									System.out.println(x + "-" + empresas.get(i).estoque.produtosEmEstoque.get(x).nomeProduto);
								}
								
								System.out.println("Escolha o número refente ao produto a ser removido");
								int escolhaProduto = input.nextInt();
								
								System.out.println("Tem certeza que deseja remover o produto " + escolhaProduto + " da empresa -"
										+ empresas.get(i).nome +"- ? (\"s\" para sim)");
								escolha = input.next();
								
								if (escolha.equals("s")) {
									 empresas.get(i).estoque.produtosEmEstoque.remove(escolhaProduto);
									 System.out.println("Produto Removido!");
								}
								
							}
							
						} else {
							System.out.println("Não existe essa opção");
						}
					} else {
						System.out.println("Não existe essa empresa");
					}

					break;
					
				case "7":
					
					System.out.println("------GRÁFICO DE CAPACIDADE DE ESTOQUE------");
					//Aqui é aplicada a funçao de imprimir o gráfico de ocupação do estoque
					int verificador=0;
					for(i=0;i<empresas.size();i++) {
						if(empresas.get(i) instanceof Loja) {
							System.out.println(i+" - "+ "Loja " +empresas.get(i).nome);
							
						} else System.out.println(i+" - "+ "Fornecedor " +empresas.get(i).nome);
								
					}
					
					System.out.println("Escolha o número referente a empresa para verificar estoque");
					int posicao = input.nextInt();
					input = new Scanner(System.in);
					
					for(int y=0; y<i; y++) {
						if(y==posicao) {
							verificador++;
							break;
						} 
					}
					
					if(verificador!=1) {
						System.out.println("Número inválido");
						break;
					}
					
					int quantidade=0, soma=0;
					int[] emEstoque= new int[100];
					String[] produtos= new String[100];
					
					for(quantidade=0; quantidade<empresas.get(posicao).estoque.produtosEmEstoque.size(); quantidade++) {
						soma+=empresas.get(posicao).estoque.produtosEmEstoque.get(quantidade).getQuatidadeEstoque();
						produtos[quantidade]=empresas.get(posicao).estoque.produtosEmEstoque.get(quantidade).nomeProduto;
						emEstoque[quantidade]=empresas.get(posicao).estoque.produtosEmEstoque.get(quantidade).getQuatidadeEstoque();
						continue;
					}
					
					emEstoque[quantidade]=empresas.get(posicao).estoque.getCapacidadeMax()-soma;
					produtos[quantidade]="Disponível";
				
					new Grafico(empresas.get(posicao).nome, emEstoque,
							produtos,quantidade+1).setVisible(true);
					
					System.out.println("Pressione ENTER para continuar");
					
					time = input.nextLine();//mostrar o gráfico
					
					break;
					
				case "8":
					
					/*
					 * Aqui o programa é fechado e é gravado os dados do programa 
					 */ 
					try {
						
						FileWriter arquivo1 = new FileWriter("Dados.txt");
						PrintWriter pr = new PrintWriter(arquivo1);
						String texto;

						for (i = 0; i < empresas.size(); i++) {
							
							if (empresas.get(i) instanceof Fornecedor) {
								
								pr.print("fornecedor;");	
								
								nome = empresas.get(i).nome;
								telefone = empresas.get(i).telefone;
								cnpj = empresas.get(i).cnpj;
								razaoSocial = empresas.get(i).razaoSocial;
								// Estoque
								capacidadeMax = empresas.get(i).estoque.getCapacidadeMax();	
								// Endereço
								estado = empresas.get(i).endereco.estado;
								cidade = empresas.get(i).endereco.cidade;
								bairro = empresas.get(i).endereco.bairro;

								texto = nome + ";" + telefone + ";" + cnpj + ";" + razaoSocial + ";" +
									capacidadeMax + ";" + estado + ";" + cidade + ";" + bairro + ";";
								pr.println(texto);
								pr.flush();
								
								if(empresas.get(i).estoque.produtosEmEstoque.isEmpty()) {
									continue;
								} else {
									for(int j=0; j<empresas.get(i).estoque.produtosEmEstoque.size(); j++) {	
											pr.print("produto;");
											
											String nomeProduto = empresas.get(i).estoque.produtosEmEstoque.get(j).nomeProduto;
											int quatidadeEstoque = empresas.get(i).estoque.produtosEmEstoque.get(j).getQuatidadeEstoque();
											double precoCusto = empresas.get(i).estoque.produtosEmEstoque.get(j).getPrecoCusto();
											double precoVenda = empresas.get(i).estoque.produtosEmEstoque.get(j).precoVenda;
											String dataUltimaCompraEstoque = empresas.get(i).estoque.produtosEmEstoque.get(j).getDataUltimaCompraEstoque();
											// Fornecedor
											String forenecedora = empresas.get(i).estoque.produtosEmEstoque.get(j).fornecedora.nome; //Aqui imprimo o nome do fornecedor
											
											// Texto
											String texto2 = nomeProduto + ";" + quatidadeEstoque + ";" + precoCusto + ";" + 
													precoVenda + ";" + dataUltimaCompraEstoque + ";" + forenecedora + ";";
											pr.println(texto2);
											pr.flush();
									}
								}
							}
						}
						
						for (i = 0; i < empresas.size(); i++) {
							
							if (empresas.get(i) instanceof Loja) {
								
								pr.print("loja;");	
								
								nome = empresas.get(i).nome;
								telefone = empresas.get(i).telefone;
								cnpj = empresas.get(i).cnpj;
								razaoSocial = empresas.get(i).razaoSocial;
								// Estoque
								capacidadeMax = empresas.get(i).estoque.getCapacidadeMax();	
								// Endereço
								estado = empresas.get(i).endereco.estado;
								cidade = empresas.get(i).endereco.cidade;
								bairro = empresas.get(i).endereco.bairro;

								texto = nome + ";" + telefone + ";" + cnpj + ";" + razaoSocial + ";" +
									capacidadeMax + ";" + estado + ";" + cidade + ";" + bairro + ";";
								pr.println(texto);
								pr.flush();
								
								if(empresas.get(i).estoque.produtosEmEstoque.isEmpty()) {
									continue;
								} else {
									for(int j=0; j<empresas.get(i).estoque.produtosEmEstoque.size(); j++) {
										
											pr.print("produto;");
											
											String nomeProduto = empresas.get(i).estoque.produtosEmEstoque.get(j).nomeProduto;
											int quatidadeEstoque = empresas.get(i).estoque.produtosEmEstoque.get(j).getQuatidadeEstoque();
											double precoCusto = empresas.get(i).estoque.produtosEmEstoque.get(j).getPrecoCusto();
											double precoVenda = empresas.get(i).estoque.produtosEmEstoque.get(j).precoVenda;
											String dataUltimaCompraEstoque = empresas.get(i).estoque.produtosEmEstoque.get(j).getDataUltimaCompraEstoque();
											// Fornecedor
											String forenecedora = empresas.get(i).estoque.produtosEmEstoque.get(j).fornecedora.nome; //Aqui imprimo o nome do fornecedor
											
											// Texto
											String texto2 = nomeProduto + ";" + quatidadeEstoque + ";" + precoCusto + ";" + 
													precoVenda + ";" + dataUltimaCompraEstoque + ";" + forenecedora + ";";
											pr.println(texto2);
											pr.flush();
									}
								}
							}
						}
						arquivo1.close();
						pr.close();

					} catch(Exception e){
						throw new IllegalAccessError("Arquivo não existe");
					}
				sair = false;
			}
		}
	}
}
