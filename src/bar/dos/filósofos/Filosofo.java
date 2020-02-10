package bar.dos.filósofos;
import java.util.ArrayList;
import static bar.dos.filósofos.BarDosFilósofos.Filosofos;
import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadLocalRandom;

public class Filosofo extends Thread {
    private static final int TRANQUILO = 0;
    private static final int COMSEDE = 1;
    private static final int BEBENDO = 2;
    public int state = TRANQUILO;  
    public int number;
    public int N = 0;
    public int p = 0;
    public boolean run = true;
    public int epocas = 0;
    public ArrayList<Garrafa> garrafas = new ArrayList<>();
    private final Semaphore self;

    Filosofo(int num, int n) {
      this.number = num;
      self = new Semaphore(0);
      this.N = n;
    }
    
    public void run(){
    //System.out.println("Oi! Sou o Filosofo #" + number);

    int qtdgarraf = 2;
    if(N > 2){  
    qtdgarraf = ThreadLocalRandom.current().nextInt(2, N);
    }

  //System.out.println("Size: " + garrafas.size());
    //try{
      while(true){
      //for(int i = 0; i < qtdgarraf; i++){   
       printState();
       //int i = 0;
          switch(state) {
              case TRANQUILO:
                  think();
                  //for(int i = 0; i < qtdgarraf; i++){  garrafas.get(i).pegar(number); }
                  state = COMSEDE;
                  break;
              case COMSEDE:
                  test(this);
                  for(int i = 0; i < qtdgarraf; i++){ if(garrafas.get(i).taLivre()){ garrafas.get(i).pegar(number); p++;}}
                  //garrafas.get(i).pegar(number);          
//              try {
//                  self.acquire();
//              } catch (InterruptedException ex) {
//                  Logger.getLogger(Filosofo.class.getName()).log(Level.SEVERE, null, ex);
//              }
                  if (p == qtdgarraf){
                      System.out.println(p + " e " + qtdgarraf);
                      state = BEBENDO;
                  } else { state = COMSEDE; run = false; dorme(); }
                  break;
              case BEBENDO:
                  p = 0;
                  //for(int i = 0; i < qtdgarraf; i++){  garrafas.get(i).pegar(number); }
                  drink();
                  int vizinhos[] = vizinhos(this);
//                  for(int v = 0; v < vizinhos.length; v++){ test(Filosofos[vizinhos[v]]); }
                  for(int i = 0; i < qtdgarraf; i++){  garrafas.get(i).soltar(number); }
                  for(int v = 0; v < vizinhos.length; v++){ Filosofos[vizinhos[v]].acorda(); }
                  state = TRANQUILO;
                  epocas++;
                  break;
          //}     
      }
        if(epocas >= 6) { System.out.println("###### O filosofo " + number + " terminou ######"); this.stop(); }    
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
    
     private void printState() {
     if(state == 0){    
      System.out.println("Filosofo " + number + " esta tranquilo");
     } else if (state == 1) { System.out.println("Filosofo " + number + " com sede"); } 
     else if (state == 2) { System.out.println("Filosofo " + number + " esta bebendo"); }
     }

  private void test(Filosofo p) {
        for(int k = 0; k < Filosofos.length; k++){
          for(int j = 0; j < garrafas.size(); j++){
              
            if(garrafas.get(j).id.contains(String.valueOf(k))){
              if(number != k){  
               if (Filosofos[k].state != BEBENDO && p.state == COMSEDE) {
                  p.state = BEBENDO;
                  //p.self.release();
               }
            }
           }
         }      
       }
    }
  int i = 0;
     int[] vizinhos(Filosofo p) {
       int vizinhos[] = new int[garrafas.size()]; 
        for(int k = 0; k < Filosofos.length; k++){
          for(int j = 0; j < garrafas.size(); j++){              
            if(garrafas.get(j).id.contains(String.valueOf(k))){
              if(number != k && garrafas.get(j).id.length() < 3){
                  System.out.println(k + " de " + garrafas.get(j).id + " e vizinho de " + number);
                  vizinhos[i] = k;
                  i++;
            } else if(number != k && garrafas.get(j).id.length() >= 3 && Integer.toString(k).length() >= 2) {
                System.out.println(k + " de " + garrafas.get(j).id + " e vizinho de " + number);
                vizinhos[i] = k;
                i++;
            }
           }
         }      
       }
       i = 0; 
       return vizinhos; 
    }  
     
     protected synchronized void dorme() {
    while (!run) {
    try { wait(); }
    catch (InterruptedException ex) {}
    }
}
     
     protected synchronized void acorda() {
        run = true;
        notifyAll();
    }
    
    public ArrayList<Garrafa> getGarrafas() {
        return garrafas;
    }

    public void setGarrafas(ArrayList<Garrafa> garrafas) {
        this.garrafas = garrafas;
    }
    
    
  }