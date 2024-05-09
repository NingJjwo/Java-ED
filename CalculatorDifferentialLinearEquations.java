import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CalculatorDifferentialLinearEquations {
    private String equation;
    private final List<String> splitA, splitP, splitQ;

    private List <String> normalizateP;

    private static List<String> normalize(String input) {
        List<String> list = new ArrayList<>();
        char[] cs = input.toCharArray();
        String str = "";
        boolean negative = false;
        for (char c : cs) {
            if (c == ' ') {
                if (!str.isEmpty()) {
                    list.add(str);
                    str = "";
                }
                negative = false;
            } else if (c == '-') {
                negative = true;
            } else if (Character.isDigit(c)) {
                str += (negative ? "-" : "") + c;
                negative = false;
            } else {
                if (!str.isEmpty()) {
                    list.add(str);
                    str = "";
                }
                list.add("" + c);
            }
        }
        if (!str.isEmpty()){
            list.add(str);}
        return list;
    }

    /*Constructor de la calculadora, este constructor va a obtener las funciones a, p y q, al tener las funciones,
    las separa por términos usando el método split, donde indica que por cada espacio, se va a separar los términos,
    como resultado y por ello es de suma importancia de escribir las funciones separadas por un espacio por cada
    valor dado, por ejemplo 3x^2 seria: 3 x ^ 2 además, el arreglo resultante de una función como 5x^3, se convertirá
    en un array [5, x, ^, 3], gracias a esto, facilita a la hora de realizar las operaciones matemáticas y pasos de
    forma de un arreglo en forma de string, que puramente matemático, y finalmente arma la ecuación principal,
    con base de las funciones iniciales dadas.*/
    public CalculatorDifferentialLinearEquations(String a, String p, String q){
        this.splitA = normalize(a);
        this.splitP = normalize(p);
        this.splitQ = normalize(q);
        this.equation = "Ecuacion Original - #1 ( " + a + " )y' + ( " + p + " )y = " + q;
    }

    /*El método divideFraction, está creado con el fin de poder normalizar la ecuación inicial, o simplificar
    los fraccionarios, para eso se debe realizar por primer paso, la comprobación de que los dos números de entrada
    no son divisibles entre sí (o con el mismo número), en dado caso que si sean, se dividen y se vuelve a
    comprobar si aún siguen siendo divisibles, manteniendo la estructura de fracciones, es decir 8 / 4 = 1 / 2 y
    no 0.5, al final de este proceso, se inicializa una variable llamada fraction, la cual será el
    retorno del método, y se hacen las validaciones pertinentes para retornar el resultado deseado, ya sea un
    número entero o una fracción.*/
    private String divideFraction(int a, int b){
        for (int i = 2; i<10; i++){
            if (a%i==0 && b%i==0){
                a/=i;
                b/=i;
                i--;
            }
        }
        String fraction;
        if (b==1 || b==-1){
            a*=b;
            fraction = String.valueOf(a);
        }
        else if(a<0 && b<0){fraction = a*-1 + " / " + b*-1;}
        else{fraction = a + " / " + b;}
        return fraction;
    }

    /*El método divideVariables permite dividir dos variables x, con un exponente, ya sea 1, iguales u otros,
    para ello se solicitan los números de los exponentes y se restan, como validación, siempre dara un número
    positivo, ya que en el constructor de la función, la variable se ubicara en la parte donde le corresponda, ya sea
    como numerador o denominador*/
    private String divideVariables(int a, int b){
        if(a<0 && b<0){
            b+=a;
            b*=-1;
            return String.valueOf(b);
        }
        else if(a<b){
            b-=a;
            return String.valueOf(b);
        }
        else{
            a-=b;
            return String.valueOf(a);
        }
    }

    /*El método constructNormalizateFunction, es el encargado de normalizar una función, para que sea de la forma
    dy/dx + p(x)y = q(x), lo que realiza principalmente, es dividir la función a, con el resto de la ecuación,
    para ello, se tomaron ciertas validaciones, las cuales son, si la variable del numerador es una constante, tiene
    una variable o tiene una variable con exponente, y la comparación con el denominador ya sea constante, con variable
    o con una variable con exponente, se utilizan los arrays creados en el constructor y midiendo su tamaño, para
    determinar si son una sola constante (size = 1), si son constante y variable (size = 2) o si tienen exponente
    (size = 4). después de esas validaciones, se retorna un string con la respuesta de esa simplificación de dos
    funciones.*/
    private String constructNormalizateFunction(List<String> a, List<String> b){
        String results;

        if ((a.size() == b.size() && a.size()<=2) || ((a.size()==4 && b.size()==a.size())&&(Integer.parseInt(a.get(a.size()-1)) == Integer.parseInt(b.get(b.size()-1))))){results = divideFraction(Integer.parseInt(a.get(0)),Integer.parseInt(b.get(0)));}
        else if((((a.size() <= 2 || b.size()==2) && b.size()==4)&&Integer.parseInt(b.get(3))>0) || (a.size() == 4 && ((b.size()==1 || b.size()==2) && Integer.parseInt(a.get(3))<0)) || (a.size() == 1 && b.size()==2)){
            int minorSize=0;
            if(a.size()==2||b.size()==2){minorSize=1;}
            results = divideFraction(Integer.parseInt(a.get(0)),Integer.parseInt(b.get(0))) + " )( 1 / x ^ ";
            if(a.size()==4){results += divideVariables(Integer.parseInt(a.get(3)),minorSize);}
            else if (b.size()==4){results += divideVariables(minorSize, Integer.parseInt(b.get(3)));}
            else results+="1";
        }
        else if ((((a.size() == 1 || a.size()==2) && b.size()==4) && Integer.parseInt(b.get(3))<0) || (a.size() == 4 && (b.size()==1) || b.size()==2) || (a.size() == 2 && b.size()==1)){
            int minorSize=0;
            if(a.size()==2||b.size()==2){minorSize=1;}
            results = divideFraction(Integer.parseInt(a.get(0)),Integer.parseInt(b.get(0))) + " )( x ^ ";
            if(a.size()==4){results += divideVariables(Integer.parseInt(a.get(3)),minorSize);}
            else if(b.size()==4){results += divideVariables(minorSize, Integer.parseInt(b.get(3)));}
            else results+="1";
        }
        else{
            if ((Integer.parseInt(a.get(3))<Integer.parseInt(b.get(3))|| Integer.parseInt(a.get(3))<0) && Integer.parseInt(b.get(3))>0){results = divideFraction(Integer.parseInt(a.get(0)),Integer.parseInt(b.get(0))) + " )( 1 / x ^ " + divideVariables(Integer.parseInt(a.get(3)),Integer.parseInt(b.get(3)));}
            else results = divideFraction(Integer.parseInt(a.get(0)),Integer.parseInt(b.get(0))) + " )( x ^ " + divideVariables(Integer.parseInt(a.get(3)),Integer.parseInt(b.get(3)));
        }
        return results;
    }

    /*El método solveDifferentialLinearEquation, es el método público del objeto, que se encarga de escribir la respuesta
    de la ecuación diferencial lineal en la variable equation, este método llama a los demás metodos para lograr crear
    la solución hasta dejar determinada la solución de y por el factor integrante = integral de q por el factor integrante*/
    public void solveDifferentialLinearEquation(){
        this.normalizateP = Arrays.asList(constructNormalizateFunction(splitP,splitA).split(" "));
        String normalizateQ = constructNormalizateFunction(splitQ,splitA);
        this.equation += "\nNormalizacion de la ecuación - #2 -> y' + ( " + constructNormalizateFunction(splitP,splitA) + " )y = ( " + constructNormalizateFunction(splitQ,splitA) + " ) ";
        this.equation += "\nValor del factor integrante - #3 -> " + miu() + " )";
        this.equation += "\nMultiplicacion del factor integrante de ambos lados y solucion del lado izquierdo) - #4 -> y" + miu() + " ) = \u222b(( " + normalizateQ + " )( " + miu() + " ))";
        System.out.println(equation);
    }

    /*Tomando el resultado del método constructNormalizeFunction, se puede encontrar el factor integrante o (miu)
    con cinco validaciones, cuando el array tiene un tamaño de 1, que significa que p(x) es una constante entera, cuando
    tiene el tamaño 3, lo cual indica que hay una fracción constante, el tamaño 5 indica que hay una constante con la
    variable, y la variable tiene un exponente (ya sea 1), el tamaño 7, puede indicar dos probabilidades, que sea una fracción multiplicada
    por una variable con exponente, o que sea un entero dividiendo a una variable con exponente, y finalmente el tamaño 9, o el último
    return, significa que hay una fracción, multiplicando a una variable en el denominador, el resultado de todas estas validaciones
    son el factor integrante pertinente.*/
    private String miu (){
        if (this.normalizateP.size() == 1){return "e^( " + normalizateP.get(0) + " x";}
        else if (normalizateP.size() == 3){return "e^(( " + normalizateP.get(0) + " / " + normalizateP.get(2) + " )( x";}
        else if (normalizateP.size() == 5){return "e^(( " + divideFraction(Integer.parseInt(normalizateP.get(0)),(Integer.parseInt(normalizateP.get(4))+1)) + " )( x ^ " + (Integer.parseInt(normalizateP.get(4))+1) ;}
        else if (normalizateP.size() == 7){
            if(Integer.parseInt(normalizateP.get(2)) == 1) {
                if (normalizateP.get(6).equals("1")) {
                    return "x^( " + Integer.parseInt(normalizateP.get(0)) * (Integer.parseInt(normalizateP.get(6))) + " )";
                }else {
                    return "e^-( " + divideFraction(Integer.parseInt(normalizateP.get(0)),Integer.parseInt(normalizateP.get(2))*(Integer.parseInt(normalizateP.get(6))-1)) + " )( 1 / x ^ " + (Integer.parseInt(normalizateP.get(6))-1);
                }
            }
                else return "e^(( " +divideFraction(Integer.parseInt(normalizateP.get(0)),Integer.parseInt(normalizateP.get(2))*(Integer.parseInt(normalizateP.get(6))+1)) + " )( x^( " + (Integer.parseInt(normalizateP.get(6))+1) + " )";
        } else if (normalizateP.size() == 9 && normalizateP.get(8).equals("1")) {
            return "x^( " + divideFraction((Integer.parseInt(normalizateP.get(0))*Integer.parseInt(normalizateP.get(8))),Integer.parseInt(normalizateP.get(2)));
        }
        return "e^-( " + divideFraction((Integer.parseInt(normalizateP.get(0))),Integer.parseInt(normalizateP.get(2))*(Integer.parseInt(normalizateP.get(8))-1)) +" )( 1 / x ^ " + (Integer.parseInt(normalizateP.get(8))-1);
    }
}