package ai_tsp;

import java.util.*; 

public class HeuristicFunction 
{ 
	public class Edge implements Comparable<Edge> 
	{ 
		int src, dest, weight; 

		public int compareTo(Edge compareEdge) 
		{ 
			return this.weight-compareEdge.weight; 
		} 
	}; 

	public class subset 
	{ 
		int parent, rank; 
	}; 

	public int V, E; // V-> no. of vertices & E->no.of edges 
	public Edge edge[]; // collection of all edges 

	public HeuristicFunction(int v, int e) 
	{ 
		V = v; 
		E = e; 
		edge = new Edge[E]; 
		for (int i=0; i<e; ++i) 
			edge[i] = new Edge(); 
	} 

	public int find(subset subsets[], int i) 
	{ 
		if (subsets[i].parent != i) 
			subsets[i].parent = find(subsets, subsets[i].parent); 

		return subsets[i].parent; 
	} 

	public void Union(subset subsets[], int x, int y) 
	{ 
		int xroot = find(subsets, x); 
		int yroot = find(subsets, y); 

		if (subsets[xroot].rank < subsets[yroot].rank) 
			subsets[xroot].parent = yroot; 
		else if (subsets[xroot].rank > subsets[yroot].rank) 
			subsets[yroot].parent = xroot; 

		else
		{ 
			subsets[yroot].parent = xroot; 
			subsets[xroot].rank++; 
		} 
	} 

	int KruskalMST() 
	{ 
		Edge result[] = new Edge[V]; // Tnis will store the resultant MST 
		int e = 0; // An index variable, used for result[] 
		int i = 0; // An index variable, used for sorted edges 
		for (i=0; i<V; ++i) 
			result[i] = new Edge(); 

		Arrays.sort(edge); 

		subset subsets[] = new subset[V]; 
		for(i=0; i<V; ++i) 
			subsets[i]=new subset(); 

		for (int v = 0; v < V; ++v) 
		{ 
			subsets[v].parent = v; 
			subsets[v].rank = 0; 
		} 

		i = 0; // Index used to pick next edge 

		while (e < V - 1) 
		{ 
			Edge next_edge = new Edge(); 
			next_edge = edge[i++]; 

			int x = find(subsets, next_edge.src); 
			int y = find(subsets, next_edge.dest); 

			if (x != y) 
			{ 
				result[e++] = next_edge; 
				Union(subsets, x, y); 
			} 
		} 

		int wt = 0;
		for (i = 0; i < e; ++i) 
		{
			wt = wt + result[i].weight;
		}
		return wt;
			
	} 

	
	
	

}