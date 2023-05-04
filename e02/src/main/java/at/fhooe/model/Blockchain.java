package at.fhooe.model;

import at.fhooe.strings.HashedString;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Blockchain {
    private static final int DIFFICULTY = 4;

    private final List<Block> chain;
    private final List<Transaction> transactions = new ArrayList<>();

    private Blockchain(List<Block> chain) {
        this.chain = new ArrayList<>(chain);
        buildBlock(100, new HashedString("I'm genesis").toString());
    }

    public Blockchain() {
        this(new ArrayList<>());
    }

    public List<Block> getChain() {
        return List.copyOf(chain);
    }

    public void buildBlock() {
        buildBlock(proofOfWork(), head().hash());
    }

    public void addTransaction(Transaction transaction) {
        transactions.add(transaction);
    }

    public Block head() {
        return chain.get(chain.size() - 1);
    }

    private void buildBlock(long proof, String previousHash) {
        chain.add(
                new Block(chain.size(), LocalDateTime.now(), List.copyOf(transactions), proof, previousHash)
        );
        transactions.clear();
    }

    private long proofOfWork() {
        long proof = 0;
        while (proofIsNotValid(proof)) {
            proof++;
        }
        return proof;
    }

    private boolean proofIsNotValid(long proof) {
        return !String.valueOf(new HashedString("%s%s".formatted(head().proof(), proof)))
                .startsWith("0".repeat(DIFFICULTY));
    }
}
