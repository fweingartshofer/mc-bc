package at.fhooe.model;

import io.javalin.openapi.JsonSchema;

@JsonSchema
public record Transaction(String sender, String recipient, double amount) {
}
