/*
Name: Ekim Karabey
NetId: 18ebk
Student Number: 20121769
Date: 19/02/2019
*/

import java.io.*;
import java.util.HashMap;
import java.util.ArrayList;
public class HPOExplorer {

  public static File HPO = new File("HPO.txt");
  public static File queries = new File("queries.txt");
  public static File answers = new File("answers.txt");
  public static File maxPath = new File("maxpath.txt");

  public static final int ITEM_COUNT = 13941; //The count of total entries
  public static final int OBSOLETE_COUNT = 216; //The count of obsolete entries
  public static final int VALID_ITEM_COUNT = ITEM_COUNT - OBSOLETE_COUNT; //The count of entries, not obsolete

  public static int[] ids = new int[VALID_ITEM_COUNT]; //Stores all the entries of valid entries (not obsolete)
  public static HashMap<Integer, Item> entryMap = new HashMap<Integer, Item>(); //Hashmap of entries for easy access

  public static void main (String[] args) {
    System.out.println();
    parseHPO(HPO);
    establishConnections(HPO);
    Item root = entryMap.get(1);
    root.setDepths();

    Query.readQueries(queries, answers);
    Query.writeMaxPath(maxPath);
  }

  /*
  This method parses the txt file, and puts the entries into an Item object. The objects go into a HashMap.

  The is_a (the children), are not connected yet, as they might not have been declared yet. Instead, their IDs are
  stored, and the connections are made afterwards.
  */
  public static void parseHPO (File readFile) {
    try{
      BufferedReader reader = new BufferedReader(new FileReader(readFile));

      //Gets rid of the information at the top.
      for(int i = 0; i < 29; i++){
        reader.readLine();
      }

      int count = 0;
      for(int i = 0; i < ITEM_COUNT; i++){
        String line = reader.readLine().trim(); //Gets rid of the space at the top.
        String fullEntry = "";
        while(!line.isEmpty()){
          line = reader.readLine().trim();
          fullEntry = fullEntry + "\n" + line;
        }
        fullEntry = fullEntry.substring(fullEntry.indexOf("id:"));
        //At this point, the string fullEntry contains all the information within the Term, but without the whitespace and [Term] at the top.

        if(fullEntry.indexOf("is_obsolete: true") == -1){
          Item entry = new Item(fullEntry);
          ids[count] = entry.getId();
          count++;
          entryMap.put(entry.getId(), entry); //Puts the entry in a hashmap where the Id is the key, for easy access.
        }
      }
    } catch (Exception e){
      System.out.println("Error in parseHPO");
      System.out.println(e);
    }
  }

  /*
  Goes through the ids[] array, and looks at every valid entry. Then it goes through the entry's ArrayList of parentIds.
  It then sets the parents according to the list of parentIds.
  */
  public static void establishConnections(File file){
    for(int i = 0; i < VALID_ITEM_COUNT; i++){
      Item entry = entryMap.get(ids[i]);
      if(entry.parentsIds.size() > 0) {
        for(int j = 0; j < entry.parentsIds.size(); j++){
          Item is_a = entryMap.get(entry.parentsIds.get(j));
          entry.addParent(is_a);
          is_a.addChild(entry);
        }
      }
    }
  }

  /*
  Goes through the ids[] array of all valid entry ids, and returns the entry with the largest depth.
  */
  public static Item findLargestDepth(){
    Item maxEntry = entryMap.get(ids[0]);
    int maxDepth = maxEntry.getDepth();
    for(int i = 0; i < VALID_ITEM_COUNT; i++){
      Item entry = entryMap.get(ids[i]);
      int depth = entry.getDepth();
      if(depth > maxDepth){
        maxDepth = depth;
        maxEntry = entry;
      }
    }
    return maxEntry;
  }
}
