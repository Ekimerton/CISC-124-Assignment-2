/*
Name: Ekim Karabey
NetId: 18ebk
Student Number: 20121769
Date: 19/02/2019
*/

import java.io.*;

public class Query{

  /*
  Opens and reads the queries.txt file, and writes the results to answers.txt. Nothing interesting here since all the
  backend was already explained. Just gets the toString of the root, and writes it with some formatting.
  */
  public static void readQueries(File readFile, File writeFile){
    try{
      BufferedReader reader = new BufferedReader(new FileReader(readFile));
      BufferedWriter writer = new BufferedWriter(new FileWriter(writeFile));

      String line = reader.readLine();
      while(line != null && line.indexOf("HP:") != -1){
        int id = Integer.parseInt(line.substring(line.indexOf("HP:") + 3));
        Item entry = HPOExplorer.entryMap.get(id);

        writer.write("[query_answer]");
        writer.newLine();
        writer.write(entry.toString());
        writer.newLine();

        line = reader.readLine();
      }
      writer.flush();
      writer.close();
    } catch(Exception e){
      System.out.println("Error in readQueries");
      System.out.println(e);
    }
  }

  /*
  This method is more for compartmentalization if anything. The findLargestDepth method is called, getting the deepest entry. Then the toStringMaxPath method is called, to return that Item's path. Then everything is written with fancy
  formatting. 
  */
  public static void writeMaxPath(File writeFile){
    try{
      BufferedWriter writer = new BufferedWriter(new FileWriter(writeFile));

      Item deepestEntry = HPOExplorer.findLargestDepth();
      int biggestDepth = deepestEntry.getDepth();

      writer.write("[max_path=" + (biggestDepth - 1) + "]"); //The reason why its the depth - 1 is because the question
      //asks for connections not number of nodes.
      writer.newLine();
      writer.write(deepestEntry.toStringMaxPath());

      writer.flush();
      writer.close();
    } catch (Exception e){
      System.out.println("Error in writeMaxPath");
      System.out.println(e);
    }
  }
}
