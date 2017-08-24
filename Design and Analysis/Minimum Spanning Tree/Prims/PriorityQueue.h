/*
 * PriorityQueue.h
 *
 *  Created on: Jul 7, 2017
 *      Author: digital13468
 */

#ifndef PRIORITYQUEUE_H_
#define PRIORITYQUEUE_H_
#include <iostream>
#include <vector>
using namespace std;

class QueueElement {
public:
	QueueElement(int node, double value): node(node), value(value) {}
	double value;
	int node;
	void print() { cout << "(" << node << ", " << value << ")" << endl;}
};

class PriorityQueue {
public:
	//PriorityQueue(): head() {};// cursor(nullptr) {};
	PriorityQueue();
	virtual ~PriorityQueue();

	void chgPriority(QueueElement queue_element);
	void minPriority();
	bool contains(QueueElement queue_element);
	void insert(QueueElement queue_element);
	QueueElement top();
	int size();

private:
	vector<QueueElement> PQ;
	//QueueElement* head;
	//QueueElement* cursor;
};

#endif /* PRIORITYQUEUE_H_ */
