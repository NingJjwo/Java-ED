import javax.swing.*;

public class Main {
        private static String validateInput(String input) {
            final String regex = "\\-?\\d+\\s*x\\s*\\^s*\\s*-?\\d+";
            final String regexB ="\\-?\\d+";
            final String regexC ="\\-?\\d+\\s*x";
            if (input==null){
                System.exit(0);}
            if(input.matches(regex)||input.matches(regexB)||input.matches(regexC)){
                return input;}
            else {
                throw new Error("La ecuacion " + input + " es invalida");
            }
        }

        public static void main(String[] args) {
            try {
                String a = validateInput(JOptionPane.showInputDialog(null,"Inserta el valor de a"));
                String p = validateInput(JOptionPane.showInputDialog(null,"Inserta el valor de p"));
                String q = validateInput(JOptionPane.showInputDialog(null,"Inserta el valor de q"));
                CalculatorDifferentialLinearEquations calculadora = new CalculatorDifferentialLinearEquations(a, p, q);
                calculadora.solveDifferentialLinearEquation();
            } catch (Throwable e) {
                JOptionPane.showMessageDialog(null, e.getMessage());
                e.printStackTrace();
            }

        }
    }
