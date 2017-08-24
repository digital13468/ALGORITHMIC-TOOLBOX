/*
 * Prim's.h
 *
 *  Created on: Aug 4, 2017
 *      Author: Chan-Ching Hsu
 */

#ifndef PRIMS_H_
#define PRIMS_H_
#include <vector>
#include "Graph.h"
class Prims {
public:
	Prims(Graph& G);
	virtual ~Prims();

	vector<int> tree(int u);
	double treeSize(int u);

	void print(int u);
private:
	Graph& graph;
};

#endif /* PRIMS_H_ */
