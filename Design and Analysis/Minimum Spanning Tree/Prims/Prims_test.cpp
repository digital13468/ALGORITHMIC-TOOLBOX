/*
 * Prim's_test.cpp
 *
 *  Created on: Aug 4, 2017
 *      Author: Chan-Ching Hsu
 */

#include "Prims.h"
#include <iostream>
int main() {
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
	Prims Prim(G1);
	Prim.print(0);
	cout << "\n" << Prim.treeSize(0) << endl;

	Graph G2(8);
	G2.set_edge_value(0, 3, 6);
	G2.set_edge_value(3, 0, 6);
	G2.set_edge_value(0, 2, 8);
	G2.set_edge_value(2, 0, 8);
	G2.set_edge_value(2, 4, 1);
	G2.set_edge_value(4, 2, 1);
	G2.set_edge_value(1, 5, 7);
	G2.set_edge_value(5, 1, 7);
	G2.print();
	Prims Prim2(G2);
	Prim2.print(3);

	Graph G3(8);
	G3.set_edge_value(0, 1, 2);
	G3.set_edge_value(0, 2, 7);
	G3.set_edge_value(0, 3, 4);
	G3.set_edge_value(0, 4, 8);
	G3.set_edge_value(1, 0, 2);
	G3.set_edge_value(1, 3, 5);
	G3.set_edge_value(1, 5, 5);
	G3.set_edge_value(1, 6, 6);
	G3.set_edge_value(2, 0, 7);
	G3.set_edge_value(2, 4, 5);
	G3.set_edge_value(2, 5, 9);
	G3.set_edge_value(2, 6, 3);
	G3.set_edge_value(3, 0, 4);
	G3.set_edge_value(3, 1, 5);
	G3.set_edge_value(4, 0, 8);
	G3.set_edge_value(4, 2, 5);
	G3.set_edge_value(4, 7, 7);
	G3.set_edge_value(5, 1, 5);
	G3.set_edge_value(5, 2, 9);
	G3.set_edge_value(5, 6, 7);
	G3.set_edge_value(6, 1, 6);
	G3.set_edge_value(6, 2, 3);
	G3.set_edge_value(6, 5, 7);
	G3.set_edge_value(7, 4, 7);
	G3.print();
	Prims Prims3(G3);
	Prims3.print(4);

	Graph G4(8);
	G4.set_edge_value(0, 1, 5);
	G4.set_edge_value(0, 4, 3);
	G4.set_edge_value(1, 0, 5);
	G4.set_edge_value(1, 5, 3);
	G4.set_edge_value(2, 5, 9);
	G4.set_edge_value(3, 5, 1);
	G4.set_edge_value(4, 0, 3);
	G4.set_edge_value(5, 1, 3);
	G4.set_edge_value(5, 2, 9);
	G4.set_edge_value(5, 3, 1);
	Prims Prims4(G4);
	Prims4.print(5);
}
