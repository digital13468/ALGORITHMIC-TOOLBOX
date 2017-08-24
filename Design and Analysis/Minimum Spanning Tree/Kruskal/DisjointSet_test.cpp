/*
 * DisjointSet_test.cpp
 *
 *  Created on: Aug 5, 2017
 *      Author: Chan-Ching Hsu
 */
#include "DisjointSet.h"
#include <iostream>
int main() {
	DisjointSet DS;
	DS.makeSet(0);
	DS.makeSet(1);
	DS.makeSet(2);
	DS.makeSet(3);
	DS.makeSet(4);
	DS.union_sets(0, 2);
	//cout << DS.find(2) << endl;
	DS.union_sets(3, 4);
	DS.union_sets(0, 1);
	DS.union_sets(1, 2);
	DS.union_sets(1, 3);
	cout << DS.size() << endl;
	cout << DS.find(0) << endl;
	cout << DS.find(1) << endl;
	cout << DS.find(2) << endl;
	cout << DS.find(3) << endl;
	cout << DS.find(4) << endl;
	//for(int i = 0; i < DS.size(); i++)
	//	cout << i << "'s root: " << DS.find(i) << endl;
	//DS.print();
}
