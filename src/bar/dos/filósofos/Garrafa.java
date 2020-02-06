package bar.dos.filósofos;

import java.util.concurrent.Semaphore;

  public class Garrafa {

    public Semaphore semaforo = new Semaphore(1);
    public String id = null;

    public Garrafa(String id) {
        this.id = id;
    }
   
    void pegar() {
      try {
        semaforo.acquire();
      }
      catch (Exception e) {
        e.printStackTrace(System.out);
      }
    }

    void soltar() {
      semaforo.release();
    }

    boolean taLivre() {
      return semaforo.availablePermits() > 0;
    }

  }