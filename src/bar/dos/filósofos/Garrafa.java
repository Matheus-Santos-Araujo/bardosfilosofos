package bar.dos.filósofos;

import java.util.concurrent.Semaphore;

  public class Garrafa {

    public Semaphore semaforo = new Semaphore(1);
    public String id = null;

    public Garrafa(String id) {
        this.id = id;
    }
   
    void pegar(int n) {
      try {
        System.out.println("-------> Filosofo " + n + " pegou a garrafa " + id);
        semaforo.acquire();
      }
      catch (Exception e) {
        e.printStackTrace(System.out);
      }
    }

    void soltar(int n) {
      System.out.println("------->  Filosofo " + n + " soltou a garrafa " + id);
      semaforo.release();
    }

    boolean taLivre() {
      return semaforo.availablePermits() > 0;
    }

  }