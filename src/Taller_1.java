import edu.princeton.cs.stdlib.StdDraw;
import edu.princeton.cs.stdlib.StdIn;
import edu.princeton.cs.stdlib.StdOut;

import java.awt.*;

public class Taller_1 {

    public static void main(String[] args) {

        //Hecho por Benjamín Rivera Portilla (paralelo C2 - Programación Avanzada)

        //Se devuelve un vector auxiliar con el valor mínimo y máximo, en este subprograma.
        double [] min_y_max = Definicion_De_Pantalla();

        //Se devuelve una matriz que almacena las diferentes velocidades de cada coordenada de cada línea.
        double [][] MatrizVelocidades = Definicion_De_Velocidades();

        //Se devuleve una matriz que almacena cada coordenada para cada una de las 6 líneas.
        double [][] MatrizPosiciones = Definicion_De_Posiciones(min_y_max,MatrizVelocidades);

        //Se crea un vector que almacena cada color, y después se devuelve un vector con cada número de color que posee cada línea.
        Color[] colores = {Color.red,Color.ORANGE,Color.CYAN,Color.PINK,Color.GREEN,Color.MAGENTA,Color.BLUE,Color.YELLOW};
        int[] colorPorLinea = Relleno_Primeros_Colores(colores);

        //Se pregunta al usuario si desea que las líneas cambien de color al contactar el borde de la ventana.
        String respuestaColor = ActivarCambioColorChoque();

        while (true){
            //Ciclo infinito de dibujo de las líneas. Se dibuja cada línea con sus coordenadas y color,
            //cada línea se mueve con su respectiva velocidad en cada coordenada, y se realiza un chequeo
            //si la línea está a punto de chocar con un borde.
            DibujoDeLineas(MatrizPosiciones,MatrizVelocidades,colorPorLinea,colores);
            MovimientoDeLineas(MatrizPosiciones,MatrizVelocidades);
            ReboteDeLineas(MatrizPosiciones,MatrizVelocidades,colorPorLinea,colores,respuestaColor);
        }
    }


    /**
     * Método/subprograma: Definicion_De_Pantalla.
     * El mínimo y máximo para coordenada es -1.0 y 1.0, respectivamente. Se settean estos valores
     * para X e Y, y se activa doble búfer (evitar problemas gráficos). Se retornan en un vector auxiliar.
     * Coordenada mínima: (-1,-1). Coordenada máxima: (1,1).
     * @return min_y_max (valor mínimo, espacio 0: -1.0. valor máximo, espacio 1: 1.0)
     */

    private static double[] Definicion_De_Pantalla() {
        double min = -1.0;
        double max = 1.0;
        double [] min_y_max = new double[2];
        StdDraw.setXscale(min,max);
        StdDraw.setYscale(min,max);
        StdDraw.enableDoubleBuffering();
        min_y_max[0] = min;
        min_y_max[1] = max;
        return min_y_max;
    }

    /**
     * Método/subprograma: Definicion_De_Velocidades.
     * Se crea una matriz que almacena cada velocidad de las coordenadas para cada línea (No en todas las instancias
     * tendrán la misma velocidad). Se generan al azar hasta que todos los valores sean menores o iguales a 0.01, y
     * mayores que 0. Se almacenan en la matriz y se retorna.
     * @return MatrizVelocidades (matriz de velocidades. Fila de la matriz: cada línea. Columna 0: X0. Columna 1: Y0.
     * Columna 2: X1. Columna 3: Y1).
     */
    private static double[][] Definicion_De_Velocidades() {
        double[][] MatrizVelocidades = new double[6][4];

        while (true){
            double velocidadX0 = Math.random();
            double velocidadY0 = Math.random();
            double velocidadX1 = Math.random();
            double velocidadY1 = Math.random();

            if((velocidadX0 <= 0.01 && velocidadY0 <= 0.01 && velocidadX1 <= 0.01 && velocidadY1 <= 0.01) &&
                    (velocidadX0 > 0 && velocidadY0 > 0 && velocidadX1 > 0 && velocidadY1 > 0)){
                for (int i = 0; i < 6; i++) {
                    MatrizVelocidades[i][0] = velocidadX0;
                    MatrizVelocidades[i][1] = velocidadY0;
                    MatrizVelocidades[i][2] = velocidadX1;
                    MatrizVelocidades[i][3] = velocidadY1;
                }
                break;
            }
        }
        return MatrizVelocidades;
    }

    /**
     * Subprograma/método: Definicion_De_Posiciones.
     * Se crea una matriz que almacena todas las posiciones de cada coordenada de los 2 puntos, que forman las líneas (X0,Y0,X1,Y1).
     * Se tiene un tamaño generado por defecto ((tamaño máximo: (-0.5,-0.5,0.5,0.5)) generadas al azar hasta que todas cumplan el tamaño requerido,
     * todas son calculadas respetando los límites de la pantalla), y luego se almacenan todas las posiciones en la primera fila
     * que pertenece a la primera línea. Cada línea toma distancia en X e Y, con las velocidades generadas (en X0, Y0, X1, Y1), multiplicadas por 10.
     * @param min_y_max (vector auxiliar que posee el valor mínimo y máximo)
     * @param MatrizVelocidades (matriz que posee todas las velocidades en cada coordenada)
     * @return MatrizPosiciones (matriz que posee cada una de las coordenadas para cada punto)
     */

    private static double[][] Definicion_De_Posiciones(double[] min_y_max, double[][] MatrizVelocidades) {
        double[][] MatrizPosiciones = new double[6][4];

        while (true) {

            // mínimo + (máximo - mínimo) * valor aleatorio entre 0 y 1.

            double x0 = min_y_max[0] + ((min_y_max[1] - min_y_max[0]) * Math.random());
            double y0 = min_y_max[0] + ((min_y_max[1] - min_y_max[0]) * Math.random());
            double x1 = min_y_max[0] + ((min_y_max[1] - min_y_max[0]) * Math.random());
            double y1 = min_y_max[0] + ((min_y_max[1] - min_y_max[0]) * Math.random());

            if (((Math.abs(x0) <= 0.5) && (Math.abs(y0) <= 0.5) && (Math.abs(x1) <= 0.5) && (Math.abs(y1) <= 0.5))){
                MatrizPosiciones[0][0] = x0;
                MatrizPosiciones[0][1] = y0;
                MatrizPosiciones[0][2] = x1;
                MatrizPosiciones[0][3] = y1;
                break;
            }
        }

        //Ciclo for que resta la velocidad (multiplicado por 10), a la coordenada de la línea anterior, para tomar distancia entre
        //cada línea.

        for (int i = 1; i < 6; i++) {
            MatrizPosiciones[i][0] = MatrizPosiciones[i-1][0] - (MatrizVelocidades[i-1][0] * 10);
            MatrizPosiciones[i][1] = MatrizPosiciones[i-1][1] - MatrizVelocidades[i-1][1] * 10;
            MatrizPosiciones[i][2] = MatrizPosiciones[i-1][2] - MatrizVelocidades[i-1][2] * 10;
            MatrizPosiciones[i][3] = MatrizPosiciones[i-1][3] - (MatrizVelocidades[i-1][3] * 10);
        }
        return MatrizPosiciones;
    }

    /**
     * Método/subprograma: Relleno_Primeros_Colores.
     * Se crea un vector (colorPorLinea), que almacena cada número de color que posee cada línea. Luego se ejecuta
     * otro subprograma que asigna un color no repetido a cada línea (la primera línea siempre es negra, por esta razón, se
     * asignó el valor 8 al primer espacio, porque es un valor imposible que no existe en el vector de colores, aunque
     * en la selección de colores siempre se empieza desde el segundo espacio, el 1).
     * @param colores (vector con los colores)
     * @return colorPorLinea
     */
    private static int[] Relleno_Primeros_Colores(Color[] colores) {
        int[] colorPorLinea = new int[6];
        colorPorLinea[0] = 8;
        for (int i = 1; i < 6; i++) {
            SeleccionColor(i,colorPorLinea,colores);
        }
        return colorPorLinea;
    }

    /**
     * Subprograma/método: ActivarCambioColorChoque.
     * Se pregunta al usuario si desea que las líneas cambien de color al chocar con los bordes de la ventana. "S" para Sí,
     * "N" para No. Se volverá a preguntar en caso de que el usuario no ingrese una respuesta válida (S,s,N, o n). Esta respuesta
     * se retorna.
     * @return respuesta (S,s,N, o n)
     */

    private static String ActivarCambioColorChoque() {
        StdOut.println("*************************************************************************************************************");
        StdOut.println("¿Desea que las líneas cambien de color al hacer contacto con un borde? (La primera línea siempre será negra).");
        StdOut.println("S para Sí | N para No");
        String respuesta = StdIn.readString();
        StdOut.println("*************************************************************************************************************");

        while (!respuesta.equalsIgnoreCase("s") && !respuesta.equalsIgnoreCase("n")){
            StdOut.println("Respuesta inválida. Intente de nuevo.");
            respuesta = StdIn.readString();
        }
        return respuesta;
    }

    /**
     * Subprograma/método: DibujoDeLineas.
     * Este es el proceso principal del programa. Aquí se dibujan todas las líneas, una por una. Primero,
     * en un ciclo for, se consigue el valor numérico del color de la línea (colorPorLinea[i]), y se colorea
     * con ese respectivo color (a no ser que se trate de la primera línea, de la fila 0. Siempre será negra,
     * por eso se le asigna el valor 8 (el vector de colores posee 8 colores, del 0 hasta al 7.), y se coloreará
     * siempre con el color negro). Se establece un grosor de 0.006 a cada línea, y se dibuja con sus respectivas
     * coordenadas. Luego, se realiza una pausa de 18 tiempos, se muestran, y se despeja la pantalla (para borrar
     * las líneas hechas anteriormente, haciendo ver que las líneas se están moviendo. El movimiento es tan rápido
     * que no se alcanza a apreciar las líneas anteriores).
     * @param matrizPosiciones (matriz con las posiciones)
     * @param matrizVelocidades (matriz con las velocidades)
     * @param colorPorLinea (vector que posee los valores numéricos de los colores)
     * @param colores (vector con los colores).
     */


    private static void DibujoDeLineas(double[][] matrizPosiciones, double[][] matrizVelocidades, int[] colorPorLinea, Color[] colores) {
        for (int i = 0; i < 6; i++) {
            if (i == 0){
                colorPorLinea[0] = 8;
                StdDraw.setPenColor(Color.black);
            }else{
                int numero_color = colorPorLinea[i];
                StdDraw.setPenColor(colores[numero_color]);
            }
            StdDraw.setPenRadius(0.006);
            StdDraw.line(matrizPosiciones[i][0],matrizPosiciones[i][1],matrizPosiciones[i][2],matrizPosiciones[i][3]);
        }
        StdDraw.pause(18);
        StdDraw.show();
        StdDraw.clear();
    }

    /**
     * Subprograma/método: ReboteDeLineas.
     * Acá están las reglas cuando una de las coordenadas choca con el borde. Si el valor absoluto de la coordenada más su velocidad
     * es mayor a 1, la velocidad (en esa coordenada, de esa línea) se invierte. Si la respuesta a la pregunta desplegada
     * acerca del color es "S" o "s" (Sí), se realiza un cambio de color de la línea, llamando al subprograma de cambio de color.
     * Todo esto en un ciclo for que analiza el comportamiento de cada una de las 6 líneas.
     * @param matrizPosiciones (matriz con las posiciones)
     * @param matrizVelocidades (matriz con las velocidades)
     * @param colorPorLinea (vector que posee los valores numéricos de los colores)
     * @param colores (vectores con los colores).
     * @param respuestaColor (respuesta a la pregunta desplegada)
     */

    private static void ReboteDeLineas(double[][] matrizPosiciones, double[][] matrizVelocidades, int[] colorPorLinea, Color[] colores,String respuestaColor) {

        for (int i = 0; i < 6; i++) {

            //X0 y X1
            if (Math.abs((matrizPosiciones[i][0] + matrizVelocidades[i][0])) > 1.0) {
                matrizVelocidades[i][0] = -matrizVelocidades[i][0];
                if (respuestaColor.equalsIgnoreCase("S")){
                    SeleccionColor(i,colorPorLinea,colores);
                }
            }

            if (Math.abs((matrizPosiciones[i][2] + matrizVelocidades[i][2])) > 1.0) {
                matrizVelocidades[i][2] = -matrizVelocidades[i][2];
                if (respuestaColor.equalsIgnoreCase("S")){
                    SeleccionColor(i,colorPorLinea,colores);
                }
            }

            //Y0 y Y1
            if (Math.abs((matrizPosiciones[i][1] + matrizVelocidades[i][1])) > 1.0){
                matrizVelocidades[i][1] = -matrizVelocidades[i][1];
                if (respuestaColor.equalsIgnoreCase("S")){
                    SeleccionColor(i,colorPorLinea,colores);
                }
            }

            if (Math.abs((matrizPosiciones[i][3] + matrizVelocidades[i][3])) > 1.0){
                matrizVelocidades[i][3] = -matrizVelocidades[i][3];
                if (respuestaColor.equalsIgnoreCase("S")){
                    SeleccionColor(i,colorPorLinea,colores);
                }
            }


            //X0,Y0 / X1,Y1 (si el punto está a punto de superar a 1 al mismo tiempo por ambas coordenadas).
            if(((Math.abs(matrizPosiciones[i][0] + matrizVelocidades[i][0]) > 1.0 && Math.abs(matrizPosiciones[i][1] + matrizVelocidades[i][1]) > 1.0))){
                matrizVelocidades[i][0] = -matrizVelocidades[i][0];
                matrizVelocidades[i][1] = -matrizVelocidades[i][1];
                if (respuestaColor.equalsIgnoreCase("S")){
                    SeleccionColor(i,colorPorLinea,colores);
                }
            }

            if(Math.abs(matrizPosiciones[i][2] + matrizVelocidades[i][2]) > 1.0 && (Math.abs(matrizPosiciones[i][3] + matrizVelocidades[i][3]) > 1.0)){
                matrizVelocidades[i][2] = -matrizVelocidades[i][2];
                matrizVelocidades[i][3] = -matrizVelocidades[i][3];
                if (respuestaColor.equalsIgnoreCase("S")){
                    SeleccionColor(i,colorPorLinea,colores);
                }
            }
        }
    }

    /**
     * Subprograma/método: SeleccionColor.
     * En este subprograma, se seleccionan los colores de cada una de las líneas (se trae una posición en específico),
     * asignando un número a cada una. Siempre se empezará desde la segunda línea (espacio 1), ya que la primera siempre
     * es negra, para no perder vista de ella. Se genera un número aleatorio entero (multiplicación entre la longitud del
     * vector de colores (8) y un número entre 0 y 1). Luego, se realiza un chequeo con un ciclo for para revisar si ya existe
     * ese valor en el vector de color por línea (para que ningún color de línea se repita). Si ya existe ese valor, se rompe el ciclo
     * for y se inicia otra vez el ciclo while, y se repite hasta que no se logre encontrar el valor en el vector de color por
     * línea (es decir, que no sea un color repetido). Se asigna el número en la posición de la línea indicada, y se rompe
     * el ciclo while.
     * @param posicion (línea indicada para cambiarle el color).
     * @param colorPorLinea (vector que posee los valores numéricos de los colores)
     * @param colores (vectores con los colores).
     */

    private static void SeleccionColor(int posicion, int[] colorPorLinea, Color[] colores) {

        while (true){
            int random = (int) (colores.length * Math.random());
            int i = 1;
            for (i = 1; i < 6; i++) {
                if (random == colorPorLinea[i]){
                    break;
                }
            }

            if (i == 6){
                colorPorLinea[posicion] = random;
                break;
            }
        }
    }

    /**
     * Subprograma/método: MovimientoDeLineas.
     * Por cada repetición, cada coordenada de cada línea se desplaza con su respectiva velocidad.
     * @param MatrizPosiciones (matriz de posiciones).
     * @param MatrizVelocidades (matriz de velocidades).
     */

    private static void MovimientoDeLineas(double[][] MatrizPosiciones, double[][] MatrizVelocidades){

        for (int i = 0; i < 6; i++) {
            MatrizPosiciones[i][0] += MatrizVelocidades[i][0];
            MatrizPosiciones[i][2] += MatrizVelocidades[i][2];
            MatrizPosiciones[i][1] += MatrizVelocidades[i][1];
            MatrizPosiciones[i][3] += MatrizVelocidades[i][3];
        }
    }
    //Hecho por Benjamín Rivera Portilla (paralelo C2 - Programación Avanzada)
}