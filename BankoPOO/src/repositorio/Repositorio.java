package repositorio;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Scanner;

import modelo.Conta;
import modelo.ContaEspecial;
import modelo.Correntista;


public class Repositorio {
	private ArrayList<Conta> contas = new ArrayList<>();
	private ArrayList<Correntista> correntistas  = new ArrayList<>(); 

	public Repositorio() {
		carregarObjetos();
	}
	public void adicionarConta(Conta c)	{
		contas.add(c);
	}

	public void remover(Conta c)	{
		contas.remove(c);
	}

	public Conta localizarConta(int id)	{
		for(Conta c : contas)
			if(c.getId() == id)
				return c;
		return null;
	}

	public void adicionarCorrentista(Correntista c)	{
		correntistas.add(c);
	}


	public Correntista localizarCorrentista(String cpf)	{
		for(Correntista c : correntistas)
			if(c.getCpf().equals(cpf))
				return c;
		return null;
	}
	public Correntista localizarCorrentistaNome(String nome)	{
		for(Correntista c : correntistas)
			if(c.getNome().equals(nome))
				return c;
		return null;
	}

	public ArrayList<Correntista> getCorrentistas() {
		return correntistas;
	}
	
	public ArrayList<Conta> getContas() 	{
		return contas;
	}

	public int getTotalContas()	{
		return contas.size();
	}

	public int getTotalCorrentistas()	{
		return correntistas.size();
	}

	public int gerarIdConta() {
		if (contas.isEmpty())
			return 1;
		else {
			Conta ultimo = contas.get(contas.size()-1);
			return ultimo.getId() + 1;
		}
	}
	
	
	
	
	public void carregarObjetos()  	{
		// carregar para o repositorio os objetos dos arquivos csv
		try {
			//caso os arquivos nao existam, serao criados vazios
			File f1 = new File( new File(".\\correntistas.csv").getCanonicalPath() ) ; 
			File f2 = new File( new File(".\\contas.csv").getCanonicalPath() ) ; 
			if (!f1.exists() || !f2.exists() ) {
				//System.out.println("criando arquivo .csv vazio");
				FileWriter arquivo1 = new FileWriter(f1); arquivo1.close();
				FileWriter arquivo2 = new FileWriter(f2); arquivo2.close();
				return;
			}
		}
		catch(Exception ex)		{
			throw new RuntimeException("criacao dos arquivos vazios:"+ex.getMessage());
		}

		String linha;	
		String[] partes;	
		Correntista co;
		Conta c;

		try	{
			String cpf, nome, senha;
			File f = new File( new File(".\\correntistas.csv").getCanonicalPath() )  ;
			Scanner arquivo1 = new Scanner(f);	 
			while(arquivo1.hasNextLine()) 	{
				linha = arquivo1.nextLine().trim();		
				partes = linha.split(";");	
				//System.out.println(Arrays.toString(partes));
				cpf = partes[0];
				nome = partes[1];
				senha = partes[2];
				co = new Correntista(cpf, nome, senha);
				this.adicionarCorrentista(co);
			} 
			arquivo1.close();
		}
		catch(Exception ex)		{
			throw new RuntimeException("leitura arquivo de correntistas:"+ex.getMessage());
		}

		try	{
			String tipo, id, data, saldo, limite = null, listaCorrentistas;
			File f = new File( new File(".\\contas.csv").getCanonicalPath())  ;
			Scanner arquivo2 = new Scanner(f);	 
			while(arquivo2.hasNextLine()) 	{
				linha = arquivo2.nextLine().trim();	
				partes = linha.split(";");
				//System.out.println(Arrays.toString(partes));
				if (partes.length == 6) {
					tipo = partes[0];
					id = partes[1];
					data = partes[2];
					saldo = partes[3];
					limite = partes[4];
					listaCorrentistas = partes[5];	
				}
				else {
					tipo = partes[0];
					id = partes[1];
					data = partes[2];
					saldo = partes[3];
					listaCorrentistas = partes[4];	
				}
				
				String[] lista = listaCorrentistas.replace("[", "").replace("]", "").replace(" ", "").split(",");
				if(tipo.equals("CONTA")) {
					c = new Conta(Integer.parseInt(id), data);
					this.adicionarConta(c);
					c.setSaldo(Double.parseDouble(saldo));
					for (int i=0; i < lista.length; i++){
						if (i == 0) { 
							co = localizarCorrentista(lista[i]);
							c.adicionar(co);
							co.adicionar(c);
							co.setTitular(true);
							}
						/*else {
							co = localizarCorrentista(lista[i]);
							c.adicionar(co);
							co.adicionar(c);
						}*/
						
					}
					
				}
				else {
					
					c = new ContaEspecial(Integer.parseInt(id), data, Double.parseDouble(limite));
					this.adicionarConta(c);
					c.setSaldo(Double.parseDouble(saldo));
					for (int i=0; i < lista.length; i++){
						if (i == 0) { 
							co = localizarCorrentista(lista[i]);
							c.adicionar(co);
							co.adicionar(c);
							co.setTitular(true);
							}
						/*else {
							co = localizarCorrentista(lista[i]);
							c.adicionar(co);
							co.adicionar(c);
						}*/
					}
						
				}
				
			}
			
			arquivo2.close();
		}
		catch(Exception ex)		{
			throw new RuntimeException("leitura arquivo de contas:"+ex.getMessage());
		}
		
		try	{
			String id, listaCorrentistas;
			File f = new File( new File(".\\contas.csv").getCanonicalPath())  ;
			Scanner arquivo2 = new Scanner(f);	 
			while(arquivo2.hasNextLine()) 	{
				linha = arquivo2.nextLine().trim();	
				partes = linha.split(";");
				if (partes.length == 6) {
					id = partes[1];
					listaCorrentistas = partes[5];	
				}
				else {
					id = partes[1];
					listaCorrentistas = partes[4];	
				}
				String[] lista = listaCorrentistas.replace("[", "").replace("]", "").replace(" ", "").split(",");
				
				for (int i=0; i < lista.length; i++){
					if (i != 0) {
					co = localizarCorrentista(lista[i]);
					c = localizarConta(Integer.parseInt(id));
					c.adicionar(co);
					co.adicionar(c);
							}	
					}
					
				}
			
			arquivo2.close();
		}
		catch(Exception ex)		{
			throw new RuntimeException("leitura arquivo de contas:"+ex.getMessage());
		}
	}

	//--------------------------------------------------------------------
	public void	salvarObjetos()  {
		//gravar nos arquivos csv os objetos que est�o no reposit�rio
		try	{
			File f = new File( new File(".\\correntistas.csv").getCanonicalPath())  ;
			FileWriter arquivo1 = new FileWriter(f); 
			for(Correntista co : correntistas) 	{
				arquivo1.write(co.getCpf()+";"+co.getNome()+";"+co.getSenha() + "\n");	
			} 
			arquivo1.close();
		}
		catch(Exception e){
			throw new RuntimeException("problema na cria��o do arquivo correntistas "+e.getMessage());
		}

		try	{
			File f = new File( new File(".\\contas.csv").getCanonicalPath())  ;
			FileWriter arquivo2 = new FileWriter(f) ; 
			ArrayList<String> lista ;
			for(Conta c : contas) {
				//montar uma lista com os id dos eventos do participante
				lista = new ArrayList<>();
				for(Correntista co : c.getCorrentistas()) {
					lista.add(co.getCpf());
				}
				

				if(c instanceof ContaEspecial c1 )
					arquivo2.write("CONTAESPECIAL;" + c1.getId() + ";" + c1.getData() +";" 
							+ c1.getSaldo() +";"+ c1.getLimite() +";"+ lista +"\n");	
				else
					arquivo2.write("CONTA;"+ c.getId() + ";" + c.getData() +";" 
							+ c.getSaldo() +";"+ lista +"\n");	

			} 
			arquivo2.close();
		}
		catch (Exception e) {
			throw new RuntimeException("problema na cria��o do arquivo contas "+e.getMessage());
		}

	}
}