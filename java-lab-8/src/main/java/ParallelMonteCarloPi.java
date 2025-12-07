public class ParallelMonteCarloPi {

    private static final long ITERATIONS = 1_000_000_000L;

    public static void main(String[] args) {
        if (args.length != 1) {
            System.err.println("Usage: java ParallelMonteCarloPi <threads>");
            return;
        }

        int threadsCount;
        try {
            threadsCount = Integer.parseInt(args[0]);
            if (threadsCount <= 0) {
                throw new NumberFormatException();
            }
        } catch (NumberFormatException e) {
            System.err.println("Threads must be positive integer");
            return;
        }

        Thread[] threads = new Thread[threadsCount];
        long[] results = new long[threadsCount];

        long baseIterationsPerThread = ITERATIONS / threadsCount;
        long remainder = ITERATIONS % threadsCount; // якщо не ділиться порівну

        long startTime = System.nanoTime();

        for (int i = 0; i < threadsCount; i++) {
            long itersForThisThread = baseIterationsPerThread + (i < remainder ? 1 : 0);
            final int index = i;

            threads[i] = new Thread(() -> {
                java.util.Random random = new java.util.Random();

                long insideCircle = 0;
                for (long j = 0; j < itersForThisThread; j++) {
                    double x = random.nextDouble(); // [0,1)
                    double y = random.nextDouble(); // [0,1)

                    if (x * x + y * y <= 1.0) {
                        insideCircle++;
                    }
                }

                results[index] = insideCircle;
            });

            threads[i].start();
        }

        for (int i = 0; i < threadsCount; i++) {
            try {
                threads[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
                return;
            }
        }

        long endTime = System.nanoTime();

        long totalInsideCircle = 0;
        for (long c : results) {
            totalInsideCircle += c;
        }

        double piEstimate = 4.0 * totalInsideCircle / ITERATIONS;
        double timeMs = (endTime - startTime) / 1_000_000.0;

        System.out.printf("PI is %.5f%n", piEstimate);
        System.out.println("THREADS " + threadsCount);
        System.out.println("ITERATIONS " + String.format("%,d", ITERATIONS));
        System.out.printf("TIME %.2fms%n", timeMs);
    }
}
