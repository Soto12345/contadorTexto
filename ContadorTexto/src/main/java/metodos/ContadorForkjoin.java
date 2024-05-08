package metodos;

import java.util.concurrent.RecursiveTask;

public class ContadorForkjoin {

    // Clase interna para dividir la tarea en sub tareas
    static class ContadorTask extends RecursiveTask<Integer> {

        private static final int UMBRAL = 1000; // Umbral para la división de la tarea

        private String texto;
        private int inicio;
        private int fin;

        ContadorTask(String texto, int inicio, int fin) {
            this.texto = texto;
            this.inicio = inicio;
            this.fin = fin;
        }

        @Override
        protected Integer compute() {
            if (fin - inicio <= UMBRAL) { // Verificar si la tarea es lo suficientemente pequeña como para ser realizada secuencialmente
                int contador = 0;
                for (int i = inicio; i < fin; i++) {
                    if (Character.isLetter(texto.charAt(i))) {
                        contador++;
                    }
                }
                return contador;
            } else { // Dividir la tarea en sub-tareas
                int mitad = inicio + (fin - inicio) / 2;
                ContadorTask leftTask = new ContadorTask(texto, inicio, mitad);
                ContadorTask rightTask = new ContadorTask(texto, mitad, fin);
                leftTask.fork();
                int rightResult = rightTask.compute();
                int leftResult = leftTask.join();
                return leftResult + rightResult;
            }
        }
    }

   public static void ejecucionInicial(String texto) {
        Thread t = new Thread(() -> contarLetras(texto));
        t.start();
    }

    public static int contarLetras(String texto) {
        ContadorTask task = new ContadorTask(texto, 0, texto.length());
        return task.invoke(); // Invocar la tarea Fork/Join
    }
}
