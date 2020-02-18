package ai_tsp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.InputMismatchException;
import java.util.PriorityQueue;
import java.util.Scanner;

public class UCS
{
	private PriorityQueue<Node> priorityQueue;
	private int distances[];
	private int pathcost = 0;
	private int numberOfNodes;
	private int adjacencyMatrix[][];
	public int source;
	public static final int MAX_VALUE = 1000; 
	public static ArrayList<Integer> visited = new ArrayList<Integer>();
	public int n_edges;

	public UCS(int numberOfNodes, int edges)
	{
		this.numberOfNodes = numberOfNodes;
		this.priorityQueue = new PriorityQueue<>(numberOfNodes, new Node());
		this.distances = new int[numberOfNodes + 1];
		this.adjacencyMatrix = new int[numberOfNodes + 1][numberOfNodes + 1]; 
		this.n_edges = edges;
	}

	public int Heuristic(int node, ArrayList<Integer> visited)
	{
		int min = MAX_VALUE;
		for(int i =0; i<numberOfNodes; i++)
		{
			if(!(visited.contains(i)) && i!= node)
			{
				if(adjacencyMatrix[node][i]<min)
					min = adjacencyMatrix[node][i];
			}
		}

		int nearest_dist = MAX_VALUE;
		for(int i =0; i<numberOfNodes; i++)
		{
			if(!(visited.contains(i)) && i!=node)
			{
				if(adjacencyMatrix[i][0]<nearest_dist)
					nearest_dist = adjacencyMatrix[i][0];
			}
		}

		if(n_edges == 0)
			return (0+min+nearest_dist);
		int wt = min+nearest_dist;
		int v = numberOfNodes - visited.size();
		HeuristicFunction heuristicFunction = new HeuristicFunction(v, n_edges); 

		int i=0, j=0;
		int e =0;
		for(int it = 0; it<numberOfNodes; it++)
		{
			if(!(visited.contains(it)))
			{
				j = i+1;
				for(int k =it+1; k<numberOfNodes; k++)
				{
					if(!(visited.contains(k)) && (adjacencyMatrix[it][k]!= MAX_VALUE))
					{
						heuristicFunction.edge[e].src = i;
						heuristicFunction.edge[e].dest = j;
						heuristicFunction.edge[e].weight = adjacencyMatrix[it][k];

						e++;
						heuristicFunction.edge[e].src = j;
						heuristicFunction.edge[e].dest = i;
						heuristicFunction.edge[e].weight = adjacencyMatrix[k][it];
						e++;
						j++;
					}
				}
				i++;
			}
		}

		return wt + heuristicFunction.KruskalMST();
	}

	public int uniformCostSearch(int adjacencyMatrix[][], int source)
	{

		int evaluationNode;
		this.source = source; 
		for (int i = 0; i < numberOfNodes; i++)
		{
			distances[i] = MAX_VALUE;
		}

		for (int sourcevertex = 0; sourcevertex < numberOfNodes; sourcevertex++)
		{
			for (int destinationvertex = 0; destinationvertex < numberOfNodes; destinationvertex++)
			{
				this.adjacencyMatrix[sourcevertex][destinationvertex] =
						adjacencyMatrix[sourcevertex][destinationvertex];
			}
		}

		priorityQueue.add(new Node(source, 0));
		distances[source] = 0;
		int p =source;

		while (!priorityQueue.isEmpty())
		{
			evaluationNode = getNodeWithMinDistanceFromPriorityQueue();
			pathcost = pathcost + adjacencyMatrix[p][evaluationNode];
			p = evaluationNode;
			visited.add(evaluationNode);
			for(int j = 0; j< numberOfNodes; j++)
			{
				if( adjacencyMatrix[evaluationNode][j] != MAX_VALUE && !(visited.contains(j)))
					n_edges = n_edges-2;
			}

			// System.out.println("The eval Node is " + evaluationNode+" ...no of edges are "+n_edges);

			addFrontiersToQueue(evaluationNode);
			if(priorityQueue.isEmpty())
			{
				pathcost = pathcost + adjacencyMatrix[evaluationNode][source];
				return pathcost;
			}
		}

		return pathcost;
		// return distances[destination];
	}

	private void addFrontiersToQueue(int evaluationNode)
	{


		for (int destination = 0; destination < numberOfNodes; destination++)
		{
			if (!(visited.contains(destination)))
			{
				if (adjacencyMatrix[evaluationNode][destination] != MAX_VALUE)
				{
					{
						distances[destination] = adjacencyMatrix[evaluationNode][destination]	
								+ distances[evaluationNode]; 
					}

					try {
						int heuristic = Heuristic(destination,visited);
						//System.out.println("heuristic of "+destination+"= "+heuristic);
						Node node = new Node(destination, distances[destination]+heuristic);
						if (priorityQueue.contains(node))
						{
							priorityQueue.remove(node);
						}
						priorityQueue.add(node);
					}catch(Exception e)
					{
						e.printStackTrace();
					}


				}
			}
		}
	}

	private int getNodeWithMinDistanceFromPriorityQueue()
	{
		Node node = priorityQueue.remove();
		return node.node;
	}

	public static void main(String... arg)
	{
		int adjacency_matrix[][];
		int number_of_vertices;
		int source = 0;
		// int destination = 0;
		int distance;
		int no_of_edges;
		Scanner scan = new Scanner(System.in);
		try
		{
			System.out.println("Enter the number of vertices");
			number_of_vertices = scan.nextInt();
			no_of_edges = number_of_vertices *(number_of_vertices-1);

			adjacency_matrix = new int[number_of_vertices + 1][number_of_vertices + 1];
			System.out.println("Enter the Weighted Matrix for the graph");
			for (int i = 0; i < number_of_vertices; i++)
			{
				for (int j = 0; j < number_of_vertices; j++)
				{
					adjacency_matrix[i][j] = scan.nextInt();
					if (i == j)
					{
						adjacency_matrix[i][j] = 0;
						continue;
					}
					if (adjacency_matrix[i][j] == 0)
					{
						adjacency_matrix[i][j] = MAX_VALUE;
						no_of_edges -= 1; 

					}
				}
			}

			//System.out.println("no of edges = "+no_of_edges);
			System.out.println("Enter the source ");
			source = scan.nextInt();

			UCS uniformCostSearch = new UCS(number_of_vertices, no_of_edges);
			distance = uniformCostSearch.uniformCostSearch(adjacency_matrix,source);
			//uniformCostSearch.printPath();
			System.out.println("Travelling Salesman path is:--");
			visited.add(source);
			System.out.println(Arrays.toString(visited.toArray()));

			System.out.println("\nTotal path cost is " + distance);

		} catch (InputMismatchException inputMismatch)
		{
			System.out.println("Wrong Input Format");
		}
		scan.close();
	}
}

class Node implements Comparator<Node>
{
	public int node;
	public int cost;

	public Node()
	{

	}

	public Node(int node, int cost)
	{
		this.node = node;
		this.cost = cost;
	}

	@Override
	public int compare(Node node1, Node node2)
	{
		if (node1.cost < node2.cost)
			return -1;
		if (node1.cost > node2.cost)
			return 1;
		if (node1.node < node2.node)
			return -1;
		return 0;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (obj instanceof Node)
		{
			Node node = (Node) obj;
			if (this.node == node.node)
			{
				return true;
			}
		}
		return false;
	}
}