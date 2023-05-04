package at.fhooe.resources;

import at.fhooe.model.Block;
import at.fhooe.model.Blockchain;
import io.javalin.http.Context;
import io.javalin.http.HttpStatus;
import io.javalin.openapi.*;

public class MineResource {
    private final Blockchain blockchain;

    public MineResource(Blockchain blockchain) {
        this.blockchain = blockchain;
    }

    @OpenApi(
            path = "/mine/",
            methods = HttpMethod.POST,
            responses = {
                    @OpenApiResponse(status = "200", content = @OpenApiContent( from = Block.class)),
            }
    )
    public void mine(Context ctx) {
        blockchain.buildBlock();
        ctx.json(blockchain.head());
        ctx.status(HttpStatus.OK);
    }
}
