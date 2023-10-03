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

public class ArbolBinario {
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

    public void dibujarArbol() {
        dibujarArbolRec(raiz, "", true);
    }

    private void dibujarArbolRec(Nodo nodo, String prefix, boolean isLeft) {
        if (nodo != null) {
            System.out.println(prefix + (isLeft ? "├── " : "└── ") + nodo.valor);
            dibujarArbolRec(nodo.izquierdo, prefix + (isLeft ? "│   " : "    "), true);
            dibujarArbolRec(nodo.derecho, prefix + (isLeft ? "│   " : "    "), false);
        }
    }

    public List<Integer> obtenerRamaMayorPonderacion() {
        List<Integer> ramaActual = new ArrayList<>();
        List<Integer> mejorRama = new ArrayList<>();
        int[] maxPonderacion = { Integer.MIN_VALUE };
        obtenerRamaMayorPonderacionRec(raiz, ramaActual, mejorRama, maxPonderacion);
        return mejorRama;
    }

    private void obtenerRamaMayorPonderacionRec(Nodo nodo, List<Integer> ramaActual, List<Integer> mejorRama,
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

        obtenerRamaMayorPonderacionRec(nodo.izquierdo, ramaActual, mejorRama, maxPonderacion);
        obtenerRamaMayorPonderacionRec(nodo.derecho, ramaActual, mejorRama, maxPonderacion);

        ramaActual.remove(ramaActual.size() - 1);
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

        System.out.println("Árbol Binario:");
        arbol.dibujarArbol();

        List<Integer> ramaMayorPonderacion = arbol.obtenerRamaMayorPonderacion();
        System.out.println("\nRama con mayor ponderación: " + ramaMayorPonderacion);
    }
}
