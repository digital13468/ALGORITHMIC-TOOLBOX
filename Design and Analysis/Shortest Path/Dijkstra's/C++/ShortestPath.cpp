/*
 * ShortestPath.cpp
 *
 *  Created on: Jul 8, 2017
 *      Author: digital13468
 */

#include "ShortestPath.h"
#include "PriorityQueue.h"
//#include <cstddef>
//#include <iostream>
//using namespace std;
ShortestPath::ShortestPath(Graph& G): graph(G) {}
//ShortestPath::ShortestPath(Graph G): graph(G) {}
ShortestPath::~ShortestPath() {}

vector<int> ShortestPath::path(int s, int w) {
	vector<double> distance (graph.V(), 0.0);
	vector<int> prev (graph.V(), -1);
	PriorityQueue PQ;
	for(int i = 0; i < graph.V(); i ++) {
		if(i != s)
			distance[i] = INT_MAX;
		PQ.insert(QueueElement (i, distance[i]));
	}
	while(PQ.size() > 0) {
		QueueElement u = PQ.top();
		PQ.minPriority();
		if(u.node == w)
			break;
		else {
			vector<int> v = graph.neighbors(u.node);
			for(int i = 0; i < v.size(); i ++) {
				double alt = distance[u.node] + graph.get_edge_value(u.node, v[i]);
				if(alt < distance[v[i]]) {
					distance[v[i]] = alt;
					prev[v[i]] = u.node;
					PQ.chgPriority(QueueElement (v[i], alt));
				}
			}
		}
	}
	vector<int> S;
	int u = w;
	while(prev[u] != -1) {
		S.insert(S.begin(), u);
		u = prev[u];
	}
	if(u == s)
		S.insert(S.begin(), u);
	else
		S.erase(S.begin(), S.end());
	return S;
}

double ShortestPath::pathSize(int u, int w) {
	vector<int> p = path(u, w);
	double pathS = 0;
	if(!p.empty())
		for(int i = 0; i < p.size() - 1; i++)
			pathS = pathS + graph.get_edge_value(p[i], p[i + 1]);
	else
		if(u == w)
			pathS = 0;
		else
			pathS = -1;
	return pathS;
}

void ShortestPath::print(int u, int w) {
	vector<int> p = path(u, w);
	if(p.empty()){
		if(u == w)
			cout << u << " -> " << w;
		else
			cout << u << " x " << w;
	}
	else
		while(p.size() > 0) {
			cout << p.front();
			p.erase(p.begin());
			if(p.size() > 0)
				cout << " -> ";
		}
}
