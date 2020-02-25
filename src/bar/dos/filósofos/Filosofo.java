package bar.dos.filósofos;
import java.util.ArrayList;
import static bar.dos.filósofos.BarDosFilósofos.Filosofos;
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
    public long tempomediotranquilo = 0;    
    public long tempomediocomsede = 0;    
    public long tempomediobebendo = 0;    
    public int qtddormidas = 0;
    public ArrayList<Garrafa> garrafas = new ArrayList<>();

    Filosofo(int num, int n) {
      this.number = num;
      this.N = n;
    }
    
    public void run(){

    int qtdgarraf = 2;
    if(N > 2){  
    qtdgarraf = ThreadLocalRandom.current().nextInt(2, N);
    }

      while(true){
       printState();
          switch(state) {
              case TRANQUILO:
                  long startTimetran = System.nanoTime();
                  think();
                  state = COMSEDE;
                  long endTimetran = System.nanoTime();
                  tempomediotranquilo += endTimetran - startTimetran;
                  break;
              case COMSEDE:
                  long startTimecom = System.nanoTime();
                  test(this);
                  for(int i = 0; i < qtdgarraf; i++){ if(garrafas.get(i).taLivre()){ garrafas.get(i).pegar(number); p++;}}

                  if (p == qtdgarraf){
                      state = BEBENDO;
                  } else { state = COMSEDE; run = false; dorme(); }
                  long endTimecom = System.nanoTime();
                  tempomediocomsede += endTimecom - startTimecom;
                  break;
              case BEBENDO:
                  long startTimebeb = System.nanoTime();
                  p = 0;
                  drink();
                  int vizinhos[] = vizinhos(this);
                  for(int i = 0; i < qtdgarraf; i++){  garrafas.get(i).soltar(number); }
                  for(int v = 0; v < vizinhos.length; v++){ Filosofos[vizinhos[v]].acorda(); }
                  state = TRANQUILO;
                  epocas++;
                  long endTimebeb = System.nanoTime();
                  tempomediobebendo += endTimebeb - startTimebeb;
                  break;
          //}     
      }
        if(epocas >= 6) { 
            System.out.println("###### O filosofo " + number + " terminou ######"); 
            double tmtran = (double)tempomediotranquilo/1_000_000_000.0; 
            double tmcom = (double)tempomediocomsede/1_000_000_000.0;
            double tmbeb = (double)tempomediobebendo/1_000_000_000.0;
            System.out.println("Filosofo " + number + " ficou tranquilo em media " + tmtran/epocas + "s");
            System.out.println("Filosofo " + number + " ficou com sede em media " + tmcom/epocas + "s");
            System.out.println("Filosofo " + number + " bebeu em media " + tmbeb/epocas + "s");
            this.stop(); 
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
    
     private void printState() {
        switch (state) {
            case 0:
                System.out.println("Filosofo " + number + " esta tranquilo");
                break;
            case 1:
                System.out.println("Filosofo " + number + " com sede");
                break;
            case 2:
                System.out.println("Filosofo " + number + " esta bebendo");
                break;
            default:
                break;
        }
     }

  private void test(Filosofo p) {
        for(int k = 0; k < Filosofos.length; k++){
          for(int j = 0; j < garrafas.size(); j++){
              
            if(garrafas.get(j).id.contains(String.valueOf(k))){
              if(number != k && garrafas.get(j).id.length() < 3){  
               if (Filosofos[k].state != BEBENDO && p.state == COMSEDE) {
                  p.state = BEBENDO;
               }
                } else if(number != k && garrafas.get(j).id.length() >= 3 && Integer.toString(k).length() >= 2) {
                p.state = BEBENDO;
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
                  vizinhos[i] = k;
                  i++;
            } else if(number != k && garrafas.get(j).id.length() >= 3 && Integer.toString(k).length() >= 2) {
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
     try { wait();  }
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