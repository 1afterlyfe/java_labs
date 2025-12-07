public class Bank {

    public void transfer(Account from, Account to, int amount) {
        if (from == to) {
            return;
        }

        Account firstLock;
        Account secondLock;

        if (from.getId() < to.getId()) {
            firstLock = from;
            secondLock = to;
        } else {
            firstLock = to;
            secondLock = from;
        }

        synchronized (firstLock) {
            synchronized (secondLock) {
                if (from.withdraw(amount)) {
                    to.deposit(amount);
                }
            }
        }
    }
}
