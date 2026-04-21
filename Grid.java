import java.util.*;
import javax.swing.*;

public class Grid
{
    private int N;//N=(number of rows and collumns)
    private double p;//p=(probability of blocked cells)
    private Node[][] grid;//grid made out of nodes
    
    private Node S;//starting node
    private Node G1;//ending node number 1
    private Node G2;//ending node number 2

    Grid(int N, double p)
    {
        this.N=N;
        this.p=p;

        grid=new Node[N][N];

        populateGrid();
        blockCells();
    }

    //adds a node to every postion of the grid with a random value 
    private void populateGrid()
    {
        Random random=new Random();
        int randomValue=0;

        for(int i=0;i<N;i++)
        {
            for(int j=0;j<N;j++)
            {
                randomValue=random.nextInt(1,5);
                grid[i][j]=new Node(randomValue);

                grid[i][j].row=i;
                grid[i][j].collumn=j;
            }
        }
        connectNeighbors();
        neighborsToAllNeighbors();  
    }

    public void connectNeighbors()
    {
        for(int i=0;i<N;i++)
        {
            for(int j=0;j<N;j++)
            {
                if(i>0)//connects every node with (vertical-up) neighbor and vice versa
                {
                    grid[i][j].addLinearNeighbor(grid[i-1][j]);
                    grid[i-1][j].addLinearNeighbor(grid[i][j]);
                }
                if(j>0)//connects every node with (horizontal-left) neighbor and vice versa
                {
                    grid[i][j].addLinearNeighbor(grid[i][j-1]);
                    grid[i][j-1].addLinearNeighbor(grid[i][j]);
                }
                if(j>0 && i>0)//connects every node with (diagonal-right-up) neighbor and vice versa
                {
                    grid[i][j].addDiagNeighbor(grid[i-1][j-1]);
                    grid[i-1][j-1].addDiagNeighbor(grid[i][j]);
                }
                if(j<N-1 && i>0)//connects every node with (diagonal-left-up) neighbor and vice versa
                {
                    grid[i][j].addDiagNeighbor(grid[i-1][j+1]);
                    grid[i-1][j+1].addDiagNeighbor(grid[i][j]);
                }
            }
        }
    }

    //adds neighbor arraylists to allNeighbors
    public void neighborsToAllNeighbors()
    {
        for(int i=0;i<N;i++)
        {
            for(int j=0;j<N;j++)
            {
                grid[i][j].allNeighbors.get(0).addAll(grid[i][j].linearNeighbors);
                grid[i][j].allNeighbors.get(1).addAll(grid[i][j].diagNeighbors);
            }
        }
    }

    //creates walls for our grid to become a labyrinth
    private void blockCells()
    {
        if(p==0) return;//if p==0 return, no walls needed

        //list where we add all coordinates, to pick at random. We need to make a list so we dont get the same coordinates multiple times. 
        //After a wall is made at the returned coords we remove the coordinates from the list
        ArrayList<int[]> list = new ArrayList<>(); 
        for(int i=0;i<N;i++)
        {
            for(int j=0;j<N;j++)
            {
                list.add(new int[] {i, j});//list with all the coordinates
            }   
        }

        int rowCoordinates,collumnCoordinates,randomInt;

        Random random=new Random();
        int blockedCells=(int)(N*N*p);//number of blocked cells

        for(int i=0;i<blockedCells;i++)
        {
            randomInt=random.nextInt(list.size());//random int[] from list

            rowCoordinates=list.get(randomInt)[0];//random row
            collumnCoordinates=list.get(randomInt)[1];//random collumn

            list.remove(randomInt);//removes coords from list. So we dont get same coords multiple times
            grid[rowCoordinates][collumnCoordinates].value=-1;
        }
    }

    //finds and returns the node that contains the given panel. Used in client class (front end)
    public Node findPanel(JPanel p)
    {
        for(int i=0;i<N;i++)
        {
            for(int j=0;j<N;j++)
            {
                if(p==grid[i][j].nodePanel)
                {
                    System.out.print("("+i+","+j+")");
                    return grid[i][j];
                }
            }
        }
        return null;
    }

    public void calculateDistance()
    {
        for(int i=0;i<N;i++)
        {
            for(int j=0;j<N;j++)
            {
                grid[i][j].setAbsolutDistance(G1, G2);
            }
        }
    }

    //starting node declaration
    public void setS(Node S)
    {
        this.S=S;
        this.S.value=0;
    }

    //goal 1 declaration
    public void setG1(Node G1)
    {
        this.G1=G1;
        this.G1.value=0;
    }

    //goal 2 declaration
    public void setG2(Node G2)
    {
        this.G2=G2;
        this.G2.value=0;
    }

    //returns S
    public Node getS()
    {
        return S;
    }

    //returns G1
    public Node getG1()
    {
        return G1;
    }

    //returns G2
    public Node getG2()
    {
        return G2;
    }

    //returns node from given coords
    public Node getNode(int i,int j)
    {
        return grid[i][j];
    }

    
}
