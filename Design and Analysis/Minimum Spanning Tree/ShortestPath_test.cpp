/*
 * ShortestPath_test.cpp
 *
 *  Created on: Jul 8, 2017
 *      Author: digital13468
 */

#include "ShortestPath.h"
#include <iostream>
using namespace std;

int main() {
	Graph G(0.1, 50, 1.0, 10.0);
	G.print();
	ShortestPath SP(G);
	for(int i = 0; i < G.V(); i++)
		for(int j = 0; j < G.V(); j++){
			//if(i != j) {
				cout << i << " to " << j << ": ";
				SP.print(i, j);
				cout << " in " << SP.pathSize(i, j) << endl;
			}

	Graph G1(4);
	G1.set_edge_value(0, 1, 24);
	G1.set_edge_value(1, 0, 24);
	G1.set_edge_value(0, 3, 20);
	G1.set_edge_value(3, 0, 20);
	G1.set_edge_value(0, 2, 3);
	G1.set_edge_value(3, 0, 3);
	G1.set_edge_value(2, 3, 12);
	G1.set_edge_value(3, 2, 12);
	ShortestPath SP1(G1);
	cout << SP1.pathSize(0, 1) << endl;
	cout << SP1.pathSize(0, 2) << endl;
	cout << SP1.pathSize(0, 3) << endl;

	Graph G2(9);
	G2.set_edge_value(0, 1, 4);
	G2.set_edge_value(1, 0, 4);
	G2.set_edge_value(0, 7, 8);
	G2.set_edge_value(7, 0, 8);
	G2.set_edge_value(1, 7, 11);
	G2.set_edge_value(7, 1, 11);
	G2.set_edge_value(1, 2, 8);
	G2.set_edge_value(2, 1, 8);
	G2.set_edge_value(7, 8, 7);
	G2.set_edge_value(8, 7, 7);
	G2.set_edge_value(7, 6, 1);
	G2.set_edge_value(6, 7, 1);
	G2.set_edge_value(2, 8, 2);
	G2.set_edge_value(8, 2, 2);
	G2.set_edge_value(8, 6, 6);
	G2.set_edge_value(6, 8, 6);
	G2.set_edge_value(2, 3, 7);
	G2.set_edge_value(3, 2, 7);
	G2.set_edge_value(2, 5, 4);
	G2.set_edge_value(5, 2, 4);
	G2.set_edge_value(6, 5, 2);
	G2.set_edge_value(5, 6, 2);
	G2.set_edge_value(3, 5, 14);
	G2.set_edge_value(5, 3, 14);
	G2.set_edge_value(3, 4, 9);
	G2.set_edge_value(4, 3, 9);
	G2.set_edge_value(5, 4, 10);
	G2.set_edge_value(4, 5, 10);
	ShortestPath SP2(G2);
	for(int i = 1; i < G2.V(); i ++) {
		SP2.print(0, i);
		cout << endl;
	}
}
