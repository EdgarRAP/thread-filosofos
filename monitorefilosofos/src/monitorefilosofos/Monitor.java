package monitorefilosofos;

import java.util.Random;

public class Monitor {

	private Filosofo [] filosofos;
	private enum status {PENSANDO, FAMINTO, HASHI_E, HASHI_D, COMENDO};
	private status [] estado;
	/*
	 * PENSANDO - Esta parado
	 * FAMINTO - Quer pegar hashi
	 * HASHI_E - Tem apenas o hashi a sua esquerda
	 * HASHI_D - Tem apenas o hashi a sua direita
	 * COMENDO - Tem dois hashis
	 */
	
	public Monitor(Filosofo [] f) {
		filosofos = new Filosofo[f.length];
		estado = new status [f.length];
		for (int i = 0; i< f.length; i++) {
			filosofos[i] = f[i];
			estado[i] = status.PENSANDO;
		}
	}
	
	private boolean hashiOcupado(int pos) {
		//Consideramos os filosofos f1, f2 e f3, com foco no f2

		/* f3 esta a direita de f2
		 * o hashi esquerdo de f3 eh o hashi direito de f2
		 * se f3 esta segurando o hashi esquerdo ou comendo, significa que o hashi direito de f2 esta ocupado
		 */
		if (estado[(pos+1)%estado.length] == status.COMENDO || estado[(pos+1)%estado.length] == status.HASHI_E)
			return true;
		
		/* f1 esta a esquerda de f2
		 * o hashi direito de f1 eh o hashi esquerdo de f2
		 * se f1 esta segurando o hashi direito ou comendo, significa que o hashi esquerdo de f2 esta ocupado
		 */
		else if (estado[(pos+estado.length-1)%estado.length] == status.COMENDO || estado[(pos+estado.length-1)%estado.length] == status.HASHI_D)
			return true;
		
		else //Se f1 ou f3 estiverem pensando ou segurando respectivamente apenas seus hashis esquerdo e direito,
			 //f2 poderá pegar o hashi que precisa
			 
			return false;
	}
	
	public synchronized void pegarHashiESQ(int pos) throws InterruptedException{
		System.out.println(filosofos[pos].getNome() + " esta com fome.");
		estado[pos] = status.FAMINTO;
		notifyAll();
		while(hashiOcupado(pos)){
			wait();
		}
		estado[pos] = status.HASHI_E;
		System.out.println(filosofos[pos].getNome()+" pegou o hashi "+filosofos[pos].getHashiEsq()+" a sua esquerda.");
		Thread.sleep(new Random().nextInt(200)+100);
	}
	public synchronized void pegarHashiDIR(int pos) throws InterruptedException{
		while(hashiOcupado(pos)){
			wait();
		}
			estado[pos] = status.COMENDO;
		System.out.println(filosofos[pos].getNome()+" pegou o hashi "+filosofos[pos].getHashiDir()+" a sua direita.");
		Thread.sleep(new Random().nextInt(200)+100);
	}
	
	
	public synchronized void soltarHashiESQ(int pos) throws InterruptedException{
		System.out.println(filosofos[pos].getNome() + " esta satisfeito.");
		estado[pos] = status.HASHI_D;
		System.out.println(filosofos[pos].getNome()+" colocou na mesa o hashi "+filosofos[pos].getHashiEsq()+" a sua esquerda.");
		notifyAll();
		Thread.sleep(new Random().nextInt(200)+100);
	}
	public synchronized void soltarHashiDIR(int pos) throws InterruptedException{
		estado[pos] = status.PENSANDO;
		System.out.println(filosofos[pos].getNome()+" colocou na mesa o hashi "+filosofos[pos].getHashiDir()+" a sua direita.");
		notifyAll();
		Thread.sleep(new Random().nextInt(200)+100);
	}
}
