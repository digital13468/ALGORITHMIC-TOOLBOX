/*
 * Kruskal.cpp
 *
 *  Created on: Aug 6, 2017
 *      Author: Chan-Ching Hsu
 */

#include "Kruskal.h"
//#include "DisjointSet.h"
#include <numeric>

Kruskal::Kruskal(Graph& G): graph(G) {}

Kruskal::~Kruskal() {}

vector<vector<int>> Kruskal::tree() {
	vector<vector<int>> prev (graph.V());
	DisjointSet DS;
	for(int i = 0; i < graph.V(); i++)
		DS.makeSet(i);
	vector<string> edges;
	vector<double> weights;
	for(int i = 0; i < graph.V()-1; i++)
		for(int j = i+1; j < graph.V(); j++)
			if(graph.adjacent(i, j)) {
				edges.push_back(to_string(i) + "->" +to_string(j));
				weights.push_back(graph.get_edge_value(i, j));
			}
	vector<int> idx(weights.size());
	iota(idx.begin(), idx.end(), 0);
	sort(idx.begin(), idx.end(),
			[&weights](int i1, int i2) {return weights[i1] < weights[i2];});
	for(int i = 0; i < idx.size(); i++) {
		string edge = edges[idx[i]]; //cout << edge << endl;
		int u = stoi(edge.substr(0, edge.find("->")));
		//cout << stoi(edge.substr(0, edge.find("->"))) << endl;
		int v = stoi(edge.substr(edge.find("->") + 2, edge.size()));
		//cout << v << endl;
		if(DS.find(u) != DS.find(v)) {
			//if(prev[v].empty())
				prev[v].push_back(u);//cout << u << ", " << v << endl;
			DS.union_sets(u, v);
		}
	}
	return prev;
}

double Kruskal::treeSize() {
	vector<vector<int>> t = tree();
	double size = 0;
	if(!t.empty())
		for(int i = 0; i < t.size(); i++)
			for(int j = 0; j < t[i].size(); j++)
			//if(t[i] != -1)
				size = size + graph.get_edge_value(t[i][j], i);
	return size;
}

void Kruskal::print() {
	vector<vector<int>> t = tree();
	for(int i = 0; i < t.size(); i++)
		for(int j = 0; j < t[i].size(); j++)//if(t[i] != -1)
			cout << i << "->" << t[i][j] << endl;
}
