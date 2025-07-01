package monitorefilosofos;

public class Jantar {
	public static void main(String[] args) {
		Hashi [] mesa = new Hashi[5];
		Filosofo [] cadeira = new Filosofo[5];
		
		cadeira[0] = new Filosofo("Aristoteles", 0);
		cadeira[1] = new Filosofo("Nietzsche", 1);
		cadeira[2] = new Filosofo("Rosseau", 2);
		cadeira[3] = new Filosofo("Sartre", 3);
		cadeira[4] = new Filosofo("Herbert Spencer", 4);
		
		//Colocando a referencia de cada hashi em relacao a cada um filosofo
		/*Ex.: O filosofo na cadeira 0 esta a esquerda do hashi 1 e a direita do hashi 2,
		 *     o da cadeira 1 esta a esquerda do hashi dois e a direita do hashi 3 e 
		 *     o que esta na cadeira 4 esta a esquerda do hashi 5 e a direita do hashi 1
		*/
		for (int i = 0; i < 5; i++) {
			mesa[i] = new Hashi(i+1);
			if (i > 0) {
				cadeira[i-1].setHashiEsq(mesa[i-1].getId());
				cadeira[i-1].setHashiDir(mesa[i].getId());
				if (i == 4) {
					cadeira[i].setHashiEsq(mesa[i].getId());
					cadeira[i].setHashiDir(mesa[0].getId());
				}
			}
		}
		System.out.printf("A mesa foi arrumada.%n%n");
		
		Thread t;
		Monitor m = new Monitor(cadeira);
		for (int i = 0; i < 5; i++) {
			System.out.printf("%s se sentou para jantar. %nEsta sentado em frente dos hashis %d e %d.%n%n", cadeira[i].getNome(),cadeira[i].getHashiEsq(),cadeira[i].getHashiDir());
			cadeira[i].setMonitor(m);
			t = new Thread(cadeira[i]);
			t.start();
			
		}
	}	
}


