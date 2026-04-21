# Pathfinding Algorithms in Java

A Java desktop application that visualizes **Uniform Cost Search (UCS)** and **A\*** on a weighted grid with blocked cells.

The project uses a Swing-based graphical interface where the user selects:
- one starting node (**S**)
- two goal nodes (**G1**, **G2**)

The program then runs:
1. **Uniform Cost Search (UCS)**
2. **A\*** search

and highlights the resulting path on the grid.

## Features

- Java Swing GUI
- Random weighted grid generation
- Random obstacle placement based on probability
- Interactive node selection with mouse clicks
- Visualization of pathfinding results
- Comparison between UCS and A* behavior

## Project Structure

```text
├── App.java
├── Astar.java
├── Client.java
├── Grid.java
├── Node.java
├── Ucs.java
├── README.md
├── LICENSE
└── .gitignore
