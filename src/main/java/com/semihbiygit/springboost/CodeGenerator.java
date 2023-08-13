package com.semihbiygit.springboost;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import static com.semihbiygit.springboost.ANSIColors.*;

public class CodeGenerator {


    public void generateProject(String projectName, String packageName, String dbType) {
        File projectDir = new File(projectName);
        if (!projectDir.exists()) {
            projectDir.mkdir();
        }

        String packagePath = packageName.replace('.', '/');
        File packageDir = new File(projectName + "/src/main/java/" + packagePath);
        File modelDir = new File(packageDir, "model");
        File repositoryDir = new File(packageDir, "repository");
        File serviceDir = new File(packageDir, "service");
        File controllerDir = new File(packageDir, "controller");
        packageDir.mkdirs();


        File resourcesDir = new File(projectName + "/src/main/resources");
        resourcesDir.mkdirs();

        File testDir = new File(projectName + "/src/test/java/" + packagePath);
        testDir.mkdirs();


        generateApplicationProperties(resourcesDir, dbType);
        generateMainClass(packageDir, packageName);
        generateModelClass(modelDir, packageName);
        generateRepositoryClass(repositoryDir, packageName);
        generateServiceClass(serviceDir, packageName);
        generateControllerClass(controllerDir, packageName);
        generateTests(testDir, packageName);
        writePom(projectDir, projectName, dbType);

        System.out.println(ANSI_BOLD + ANSI_GREEN + "Project successfully generated!" + ANSI_RESET);
    }

    private void generateApplicationProperties(File resourcesDir, String dbType) {
        String applicationPropertiesContent = "# Application properties here\n"
                + "server.port=8080\n";

        if ("mysql".equalsIgnoreCase(dbType)) {
            applicationPropertiesContent += "spring.datasource.url=jdbc:mysql://localhost:3306/boostdb\n"
                    + "spring.datasource.username=root\n"
                    + "spring.datasource.password=123456\n"
                    + "spring.jpa.hibernate.ddl-auto=update\n"
                    + "spring.jpa.show-sql=true\n"
                    + "spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL5Dialect\n";
        } else if ("h2".equalsIgnoreCase(dbType)) {
            applicationPropertiesContent += "spring.datasource.url=jdbc:h2:mem:testdb\n"
                    + "spring.datasource.username=sa\n"
                    + "spring.datasource.password=\n"
                    + "spring.jpa.hibernate.ddl-auto=update\n"
                    + "spring.jpa.show-sql=true\n"
                    + "spring.h2.console.enabled=true\n";
        } else if ("postgresql".equalsIgnoreCase(dbType)) {
            applicationPropertiesContent += "spring.datasource.url=jdbc:postgresql://localhost:5432/boostdb\n"
                    + "spring.datasource.username=postgres\n"
                    + "spring.datasource.password=123456\n"
                    + "spring.jpa.hibernate.ddl-auto=update\n"
                    + "spring.jpa.show-sql=true\n"
                    + "spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect\n";
        } else if ("mssql".equalsIgnoreCase(dbType)) {
            applicationPropertiesContent += "spring.datasource.url=jdbc:sqlserver://localhost:1433;databaseName=boostdb\n"
                    + "spring.datasource.username=sa\n"
                    + "spring.datasource.password=123456\n"
                    + "spring.jpa.hibernate.ddl-auto=update\n"
                    + "spring.jpa.show-sql=true\n"
                    + "spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.SQLServerDialect\n";
        } else {
            System.out.println("Invalid database type: " + dbType);
            System.exit(0);
        }

        writeToFile(resourcesDir, "application.properties", applicationPropertiesContent);
    }

    private void generateMainClass(File packageDir, String packageName) {
        String mainContent = "//Main class code here\n"
                + "package " + packageName + ";\n\n"
                + "import org.springframework.boot.SpringApplication;\n"
                + "import org.springframework.boot.autoconfigure.SpringBootApplication;\n\n"
                + "@SpringBootApplication\n"
                + "public class BoostApplication {\n\n"
                + "    public static void main(String[] args) {\n"
                + "        SpringApplication.run(BoostApplication.class, args);\n"
                + "    }\n\n"
                + "}\n";
        writeToFile(packageDir, "BoostApplication.java", mainContent);
    }

    private void generateModelClass(File packageDir, String packageName) {
        String modelContent = "// Model class code here\n"
                + "package " + packageName + ";\n\n"
                + "import jakarta.persistence.*;\n\n"
                + "@Entity\n"
                + "@Table\n"
                + "public class BoostModel {\n\n"
                + "    // Fields and methods for the data entity\n\n"
                + "}\n";

        writeToFile(packageDir, "BoostModel.java", modelContent);
    }

    private void generateRepositoryClass(File packageDir, String packageName) {
        String repositoryContent = "// Repository class code here\n"
                + "package " + packageName + ";\n\n"
                + "import org.springframework.data.jpa.repository.JpaRepository;\n"
                + "import org.springframework.stereotype.Repository;\n\n"
                + "@Repository\n"
                + "public interface BoostRepository extends JpaRepository<BoostModel, Long> {\n\n"
                + "    // Data source details and connection here \n\n"
                + "    // CRUD operations and data interaction methods\n\n"
                + "}\n";

        writeToFile(packageDir, "BoostRepository.java", repositoryContent);
    }

    private void generateServiceClass(File packageDir, String packageName) {
        String serviceContent = "// Service class code here\n"
                + "package " + packageName + ";\n\n"
                + "import org.springframework.stereotype.Service;\n\n"
                + "@Service\n"
                + "public class BoostService {\n\n"
                + "    // Business logic implementation and interaction with Repository\n\n"
                + "}\n";

        writeToFile(packageDir, "BoostService.java", serviceContent);
    }

    private void generateControllerClass(File packageDir, String packageName) {
        String controllerContent = "// Controller class code here\n"
                + "package " + packageName + ";\n\n"
                + "import org.springframework.web.bind.annotation.*;\n\n"
                + "@RestController\n"
                + "@RequestMapping\n"
                + "public class BoostController {\n\n"
                + "    //  Mappings (GET,POST,PUT,DELETE,PATCH) and interaction with Service\n\n"
                + "}\n";

        writeToFile(packageDir, "BoostController.java", controllerContent);
    }

    private void generateTests(File testDir, String packageName) {
        String testContent = "// Test class code here\n"
                + "package " + packageName + ";\n\n"
                + "import org.junit.jupiter.api.Test;\n"
                + "import org.springframework.boot.test.context.SpringBootTest;\n\n"
                + "@SpringBootTest\n"
                + "class BoostApplicationTests {\n\n"
                + "    @Test\n"
                + "    void contextLoads() {\n"
                + "    }\n\n"
                + "}\n";

        writeToFile(testDir, "BoostApplicationTests.java", testContent);
    }

    private void writePom(File projectDir, String packageName, String dbType) {
        String javaVersion = "17";
        String springBootVersion = "2.4.2";
        String jpaVersion = "2.2.3";
        String mysqlVersion = "8.0.33";
        String h2Version = "2.2.220";
        String postgresqlVersion = "42.6.0";
        String mssqlVersion = "12.4.0.jre11";


        String dbDependency = "";
        switch (dbType.toLowerCase()) {
            case "mysql":
                dbDependency = "<dependency>\n" +
                        "           <groupId>mysql</groupId>\n" +
                        "           <artifactId>mysql-connector-java</artifactId>\n" +
                        "           <version>" + mysqlVersion + "</version>\n" +
                        "       </dependency>\n";
                break;
            case "h2":
                dbDependency = "<dependency>\n" +
                        "           <groupId>com.h2database</groupId>\n" +
                        "           <artifactId>h2</artifactId>\n" +
                        "           <version>" + h2Version + "</version>\n" +
                        "           <scope>test</scope>\n" +
                        "       </dependency>\n";
                break;
            case "postgresql":
                dbDependency = "<dependency>\n" +
                        "           <groupId>org.postgresql</groupId>\n" +
                        "           <artifactId>postgresql</artifactId>\n" +
                        "           <version>" + postgresqlVersion + "</version>\n" +
                        "       </dependency>\n";
                break;
            case "mssql":
                dbDependency = "<dependency>\n" +
                        "           <groupId>com.microsoft.sqlserver</groupId>\n" +
                        "           <artifactId>mssql-jdbc</artifactId>\n" +
                        "           <version>" + mssqlVersion + "</version>\n" +
                        "       </dependency>\n";
                break;
            default:
                System.out.println("Invalid database type: " + dbType);
                System.exit(0);
        }
        String pomContent = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
                + "<project xmlns=\"http://maven.apache.org/POM/4.0.0\"\n"
                + "         xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"\n"
                + "         xsi:schemaLocation=\"http://maven.apache.org/POM/4.0.0\n"
                + "         http://maven.apache.org/xsd/maven-4.0.0.xsd\">\n\n"
                + "    <modelVersion>4.0.0</modelVersion>\n\n"
                + "    <groupId>" + packageName + "</groupId>\n"
                + "    <artifactId>boost</artifactId>\n"
                + "    <version>1.0-SNAPSHOT</version>\n\n"
                + "    <properties>\n"
                + "        <java.version>" + javaVersion + "</java.version>\n"
                + "        <spring-boot.version>" + springBootVersion + "</spring-boot.version>\n"
                + "        <jakarta.persistence.version>" + jpaVersion + "</jakarta.persistence.version>\n"
                + "    </properties>\n\n"
                + "    <dependencies>\n"
                + "        <dependency>\n"
                + "            <groupId>org.springframework.boot</groupId>\n"
                + "            <artifactId>spring-boot-starter-data-jpa</artifactId>\n"
                + "            <version>${spring-boot.version}</version>\n"
                + "        </dependency>\n"
                + "        <dependency>\n"
                + "            <groupId>org.springframework.boot</groupId>\n"
                + "            <artifactId>spring-boot-starter-web</artifactId>\n"
                + "            <version>${spring-boot.version}</version>\n"
                + "        </dependency>\n"
                + "        <dependency>\n"
                + "            <groupId>org.springframework.boot</groupId>\n"
                + "            <artifactId>spring-boot-starter-test</artifactId>\n"
                + "            <version>${spring-boot.version}</version>\n"
                + "            <scope>test</scope>\n"
                + "        </dependency>\n"
                + "        <dependency>\n"
                + "            <groupId>jakarta.persistence</groupId>\n"
                + "            <artifactId>jakarta.persistence-api</artifactId>\n"
                + "            <version>${jakarta.persistence.version}</version>\n"
                + "        </dependency>\n"
                + dbDependency
                + "    </dependencies>\n\n"
                + "    <build>\n"
                + "        <plugins>\n"
                + "            <plugin>\n"
                + "                <groupId>org.springframework.boot</groupId>\n"
                + "                <artifactId>spring-boot-maven-plugin</artifactId>\n"
                + "                <version>${spring-boot.version}</version>\n"
                + "            </plugin>\n"
                + "        </plugins>\n"
                + "    </build>\n\n"
                + "</project>\n";

        writeToFile(projectDir, "pom.xml", pomContent);
    }

    private void writeToFile(File packageDir, String fileName, String content) {
        String filePath = packageDir + File.separator + fileName;
        try {
            File file = new File(filePath);
            file.getParentFile().mkdirs();
            FileWriter writer = new FileWriter(file);
            writer.write(content);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
