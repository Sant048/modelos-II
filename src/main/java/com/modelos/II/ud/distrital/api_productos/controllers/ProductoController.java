package com.modelos.II.ud.distrital.api_productos.controllers;

import com.modelos.II.ud.distrital.api_productos.models.Producto;
import com.modelos.II.ud.distrital.api_productos.services.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/productos")
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    // Obtener todos los productos
    @GetMapping("/")
    public List<Producto> obtenerTodos() {
        return productoService.obtenerTodos();
    }

    // Obtener un producto por ID
    @GetMapping("/{id}")
    public ResponseEntity<Producto> obtenerPorId(@PathVariable Long id) {
        Optional<Producto> producto = productoService.obtenerPorId(id);
        if (producto.isPresent()) {
            return ResponseEntity.ok(producto.get());  // Si existe, devolver con 200 OK
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();  // Si no existe, devolver 404 Not Found
        }
    }

    // Crear un producto
    @PostMapping("/")
    public ResponseEntity<Producto> crearProducto(@RequestBody Producto producto) {
        Producto productoCreado = productoService.crearProducto(producto);
        return ResponseEntity.status(HttpStatus.CREATED).body(productoCreado);  // Devuelve el producto creado con 201 Created
    }

    // Actualizar un producto
    @PutMapping("/{id}")
    public ResponseEntity<Producto> actualizarProducto(@PathVariable Long id, @RequestBody Producto producto) {
        Producto productoActualizado = productoService.actualizarProducto(id, producto);
        if (productoActualizado != null) {
            return ResponseEntity.ok(productoActualizado);  // Devuelve el producto actualizado con 200 OK
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();  // Si no se encuentra, devuelve 404 Not Found
        }
    }

    // Eliminar un producto
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarProducto(@PathVariable Long id) {
        boolean eliminado = productoService.eliminarProducto(id);
        if (eliminado) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();  // Devuelve 204 No Content
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();  // Si no se encuentra, devuelve 404 Not Found
        }
    }
}
