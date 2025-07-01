package monitorefilosofos;

import java.util.Random;

public class Filosofo implements Runnable{
	
	private String nome;
	private int fome;	  //define por quanto tempo a thread existirá
	private int posicao;  //lugar sentado a mesa
	private int hashiEsq; //Respectivamente, a esquerda e direita do filosofo em questao
	private int hashiDir;
	private Monitor monitor;	

	public Filosofo(String nome, int posicao) {
		this.nome = nome;
		this.posicao = posicao;
		fome = (new Random().nextInt(5)) + 5;
	}
	public String getNome() {
		return nome;
	}
	public int getPosicao() {
		return posicao;
	}
	
	
	public int getHashiEsq() {
		return hashiEsq;
	}
	public void setHashiEsq(int hashiEsq) {
		this.hashiEsq = hashiEsq;
	}
	
	
	public int getHashiDir() {
		return hashiDir;
	}
	public void setHashiDir(int hashiDir) {
		this.hashiDir = hashiDir;
	}
	
	
	public void setMonitor(Monitor monitor) {
		this.monitor = monitor;
	}

	//get
	private void comer() throws InterruptedException{
		System.out.printf("%s esta comendo.%n", nome);
		fome--;
		Thread.sleep(new Random().nextInt(500));
	}
	
	//put
	private void pensar() throws InterruptedException{
		System.out.printf("%s esta pensando.%n", nome);
		Thread.sleep(new Random().nextInt(500));
	}
	

	@Override
	public void run() {
		try {
			while(true) {
				pensar();
				monitor.pegarHashiESQ(posicao);
				monitor.pegarHashiDIR(posicao);
				comer();
				monitor.soltarHashiESQ(posicao);
				monitor.soltarHashiDIR(posicao);
				if (fome == 0) {
					Thread.currentThread().interrupt();
					System.out.printf("%s esta plenamente satisfeito e lambeu o prato.\n",nome);
				}	
			}
		}
		catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
	}
	
}
