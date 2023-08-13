package com.semihbiygit.springboost;

import java.util.Scanner;

import picocli.CommandLine;

import static com.semihbiygit.springboost.ANSIColors.*;

@CommandLine.Command(name = "spring-boost", mixinStandardHelpOptions = true, version = "1.0")
public class SpringBoostCLI implements Runnable {

    @CommandLine.Option(names = {"--projectName"}, description = "The name of the Spring Boot project to be generated.")
    private String projectName;

    @CommandLine.Option(names = {"--packageName"}, description = "The base package name for the Spring Boot project.")
    private String packageName;

    @CommandLine.Option(names = {"--dbType"}, description = "The type of the database to be used (e.g., mysql, h2, postgresql).")
    private String dbType;

    public static void main(String[] args) {
        String welcomeMessage = ANSI_CYAN + ANSI_BOLD
                + "                                                                   \n"
                + "      *************************************************************\n"
                + "      **                                                         **\n"
                + "      **             Welcome to the Spring BOOST CLI             **\n"
                + "      **                                                         **\n"
                + "      *************************************************************\n"
                + "                                                                   \n"
                + ANSIColors.ANSI_RESET;

        System.out.println(welcomeMessage);
        System.out.println(ANSI_BLUE + ANSI_BOLD + "This command line tool helps you generate boilerplate code of Spring Boot projects.\n" + ANSI_RESET);
        System.out.println(ANSI_RED + ANSI_BOLD + ANSI_UNDERLINE + "For more information, please visit github.com/semihbiygit/spring-boost-cli.\n\n" + ANSI_RESET);

        SpringBoostCLI app = new SpringBoostCLI();
        CommandLine cmd = new CommandLine(app);
        cmd.parseArgs(args);

        if (app.projectName == null || app.packageName == null || app.dbType == null) {
            Scanner scanner = new Scanner(System.in);

            if (app.projectName == null) {
                System.out.print(ANSI_YELLOW + ANSI_BOLD + ANSI_UNDERLINE + "Enter the project name: " + ANSI_RESET);
                app.projectName = scanner.nextLine();
            }

            if (app.packageName == null) {
                System.out.print(ANSI_YELLOW + ANSI_BOLD + ANSI_UNDERLINE + "Enter the package name (com.example.demo): " + ANSI_RESET);
                app.packageName = scanner.nextLine();
            }

            if (app.dbType == null) {
                System.out.print(ANSI_YELLOW + ANSI_BOLD + ANSI_UNDERLINE + "Enter the database type (mysql, h2, postgresql, mssql): " + ANSI_RESET);
                app.dbType = scanner.nextLine();
            }

            scanner.close();
        }

        app.run();
    }

    @Override
    public void run() {
        CodeGenerator codeGenerator = new CodeGenerator();
        codeGenerator.generateProject(projectName, packageName, dbType);
    }
}
