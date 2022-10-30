import java.util.Scanner;
import java.util.TreeMap;

public class Main {

    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        System.out.print("Введите выражение: ");
        String exp = sc.nextLine();
        String input = exp.replaceAll("[^\\da-zA-Z+\\-*/]", "");
        String result = calc(input);
        System.out.println("Результат операции: " + result);
    }

    public static String calc(String input) throws Exception {
        Converter converter = new Converter();
        int a, b, c;
        String result;
        String[] str;

        if (input.contains("+")) {
            str = input.split("\\+");
        } else if (input.contains("-")) {
            str = input.split("-");
        } else if (input.contains("*")) {
            str = input.split("\\*");
        } else if (input.contains("/")) {
            str = input.split("/");
        } else
            throw new Exception("т.к. строка не является математической операцией");

        if(str.length !=2)throw new Exception("т.к. формат математической операции не удовлетворяет заданию - два операнда и один оператор (+, -, /, *)");

        if (converter.isRoman(str[0]) == converter.isRoman(str[1])) {
            boolean isRoman = converter.isRoman(str[0]);
            if (isRoman) {
                a = converter.romanToInt(str[0]);
                b = converter.romanToInt(str[1]);

            } else {
                a = Integer.parseInt(str[0]);
                b = Integer.parseInt(str[1]);
            }

            if (a > 10) throw new Exception("Одно из чисел не подходит по условию");
            if (b > 10) throw new Exception("Одно из чисел не подходит по условию");
            if (a < 1) throw new Exception("Одно из чисел не подходит по условию");
            if (b < 1) throw new Exception("Одно из чисел не подходит по условию");

            if (input.contains("+")) {
                c = a + b;
            } else if (input.contains("-")) {
                c = a - b;
            } else if (input.contains("*")) {
                c = a * b;
            } else if (input.contains("/")) {
                c = a / b;
            } else
                throw new Exception("т.к. формат математической операции не удовлетворяет заданию - два операнда и один оператор (+, -, /, *)");

            if (c <= 0 && isRoman) {
                throw new Exception("т.к. в римской системе нет отрицательных чисел");
            }
            if (isRoman) {
                result = String.valueOf(converter.intToRoman(c));
            } else {
                result = String.valueOf(c);
            }
        } else {
            throw new Exception("т.к. используются одновременно разные системы счисления");
        }

        return result;
    }

    static class Converter {
        TreeMap<Character, Integer> romanKeyMap = new TreeMap<>();
        TreeMap<Integer, String> arabianKeyMap = new TreeMap<>();

        public Converter() {
            romanKeyMap.put('I', 1);
            romanKeyMap.put('V', 5);
            romanKeyMap.put('X', 10);
            romanKeyMap.put('L', 50);
            romanKeyMap.put('C', 100);
            romanKeyMap.put('D', 500);
            romanKeyMap.put('M', 1000);

            arabianKeyMap.put(1000, "M");
            arabianKeyMap.put(900, "CM");
            arabianKeyMap.put(500, "D");
            arabianKeyMap.put(400, "CD");
            arabianKeyMap.put(100, "C");
            arabianKeyMap.put(90, "XC");
            arabianKeyMap.put(50, "L");
            arabianKeyMap.put(40, "XL");
            arabianKeyMap.put(10, "X");
            arabianKeyMap.put(9, "IX");
            arabianKeyMap.put(5, "V");
            arabianKeyMap.put(4, "IV");
            arabianKeyMap.put(1, "I");

        }

        public boolean isRoman(String number) {
            return romanKeyMap.containsKey(number.charAt(0));
        }

        public String intToRoman(int number) {
            String roman = "";
            int arabianKey;
            do {
                arabianKey = arabianKeyMap.floorKey(number);
                roman += arabianKeyMap.get(arabianKey);
                number -= arabianKey;
            } while (number != 0);
            return roman;
        }

        public int romanToInt(String s) {
            int end = s.length() - 1;
            char[] arr = s.toCharArray();
            int arabian;
            int result = romanKeyMap.get(arr[end]);
            for (int i = end - 1; i >= 0; i--) {
                arabian = romanKeyMap.get(arr[i]);

                if (arabian < romanKeyMap.get(arr[i + 1])) {
                    result -= arabian;
                } else {
                    result += arabian;
                }
            }
            return result;
        }
    }
}

