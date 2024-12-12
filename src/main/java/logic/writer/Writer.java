package logic.writer;

import java.io.FileWriter;
import java.io.IOException;

public class Writer {

    public static void write(String content, String filePath) {
        try (FileWriter writer = new FileWriter(filePath)) {
            writer.write(content);
            System.out.println("Запись в файл успешна.");
        } catch (IOException e) {
            System.out.println("Ошибка при записи в файл: " + e.getMessage());
        }
    }
}
