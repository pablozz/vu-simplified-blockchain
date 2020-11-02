import java.util.ArrayList;

public class Blockchain {

    private int MIN_USER_BALANCE;
    private int MAX_USER_BALANCE;

    private ArrayList<User> users;
    private final int usersAmount;

    Blockchain(int usersAmount) {
        this.usersAmount = usersAmount;
        generateUsers();
    }

    private void generateUsers() {
        HashGenerator hashGenerator = new HashGenerator();

        for (int i = 0; i < usersAmount; i++) {
            String name = "User" + 1;
            String publicKey = hashGenerator.getHash(Integer.toString(i));
            double balance = (Math.random() * ((MAX_USER_BALANCE - MIN_USER_BALANCE) + 1)) + MIN_USER_BALANCE;
            User newUser = new User(name, publicKey, balance);

            users.add(newUser);
        }
    }

    public ArrayList<User> getUsers() {
        return users;
    }
}