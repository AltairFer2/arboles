import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

class Nodo {
    int valor;
    Nodo izquierdo;
    Nodo derecho;

    public Nodo(int valor) {
        this.valor = valor;
        this.izquierdo = null;
        this.derecho = null;
    }
}

class ArbolBinario {
    Nodo raiz;

    public ArbolBinario() {
        raiz = null;
    }

    public void insertar(int valor) {
        raiz = insertarRec(raiz, valor);
    }

    private Nodo insertarRec(Nodo nodo, int valor) {
        if (nodo == null) {
            return new Nodo(valor);
        }

        if (valor < nodo.valor) {
            nodo.izquierdo = insertarRec(nodo.izquierdo, valor);
        } else if (valor > nodo.valor) {
            nodo.derecho = insertarRec(nodo.derecho, valor);
        }

        return nodo;
    }
}

public class ArbolBinarioGUI extends JFrame {
    private ArbolBinario arbol;
    private JButton iluminarButton;
    private boolean iluminarRama = false;
    private List<Integer> ramaMayorPonderacion = new ArrayList<>();

    public ArbolBinarioGUI(ArbolBinario arbol) {
        this.arbol = arbol;
        setTitle("Árbol Binario");
        setSize(800, 400); // Cambia las dimensiones del JFrame
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);
        setResizable(false);

        DibujoArbol dibujoArbol = new DibujoArbol(arbol);
        add(dibujoArbol, BorderLayout.CENTER);

        iluminarButton = new JButton("Iluminar Rama Mayor Ponderación");
        iluminarButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ramaMayorPonderacion = encontrarRamaMayorPonderacion(arbol.raiz);
                iluminarRama = true;
                repaint();
            }
        });
        add(iluminarButton, BorderLayout.SOUTH);

        setVisible(true);
    }

    public static void main(String[] args) {
        ArbolBinario arbol = new ArbolBinario();
        arbol.insertar(50);
        arbol.insertar(30);
        arbol.insertar(70);
        arbol.insertar(20);
        arbol.insertar(40);
        arbol.insertar(60);
        arbol.insertar(80);

        SwingUtilities.invokeLater(() -> new ArbolBinarioGUI(arbol));
    }

    class DibujoArbol extends JPanel {
        private int radioNodo = 40; // Cambia el tamaño de los nodos
        private int espacioHorizontal = 80; // Cambia el espacio horizontal entre nodos
        private Font font = new Font("Arial", Font.BOLD, 18); // Cambia la fuente y tamaño de fuente

        public DibujoArbol(ArbolBinario arbol) {
            setPreferredSize(new Dimension(1200, 800)); // Cambia las dimensiones del JPanel
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.setFont(font); // Establece la fuente
            if (arbol.raiz != null) {
                dibujarNodo(g, getWidth() / 2, 50, arbol.raiz);
            }
        }

        private void dibujarNodo(Graphics g, int x, int y, Nodo nodo) {
            if (iluminarRama && ramaMayorPonderacion.contains(nodo.valor)) {
                g.setColor(Color.RED);
            } else {
                g.setColor(Color.WHITE);
            }
            g.fillOval(x - radioNodo, y - radioNodo, 2 * radioNodo, 2 * radioNodo);
            g.setColor(Color.BLACK);
            g.drawOval(x - radioNodo, y - radioNodo, 2 * radioNodo, 2 * radioNodo);
            g.drawString(String.valueOf(nodo.valor), x - 10, y + 6); // Ajusta la posición del texto

            if (nodo.izquierdo != null) {
                int xIzq = x - espacioHorizontal / 2;
                int yIzq = y + 100; // Ajusta la posición vertical de los nodos hijos
                g.drawLine(x, y + radioNodo, xIzq, yIzq - radioNodo);
                dibujarNodo(g, xIzq, yIzq, nodo.izquierdo);
            }

            if (nodo.derecho != null) {
                int xDer = x + espacioHorizontal / 2;
                int yDer = y + 100; // Ajusta la posición vertical de los nodos hijos
                g.drawLine(x, y + radioNodo, xDer, yDer - radioNodo);
                dibujarNodo(g, xDer, yDer, nodo.derecho);
            }
        }
    }

    private List<Integer> encontrarRamaMayorPonderacion(Nodo nodo) {
        List<Integer> ramaActual = new ArrayList<>();
        List<Integer> mejorRama = new ArrayList<>();
        int[] maxPonderacion = { Integer.MIN_VALUE };
        encontrarRamaMayorPonderacionRec(nodo, ramaActual, mejorRama, maxPonderacion);
        return mejorRama;
    }

    private void encontrarRamaMayorPonderacionRec(Nodo nodo, List<Integer> ramaActual, List<Integer> mejorRama,
            int[] maxPonderacion) {
        if (nodo == null) {
            return;
        }

        ramaActual.add(nodo.valor);

        if (nodo.valor > maxPonderacion[0]) {
            maxPonderacion[0] = nodo.valor;
            mejorRama.clear();
            mejorRama.addAll(ramaActual);
        }

        encontrarRamaMayorPonderacionRec(nodo.izquierdo, ramaActual, mejorRama, maxPonderacion);
        encontrarRamaMayorPonderacionRec(nodo.derecho, ramaActual, mejorRama, maxPonderacion);

        ramaActual.remove(ramaActual.size() - 1);
    }
}
