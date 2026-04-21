import java.util.Scanner;

public class App 
{
    public static void printLine()
    {
        System.out.println("-----------------------------------------------------------------------------------");
    }
    public static void main(String[] args)
    {
        printLine();
        Scanner s=new Scanner(System.in);
        System.out.println("Enter the size of N (<100 for readability, 40 is best):");
        int N = s.nextInt();  // Read user input

        System.out.println("Enter the probabilty of blocks in grid (<1):");
        Double p = s.nextDouble();  // Read user input
        
        printLine();
        System.out.println("Click grid to select starting square (S). Then click to select 2 endPoints (G1, G2).");
        
    
        Client client=new Client(N,p);
        client.display();
        String next="";

        printLine();
        while(true)
        {
            next = s.nextLine();  // Read user input
            if(next.equals("start"))
            {
                printLine();
                client.ucs();
                printLine();
                break;
            }
        }
        System.out.print("Type clear to clear grid: ");
        while(true)
        {
            next = s.nextLine();  // Read user input
            if(next.equals("clear"))
            {
                client.clearGridSamePoints();
                printLine();
                break;
            }
        }
        System.out.print("type start: ");
        while(true)
        {
            next = s.nextLine();  // Read user input
            if(next.equals("start"))
            {
                printLine();
                client.astar();
                printLine();
                break;
            }
        }
        System.out.println("Done. Close grid window to terminate program!!!");
        printLine();
        s.close();
    }
}
