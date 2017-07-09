/*
 * Graph.cpp
 *
 *  Created on: Jul 6, 2017
 *      Author: digital13468
 */

#include "Graph.h"
#include <vector>
#include <cstdlib>
#include <ctime>
#include <iostream>
//#include <climits>
using namespace std;
Graph::Graph(int nodes) {
	this->nodes = nodes;
	min_dist = 0;
	max_dist = 0;
	connect_mat = new double* [nodes];
	srand(static_cast <unsigned> (time(0)));
	for(int i = 0; i < nodes; i ++)
		connect_mat[i] = new double [nodes];
	for(int i = 0; i < nodes; i ++)
		for(int j = 0; j < nodes; j ++)
			if (i == j)
				set_edge_value(i, j, 0.0);
			else
				set_edge_value(i, j, -1);
}

Graph::Graph(double density, int nodes, double min_dist, double max_dist) {
	// TODO Auto-generated constructor stub
	this->min_dist = min_dist;
	this->max_dist = max_dist;
	this->nodes = nodes;
	connect_mat = new double* [nodes];
	srand(static_cast <unsigned> (time(0)));
	for(int i = 0; i < nodes; i ++)
		connect_mat[i] = new double [nodes];
	for(int i = 0; i < nodes; i ++)
		for(int j = 0; j < nodes; j ++)
			set_edge_value(i, j, 0.0);
	for(int i = 0; i < nodes - 1; i ++)
		for(int j = i + 1; j < nodes; j ++) {
			double prob = (double) rand() / (double) RAND_MAX;
			if(prob <= density)
				add(i, j);
			else {
				delete_edge(i, j);
				delete_edge(j, i);
			}
		}
}

Graph::~Graph() {
	// TODO Auto-generated destructor stub
	for(int i = 0; i < nodes; i++)
		delete connect_mat[i];
	delete connect_mat;
}

int Graph::V() {
	return nodes;
}

int Graph::E() {
	int edges = 0;
	for(int i = 0; i < V(); i++)
		for(int j = i + 1; j < V(); j++)
			if(connect_mat[i][j] > 0.0)
				edges++;
	return edges;
}

bool Graph::adjacent(int x, int y) {
	return (connect_mat[x][y] > 0.0);
}

vector<int> Graph::neighbors(int x) {
	vector<int> nbs;
	for(int i = 0; i < V(); i++)
		if(connect_mat[x][i] > 0.0)
			nbs.push_back(i);
	return nbs;
}

void Graph::add(int x, int y) {
	if(connect_mat[x][y] == 0.0) {
		//cout << "adding (" << x << "," << y << ")" << endl;
		double dist = min_dist + (((double) (rand()) / (double) (RAND_MAX)) * (max_dist - min_dist));
		set_edge_value(x, y, dist);
		set_edge_value(y, x, dist);
	}
}

void Graph::delete_edge(int x, int y) {
	if(x == y) {
		set_edge_value(x, y, 0.0);
		set_edge_value(y, x, 0.0);
		//connect_mat[x][y] = 0.0;
		//connect_mat[y][x] = 0.0;
	}
	else {
		set_edge_value(x, y, -1.0);
		set_edge_value(y, x, -1.0);
		//connect_mat[x][y] = -1;
		//connect_mat[y][x] = -1;
	}
}

/*double Graph::get_node_value(int x) {

}

void Graph::set_node_value(int x, double a) {

}*/

double Graph::get_edge_value(int x, int y) {
	return connect_mat[x][y];
}

void Graph::set_edge_value(int x, int y, double v) {
	if(x != y)
		connect_mat[x][y] = v;
	else if(connect_mat[x][y] != 0)
		connect_mat[x][y] = 0;
}

void Graph::print() {
	for(int i = 0; i < V(); i ++)
		cout << "\t" << i;
	cout << endl;
	for(int i = 0; i < V(); i ++) {
		cout << i << "\t";
		for(int j = 0; j < V(); j ++) {
			cout << connect_mat[i][j] << "\t";
		}
		cout << endl;
	}
}
