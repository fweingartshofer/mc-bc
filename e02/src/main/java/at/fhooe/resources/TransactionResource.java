package at.fhooe.resources;

import at.fhooe.model.Blockchain;
import at.fhooe.model.Transaction;
import io.javalin.http.Context;
import io.javalin.http.HttpStatus;
import io.javalin.http.UnprocessableContentResponse;
import io.javalin.openapi.*;

public class TransactionResource {

    private final Blockchain blockchain;
    public TransactionResource(Blockchain blockchain) {
         this.blockchain = blockchain;
    }

    @OpenApi(
            path = "/transactions/",
            methods = HttpMethod.POST,
            requestBody = @OpenApiRequestBody(
                    content = {
                            @OpenApiContent(from = Transaction.class),
                    }
            ),
            responses = {
                    @OpenApiResponse(status = "201"),
                    @OpenApiResponse(status = "422", description = "Returned when the transaction is malformed, amount smaller or equal to zero, sender or recipient not set")
            }
    )
    public void createTransaction(Context ctx) {
        Transaction trans = ctx.bodyAsClass(Transaction.class);
        if (trans.sender() == null || trans.recipient() == null || trans.amount() <= 0) {
            throw new UnprocessableContentResponse();
        }
        blockchain.addTransaction(trans);
        ctx.status(HttpStatus.CREATED);
    }
}
