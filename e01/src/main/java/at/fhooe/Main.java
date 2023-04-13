package at.fhooe;

import io.javalin.Javalin;

public class Main {
    public static void main(String[] args) {
        var app = Javalin.create(/*config*/)
                .before("/", new HashCash(1))
                .get("/", ctx -> ctx.result("Hello World"))
                .before("/greet", new HashCash(2))
                .get("/greet", ctx -> ctx.result("Hello there!"))
                .before("/upload", new HashCash(3))
                .post("/upload", ctx -> ctx.result(ctx.body()))
                .start(7070);
    }
}
