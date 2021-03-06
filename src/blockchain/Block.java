package blockchain;

import blockchain.constants.Constants;
import blockchain.utils.HashGenerator;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Block {

    private final String prevHash;
    private final String timeStamp;
    private final String merkleRootHash;
    private int nonce = -1;
    private final int difficulty;
    private final ArrayList<Transaction> blockTransactions;

    public Block(String prevHash, int difficulty, ArrayList<Transaction> transactions) {
        this.prevHash = prevHash;
        this.merkleRootHash = getMerkleRootHash();
        this.difficulty = difficulty;
        timeStamp = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss").format(new Date());
        blockTransactions = transactions;
    }

    private String getMerkleRootHash() {
        if (blockTransactions == null) {
            return null;
        } else if (blockTransactions.size() == 1) {
            return blockTransactions.get(0).getId();
        }

        ArrayList<String> merkle = new ArrayList<>();

        for (Transaction blockTransaction : blockTransactions) {
            merkle.add(blockTransaction.getId());
        }

        while (merkle.size() > 1) {
            int merkleSize = merkle.size();

            if (merkleSize % 2 != 0) {
                merkle.add(merkle.get(merkleSize - 1));
            }

            ArrayList<String> newMerkle = new ArrayList<>();

            for (int i =0; i < merkleSize; i += 2) {
                String concatHashes = merkle.get(i) + merkle.get(i + 1);
                newMerkle.add(HashGenerator.getSHA256Hash(concatHashes));
            }

            merkle = newMerkle;
        }

        return merkle.get(0);
    }

    public String getHeaderHash() {
        return HashGenerator.getSHA256Hash(prevHash + timeStamp + Constants.VERSION + merkleRootHash + nonce + difficulty);
    }

    public ArrayList<Transaction> getBlockTransactions() {
        return blockTransactions;
    }

    public boolean isMined(int mineTrials) {
        boolean isMined = false;
        int trialsCount = 0;

        while (trialsCount <= mineTrials && !isMined) {
            nonce++;
            trialsCount++;

            String newHash = getHeaderHash();
            int suitableCharsCount = 0;

            for (int i = 0; i < difficulty; i++) {
                if(newHash.charAt(i) == '0') {
                    suitableCharsCount++;
                }
            }

            if (suitableCharsCount == difficulty) {
                isMined = true;
            }
        }

        return isMined;
    }

    public String getTimeStamp() {
        return timeStamp;
    }
}
