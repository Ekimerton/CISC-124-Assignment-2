import java.util.*;

public class Item {
  int id = -1;
  String contents = "";
  List<Item> children = null;

  public Item(int i){
    id = i;
    contents = "";
    children = new ArrayList<Item>();
  }

  public void addContent(String s){
    contents = contents + s + "\n";
  }

  public void addChild(Item child){
    this.children.add(child);
  }

  public int getId(){
    return this.id;
  }

  public String toString(){
    String childrenIds = "";
    if(children.size() == 0){
      return("Id: " + this.id + "\n" +
      "Contents: " + "\n" + this.contents);
    }

    for(int i = 0; i < children.size(); i++){
      Item child = children.get(i);
      childrenIds = childrenIds + child.getId() + ", ";
    }
    return("Id: " + this.id + "\n" +
    "Contents: " + "\n" + this.contents +
    "Children: " + childrenIds);
  }
}
