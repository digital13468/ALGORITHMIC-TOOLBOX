/*
 * Kruskal.h
 *
 *  Created on: Aug 6, 2017
 *      Author: Chan-Ching Hsu
 */

#ifndef KRUSKAL_H_
#define KRUSKAL_H_

#include "Graph.h"
#include "DisjointSet.h"
class Kruskal {
public:
	Kruskal(Graph& G);
	virtual ~Kruskal();

	vector<vector<int>> tree();
	double treeSize();

	void print();
private:
	Graph& graph;
};

#endif /* KRUSKAL_H_ */
