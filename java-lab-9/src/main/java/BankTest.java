import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class BankTest {
    public static void main(String[] args) throws InterruptedException {
        Bank bank = new Bank();
        List<Account> accounts = new ArrayList<>();
        Random random = new Random();

        int accountsCount = 100;
        int maxInitialBalance = 10_000;

        for (int i = 0; i < accountsCount; i++) {
            int initialBalance = random.nextInt(maxInitialBalance + 1);
            accounts.add(new Account(i, initialBalance));
        }

        long initialTotal = totalBalance(accounts);
        System.out.println("Загальний баланс: " + initialTotal);

        int transfersCount = 50_000;
        int maxTransferAmount = 500;

        ExecutorService executor = Executors.newFixedThreadPool(100);

        for (int i = 0; i < transfersCount; i++) {
            executor.submit(() -> {
                Account from = accounts.get(random.nextInt(accounts.size()));
                Account to = accounts.get(random.nextInt(accounts.size()));

                if (from == to) {
                    return;
                }

                int amount = random.nextInt(maxTransferAmount + 1);

                bank.transfer(from, to, amount);
            });
        }

        executor.shutdown();
        executor.awaitTermination(1, TimeUnit.MINUTES);

        long finalTotal = totalBalance(accounts);
        System.out.println("Підсумковий загальний баланс: " + finalTotal);

        if (initialTotal == finalTotal) {
            System.out.println("Успіх: баланс зберігається");
        } else {
            System.out.println("Помилка: баланс не збігається");
        }
    }

    private static long totalBalance(List<Account> accounts) {
        long sum = 0;
        for (Account acc : accounts) {
            sum += acc.getBalance();
        }
        return sum;
    }
}
