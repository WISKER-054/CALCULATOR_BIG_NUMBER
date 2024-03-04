/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Logic;

import java.util.LinkedList;
import java.util.Objects;

/**
 *
 * @author truon
 */
public class Number {

    private LinkedList<Integer> listNumber = new LinkedList<>();
    private Character integerType = '+';

    public Number() {
    }

    public Number(Number number) {
        listNumber = number.listNumber;
        integerType = number.integerType;
    }

    // hàm chuyển số thành chuỗi
    public String numberToString() {
        String stringNumber = "";
        for (int i : listNumber) {
            stringNumber = stringNumber + i;
        }
        if (integerType == '-') {
            stringNumber = integerType + stringNumber;
        }
        if ("-0".equals(stringNumber)) {
            stringNumber = "0";
        }
        return stringNumber;
    }

    // hàm tạo
    public Number(String string) {
        string = removeZeroInFirstString(string);
        if (string.length() == 0) {
            string = "0";
        }
        if (string.charAt(0) == '-' || string.charAt(0) == '+') {
            integerType = string.charAt(0);
        } else {
            char ch = string.charAt(0);
            int intNum = Character.getNumericValue(ch);
            listNumber.addLast(intNum);
        }
        for (int i = 1; i < string.length(); i++) {
            char ch = string.charAt(i);//khai báo ký tự
            int intNum = Character.getNumericValue(ch);//đổi ký tự thành số
            listNumber.addLast(intNum);//chuyển tất cả số nguyên vào list
        }

        if (string.length() == 1 && string.charAt(0) == '-') {
            char ch = 'x';
            int intNum = Character.getNumericValue(ch);
            listNumber.addLast(intNum);
        }
        if (string.length() == 1 && string.charAt(0) == '+') {
            char ch = 'x';
            int intNum = Character.getNumericValue(ch);
            listNumber.addLast(intNum);
        }
    }

    public boolean isIntegerList() {
        boolean checked = true;//list danh sách số
        for (int i = 0; i < listNumber.size(); i++) {
            int currentNumber = listNumber.get(i);//kiểm tra trong list có chữ hay ký tự đặt biệt không
            checked = 0 <= currentNumber && currentNumber <= 9;
            if (!checked) {
                checked = false;//trả về sai
                break;
            }
        }
        return checked;//trả về list danh sách số đúng
    }

    // Thực hiện xóa các số 0 trước chuỗi số
    static String removeZeroInFirstString(String string) {
        int startIndex = 0;
        char ch = string.charAt(0);

        // Kiểm tra xem chuỗi có dấu '-' ở đầu không
        boolean hasNegativeSign = string.startsWith("-") || string.startsWith("+");

        if (hasNegativeSign) {
            startIndex = 1; // Bỏ qua dấu '-' hoặc dấu '+' nếu có
        }

        // Tìm vị trí bắt đầu của phần không phải là số 0
        while (startIndex < string.length() && string.charAt(startIndex) == '0') {
            startIndex++;
        }

        // Trích xuất phần của chuỗi bắt đầu từ startIndex đến cuối chuỗi
        String result = string.substring(startIndex);

        // Thêm lại dấu '-' nếu có
        if (hasNegativeSign) {
            result = ch + result;
        }
        return result;

    }

    // Thực hiện cộng 2 số A và B (A+B) trả về 1 đối tượng Number
    public static Number addition(Number numberA, Number numberB) {
        String stringNewNumber = "";//chuỗi rỗng
        if (Objects.equals(numberA.integerType, numberB.integerType)) {
            int up = 0;
            while (!numberA.listNumber.isEmpty() || !numberB.listNumber.isEmpty()) {//kiểm tra A và B có rỗng không nếu có thì tiếp tục
                int numA = numberA.listNumber.isEmpty() ? 0 : numberA.listNumber.removeLast();//danh sách A không rỗng lấy giá trị cuối A = numA rồi xóa giá trị đó
                int numB = numberB.listNumber.isEmpty() ? 0 : numberB.listNumber.removeLast();//như trên nếu rỗng trả về số 0

                int sum = numA + numB + up;
                up = sum / 10;
                stringNewNumber = (sum % 10) + stringNewNumber;
            }
            if (up > 0) {
                stringNewNumber = up + stringNewNumber;
            }
            if (numberA.integerType == '-') {
                stringNewNumber = "-" + stringNewNumber;
            }
        } else {
            if (numberA.integerType == '-') {
                numberA.integerType = '+';
                stringNewNumber = Number.subtraction(numberB, numberA).numberToString();
            } else {
                numberB.integerType = '+';
                stringNewNumber = Number.subtraction(numberA, numberB).numberToString();
            }
        }
        Number total = new Number(stringNewNumber);
        return total;
    }

    // Thực hiện so sánh 2 số bỏ qua dạng số nguyên
    public static boolean numberAThanNumberB(Number numberA, Number numberB) {
        int sizeA = numberA.listNumber.size();
        int sizeB = numberB.listNumber.size();
        if (sizeA > sizeB) {
            return true;
        }
        if (sizeA < sizeB) {
            return false;
        }
        for (int i = 0; i < sizeA; i++) {
            if (numberA.listNumber.get(i) > numberB.listNumber.get(i)) {
                return true;
            } else if (numberA.listNumber.get(i) < numberB.listNumber.get(i)) {
                return false;
            }
        }
        return true;
    }

    // Thực hiện trừ 2 số A và B (A-B) trả về 1 đối tượng Number
    public static Number subtraction(Number numberA, Number numberB) {
        String stringNewNumber = "";
        if (Objects.equals(numberA.integerType, numberB.integerType)) {
            if (numberA.integerType == '+') {
                if (numberAThanNumberB(numberA, numberB)) {
                    int carry = 0;
                    while (!numberA.listNumber.isEmpty() || !numberB.listNumber.isEmpty()) {
                        int numA = numberA.listNumber.isEmpty() ? 0 : numberA.listNumber.removeLast();
                        int numB = numberB.listNumber.isEmpty() ? 0 : numberB.listNumber.removeLast();

                        int sub = (numA - numB - carry + 10) % 10;
                        carry = (numA - numB - carry < 0) ? 1 : 0;

                        stringNewNumber = sub + stringNewNumber;
                    }
                    if ("".equals(stringNewNumber)) {
                        stringNewNumber = "0";
                    }
                } else {
                    stringNewNumber = "-" + subtraction(numberB, numberA).numberToString();
                }
            } else {
                numberA.integerType = '+';
                numberB.integerType = '+';
                stringNewNumber = Number.subtraction(numberB, numberA).numberToString();
            }
        } else {
            if (numberA.integerType == '-') {
                numberA.integerType = '+';
                numberB.integerType = '+';
                stringNewNumber = Number.addition(numberB, numberA).numberToString();
                stringNewNumber = "-" + stringNewNumber;
            } else {
                numberA.integerType = '+';
                numberB.integerType = '+';
                stringNewNumber = Number.addition(numberA, numberB).numberToString();
            }
        }
        Number total = new Number(stringNewNumber);
        return total;
    }

    // Thực hiện nhân 1 đối tượng Number với 10 nTen lần trả về 1 đối tượng Number mới
    public void multiplyByNTen(int nTen) {
        for (int i = 0; i < nTen; i++) {
            listNumber.addLast(0);
        }
    }

    //Thực hiện nhân 1 đối tượng Number với 1 số trả về 1 đối tượng Number mới
    public static Number multiplyByNumber(Number number, int num) {
        String stringNewNumber = "";
        int up = 0;
        for (int i = number.listNumber.size() - 1; i >= 0; i--) {
            int n = number.listNumber.get(i) * num + up;
            if (n >= 10) {
                stringNewNumber = n % 10 + stringNewNumber;
                up = n / 10;
            } else {
                stringNewNumber = n + stringNewNumber;
                up = 0;
            }
        }
        if (up > 0) {
            stringNewNumber = up + stringNewNumber;
        }
        Number numberNew = new Number(stringNewNumber);
        numberNew.integerType = number.integerType;
        return numberNew;
    }

    // Thực hiện nhân 2 số A và B (A*B) trả về 1 đối tượng Number
    public static Number multiplication(Number numberA, Number numberB) {
        LinkedList<Number> list = new LinkedList<>();
        int nTen = 0;
        for (int i = numberA.listNumber.size() - 1; i >= 0; i--) {
            int element = numberA.listNumber.get(i);
            Number newNumber = Number.multiplyByNumber(numberB, element);
            newNumber.multiplyByNTen(nTen);
            list.add(newNumber);
            nTen++;
        }
        Number total = new Number();
        for (Number i : list) {
            total = addition(total, i);
        }
        if (!Objects.equals(numberA.integerType, numberB.integerType)) {
            total.integerType = '-';
        } else {
            total.integerType = '+';
        }
        return total;
    }

    // Cộng thêm 1 cho Number trả về 1 Number
    public static Number addOne(Number number) {
        Number one = new Number("1");
        Number result = addition(number, one);
        return result;
    }

    // Cộng thêm 1 cho Number
    public static Number subOne(Number number) {
        Number one = new Number("1");
        Number result = subtraction(number, one);
        return result;
    }

    // Kiểm tra B = 0
    public static boolean checkZero(Number number) {
        boolean check = false;
        if (number.listNumber.size() == 1) {
            if (number.listNumber.get(0) == 0) {
                check = true;
            }
        }
        return check;
    }

    // B nhân 10 bao nhiêu lần mới gần bằng A
    public static int zeroMore(Number numberA, Number numberB) {
        int n = numberA.listNumber.size() - numberB.listNumber.size();
        if(n > 0){
            n--;
        }
        return n;
    }

    // Thực hiện chia 2 số A và B (A/B) trả về 1 đối tượng Number
    public static Number division(Number numberA, Number numberB) {
        Number resultEnd = new Number("0");
        numberA.integerType = '+';
        numberB.integerType = '+';
        if (numberAThanNumberB(numberA, numberB)) {
            boolean check = numberAThanNumberB(numberA, numberB);
            while (check) {
                resultEnd = addOne(resultEnd);
                check = numberAThanNumberB(numberA, multiplication(numberB, resultEnd));
            }
            resultEnd = subOne(resultEnd);
        }
        return resultEnd;
    }

    // Thực hiện chia A và B trả về phần dư bằng 1 Number
    public static Number remainderNumber(Number number1, Number numberA, Number numberB) {
        Number number2 = multiplication(number1, numberB);
        Number remainder = subtraction(numberA, number2);
        return remainder;
    }
    
    public static Number div(Number A, Number B){
        LinkedList<Number> list = new LinkedList<>();
        Number numberList;
        Number newA = new Number();
        
        for( int i : A.listNumber){
            newA.listNumber.addLast(i);
            
            numberList = new Number(division(newA,B));
            list.add(numberList);
            newA = new Number(remainderNumber(numberList,newA,B));
            
        }
        
        Number total = new Number("0");
        for (Number i : list) {
            total = addition(total, i);
        }
        return total;
    }
    
}
