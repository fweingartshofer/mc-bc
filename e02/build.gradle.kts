plugins {
    id("java")
    application
}

group = "at.fhooe"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.slf4j:slf4j-simple:2.0.6")
    implementation("io.javalin:javalin:5.4.2")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:2.14.2")

    val openapi = "5.4.2"

    // For Java projects
    annotationProcessor("io.javalin.community.openapi:openapi-annotation-processor:$openapi")
    implementation("io.javalin.community.openapi:javalin-openapi-plugin:$openapi") // for /openapi route with JSON scheme
    implementation("io.javalin.community.openapi:javalin-swagger-plugin:$openapi") // for Swagger UI
    implementation("io.javalin.community.openapi:javalin-redoc-plugin:$openapi") // for ReDoc UI
}

tasks.test {
    useJUnitPlatform()
}

application {
    mainClass.set("at.fhooe.Server")
}
