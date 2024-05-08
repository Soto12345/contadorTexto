
package metodos;

public class ContadorSecuencial {
    public static int contarLetras(String texto) {
        int contador = 0;

        // Recorremos cada carácter del texto
        for (int i = 0; i < texto.length(); i++) {
            char caracter = texto.charAt(i);
            // Verificamos si el carácter es una letra del alfabeto
            if (Character.isLetter(caracter)) {
                contador++;
            }
        }

        return contador;
    }
}
