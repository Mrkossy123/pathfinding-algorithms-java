import java.util.*;
import javax.swing.*;

public class Node {
    public int value;//value of node
    public double pathValue;//total value of moving from one node to another
    public double cost=0;
    public double absoluteDistance;//h(n)

    public int row;
    public int collumn;

    public JPanel nodePanel;//used for front end purposes
    
    //different types of neighbors results in different pathValue
    public ArrayList<Node> linearNeighbors = new ArrayList<Node>();
    public ArrayList<Node> diagNeighbors = new ArrayList<Node>();
    //allNeighbors.get(0) gets all linear Neighbors, allNeighbors.get(1) gets all diagonal Neighbors. ArrayList is used for easier looping through all neighbors
    public ArrayList<ArrayList <Node>> allNeighbors = new ArrayList<ArrayList <Node>>(2);
	public Node parent;

	public Node(int value){
        this.value=value;
        pathValue=0;

        //initialises each element of ArrayList with another ArrayList
        for(int i=0; i < 2; i++) allNeighbors.add(new ArrayList<>());
	}

    public void addLinearNeighbor(Node e)
    {
        linearNeighbors.add(e);
    }

    public void addDiagNeighbor(Node e)
    {
        diagNeighbors.add(e);
    }

    public void setAbsolutDistance(Node g1, Node g2)
    {
        double distanceG1,distanceG2;

        int i=(row-collumn)*(row-collumn);
        int j=(g1.row-g1.collumn)*(g1.row-g1.collumn);
        distanceG1=Math.sqrt(i+j);
       
        i=(row-collumn)*(row-collumn);
        j=(g2.row-g2.collumn)*(g2.row-g2.collumn);
        distanceG2=Math.sqrt(i+j);
        
        if(distanceG1<distanceG2)absoluteDistance=distanceG1;
        else absoluteDistance=distanceG2;
    }
    
}
