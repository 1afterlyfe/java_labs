public class RingBuffer<T> {
    private final Object[] buffer;
    private final int capacity;

    private int head = 0;
    private int tail = 0;
    private int count = 0;

    public RingBuffer(int capacity) {
        if (capacity <= 0) {
            throw new IllegalArgumentException("Capacity must be > 0");
        }
        this.capacity = capacity;
        this.buffer = new Object[capacity];
    }

    public synchronized void put(T value) throws InterruptedException {
        while (count == capacity) {
            wait();
        }

        buffer[tail] = value;
        tail = (tail + 1) % capacity;
        count++;

        notifyAll();
    }

    @SuppressWarnings("unchecked")
    public synchronized T take() throws InterruptedException {
        while (count == 0) {
            wait();
        }

        T value = (T) buffer[head];
        buffer[head] = null;
        head = (head + 1) % capacity;
        count--;
        
        notifyAll();

        return value;
    }

    public synchronized int size() {
        return count;
    }
}
