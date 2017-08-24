/*
 * Prims's.cpp
 *
 *  Created on: Aug 4, 2017
 *      Author: Chan-Ching Hsu
 */

#include "Prims.h"
#include "PriorityQueue.h"
Prims::Prims(Graph& G): graph(G) {}
Prims::~Prims() {}

vector<int> Prims::tree(int s) {
	vector<double> distance (graph.V(), 0.0);
	vector<int> prev (graph.V(), -1);
	//Graph tree(graph.V());
	PriorityQueue PQ;
	for(int i = 0; i < graph.V(); i ++) {
		if(i == s) {
			distance[i] = 0;
			PQ.insert(QueueElement(i, 0));
		}
		else {
			distance[i] = INT_MAX;
			PQ.insert(QueueElement(i, INT_MAX));
		}
	}
	while(PQ.size() > 0) {
		QueueElement u = PQ.top();
		PQ.minPriority();
		vector<int> v = graph.neighbors(u.node);
		cout << u.node << " has " << v.size() << " nbrs." << endl;
		for(int i = 0; i < v.size(); i ++) {
			if (graph.get_edge_value(u.node, v[i]) < distance[v[i]]
				&& PQ.contains(QueueElement(v[i], distance[v[i]]))) {
				distance[v[i]] = graph.get_edge_value(u.node, v[i]);
				prev[v[i]] = u.node;
				cout << v[i] << endl;
				PQ.chgPriority(QueueElement (v[i], distance[v[i]]));
			}
		}
		for (int j = 0; j < prev.size(); j ++)
			cout << j << "'s parent:" << prev[j] << endl;
	}
	return prev;
}

double Prims::treeSize(int u) {
	vector<int> t = tree(u);
	double size = 0;
	if(!t.empty())
		for(int i = 0; i < t.size(); i ++)
			if(t[i] != -1)
				size = size + graph.get_edge_value(t[i], i);
	return size;
}

void Prims::print(int u) {
	vector<int> t = tree(u);
	for(int i = 0; i < t.size(); i ++)
		if(t[i] != -1)
			cout << i << "->" << t[i] << endl;
}
