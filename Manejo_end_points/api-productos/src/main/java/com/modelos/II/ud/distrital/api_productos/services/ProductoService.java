package com.modelos.II.ud.distrital.api_productos.services;

import com.modelos.II.ud.distrital.api_productos.models.Producto;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductoService {

    private List<Producto> productos = new ArrayList<>();
    private Long idCounter = 1L;  // Empezamos el contador de IDs en 1
    private static final String FILE_NAME = "productos.dat"; 
    private static final String COUNTER_FILE = "idCounter.dat";
    

    public ProductoService() {
        cargarProductos();
        cargarIdCounter();
    }

    public List<Producto> obtenerTodos() {
        return productos;
    }

    public Optional<Producto> obtenerPorId(Long id) {
        return productos.stream()
                .filter(producto -> producto.getId().equals(id))
                .findFirst();
    }

    // Método para crear un nuevo producto
    public Producto crearProducto(Producto producto) {
        producto.setId(idCounter++);  // Asignamos el siguiente ID
        productos.add(producto);  // Añadir el producto a la lista
        guardarProductos();
        guardarIdCounter();
        return producto;
    }

    // Método para actualizar un producto
    public Producto actualizarProducto(Long id, Producto producto) {
        for (int i = 0; i < productos.size(); i++) {
            if (productos.get(i).getId().equals(id)) {
                productos.set(i, producto);
                guardarProductos();
                return producto;
            }
        }
        return null;
    }

    // Método para eliminar un producto
    public boolean eliminarProducto(Long id) {
        boolean eliminado = productos.removeIf(producto -> producto.getId().equals(id));  // Elimina el producto por su ID
        if (eliminado) {
            reorganizarIds();  // Reorganizamos los IDs después de eliminar
            guardarProductos();  // Guardamos los cambios
        }
        return eliminado;
    }

    // Método para reorganizar los IDs después de eliminar un producto
    private void reorganizarIds() {
        long nuevoId = 1L;  // Reasignamos los IDs empezando desde 1
        for (Producto producto : productos) {
            producto.setId(nuevoId++);  // Asignamos un nuevo ID secuencial
        }
        idCounter = nuevoId;  // Actualizamos el contador de IDs
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
            productos = (List<Producto>) ois.readObject();
            System.out.println("Productos cargados desde archivo: " + productos.size());
        } catch (FileNotFoundException e) {
            System.out.println("Archivo de productos no encontrado, se inicializa lista vacía.");
            productos = new ArrayList<>();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            productos = new ArrayList<>();
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
            System.out.println("Archivo de contador no encontrado, se inicializa con valor 1.");
            idCounter = 1L;
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            idCounter = 1L;  // Si ocurre un error, reiniciamos el contador
        }
    }
}
