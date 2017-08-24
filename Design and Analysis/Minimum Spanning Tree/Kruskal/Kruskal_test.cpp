/*
 * Kruskal_test.cpp
 *
 *  Created on: Aug 6, 2017
 *      Author: digital13468
 */

#include "Kruskal.h"

int main(){
	Graph G1(9);
	G1.set_edge_value(0, 1, 4);
		G1.set_edge_value(1, 0, 4);
		G1.set_edge_value(0, 7, 8);
		G1.set_edge_value(7, 0, 8);
		G1.set_edge_value(1, 7, 11);
		G1.set_edge_value(7, 1, 11);
		G1.set_edge_value(1, 2, 8);
		G1.set_edge_value(2, 1, 8);
		G1.set_edge_value(2, 8, 2);
		G1.set_edge_value(8, 2, 2);
		G1.set_edge_value(7, 8, 7);
		G1.set_edge_value(8, 7, 7);
		G1.set_edge_value(8, 6, 6);
		G1.set_edge_value(6, 8, 6);
		G1.set_edge_value(7, 6, 1);
		G1.set_edge_value(6, 7, 1);
		G1.set_edge_value(2, 3, 7);
		G1.set_edge_value(3, 2, 7);
		G1.set_edge_value(3, 5, 14);
		G1.set_edge_value(5, 3, 14);
		G1.set_edge_value(2, 5, 4);
		G1.set_edge_value(5, 2, 4);
		G1.set_edge_value(6, 5, 2);
		G1.set_edge_value(5, 6, 2);
		G1.set_edge_value(3, 4, 9);
		G1.set_edge_value(4, 3, 9);
		G1.set_edge_value(4, 5, 10);
		G1.set_edge_value(5, 4, 10);
		G1.print();
		Kruskal kruskal1(G1);
		kruskal1.print();
		cout << "\n" << kruskal1.treeSize() << endl;

		Graph G2(8);
		G2.set_edge_value(0, 2, 6);
		G2.set_edge_value(0, 4, 8);
		G2.set_edge_value(1, 2, 4);
		G2.set_edge_value(1, 3, 5);
		G2.set_edge_value(1, 5, 1);
		G2.set_edge_value(1, 6, 1);
		G2.set_edge_value(2, 0, 6);
		G2.set_edge_value(2, 1, 4);
		G2.set_edge_value(3, 1, 5);
		G2.set_edge_value(4, 0, 8);
		G2.set_edge_value(4, 6, 1);
		G2.set_edge_value(5, 1, 1);
		G2.set_edge_value(5, 6, 1);
		G2.set_edge_value(5, 7, 2);
		G2.set_edge_value(6, 1, 1);
		G2.set_edge_value(6, 4, 1);
		G2.set_edge_value(6, 5, 1);
		G2.set_edge_value(7, 5, 2);
		Kruskal kruskal2(G2);
		kruskal2.print();
}
