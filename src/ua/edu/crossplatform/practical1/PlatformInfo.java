package ua.edu.crossplatform.practical1;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Locale;

public final class PlatformInfo {

    private PlatformInfo() {
    }

    public static void main(String[] args) {
        if (args.length == 0 || args[0].isBlank()) {
            System.err.println("Помилка: ім'я студента не вказано!");
            System.err.println("Використання: java -jar platform-info.jar \"Ім'я Прізвище\"");
            System.exit(1);
        }

        String student = String.join(" ", args);

        String report = buildReport(student);

        System.out.print(report);

        try {
            Path reportPath = Path.of("reports", "platform-info.txt");

            if (reportPath.getParent() != null) {
                Files.createDirectories(reportPath.getParent());
            }
            Files.writeString(reportPath, report, StandardCharsets.UTF_8);

            System.out.println("\nФайл звіту збережено за шляхом:");
            System.out.println(reportPath.toAbsolutePath().normalize());

        } catch (IOException e) {
            System.err.println("Помилка запису файлу звіту: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static String buildReport(String student) {
        return """
            Студент: %s
            Версія Java: %s
            Постачальник JDK: %s
            Операційна система: %s
            Версія ОС: %s
            Архітектура: %s
            Файловий роздільник: %s
            Кодування за замовчуванням: %s
            Локаль за замовчуванням: %s
            Кількість доступних процесорів: %d
            Робочий каталог: %s
            """.formatted(
                student,
                Runtime.version(),
                System.getProperty("java.vendor"),
                System.getProperty("os.name"),
                System.getProperty("os.version"),
                System.getProperty("os.arch"),
                System.getProperty("file.separator"),
                Charset.defaultCharset(),
                Locale.getDefault(),
                Runtime.getRuntime().availableProcessors(),
                Path.of("").toAbsolutePath().normalize()
        );
    }
}