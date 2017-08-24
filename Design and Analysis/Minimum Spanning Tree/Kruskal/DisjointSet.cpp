/*
 * DisjointSet.cpp
 *
 *  Created on: Aug 5, 2017
 *      Author: Chan-Ching Hsu
 */

#include "DisjointSet.h"
#include <iostream>

DisjointSet::DisjointSet() {}

DisjointSet::~DisjointSet() { SE.erase(SE.begin(), SE.end()); }

void DisjointSet::makeSet(int x) {
	bool present = false;
	for(int i = 0; i < SE.size(); i++)
		if(x == SE[i].node) {
			present = true;
			break;
		}
	if(!present)
		SE.push_back(SetElement(x));
}

int getSetElement(vector<SetElement>& SE, int x) {
	//SetElement se;
	for(int i = 0; i < SE.size(); i++)
		if(SE[i].node == x)
			return i;
	return -1;
}

int DisjointSet::find(int x) {
	if(SE[getSetElement(SE, x)].getParent() != x)
		return find(SE[getSetElement(SE, x)].getParent());
	else
		return x;
}

void DisjointSet::union_sets(int x, int y) {
	SetElement* xRoot = &SE[getSetElement(SE, find(x))];
	SetElement* yRoot = &SE[getSetElement(SE, find(y))];
	//cout << "union " << x << " and " << y << endl;
	//cout << y << "'s root: " << yRoot->node << endl;
	if(xRoot->node == yRoot->node) { //cout << "same set" << endl;
		return;
	}
	if(xRoot->getRank() < yRoot->getRank())
		xRoot->setParent(yRoot->node);
	else if(xRoot->getRank() > yRoot->getRank())
		yRoot->setParent(xRoot->node);
	else {
		yRoot->setParent(xRoot->node); //cout << yRoot.node << "'s root" << yRoot.getParent() << endl;
		xRoot->setRank(xRoot->getRank() + 1);

	}
	/*for(int i = 0; i < SE.size(); i++) {
		cout << i << "'s parent: " << SE[i].getParent() << endl;
		cout << i << "'s root: " << find(i) << endl;
	}*/
}
