package bar.dos.filósofos;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class Filosofo extends Thread {
    private static final int TRANQUILO = 0;
    private static final int COMSEDE = 1;
    private static final int BEBENDO = 2;
    public int state = TRANQUILO;  
    public int number;
    public int N = 0;
    public ArrayList<Garrafa> garrafas = new ArrayList<>();

    Filosofo(int num, int n) {
      this.number = num;
      this.N = n;
    }

    public void run(){
      System.out.println("Oi! Sou o Filosofo #" + number);

//      while (true) {
//      switch (state) {    
//      for(int i = 0; i < garrafas.size(); i++){   
//        garrafas.get(i).pegar();
//        System.out.println("Filosofo #" + number + " pega a left Garrafa.");
//        drink();
//        garrafas.get(i).soltar();
//        System.out.println("Filosofo #" + number + " solta a left Garrafa.");
//        state = 2;
//       }
//      }
//    }
//   }

  int qtdgarraf = ThreadLocalRandom.current().nextInt(2, N);
    for(int i = 0; i < qtdgarraf; i++){   
     while(true){
        switch(state) {
         case TRANQUILO: 
            think();
            garrafas.get(i).pegar();
            state = COMSEDE; 
            break;
          case COMSEDE:
            // aquire both forks, i.e. only eat if no neighbor is eating
            // otherwise wait
            //mutex.release();
            garrafas.get(i).pegar();
            state = BEBENDO;
            break;
          case BEBENDO:
            drink();
            garrafas.get(i).pegar();
            state = TRANQUILO;
            // if a hungry neighbor can now eat, nudge the neighbor.
            //test(left());  
            //test(right());
            garrafas.get(i).soltar();
            break;
        }     
      }     
    }
  } 
      
    void drink() {
      try {
        int sleepTime = 1000;
        System.out.println("Filosofo #" + " bebeu por " + sleepTime);
        Thread.sleep(sleepTime);
      }
      catch (Exception e) {
        e.printStackTrace(System.out);
      }
    }
    
    void think() {
      try {
        int sleepTime = ThreadLocalRandom.current().nextInt(0, 2000);
        System.out.println("Filosofo #" + " bebeu por " + sleepTime);
        Thread.sleep(sleepTime);
      }
      catch (Exception e) {
        e.printStackTrace(System.out);
      }
    }

    public ArrayList<Garrafa> getGarrafas() {
        return garrafas;
    }

    public void setGarrafas(ArrayList<Garrafa> garrafas) {
        this.garrafas = garrafas;
    }
    
    
  }
