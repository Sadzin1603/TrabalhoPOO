plugins {
    application
}

repositories {
    mavenCentral()
}

dependencies {
    // Driver JDBC do MariaDB
    implementation("org.mariadb.jdbc:mariadb-java-client:3.5.6")
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(25)
    }
}

application {
    mainClass = "edu.curso.fronteira.MenuPrincipalView"
}
