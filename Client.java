import javax.swing.*;

import java.awt.*;
import java.awt.event.*;

public class Client{
    //COLORS
    private final Color MAIN_COLOR=new Color(15,15,15);
    private final Color SECONDARY_COLOR=new Color(70,70,70);

    private final Color FONT_COLOR=new Color(200,200,200);

    private final Color HOVERED_COLOR=new Color(50,50,50);
    private final Color STARTING_COLOR=new Color(0, 155, 114);
    private final Color DESTINATION_COLOR=new Color(242, 100, 48);

    //FONT
    private final Font MAIN_FONT=new Font(Font.DIALOG_INPUT,Font.BOLD,10);

    private JFrame f;//main frame
    private JPanel p;//main panel
    private int N;//number of rows and collumns
    private Grid grid;
    private int clickCounter;//click counter for choosing start point and end points

    Client(int N, double probability)
    {
        this.N=N;
        this.clickCounter=0;
        grid=new Grid(N,probability);//starts a grid with the given number of rows, collumns and the probability of walls
    }

    public void display()
    {
        f=new JFrame("Path Finding Algorithms");//name of window
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//terminates program when we close the window

        f.setLocationRelativeTo(null);
        
        snapGridToFrame();
        f.pack();
        f.setMinimumSize(new Dimension(900,900));
        //f.setExtendedState(JFrame.MAXIMIZED_BOTH); 
        f.setVisible(true);//must be last line
    }

    //makes the grid visible and adds it to the main frame. Each node will display the value of the node. Walls are uneditable and cant be clicked or traspassed 
    private void snapGridToFrame()
    {
        PanelListener listener = new PanelListener();//makes panels clickable
        p=new JPanel(new GridLayout(N,N,2,2));//dynamic panel 
        
        p.setBackground(SECONDARY_COLOR);
        for(int i=0;i<N;i++)
        {
            for(int j=0;j<N;j++)
            {
                JLabel l=new JLabel((int)(grid.getNode(i,j).value)+"",SwingConstants.CENTER);//makes label that contains the value of each node
                l.setForeground(FONT_COLOR);
                l.setFont(MAIN_FONT);
                
                JPanel panel=new JPanel(new GridLayout(1,1));//label will be added to panel 
                panel.add(l);
                //panel.setMinimumSize(new Dimension(20,20));
                if(grid.getNode(i,j).value==-1)//if the node is a wall make it not visible and change its colour 
                {
                    l.setVisible(false);
                    panel.setBackground(SECONDARY_COLOR);
                }else//else make the panel clickable and change the colour to the main one
                {
                    panel.setBackground(MAIN_COLOR);
                    panel.addMouseListener(listener);
                }
                panel.add(l);
             
                grid.getNode(i,j).nodePanel=panel;
                p.add(panel);
            }
        }
        f.add(p);//after everyything the panel is added to the main frame
    }

    public class PanelListener implements MouseListener 
    {
        
        
        @Override
        public void mousePressed(MouseEvent event)//changes that happen when a panel is clicked
        {
            Object source = event.getSource();
            if(source instanceof JPanel && clickCounter<3)
            {
                JPanel panelPressed = (JPanel) source;
                if(panelPressed.getBackground()==HOVERED_COLOR)
                {
                    switch(clickCounter)
                    {
                        case 0:
                            panelPressed.removeAll();
                            panelPressed.setBackground(STARTING_COLOR);
                            panelPressed.setForeground(MAIN_COLOR);
                            System.out.print("S: ");
                            grid.setS(grid.findPanel(panelPressed));
                            clickCounter++;
                            break;
                        case 1:
                            panelPressed.removeAll();
                            panelPressed.setBackground(DESTINATION_COLOR);
                            System.out.print("\nG1:");
                            grid.setG1(grid.findPanel(panelPressed));
                            clickCounter++;
                            break;
                        case 2:
                            panelPressed.removeAll();
                            panelPressed.setBackground(DESTINATION_COLOR);
                            
                            System.out.print("\nG2:");
                            grid.setG2(grid.findPanel(panelPressed));
                            clickCounter++;
                            grid.calculateDistance();
                            System.out.println("\n----------------------------------------------------------------------------------");

                            System.out.print("type start: ");

                    }
                }
            }
        }

        @Override
        public void mouseEntered(MouseEvent event)//changes that happen when a panel is hovered
        {
            Object source = event.getSource();
            if(source instanceof JPanel && clickCounter<3)
            {
                JPanel panelHovered = (JPanel) source;
                if(clickCounter<3 && panelHovered.getBackground()==MAIN_COLOR)
                {
                    panelHovered.setBackground(HOVERED_COLOR);
                }
            }
        }

        @Override
        public void mouseExited(MouseEvent event)//changes that happen when a panel is not hovered anymore
        {
            Object source = event.getSource();
            if(source instanceof JPanel && clickCounter<3)
            {
                JPanel panelHovered = (JPanel) source;
                if(clickCounter<3 && panelHovered.getBackground()==HOVERED_COLOR)
                {
                    panelHovered.setBackground(MAIN_COLOR);
                }
            }
        } 
        @Override
        public void mouseClicked(MouseEvent arg0) {}
        @Override
        public void mouseReleased(MouseEvent arg0) {}
    }
    
    public void ucs()
    {
        System.out.println("Ucs search algorithm");

        Ucs.uniformCostSearch(grid.getS(),grid.getG1(),grid.getG2());
        for(Node e:Ucs.returnPath()){
            e.nodePanel.setBackground(STARTING_COLOR);  
        }
        f.repaint();
    }
    
    public void astar()
    {
        System.out.println("Astar search algorithm");
        Astar.aStarSearch(grid.getS(),grid.getG1(),grid.getG2());
        for(Node e:Astar.returnPath()){
            e.nodePanel.setBackground(DESTINATION_COLOR);  
        }
        f.repaint();
    }

    
    public void clearGridSamePoints()
    {
        System.out.println("Clearing grid for next algorithm");
        for(int i=0;i<N;i++)
        {
            for(int j=0;j<N;j++)
            {
                Node tempNode=grid.getNode(i,j);
                tempNode.pathValue=0;
                if(tempNode.value>0 ){
                    tempNode.nodePanel.setBackground(MAIN_COLOR);
                }
            }
        }
        grid.getS().nodePanel.setBackground(STARTING_COLOR);
        grid.getG1().nodePanel.setBackground(DESTINATION_COLOR);
        grid.getG2().nodePanel.setBackground(DESTINATION_COLOR);

        f.repaint();
    }  
}