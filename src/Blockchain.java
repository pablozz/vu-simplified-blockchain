import utils.RandomGenerator;

import java.util.ArrayList;

public class Blockchain {

    private final int usersAmount;
    private final ArrayList<User> users = new ArrayList<>();
    private final int transactionsAmount;
    private final ArrayList<Transaction> transactions = new ArrayList<>();
    private final RandomGenerator random = new RandomGenerator();
    private final HashGenerator hashGenerator = new HashGenerator();


    Blockchain(int usersAmount, int transactionsAmount) {
        this.usersAmount = usersAmount;
        generateRandomUsers();
        this.transactionsAmount = transactionsAmount;
        generateRandomTransactions();
    }

    private void generateRandomUsers() {
        for (int i = 0; i < usersAmount; i++) {
            String name = "User" + (i + 1);
            String publicKey = hashGenerator.getHash(Integer.toString(i));
            int MIN_USER_BALANCE = 1000;
            int MAX_USER_BALANCE = 1000000;
            double balance = random.getRandomDouble(MIN_USER_BALANCE, MAX_USER_BALANCE);
            User newUser = new User(name, publicKey, balance);

            users.add(newUser);
        }
    }

    private void generateRandomTransactions() {
        Transaction transaction;

        for (int i = 0; i < transactionsAmount; i++) {
            User fromUser = users.get(random.getRandomInt(0, usersAmount - 1));
            User toUser = users.get(random.getRandomInt(0, usersAmount - 1));
            double sum = random.getRandomDouble(0, fromUser.getBalance());
            String fromUserPk = fromUser.getPublicKey();
            String toUserPk = toUser.getPublicKey();
            String transactionId = hashGenerator.getHash(fromUserPk + toUserPk + sum);
            transaction = new Transaction(transactionId, fromUserPk, toUserPk, sum);

            transactions.add(transaction);
        }
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public ArrayList<Transaction> getTransactions() {
        return transactions;
    }
}
