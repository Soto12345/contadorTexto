package com.mycompany.contadortexto;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import metodos.ContadorExecutive;
import metodos.ContadorForkjoin;

public class ContadorTexto extends JFrame {
    
    private JTextArea txtOriginal;
    
    public ContadorTexto() {
        // Inicializar la instancia de Generar
        setTitle("Contador de Letras");
        setSize(820, 500);
        setLayout(null);
        setLocationRelativeTo(null);

        // Textos originales
        JLabel lblOriginal = new JLabel("Original");
        lblOriginal.setBounds(50, 20, 100, 20);
        add(lblOriginal);
        
        txtOriginal = new JTextArea();
        txtOriginal.setBounds(50, 50, 350, 100);
        add(txtOriginal);

        // Textos resultado
        JLabel lblResultado = new JLabel("Resultado");
        lblResultado.setBounds(420, 20, 100, 20);
        add(lblResultado);
        
        JTextArea txtResultado = new JTextArea();
        txtResultado.setBounds(420, 50, 350, 100);
        add(txtResultado);

        // Botones y etiquetas
        JButton btnSecuencial = new JButton("Secuencial");
        btnSecuencial.setBounds(50, 200, 150, 30);
        add(btnSecuencial);
        
        JLabel txtTituloSecuencial = new JLabel("Secuencial");
        txtTituloSecuencial.setBounds(50, 240, 150, 20);
        add(txtTituloSecuencial);
        
        JLabel txtTiempoSecuencial = new JLabel("Tiempo:");
        txtTiempoSecuencial.setBounds(50, 260, 150, 20);
        add(txtTiempoSecuencial);
        
        JButton btnForkjoin = new JButton("Fork/join");
        btnForkjoin.setBounds(250, 200, 150, 30);
        add(btnForkjoin);
        
        JLabel txtTituloForkjoin = new JLabel("Fork/Join");
        txtTituloForkjoin.setBounds(250, 240, 150, 20);
        add(txtTituloForkjoin);
        
        JLabel txtTiempoForkjoin = new JLabel("Tiempo:");
        txtTiempoForkjoin.setBounds(250, 260, 150, 20);
        add(txtTiempoForkjoin);
        
        JButton btnExecutive = new JButton("Executive Service");
        btnExecutive.setBounds(450, 200, 150, 30);
        add(btnExecutive);
        
        JLabel txtTituloExecutive = new JLabel("Executive Service");
        txtTituloExecutive.setBounds(450, 240, 150, 20);
        add(txtTituloExecutive);
        
        JLabel txtTiempoExecutive = new JLabel("Tiempo:");
        txtTiempoExecutive.setBounds(450, 260, 150, 20);
        add(txtTiempoExecutive);
        
        JButton btnBorrar = new JButton("Borrar");
        btnBorrar.setBounds(650, 200, 150, 30);
        add(btnBorrar);
        
        JLabel txtTituloBorrar = new JLabel("Borrar");
        txtTituloBorrar.setBounds(650, 240, 150, 20);
        add(txtTituloBorrar);
// Precalienta Fork/Join en segundo plano al inicio del programa
        Thread precalentamientoThread = new Thread(() -> {
            // Realiza un cálculo inicial utilizando Fork/Join
            String textoInicial = "Texto de precalentamiento"; // Puedes usar cualquier texto para el precalentamiento
            ContadorForkjoin.contarLetras(textoInicial);
            ContadorExecutive.contarLetras(textoInicial);
        });
        precalentamientoThread.start();
        //eventos 
        //evento borrar
        btnBorrar.addActionListener(e -> {
            txtResultado.setText("");
            txtOriginal.setText("");
            txtTiempoSecuencial.setText("Tiempo:");
            txtTiempoForkjoin.setText("Tiempo:");
            txtTiempoExecutive.setText("Tiempo:");
        });
        //evento de secuencial
        btnSecuencial.addActionListener(e -> {
            String texto = txtOriginal.getText(); // Obtener el texto del área de texto original

            // Medir el tiempo de ejecución
            long inicio = System.nanoTime();
            int letrasContadas = metodos.ContadorSecuencial.contarLetras(texto); // Llamar al método contarLetras
            long fin = System.nanoTime();
            long tiempoEjecucion = fin - inicio;
            
            txtResultado.setText("El número de letras en el texto es: " + letrasContadas); // Mostrar el resultado en el área de texto de resultado

            // Convertir nanosegundos a milisegundos y mostrar el tiempo de ejecución en el área de texto de tiempo
            double tiempoMilisegundos = tiempoEjecucion / 1_000_000.0;
            txtTiempoSecuencial.setText("Tiempo: " + tiempoMilisegundos + " ms");
        });
        //evento Forkjoin
        btnForkjoin.addActionListener(e -> {
            
            String texto = txtOriginal.getText(); // Obtener el texto del área de texto original

            // Medir el tiempo de ejecución
            long inicio = System.nanoTime();
            int letrasContadas = metodos.ContadorForkjoin.contarLetras(texto); // Llamar al método contarLetras utilizando Fork/Join
            long fin = System.nanoTime();
            long tiempoEjecucion = fin - inicio;
            
            txtResultado.setText("El número de letras en el texto es: " + letrasContadas); // Mostrar el resultado en el área de texto de resultado

            // Convertir nanosegundos a milisegundos y mostrar el tiempo de ejecución en el área de texto de tiempo
            double tiempoMilisegundos = tiempoEjecucion / 1_000_000.0;
            txtTiempoForkjoin.setText("Tiempo: " + tiempoMilisegundos + " ms");
        });
        //evento Executive
        btnExecutive.addActionListener(e -> {
            String texto = txtOriginal.getText(); // Obtener el texto del área de texto original

            // Medir el tiempo de ejecución
            long inicio = System.nanoTime();
            int letrasContadas = metodos.ContadorExecutive.contarLetras(texto); // Llamar al método contarLetras utilizando ExecutorService
            long fin = System.nanoTime();
            long tiempoEjecucion = fin - inicio;
            
            txtResultado.setText("El número de letras en el texto es: " + letrasContadas); // Mostrar el resultado en el área de texto de resultado

            // Convertir nanosegundos a milisegundos y mostrar el tiempo de ejecución en el área de texto de tiempo
            double tiempoMilisegundos = tiempoEjecucion / 1_000_000.0;
            txtTiempoExecutive.setText("Tiempo: " + tiempoMilisegundos + " ms");
        });
    }
    
    public static void main(String[] args) {
        ContadorTexto window = new ContadorTexto();
        window.setVisible(true);
    }
}
