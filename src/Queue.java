public class Queue<E> {
    public static class Node<E> {
        private E data;
        private Node<E> next;
        private Node<E> prev;

        public Node(E data) {
            this.data = data;
            this.next = null;
            this.prev = null;
        }

        public E getData() {
            return data;
        }

        public void setData(E data) {
            this.data = data;
        }

        public Node<E> getNext() {
            return next;
        }

        public void setNext(Node<E> next) {
            this.next = next;
        }

        public Node<E> getPrev() {
            return prev;
        }

        public void setPrev(Node<E> prev) {
            this.prev = prev;
        }
    }
    private Node<E> front;
    private Node<E> rear;

    public Queue() {
        this.front = null;
        this.rear = null;
    }

    public void enqueue(E data) {
        Node<E> newNode = new Node<>(data);
        if (isEmpty()) {
            front = rear = newNode;
        } else {
            newNode.setPrev(rear);
            rear.setNext(newNode);
            rear = newNode;
        }
    }

    public E dequeue() {
        if (isEmpty()) {
            throw new IllegalStateException("Queue is empty");
        }

        E data = front.getData();
        front = front.getNext();

        if (front != null) {
            front.setPrev(null);
        } else {
            rear = null; // Queue is now empty
        }

        return data;
    }

    public boolean isEmpty() {
        return front == null;
    }

    // Other methods can be added based on the requirements
}
