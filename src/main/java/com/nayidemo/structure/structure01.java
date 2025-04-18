package com.nayidemo.structure;

public class structure01 {
    public static void main(String[] args){

    }

    public static class CircularLinkedList<E> {
        private Node<E> head;
        private Node<E> tail;

        private static class Node<E> {
            E data;
            Node<E> next;
            Node(E data) {
                this.data = data;
            }
        }

        // 添加元素，保持链表为环形
        public void add(E data) {
            Node<E> newNode = new Node<>(data);
            if (head == null) {
                head = newNode;
                tail = newNode;
                newNode.next = head;
            } else {
                tail.next = newNode;
                tail = newNode;
                tail.next = head;
            }
        }

        // 其他操作方法可以自行扩展，比如删除、查找、遍历等
    }

}
