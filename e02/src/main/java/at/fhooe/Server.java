package at.fhooe;

import at.fhooe.model.Blockchain;
import at.fhooe.resources.ChainResource;
import at.fhooe.resources.MineResource;
import at.fhooe.resources.TransactionResource;
import io.javalin.Javalin;
import io.javalin.config.JavalinConfig;
import io.javalin.openapi.plugin.OpenApiPlugin;
import io.javalin.openapi.plugin.OpenApiPluginConfiguration;
import io.javalin.openapi.plugin.swagger.SwaggerConfiguration;
import io.javalin.openapi.plugin.swagger.SwaggerPlugin;

public class Server {

    public static void main(String[] args) {
        Blockchain blockchain = new Blockchain();
        TransactionResource transactionResource = new TransactionResource(blockchain);
        MineResource mineResource = new MineResource(blockchain);
        ChainResource chainResource = new ChainResource(blockchain);
        var app = Javalin.create(Server::setupOpenAPI)
                .post("/transactions", transactionResource::createTransaction)
                .post("/transactions/new", transactionResource::createTransaction)
                .post("/mine", mineResource::mine)
                .get("/chain", chainResource::chain)
                .start(5000);
    }

    private static void setupOpenAPI(JavalinConfig config) {
        String deprecatedDocsPath = "/openapi";
        config.plugins.register(new OpenApiPlugin(
                        new OpenApiPluginConfiguration()
                                .withDocumentationPath("/openapi")
                                .withDefinitionConfiguration((version, definition) -> definition
                                        .withOpenApiInfo((openApiInfo) -> {
                                            openApiInfo.setTitle("Microblockchain");
                                            openApiInfo.setVersion("1.0.0");
                                        })
                                        .withServer((openApiServer) -> {
                                            openApiServer.setUrl(("http://localhost:{port}/"));
                                            openApiServer.setDescription("Server description goes here");
                                            openApiServer.addVariable("port", "5000", new String[]{}, "Port of the server");
                                        })
                                )
                )
        );
        SwaggerConfiguration swaggerConfiguration = new SwaggerConfiguration();
        swaggerConfiguration.setDocumentationPath(deprecatedDocsPath);
        config.plugins.register(new SwaggerPlugin(swaggerConfiguration));
    }
}
