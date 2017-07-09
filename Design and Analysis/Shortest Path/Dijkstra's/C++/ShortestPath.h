/*
 * ShortestPath.h
 *
 *  Created on: Jul 8, 2017
 *      Author: digital13468
 */

#ifndef SHORTESTPATH_H_
#define SHORTESTPATH_H_
#include <vector>
#include "PriorityQueue.h"
#include "Graph.h"
using namespace std;
class ShortestPath {
public:
	ShortestPath(Graph& G);
	//ShortestPath();
	virtual ~ShortestPath();

	vector<int> path(int u, int w);
	double pathSize(int u, int w);

	void print(int u, int w);
private:
	//PriorityQueue PQ;
	Graph& graph;
};

#endif /* SHORTESTPATH_H_ */
