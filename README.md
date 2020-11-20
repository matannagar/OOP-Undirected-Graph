# **Weighted Graph**

This projects implements an undirected graph, including vertices, edges and a few graph traversal algorithms.

## **Classes:**
### **WGraph_DS**: 
this class holds two hashMap lists, one for the edges in the graph and the other for the nodes in the graph.
Insdie this class iv'e created an inner class, named **NodeInfo** which implements node_info interface. 
this inside class is a basic code meant to configure a vertix in a class, using a specific ID, a hashmap that stores
a list of 'neighbors', meaning a list of the connected vertices. 

### **WGraph_Algo**:
* Init
* getGraph
* shortestPathDist
* shortestPath
* isConnected
* Save
* Load

This class implements the weighted_graph_algo interface which holds more complicated algorithms such as dikjstra's algorithm and a breadth-first-search algorithm.
the **BFS** is an algorithm for traversing or searching a graph data structures. 
It starts at the a random source node and explores all of the neighbor nodes at the present depth prior to moving on to the nodes at the next depth level using a Queue that 
stores inside him the list of neighbors of the source node, marks them as visited, polls the original source node and then turns one of the
neighbors to the source node. By the end of the process, while traversing through the entire graph node's list, we can check if all of them
were marked as visited. if so, then the graph is connected.

We then use **Dijkstra's algorithm** to provide the shortest path and distance from one node to another, considering the weights of each edge. 
The algorithm sets a source node and maintains an array list of distances from that specific node. 
We initiate the array as infinity and every time that we find a shorter distance from a node to the source, we update it in the array list.
Using a Priority Queue & Comparator we would call this function a greedy function, which means that we would be traversing through the entire
graph (or most of it) by taking out from the Queue the best next visible option, the shortest option. 
In the meantime we keep another array, a parent array that stores node, in specific node-parents. 
Using another inner function, with this parent array, we would be able to construct a node path from the source node to the end node. 

For the Save and Load functions i've implemeted the **Serialization method** that java provides. 
Serialization lets us create a saved file that holds within it an object, in our case it held an object of type weighted_graph.
We then use the load method to extract that object from the file and load it into a new weighted_graph object.

### **Sources:** 
https://en.wikipedia.org/wiki/Dijkstra%27s_algorithm
https://www.youtube.com/watch?v=pVfj6mxhdMw&t=434s&ab_channel=ComputerScience
https://www.youtube.com/watch?v=pSqmAO-m7Lk&t=639s&ab_channel=WilliamFiset
https://www.youtube.com/watch?v=oDqjPvD54Ss&ab_channel=WilliamFiset
