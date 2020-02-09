package bar.dos.filósofos;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class BarDosFilósofos {

public static int numerofilosofos;
public static Filosofo Filosofos[];

  public static void main(String argv[]) throws FileNotFoundException {

String fileName = "/home/matheus_araujo/NetBeansProjects/Bar dos Filósofos/src/bar/dos/filósofos/grafo1.txt";
//System.out.print("Enter filename: ");
//Scanner scanIn = new Scanner(System.in);
//String fileName =  scanIn.nextLine();
File file = new File(fileName); 

    Scanner scanner = new Scanner(file);
    int N = scanner.nextInt();
    scanner.nextLine();

    int[][] adjMat = new int[N][N];
    ArrayList<Garrafa> garrafas = new ArrayList<>();
    Boolean b = false;
      
    for(int i = 0; i < N; i++){
        String[] lines = scanner.nextLine().split(",");
        for (int j=0; j < lines.length; j++) {
           if(Integer.parseInt(lines[j]) > 0){ 
               Garrafa g = new Garrafa(String.valueOf(i) + String.valueOf(j));
           for(Garrafa garr : garrafas) { if(garr.id.contains(String.valueOf(j) + String.valueOf(i))) { b = true; } }
               if (!b){
               garrafas.add(g);
              }
          b = false;     
            }
        adjMat[i][j] = Integer.parseInt(lines[j]);
        }
    }
    
  scanner.close();
    
  numerofilosofos = N;
    Filosofos = new Filosofo[numerofilosofos];
  //Filosofos = new Filosofo[numerofilosofos];
//    System.out.println("Garrafas: " + garrafas.size()); 
//  System.out.println("Garrafas: " + Filosofos.length); 
//  
//  for(int k = 0; k < garrafas.size(); k++){  
//  System.out.println(garrafas.get(k).id);
//  }
  
for(int i = 0; i < numerofilosofos; i++){  
 ArrayList<Garrafa> sublistagarrafas = new ArrayList<>();
 for(int k = 0; k < garrafas.size(); k++){  
    if(garrafas.get(k).id.contains(Integer.toString(i)) && garrafas.get(k).id.length() < 3){
    sublistagarrafas.add(garrafas.get(k));
    } else if (garrafas.get(k).id.contains(Integer.toString(i)) && garrafas.get(k).id.length() >= 3 && Integer.toString(i).length() >= 2){
     sublistagarrafas.add(garrafas.get(k));    
    }
 }

 //System.out.println("Sub: " + sublistagarrafas.size());
 Filosofos[i] = new Filosofo(i, sublistagarrafas.size());
 Filosofos[i].setGarrafas(sublistagarrafas);
}

    for (int i = 0; i < numerofilosofos; i++) {  
      Filosofos[i].start();
    }

    while (true) {
      try {
        // sleep 1 sec
        //Thread.sleep(1000);

        // check for deadlock
        boolean deadlock = true;
        for (Garrafa f : garrafas) {
          if (f.taLivre()) {
            deadlock = false;
            break;
          }
        }
//        if (deadlock) {
//          Thread.sleep(1000);
//          System.out.println("Deadlock");
//          break;
//        }
        int c = 0;
         for (int i = 0; i < numerofilosofos; i++) {  
            if(Filosofos[i].isAlive()){
              c++;  
            }
        }
         if (c == 0) { break; }
      }
      catch (Exception e) {
        e.printStackTrace(System.out);
      }
    }

    System.out.println("Fim");
    System.exit(0);
  }
 }

