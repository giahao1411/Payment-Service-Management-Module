import java.io.*;
import java.util.*;
public class TestTestCase {

    public static void checkTestCase(String string1) {
        System.out.println(string1.substring(0, 3) + "uest" + string1.substring(3, 4) + ":");
        File testCase, caze;
        Scanner input1, input2;
        String expectedOutput = "expected_output\\" + string1;
        String output = "output\\" + string1;
        int index = 0;
        try {
            testCase = new File(expectedOutput);
            input1 = new Scanner(testCase);
            caze = new File(output);
            input2 = new Scanner(caze);
            while(input1.hasNextLine() || input2.hasNextLine()) {
                String line1 = input1.nextLine();
                String line2 = input2.nextLine();
                index ++;
                if (line1.equals(line2)) {
                    System.out.println("True");
                }
                else {
                    System.out.println("False at index: " + index);
                }
            }
            input1.close();
            input2.close();
        } catch (FileNotFoundException e) {
            System.out.println("Sai ten file kia ni'!");
        } catch (NoSuchElementException e) {
            System.out.println("Du output hoac la thieu output so voi Test Case kia ni'!");
        }

        System.out.println();

    }
    public static void main(String[] args) {
        checkTestCase("Req3.txt");
        checkTestCase("Req4.txt");
        checkTestCase("Req5.txt");
        checkTestCase("Req6.txt");
        checkTestCase("Req7.txt");
        checkTestCase("Req8.txt");
        checkTestCase("Req9.txt");
    }
}
