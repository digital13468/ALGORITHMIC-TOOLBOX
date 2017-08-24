/*
 * DisjointSet.h
 *
 *  Created on: Aug 5, 2017
 *      Author: Chan-Ching Hsu
 */

#ifndef DISJOINTSET_H_
#define DISJOINTSET_H_

#include <vector>
#include <iostream>
using namespace std;

class SetElement {
public:
	//SetElement() {}
	SetElement(int node): node(node), parent(node), rank(0) {}
	int node;
	void setParent(int parent) { this->parent = parent; }
	void setRank(int rank) {this->rank = rank; }
	int getParent() { return parent; }
	int getRank() { return rank; }
private:
	int parent;
	int rank;
};

class DisjointSet {
public:
	DisjointSet();
	virtual ~DisjointSet();

	void makeSet(int x);
	int find(int x);
	void union_sets(int x, int y);

	int size() { return SE.size(); }
	void print() {
		for(int i = 0; i < SE.size(); i++)
			cout << i << "'s root: " << SE[i].getParent() << endl;
	}
private:
	vector<SetElement> SE;
};

#endif /* DISJOINTSET_H_ */
