package com.modelos.II.ud.distrital.api_productos.services;

import com.modelos.II.ud.distrital.api_productos.models.Producto;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductoService {

    // Lista en memoria que simula una base de datos
    private List<Producto> productos = new ArrayList<>();
    private Long idCounter = 1L;  // Empezamos el contador de IDs en 1
    private static final String FILE_NAME = "productos.dat";  // Archivo donde guardaremos los productos
    private static final String COUNTER_FILE = "idCounter.dat";  // Archivo donde guardaremos el contador de IDs

    // Constructor para cargar productos y el contador al iniciar la aplicación
    public ProductoService() {
        cargarProductos();
        cargarIdCounter();
    }

    // Método para obtener todos los productos
    public List<Producto> obtenerTodos() {
        return productos;
    }

    // Método para obtener un producto por ID
    public Optional<Producto> obtenerPorId(Long id) {
        return productos.stream()
                .filter(producto -> producto.getId().equals(id))
                .findFirst();
    }

    // Método para crear un nuevo producto
    public Producto crearProducto(Producto producto) {
        // Asignamos un ID único al producto antes de agregarlo
        producto.setId(idCounter++);
        productos.add(producto);  // Añadir el producto a la lista
        guardarProductos();  // Guardamos la lista de productos
        guardarIdCounter();  // Guardamos el valor actualizado de idCounter
        return producto;  // Devolvemos el producto con el ID asignado
    }

    // Método para actualizar un producto
    public Producto actualizarProducto(Long id, Producto producto) {
        for (int i = 0; i < productos.size(); i++) {
            if (productos.get(i).getId().equals(id)) {
                productos.set(i, producto);  // Actualizamos el producto
                guardarProductos();  // Guardamos la lista de productos
                return producto;  // Retornamos el producto actualizado
            }
        }
        return null;  // Si no se encuentra el producto, devolvemos null
    }

    // Método para eliminar un producto
    public boolean eliminarProducto(Long id) {
        boolean eliminado = productos.removeIf(producto -> producto.getId().equals(id));  // Elimina el producto por su ID
        if (eliminado) {
            guardarProductos();  // Guardamos la lista de productos
        }
        return eliminado;
    }

    // Método para guardar la lista de productos en un archivo
    private void guardarProductos() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(productos);  // Escribe la lista de productos al archivo
            System.out.println("Productos guardados en el archivo.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Método para cargar la lista de productos desde el archivo
    private void cargarProductos() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            productos = (List<Producto>) ois.readObject();  // Lee la lista de productos desde el archivo
            System.out.println("Productos cargados desde archivo: " + productos.size());
        } catch (FileNotFoundException e) {
            // Si el archivo no se encuentra, inicializamos una lista vacía.
            System.out.println("Archivo de productos no encontrado, se inicializa lista vacía.");
            productos = new ArrayList<>();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            productos = new ArrayList<>();  // Inicializamos la lista vacía
        }
    }

    // Método para guardar el contador de IDs en un archivo
    private void guardarIdCounter() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(COUNTER_FILE))) {
            oos.writeObject(idCounter);  // Escribe el valor de idCounter en el archivo
            System.out.println("Contador de IDs guardado en el archivo.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Método para cargar el contador de IDs desde el archivo
    private void cargarIdCounter() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(COUNTER_FILE))) {
            idCounter = (Long) ois.readObject();  // Lee el valor de idCounter desde el archivo
            System.out.println("Contador de IDs cargado desde archivo: " + idCounter);
        } catch (FileNotFoundException e) {
            // Si el archivo no existe, se inicializa el contador con 1L
            System.out.println("Archivo de contador no encontrado, se inicializa con valor 1.");
            idCounter = 1L;
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            idCounter = 1L;  // Si ocurre un error, reiniciamos el contador
        }
    }
}
