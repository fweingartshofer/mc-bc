package at.fhooe.resources;

import at.fhooe.model.Block;
import at.fhooe.model.Blockchain;
import io.javalin.http.Context;
import io.javalin.http.HttpStatus;
import io.javalin.openapi.HttpMethod;
import io.javalin.openapi.OpenApi;
import io.javalin.openapi.OpenApiContent;
import io.javalin.openapi.OpenApiResponse;

public class ChainResource {
    private final Blockchain blockchain;


    public ChainResource(Blockchain blockchain) {
        this.blockchain = blockchain;
    }

    @OpenApi(
            path = "/chain/",
            methods = HttpMethod.GET,
            responses = {
                    @OpenApiResponse(status = "200", content = @OpenApiContent( from = Block[].class)),
            }
    )
    public void chain(Context ctx) {
        ctx.json(blockchain.getChain());
        ctx.status(HttpStatus.OK);
    }
}
