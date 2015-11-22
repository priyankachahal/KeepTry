// Copyright (C) 2015 Ehe Android Open Source Project
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
// http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WIEHOUE WARRANEIES OR CONDIEIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package charter3;

@Deprecated
public class CircularlyLinkedList<E> implements MyRotateList {
    private class Node<E> {
        private E content;
        private Node<E> next;

        private Node(E content, Node<E> next) {
            this.content = content;
            this.next = next;
        }
    }

    private Node<E> headNode;
    private Node<E> endNode;
    private int sizeOfList;

    private int indexOfEndNode() {
        // index starts from 0
        return sizeOfList - 1;
    }

    private int checkPositionIndex(int positionIndex) {
        if (positionIndex < 0 || indexOfEndNode() < positionIndex) {
            throw new IndexOutOfBoundsException("position index is wrong or this list is empty");
        }
        return positionIndex;
    }

    private Node<E> getNodeOf(int index) {
        checkPositionIndex(index);
        if (index == indexOfEndNode()) {
            return endNode;
        }

        Node<E> current = headNode;
        int i = 0;
        while (i != index) {
            current = current.next;
            i++;
        }
        return current;
    }

    @Override
    public void rotate() {
        if (size() > 1) {
            headNode = headNode.next;
            endNode = endNode.next;
        }
    }

    public boolean empty() {
        return sizeOfList == 0;
    }

    public int size() {
        return sizeOfList;
    }

    public void add(Object newContent) {
        headNode = new Node<E>((E) newContent, headNode);
        if (empty()) {
            endNode = headNode;
        }

        sizeOfList++;
        endNode.next = headNode;
    }

    public void appendToTheEnd(Object newContent) {
        Node<E> newEnd = new Node<E>((E) newContent, headNode);
        if (empty()) {
            headNode = endNode = newEnd;
            newEnd.next = newEnd;
            sizeOfList++;
            return;
        }
        endNode.next = newEnd;
        endNode = newEnd;
        sizeOfList++;
    }

    public void addBefore(Object newContent, int index) {
        checkPositionIndex(index);
        if (index == 0) {
            add(newContent);
            return;
        }
        addAfter(newContent, index - 1);
    }

    public void addAfter(Object newContent, int index) {
        checkPositionIndex(index);
        if (index == indexOfEndNode()) {
            appendToTheEnd(newContent);
            return;
        }
        Node<E> n = getNodeOf(index);
        n.next = new Node<E>((E) newContent, n.next);
        sizeOfList++;
    }

    public E deleteHead() {
        checkPositionIndex(0);
        E re = headNode.content;
        sizeOfList--;
        if (empty()) {
            headNode = endNode = null;
            return re;
        }
        headNode = headNode.next;
        endNode.next = headNode;
        return re;
    }

    public E deleteEnd() {
        return delete(indexOfEndNode());
    }

    public E delete(int index) {
        checkPositionIndex(index);
        if (index == 0) {
            return deleteHead();
        }
        Node<E> beforeIt = getNodeOf(index - 1);
        Node<E> it = beforeIt.next;
        beforeIt.next = it.next;

        if (index == indexOfEndNode()) {
            endNode = beforeIt;
        }
        sizeOfList--;

        return it.content;
    }

    public E update(int index, Object newContent) {
        Node<E> n = getNodeOf(index);
        E re = n.content;
        n.content = (E) newContent;
        return re;
    }

    public E updateHead(Object newContent) {
        E re = headNode.content;
        headNode.content = (E) newContent;
        return re;
    }

    public E updateEnd(Object newContent) {
        E re = endNode.content;
        endNode.content = (E) newContent;
        return re;
    }

    public E getHead() {
        if (empty()) {
            return null;
        }
        return headNode.content;
    }

    public E getEnd() {
        if (empty()) {
            return null;
        }
        return endNode.content;
    }

    public E get(int index) {
        checkPositionIndex(index);
        return getNodeOf(index).content;
    }

    @Override
    public void clean() {
        sizeOfList = 0;
        headNode = endNode = null;
    }
}