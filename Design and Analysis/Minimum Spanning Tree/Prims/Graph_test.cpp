/*
 * Dijkstra's.cpp
 *
 *  Created on: Jul 6, 2017
 *      Author: digital13468
 */

#include "Graph.h"
#include <iostream>
using namespace std;

int main() {
	Graph graph(0.4, 10, 1.0, 10.0);
	cout << graph.E() << "\t" << graph.V() << endl;
	//int edges = 0;
	for(int i = 0; i < graph.V(); i++)
		for(int j = 0; j < graph.V(); j++)
			if(graph.adjacent(i, j) == 1) {
				cout << "(" << i << "," << j << ")" << " has distance " << graph.get_edge_value(i, j) << endl;
				//edges ++;
			}
	graph.print();
	return 0;
}
