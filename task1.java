import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;

public class task1 {

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
            ArrayList<Double> nums = new ArrayList<Double>();
            String line;
            int lineNum = 0;

            while ((line = reader.readLine()) != null) {
                lineNum++;
                line = line.trim();
                if (line.isEmpty()) continue;

                try {
                    nums.add(Double.parseDouble(line));
                } catch (NumberFormatException e) {
                    System.out.println("Рядок " + lineNum + " пропущено (не некоректне число): " + line);
                }
            }
            reader.close();

            if (nums.isEmpty()) {
                System.out.println("У файлі немає коректних чисел.");
                return;
            }

            Collections.sort(nums, Collections.reverseOrder());
            ArrayList<Double> top = new ArrayList<Double>();
            int limit = Math.min(10, nums.size());
            double sum = 0;

            for (int i = 0; i < limit; i++) {
                double val = nums.get(i);
                top.add(val);
                sum += val;
            }
            Collections.reverse(top);

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
            strB.append("Сума найбільших чисел: ").append(sum).append(System.getProperty("line.separator"));
            strB.append("Обрані числа за зростанням:").append(System.getProperty("line.separator"));
            for (int i = 0; i < top.size(); i++) {
                strB.append(top.get(i)).append(System.getProperty("line.separator"));
            }

            writer.write(strB.toString());
            writer.close();
            System.out.println("Готово.");

        } catch (FileNotFoundException ex) {
            System.out.println("Файл не знайдено або відсутній доступ!");
        } catch (Exception ex) {
            System.out.println("Помилка обробки файлів!");
        }
    }
}