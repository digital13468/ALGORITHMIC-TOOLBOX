/*
 * PriorityQueue_test.cpp
 *
 *  Created on: Jul 7, 2017
 *      Author: digital13468
 */

#include "PriorityQueue.h"
#include <iostream>
using namespace std;
int main() {
	PriorityQueue pq;
	pq.insert(QueueElement(1, 35));
	pq.insert(QueueElement(2, 33));
	pq.insert(QueueElement(3, 42));
	pq.insert(QueueElement(4, 10));
	pq.insert(QueueElement(5, 14));
	pq.insert(QueueElement(6, 19));
	pq.insert(QueueElement(7, 27));
	pq.insert(QueueElement(8, 44));
	pq.insert(QueueElement(9, 26));
	pq.insert(QueueElement(10, 31));
	cout << pq.contains(QueueElement(1, 35)) << endl;
	cout << pq.contains(QueueElement(2, 34)) << endl;
	while(pq.size() > 0) {
		pq.top().print();
		pq.minPriority();
	}
}
