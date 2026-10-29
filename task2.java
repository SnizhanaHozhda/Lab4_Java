import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class task2 {

    public static void main(String[] args) {
        try {
            BufferedReader console = new BufferedReader(new InputStreamReader(System.in));

            File inFile;
            while (true) {
                System.out.print("Введіть шлях до вхідного файлу: ");
                String path = console.readLine();
                if (path != null) {
                    inFile = new File(path.trim());
                    if (inFile.isFile()) {
                        break;
                    }
                }
                System.out.println("Файл не знайдено! Спробуйте ще раз.");
            }

            BufferedReader reader = new BufferedReader(new FileReader(inFile));
            ArrayList<String> words = new ArrayList<String>();
            String line;

            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue;

                String[] tmp = line.split("\\s+");
                for (int j = 0; j < tmp.length; j++) {
                    if (!tmp[j].isEmpty()) {
                        words.add(tmp[j]);
                    }
                }
            }
            reader.close();

            File outFile;
            boolean append = false;

            while (true) {
                System.out.print("Введіть шлях до вихідного файлу: ");
                String path = console.readLine();
                if (path == null) return;

                outFile = new File(path.trim());

                if (outFile.isDirectory() || outFile.getCanonicalFile().equals(inFile.getCanonicalFile())) {
                    System.out.println("Некоректне ім'я файлу!");
                    continue;
                }

                if (!outFile.exists()) {
                    System.out.print("Файл не існує. Створити? (т/н): ");
                    String ans = console.readLine();
                    if (ans != null && ans.trim().equalsIgnoreCase("т")) {
                        break;
                    }
                } else {
                    System.out.print("Файл існує. 1 - перезаписати, 2 - дописати в кінець: ");
                    String choice = console.readLine();
                    if (choice != null) {
                        choice = choice.trim();
                        if (choice.equals("1") || choice.equals("2")) {
                            append = choice.equals("2");
                            break;
                        }
                    }
                }
            }

            BufferedWriter writer = new BufferedWriter(new FileWriter(outFile, append));
            StringBuilder strB = new StringBuilder();

            for (int i = 0; i < words.size(); i++) {
                strB.append(words.get(i)).append(System.getProperty("line.separator"));
            }

            writer.write(strB.toString());
            writer.close();
            System.out.println("Готово. Записано слів: " + words.size());

        } catch (FileNotFoundException ex) {
            System.out.println("Файл не знайдено!");
        } catch (Exception ex) {
            System.out.println("Помилка обробки!");
        }
    }
}