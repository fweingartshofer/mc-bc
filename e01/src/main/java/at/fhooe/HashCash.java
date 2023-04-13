package at.fhooe;

import io.javalin.http.Context;
import io.javalin.http.ForbiddenResponse;
import io.javalin.http.Handler;
import org.jetbrains.annotations.NotNull;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

public class HashCash implements Handler {

    private static final String SEPARATOR = ";";
    private static final String HASH_REST = "HashREST";
    private final int difficulty;

    public HashCash(int difficulty) {
        this.difficulty = difficulty;
    }

    @Override
    public void handle(@NotNull Context ctx) {
        // <timestamp>;<URL>;<random>;<counter>
        var pow = ctx.header(HASH_REST);
        if (pow == null) {
            throw new ForbiddenResponse();
        }
        ProofOfWork proofOfWork;
        try {
            proofOfWork = parse(pow);
        } catch (IllegalFormatException e) {
            throw new ForbiddenResponse();
        }
        var now = LocalDateTime.now(ZoneOffset.UTC);
        if (!proofOfWork.url().endsWith(ctx.matchedPath())
                || !(proofOfWork.timestamp().isBefore(now)
                && proofOfWork.timestamp().isAfter(now.minusMinutes(5)))) {
            throw new ForbiddenResponse();
        }


        var hash = new HashedString(proofOfWork.toString()).toString();
        if (!hash.startsWith("0".repeat(difficulty))) {
            throw new ForbiddenResponse();
        }
    }

    private static ProofOfWork parse(String pow) throws IllegalFormatException {
        var components = pow.split(SEPARATOR);
        if (components.length != 4) {
            throw new IllegalFormatException();
        }
        var datetime = LocalDateTime.ofInstant(Instant.ofEpochSecond(Integer.parseInt(components[0])),
                ZoneOffset.UTC);
        return new ProofOfWork(datetime, components[1], components[2], Integer.parseInt(components[3]));
    }

    record ProofOfWork(LocalDateTime timestamp, String url, String random, int counter) {
        @Override
        public String toString() {
            return "%s;%s;%s;%s".formatted(timestamp.toEpochSecond(ZoneOffset.UTC), url, random, counter);
        }
    }

    static class IllegalFormatException extends Exception{}
}
