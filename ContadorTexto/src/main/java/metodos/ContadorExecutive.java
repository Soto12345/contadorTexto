package metodos;


import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ContadorExecutive {

      public static int contarLetras(String texto) {
        // Crear un ExecutorService con un número fijo de hilos
        ExecutorService executor = Executors.newFixedThreadPool(4);

        // Crear un CompletionService para recibir los resultados de los hilos a medida que estén disponibles
        CompletionService<Integer> completionService = new ExecutorCompletionService<>(executor);

        try {
            // Calcular el tamaño de cada parte para dividir el texto equitativamente entre los hilos
            int chunkSize = (int) Math.ceil((double) texto.length() / 4);

            // Dividir el texto en partes y asignar la tarea de contar letras a cada hilo
            for (int i = 0; i < 4; i++) {
                int inicio = i * chunkSize;
                int fin = Math.min((i + 1) * chunkSize, texto.length());
                String subTexto = texto.substring(inicio, fin);

                // Ejecutar la tarea en un hilo y enviar el Future al CompletionService
                completionService.submit(new ContadorCallable(subTexto));
            }

            // Calcular el resultado final sumando los resultados parciales
            int totalLetras = 0;
            for (int i = 0; i < 4; i++) {
                Future<Integer> resultado = completionService.take(); // Esperar a que una tarea esté completa y obtener su resultado
                totalLetras += resultado.get(); // Obtener el resultado del Future y sumarlo al total de letras
            }
            return totalLetras;
        } catch (InterruptedException | ExecutionException e) {
            Thread.currentThread().interrupt();
            return 0;
        } finally {
            // Apagar el ExecutorService después de su uso
            executor.shutdown();
        }
    }

    // Clase interna para contar letras en paralelo utilizando Callable
    static class ContadorCallable implements Callable<Integer> {
        private final String texto;

        ContadorCallable(String texto) {
            this.texto = texto;
        }

        @Override
        public Integer call() {
            int contador = 0;
            for (char c : texto.toCharArray()) {
                if (Character.isLetter(c)) {
                    contador++;
                }
            }
            return contador;
        }
    }
}