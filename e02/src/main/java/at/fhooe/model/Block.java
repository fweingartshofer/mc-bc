package at.fhooe.model;

import at.fhooe.strings.HashedString;
import io.javalin.openapi.JsonSchema;

import java.time.LocalDateTime;
import java.util.List;

@JsonSchema
public record Block(int index,
                    LocalDateTime dateTime,
                    List<Transaction> transactions,
                    long proof,
                    String previousHash) {

    public String hash() {
        return new HashedString(toString()).toString();
    }
}
