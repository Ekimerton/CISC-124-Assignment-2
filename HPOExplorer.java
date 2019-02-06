import java.io.*;
import java.util.HashMap;
public class HPOExplorer {

  public static final int ITEM_COUNT = 13941;

  public static File HPO = new File("HPO.txt");

  public static HashMap<Integer, Item> entryMap = new HashMap<Integer, Item>();

  public static void main (String[] args) {
    parseHPO(HPO);
    establishConnections(HPO);
    System.out.println(entryMap.get(1));
    System.out.print(entryMap.get(3000067));
  }

  /*
  This method parses the txt file, and puts the entries into an Item object. The objects go into a HashMap.
  */
  public static void parseHPO (File file) {
    try{
      BufferedReader br = new BufferedReader(new FileReader(file));

      //Gets rid of the information at the top.
      for(int i = 0; i < 29; i++){
        br.readLine();
      }

      for(int i = 0; i < ITEM_COUNT; i++){
        String line = br.readLine().trim();
        line = br.readLine().trim();

        int id = Integer.parseInt(line.substring(line.indexOf("HP:") + 3, line.length()));
        Item entry = new Item(id);
        while(!line.isEmpty()){
          line = br.readLine().trim();
          if(line.indexOf("is_a") == -1){
            entry.addContent(line);
          }
        }
        entryMap.put(entry.getId(), entry);
      }
    } catch (Exception e){
      System.out.println("Error in parsing into HashMap");
      System.out.println(e);
    }
  }

  public static void establishConnections(File file){
    try{
      BufferedReader br = new BufferedReader(new FileReader(file));

      //Gets rid of the information at the top.
      for(int i = 0; i < 29; i++){
        br.readLine();
      }

      for(int i = 0; i < ITEM_COUNT; i++){
        String line = br.readLine().trim();
        line = br.readLine().trim();

        int id = Integer.parseInt(line.substring(line.indexOf("HP:") + 3, line.length()));
        Item entry = new Item(id);
        while(!line.isEmpty()){
          if(line.indexOf("is_a:") != -1){
            int childId = Integer.parseInt(line.substring(line.indexOf("HP:") + 3, line.indexOf(" !")));
            Item child = entryMap.get(id);
            Item parent = entryMap.get(childId);
            parent.addChild(child);
          }
          line = br.readLine().trim();
        }
      }
    } catch (Exception e){
      System.out.println("Error in establishing connections");
      System.out.println(e);
    }
  }
}
