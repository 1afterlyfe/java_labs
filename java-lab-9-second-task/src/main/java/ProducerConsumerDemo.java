import java.util.Random;

public class ProducerConsumerDemo {
    private static final int FIRST_BUFFER_CAPACITY = 20;
    private static final int SECOND_BUFFER_CAPACITY = 20;
    private static final int PRODUCER_COUNT = 5;
    private static final int TRANSLATOR_COUNT = 2;
    private static final int MESSAGES_TO_PRINT = 100;

    public static void main(String[] args) throws InterruptedException {
        RingBuffer<String> firstBuffer = new RingBuffer<>(FIRST_BUFFER_CAPACITY);
        RingBuffer<String> secondBuffer = new RingBuffer<>(SECOND_BUFFER_CAPACITY);

        Random random = new Random();

        for (int i = 0; i < PRODUCER_COUNT; i++) {
            int producerId = i + 1;

            Thread producer = new Thread(() -> {
                int msgCounter = 1;
                try {
                    while (true) {
                        String message = "Потік No " + producerId +
                                " згенерував повідомлення " + msgCounter++;

                        firstBuffer.put(message);

                        Thread.sleep(10 + random.nextInt(40));
                    }
                } catch (InterruptedException e) {
                }
            }, "Producer-" + producerId);

            producer.setDaemon(true);
            producer.start();
        }

        for (int i = 0; i < TRANSLATOR_COUNT; i++) {
            int translatorId = i + 1;

            Thread translator = new Thread(() -> {
                try {
                    while (true) {
                        String original = firstBuffer.take();

                        String translated = "Потік No " + translatorId +
                                " переклав повідомлення: " + original;

                        secondBuffer.put(translated);
                        Thread.sleep(20 + random.nextInt(40));
                    }
                } catch (InterruptedException e) {
                }
            }, "Translator-" + translatorId);

            translator.setDaemon(true); // теж демон
            translator.start();
        }

        for (int i = 0; i < MESSAGES_TO_PRINT; i++) {
            String message = secondBuffer.take();
            System.out.println((i + 1) + ": " + message);
        }

        System.out.println("Головний потік прочитав " + MESSAGES_TO_PRINT + " повідомлень. Завершення програми.");
    }
}

