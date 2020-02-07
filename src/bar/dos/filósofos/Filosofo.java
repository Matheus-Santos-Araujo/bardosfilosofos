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

    int qtdgarraf;
    if(N > 2){  
    qtdgarraf = ThreadLocalRandom.current().nextInt(2, N);
    } else { qtdgarraf = 2; }

  //System.out.println("Size: " + garrafas.size());
    //try{
      while(true){
        for(int i = 0; i < qtdgarraf; i++){   
          switch(state) {
              case TRANQUILO:
                  think();
                  garrafas.get(i).pegar();
                  state = COMSEDE;
                  break;
              case COMSEDE:
                  //mutex.release();
                  //garrafas.get(i).soltar();
                  garrafas.get(i).pegar();
                  //self.acquire();
                  state = BEBENDO;
                  break;
              case BEBENDO:
                  drink();
                  garrafas.get(i).pegar();
                  state = TRANQUILO;
                  //test(left());
                  //test(right());
                  garrafas.get(i).soltar();
                  break;
          }     
      }
    }
 // } catch(InterruptedException e) {}    
 } 
      
    void drink() {
      try {
        int sleepTime = 1000;
        System.out.println("Filosofo #" + number + " bebeu por " + sleepTime);
        Thread.sleep(sleepTime);
      }
      catch (Exception e) {
        e.printStackTrace(System.out);
      }
    }
    
    void think() {
      try {
        int sleepTime = ThreadLocalRandom.current().nextInt(0, 2000);
        System.out.println("Filosofo #" + number + " pensou por " + sleepTime);
        Thread.sleep(sleepTime);
      }
      catch (Exception e) {
        e.printStackTrace(System.out);
      }
    }

//      static private void test(Filosofo p) {
//        for(int k = 0; k < Filosofos.lenght; k++){
//            if(p.garrafas.contains(idk)){
//              if (Filosofo[k].state != BEBENDO && p.state == COMSEDE) {
//                  p.state = BEBENDO;
//            }
//         }      
//      }
    
    public ArrayList<Garrafa> getGarrafas() {
        return garrafas;
    }

    public void setGarrafas(ArrayList<Garrafa> garrafas) {
        this.garrafas = garrafas;
    }
    
    
  }
