import java.util.PriorityQueue;
import java.util.HashSet;
import java.util.Set;

import java.util.ArrayList;
import java.util.Comparator;


public class Astar{
    private static Node result1;//G1
    private static Node result2;//G2

    private static double pathValue1=-1;//pathValue of G1
    private static double pathValue2=-1;//pathValue of G2
    
    private static boolean found1,found2;//boolean values to determine if both goals were reached

    public static void aStarSearch(Node start, Node goal1,Node goal2){
        //we create a queue to add all nodes that were checked
        PriorityQueue<Node> queue = new PriorityQueue<Node>(2000, 
            new Comparator<Node>()//we override the queue to return the node with the smallest pathValue
            {
                @Override
                public int compare(Node i, Node j){
                    if(i.cost > j.cost){
                        return 1;
                    }
                    else if (i.cost < j.cost){
                        return -1;
                    }
                    else{
                        return 0;
                    }
                }
            }
        );
        
        queue.add(start);//first element of queue must be the starting node
        Set<Node> explored = new HashSet<Node>();//set that contains all trespassed nodes
        while(!queue.isEmpty())//if there are no more nodes to explore stop loop   
        {
            Node current = queue.poll();//current node is equal to the last element added to the queue, that element is removed from the queue
            explored.add(current);
         
            if(current==goal1)//G1 was found
            {
                result1=goal1;
                found1=true;
                System.out.println("-found G1");
            }
            else if(current==goal2)//G2 was found
            {
                found2=true;
                result2=goal2;

                System.out.println("-found G2");
            } 

            //TERMINATES method if the 2 goals were found
            if (found1 && found2)return; 
            
            for(ArrayList<Node> neighborType: current.allNeighbors)
            {
                for(Node child: neighborType)
                {                         
                    double linearPathValue=Math.abs(current.value - child.value)+1;
                    double diagPathValue=Math.abs(current.value - child.value)+0.5;

                    if(child.value!=-1)//only walls have values of -1. This prevents the traversal of walls
                    {
                        if(!explored.contains(child) && !queue.contains(child))//add node to queue if node has not been explored 
                        {
                            if(neighborType==current.linearNeighbors)child.pathValue = linearPathValue;//pathValue if node is a linearNeighbor 
                            else child.pathValue = diagPathValue;//pathValue if node is a diagNeighbor 
                            
                            child.cost=child.absoluteDistance+child.pathValue;
                            child.parent = current;//define new parent
                            queue.add(child);//adds current.child to the queue

                            //if(child!=start && child!=goal1 && child!=goal2)child.nodePanel.setBackground(new Color(50,50,50));	
                        }
                        else if((queue.contains(child)))
                        {
                            //change pathValue if current path is shorter than previous path found and if node is a diagNeighbor
                            if(child.cost>diagPathValue+child.absoluteDistance && neighborType==current.diagNeighbors) 
                            {
                                child.parent=current;
                                child.pathValue = diagPathValue;
                                child.cost=child.absoluteDistance+child.pathValue;
                                queue.remove(child);
                                queue.add(child);
                            }
                            //change pathValue if current path is shorter than previous path found and if node is a linearNeighbor
                            else if(child.cost>linearPathValue+child.absoluteDistance && neighborType==current.linearNeighbors)
                            {
                                child.parent=current;
                                child.pathValue =linearPathValue;
                                child.cost=child.absoluteDistance+child.pathValue;

                                queue.remove(child);
                                queue.add(child);
                            }
                        }
                    }
                } 	    
            }	
        }
    }

    public static ArrayList<Node> returnPath(){
        ArrayList<Node> path1 = new ArrayList<Node>();//will contain path to g1
        ArrayList<Node> path2 = new ArrayList<Node>();//will contain path to g2

        for(Node node = result1; node!=null; node = node.parent){
            path1.add(node);
            pathValue1+=node.pathValue;//calculates total pathValue to G1
        }
        for(Node node = result2; node!=null; node = node.parent){
            path2.add(node);   
            pathValue2+=node.pathValue;//calculates total pathValue to G2
        }
        //prints both costs 
        System.out.println("Cost of G1: "+pathValue1);
        System.out.println("Cost of G2: "+pathValue2);
        
        //returns shortest path 
        if(pathValue1<pathValue2)return path1;
        return path2;
    }
}