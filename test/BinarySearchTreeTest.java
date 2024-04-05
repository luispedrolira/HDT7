import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Comparator;

import org.junit.Test;

public class BinarySearchTreeTest {
    @Test
    public void testInOrderWalk() {
        Comparator<Integer> comparator = Integer::compare;
        BinarySearchTree<Integer, String> tree = new BinarySearchTree<>(comparator);
        StringBuilder result = new StringBuilder();

        tree.insert(10, "10");
        tree.insert(5, "5");
        tree.insert(15, "15");

        tree.InOrderWalk(value -> result.append(value).append(","));

        assertEquals("5,10,15,", result.toString());
    }

    @Test
    public void testPostOrderWalk() {
        Comparator<Integer> comparator = Integer::compare;
        BinarySearchTree<Integer, String> tree = new BinarySearchTree<>(comparator);
        StringBuilder result = new StringBuilder();

        // Insertamos algunos nodos
        tree.insert(10, "10");
        tree.insert(5, "5");
        tree.insert(15, "15");
        tree.insert(3, "3");
        tree.insert(7, "7");

        // Realizamos el recorrido postorden
        tree.PostOrderWalk(value -> result.append(value).append(","));

        // Verificamos que el orden de los elementos sea el esperado
        assertEquals("3,7,5,15,10,", result.toString());
    }

    @Test
    public void testPreOrderWalk() {
        Comparator<Integer> comparator = Integer::compare;
        BinarySearchTree<Integer, String> tree = new BinarySearchTree<>(comparator);
        StringBuilder result = new StringBuilder();

        // Insertamos algunos nodos
        tree.insert(10, "10");
        tree.insert(5, "5");
        tree.insert(15, "15");
        tree.insert(3, "3");
        tree.insert(7, "7");

        // Realizamos el recorrido preorden
        tree.PreOrderWalk(value -> result.append(value).append(","));

        // Verificamos que el orden de los elementos sea el esperado
        assertEquals("10,5,3,7,15,", result.toString());
    }

    @Test
    public void testCount() {
        Comparator<Integer> comparator = Integer::compare;
        BinarySearchTree<Integer, String> tree = new BinarySearchTree<>(comparator);
        
        // Verificamos que el conteo inicial sea cero.
        assertEquals(0, tree.count());
        
        // Insertamos algunos nodos y verificamos el conteo después de cada inserción.
        tree.insert(10, "Diez");
        assertEquals(1, tree.count());
        
        tree.insert(5, "Cinco");
        assertEquals(2, tree.count());
        
        tree.insert(15, "Quince");
        assertEquals(3, tree.count());
    }

    @Test
    public void testFind() {
        Comparator<Integer> comparator = Integer::compare;
        BinarySearchTree<Integer, String> tree = new BinarySearchTree<>(comparator);

        tree.insert(10, "Diez");
        tree.insert(5, "Cinco");
        tree.insert(20, "Veinte");

        assertEquals("Diez", tree.find(10));
        assertEquals("Cinco", tree.find(5));
        assertNull(tree.find(999)); // Not found
    }

    @Test
    public void testInsert() {
        Comparator<Integer> comparator = Integer::compare;
        BinarySearchTree<Integer, String> tree = new BinarySearchTree<>(comparator);
        
        // Inicialmente, el árbol debe estar vacío.
        assertTrue(tree.isEmpty());

        // Insertamos un nodo y verificamos que ya no esté vacío.
        tree.insert(10, "Diez");
        assertFalse(tree.isEmpty());
        
        // Verificamos el valor insertado.
        assertEquals("Diez", tree.find(10), "El valor obtenido no coincide con el insertado.");
    }

    @Test
    public void testIsEmpty() {
        Comparator<Integer> comparator = Integer::compare;
        BinarySearchTree<Integer, String> tree = new BinarySearchTree<>(comparator);

        assertTrue(tree.isEmpty());

        tree.insert(10, "Diez");
        assertFalse(tree.isEmpty());
    }

    @Test
    public void testRemove() {
        Comparator<Integer> comparator = Integer::compare;
        BinarySearchTree<Integer, String> tree = new BinarySearchTree<>(comparator);
        
        // Insertamos algunos nodos.
        tree.insert(10, "Diez");
        tree.insert(5, "Cinco");
        tree.insert(20, "Veinte");
        tree.insert(15, "Quince");
        
        // Verificamos el conteo inicial de nodos en el árbol.
        assertEquals(4, tree.count());
        
        // Eliminamos un nodo que no tiene hijos.
        String removed = tree.remove(5);
        assertEquals("Cinco", removed);
        assertEquals(3, tree.count());
        
        // Eliminamos un nodo que tiene un hijo.
        removed = tree.remove(20);
        assertEquals("Veinte", removed);
        assertEquals(2, tree.count());
        
        // Verificamos que los nodos hayan sido eliminados correctamente.
        assertNull(tree.find(5));
        assertNull(tree.find(20));
        
        // Finalmente, eliminamos un nodo con dos hijos.
        removed = tree.remove(10);
        assertEquals("Diez", removed);
        assertEquals(1, tree.count());
        
        // Verificamos que el nodo haya sido eliminado correctamente.
        assertNull(tree.find(10));
    }
}
