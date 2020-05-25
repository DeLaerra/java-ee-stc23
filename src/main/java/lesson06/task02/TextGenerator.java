package lesson06.task02;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Класс-генератор текстовых файлов
 *
 * @author Marina_Larionova
 * @version 1.0.0
 */
public class TextGenerator {

    /**
     * Метод для создания случайных слов
     *
     * @return случайное слово в нижнем регистре
     */
    private String createWord() {
        return RandomStringUtils.randomAlphabetic(1, 15).toLowerCase();
    }

    /**
     * Метод для создания случайного количества предложений.
     * Если указанная вероятность probability выше рандомной, слово будет из массива words.
     * Первое слово с большой буквы, после него может быть запятая.
     *
     * @param probability - вероятность попадания в предложение слова из массива words
     * @param words       - массив слов для замены некоторых слов случайного текста
     * @return предложение случайной длины из случайных слов
     */
    private String createPhrase(int probability, String[] words) {
        String phrase = "";
        String word;
        String[] punctuationMarks = new String[]{".", "!", "?"};
        int punctuationMarksIndex = ThreadLocalRandom.current().nextInt(3);
        int defaultProbability = ThreadLocalRandom.current().nextInt(101);
        int wordCountInPhrase = ThreadLocalRandom.current().nextInt(1, 16);
        boolean hasComma = ThreadLocalRandom.current().nextBoolean();

        for (int i = 0; i < wordCountInPhrase; i++) {
            if (i == 0) {
                word = StringUtils.capitalize(createWord());
                phrase = hasComma ? word + ", " : word + " ";
            } else {
                int arrayNumber = ThreadLocalRandom.current().nextInt(words.length);
                word = probability > defaultProbability ? words[arrayNumber] + " " : createWord() + " ";
                phrase += word;
            }
        }

        return phrase.substring(0, phrase.length() - 1) + punctuationMarks[punctuationMarksIndex] + " ";
    }

    /**
     * Метод для создания случайных абзацев текста
     *
     * @param probability - вероятность попадания в предложение слова из массива words
     * @param words       - массив слов для замены некоторых слов случайного текста
     * @return абзац случайной длины из случайных предложений
     */
    private String createParagraph(int probability, String[] words) {
        int phraseCountInParagraph = ThreadLocalRandom.current().nextInt(1, 21);
        String paragraph = "";

        for (int i = 0; i < phraseCountInParagraph; i++) {
            paragraph += createPhrase(probability, words);
        }

        return paragraph;
    }

    /**
     * Метод для создания случайного текста
     *
     * @param probability - вероятность попадания в предложение слова из массива words
     * @param size        - размер файла с текстом
     * @param words       - массив слов для замены некоторых слов случайного текста
     * @return случайный текст заданнного размера
     */
    private String createText(int probability, int size, String[] words) {
        String text = "";

        while (text.getBytes(StandardCharsets.UTF_8).length < size) {
            text += createParagraph(probability, words) + "\n" + "\r";
        }

        return text.substring(0, size - 3) + ".";
    }

    /**
     * Метод для записи случаного текста в файлы по заданному пути
     *
     * @param path        - путь записи файлов
     * @param n           - количество файлов
     * @param size        - размер файла
     * @param words       - массив слов для замены некоторых слов случайного текста
     * @param probability - вероятность попадания в предложение слова из массива words
     * @throws IOException
     */
    public void getFiles(String path, int n, int size, String[] words, int probability) throws IOException {
        if (size < 4 || (probability < 0 || probability > 100)) throw new IllegalArgumentException();

        Path pathResult;
        List<String> textList = new ArrayList<>();
        String text;

        for (int i = 0; i < n; i++) {
            text = createText(probability, size, words);
            textList.clear();
            textList.add(text);
            pathResult = Paths.get(path + i + ".txt");
            Files.write(pathResult, textList, StandardCharsets.UTF_8);
        }
    }

    public static void main(String[] args) {
        TextGenerator tg = new TextGenerator();
        String[] words = new String[1000];
        Arrays.setAll(words, w -> tg.createWord());

        try {
            tg.getFiles("E:\\result", 3, 1800, words, 50);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
