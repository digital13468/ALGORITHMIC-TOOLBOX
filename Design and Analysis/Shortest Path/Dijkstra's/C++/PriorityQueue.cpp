/*
 * PriorityQueue.cpp
 *
 *  Created on: Jul 7, 2017
 *      Author: digital13468
 */

#include "PriorityQueue.h"
#include <cmath>
#include <iostream>
PriorityQueue::PriorityQueue() {
	// TODO Auto-generated constructor stub

}

PriorityQueue::~PriorityQueue() {
	// TODO Auto-generated destructor stub
	PQ.erase(PQ.begin(), PQ.end());
}

void downHeap(vector<QueueElement>& PQ, int parentPos = 0) {
	if(PQ.size() != 1) {
		//int parentPos = 0;
		int leftChildPos = parentPos * 2 + 1;
		int rightChildPos = parentPos * 2 + 2;
		while((leftChildPos < PQ.size() && PQ[parentPos].value > PQ[leftChildPos].value) ||
				(rightChildPos < PQ.size() && PQ[parentPos].value > PQ[rightChildPos].value)) {
			int childPos = leftChildPos;
			if(rightChildPos < PQ.size() && PQ[leftChildPos].value > PQ[rightChildPos].value)
				childPos = rightChildPos;
			QueueElement child = PQ[childPos];
			QueueElement parent = PQ[parentPos];
			PQ.insert(PQ.begin() + childPos, parent);
			PQ.erase(PQ.begin() + childPos + 1);
			PQ.insert(PQ.begin() + parentPos, child);
			PQ.erase(PQ.begin() + parentPos + 1);
			parentPos = childPos;
			leftChildPos = parentPos * 2 + 1;
			rightChildPos = parentPos * 2 + 2;
		}
	}
}

void upHeap(vector<QueueElement>& PQ, int childPos) {
	//int childPos = PQ.size() - 1;
	int parentPos = (childPos - 1) / 2;
	while(PQ[childPos].value < PQ[parentPos].value && childPos > 0) {
		QueueElement child = PQ[childPos];
		QueueElement parent = PQ[parentPos];
		PQ.insert(PQ.begin() + childPos, parent);
		PQ.erase(PQ.begin() + childPos + 1);
		PQ.insert(PQ.begin() + parentPos, child);
		PQ.erase(PQ.begin() + parentPos + 1);
		childPos = parentPos;
		parentPos = ceil(childPos - 1) / 2;
	}
}

void PriorityQueue::chgPriority(QueueElement QE) {
	for(int i = 0; i < PQ.size(); i++)
		if(PQ[i].node == QE.node) {
			if(PQ[i].value > QE.value) {
				PQ[i].value = QE.value;
				upHeap(PQ, i);
			}
			else {
				PQ[i].value = QE.value;
				downHeap(PQ, i);
			}
			break;
		}
}

void PriorityQueue::minPriority() {
	if(PQ.size() == 1)
		PQ.erase(PQ.begin());
	else if(!PQ.empty()) {
		PQ.erase(PQ.begin());
		PQ.insert(PQ.begin(), PQ.back());
		PQ.pop_back();
		downHeap(PQ);
	}
}

bool PriorityQueue::contains(QueueElement QE) {
	for(int i = 0; i < PQ.size(); i++)
		if(PQ[i].node == QE.node)
			return true;
	return false;
}

void PriorityQueue::insert(QueueElement QE) {
	if(!contains(QE)) {
		PQ.push_back(QE);
		upHeap(PQ, PQ.size() - 1);
	}
	else
		chgPriority(QE);
}

QueueElement PriorityQueue::top() {
	return PQ.front();
}

int PriorityQueue::size() {
	return PQ.size();
}
