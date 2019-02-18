import java.io.*;

public class Query{
  public static void readQueries(File file){
    try{
      BufferedReader br = new BufferedReader(new FileReader(file));
      String line = br.readLine();
      while(line != null){
        System.out.println(line);
        line = br.readLine();
      }
    } catch(Exception e){
      System.out.println("Error in readQueries");
      System.out.println(e);
    }
  }
}
