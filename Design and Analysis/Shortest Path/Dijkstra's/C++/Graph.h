/*
 * Graph.h
 *
 *  Created on: Jul 6, 2017
 *      Author: digital13468
 */

#ifndef GRAPH_H_
#define GRAPH_H_
#include <vector>
using namespace std;
class Graph {
public:
	Graph(int nodes);
	Graph(double density, int nodes, double min_dist, double max_dist);
	virtual ~Graph();

	int V();
	int E();

	bool adjacent(int x, int y);
	vector<int> neighbors(int x);
	void add(int x, int y);
	void delete_edge(int x, int y);
	double get_node_value(int x);
	void set_node_value(int x, double a);
	double get_edge_value(int x, int y);
	void set_edge_value(int x, int y, double v);

	void print();

private:
	double** connect_mat;
	double min_dist;
	double max_dist;
	int nodes;
};

#endif /* GRAPH_H_ */
