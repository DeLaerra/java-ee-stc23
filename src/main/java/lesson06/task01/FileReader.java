package lesson06.task01;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Класс, обрабатывающий исходный текстовый файл и сохраняющий значения в другой текстовый файл.
 *
 * @author Marina_Larionova
 * @version 1.0.0
 */
public class FileReader {
    private Path pathInputFile = Paths.get("src/main/resources/lesson06/task01/text.txt");
    private Path pathResultFile = Paths.get("src/main/resources/lesson06/task01/result.txt");
    private List<String> textList = new ArrayList<>();

    public Path getPathInputFile() {
        return pathInputFile;
    }

    public Path getPathResultFile() {
        return pathResultFile;
    }

    public List<String> getTextList() {
        return textList;
    }

    public void setPathInputFile(Path pathInputFile) {
        this.pathInputFile = pathInputFile;
    }

    public void setPathResultFile(Path pathResultFile) {
        this.pathResultFile = pathResultFile;
    }

    /**
     * Метод считывает строки из исходного файла в список textList
     *
     * @param pathInputFile - путь к исходному файлу по-умолчанию
     * @throws IOException
     */
    public void readInputFile(Path pathInputFile) throws IOException {
        textList = Files.readAllLines(pathInputFile, StandardCharsets.UTF_8);
        sortList(textList);
    }

    /**
     * Метод разбивает на слова строки из исходного списка, убирает знаки препинания, а потом сортирует по алфавиту
     * список слов. Слова не должны повторяться, регистр не должен учитываться.
     * Одно слово в разных падежах – это разные слова
     *
     * @param list
     */
    public void sortList(List<String> list) {
        List<String> sortedList = new ArrayList<>();
        for (String line : list) {
            line = line.toLowerCase().replaceAll("[1-9!#$%&'()*+/:;<=>?@^_{|}~`\\]]", "");
            String[] words = line.split("[,.\\s\\-]");
            sortedList.addAll(Arrays.asList(words));
        }
        textList = sortedList;
        textList.removeIf(obj -> obj.equals("–") || obj.isEmpty());
        Collections.sort(textList);
        textList = textList.stream().distinct().collect(Collectors.toList());
    }

    /**
     * Метод записывает в результирующий файл информацию из списка textList
     *
     * @param pathResultFile - путь к результирующему файлу по-умолчанию
     * @throws IOException
     */
    public void saveResultFile(Path pathResultFile) throws IOException {
        Files.write(pathResultFile, textList);
    }
}
