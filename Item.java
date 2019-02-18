import java.util.*;

public class Item {

  int id = -1;
  int depth = -1;
  String contents = "";
  List<Integer> parentsIds = null;
  List<Item> children = null;
  List<Item> parents = null;

  public Item(String fullEntry){
    id = Integer.parseInt(fullEntry.substring(fullEntry.indexOf("id: HP:") + 7, fullEntry.indexOf("id: HP:") + 14));
    depth = -1;
    contents = fullEntry;
    children = new ArrayList<Item>();
    parents = new ArrayList<Item>();
    parentsIds = new ArrayList<Integer>();

    while(fullEntry.indexOf("is_a: HP:") != -1){
      int start = fullEntry.indexOf("is_a: HP:") + 9;
      int end = fullEntry.indexOf(" !");
      int parentId = Integer.parseInt(fullEntry.substring(start, end));
      parentsIds.add(parentId);
      fullEntry = fullEntry.substring(fullEntry.indexOf("is_a: HP:") + 18);
    }

  }

  /*
  Starts at the root, with a depth of 1. Then, everytime the medhod is called recursively on the child, it takes the
  largest depth among it's parents, and adds 1 to it.
  */
  public void setDepths(){
    if(this.id == 1){
      this.setDepth(1);
    } else {
      if(this.parents.size() > 1){
        Item parent = this.parents.get(0);
        int maxDepth = parent.getDepth();
        for(int i = 0; i < this.parents.size(); i++){
          parent = this.parents.get(i);
          int depth = parent.getDepth();
          if(depth > maxDepth){
            maxDepth = depth;
          }
        }
        this.setDepth(maxDepth + 1);
      } else {
        Item parent = this.parents.get(0);
        int maxDepth = parent.getDepth();
        this.setDepth(maxDepth + 1);
      }
    }
    if(this.children.size() != 0){
      for(int i = 0; i < this.children.size(); i++){
        Item child = this.children.get(i);
        child.setDepths();
      }
    }
  }

  //Setters and Getters
  public void addChild(Item child){
    this.children.add(child);
  }

  public void addParent(Item parent){
    this.parents.add(parent);
  }

  public int getId(){
    return this.id;
  }

  public void setDepth(int d){
    this.depth = d;
  }

  public int getDepth(){
    return this.depth;
  }

  //Printing methods

  /*
  This method was mainly used for testing.
  */
  public void printChildren(){
    if(children.size() == 0){
      System.out.println(this.contents);
    } else {
      for(int i = 0; i < children.size(); i++){
        System.out.println(children.get(i).contents);
      }
    }
  }

  /*
  This method is only used for printing the maxpath.txt file, since the current print takes the route of the first parent, instead of the "deepest" parent.
  */
  public String toStringMaxPath(){
    String parentsInfo = "";
    if(parents.size() == 0){
      return(this.contents);
    }

    Item maxDepthParent = parents.get(0);
    for(int i = 0; i < parents.size(); i++){
      Item currentParent = parents.get(i);
      if(currentParent.getDepth() > maxDepthParent.getDepth()){
        maxDepthParent = currentParent;
      }
    }

    Item parent = maxDepthParent;
    parentsInfo = "\n" + parent.toStringMaxPath();
    return(this.contents + parentsInfo);
  }

  public String toString(){
    String parentsInfo = "";
    if(parents.size() == 0){
      return(this.contents);
    }

    /*
    This chunk of code follows each parent path to the root node, which means there is more "clutter". This was a part 
    of my toString method before the assignment clarification.

    for(int i = 0; i < parents.size(); i++){
      Item parent = parents.get(i);
      parentsInfo = parentsInfo + "\n" + parent.toString();
    }
    */
    Item parent = parents.get(0);
    parentsInfo = "\n" + parent.toString();
    return(this.contents + parentsInfo);
  }
}
