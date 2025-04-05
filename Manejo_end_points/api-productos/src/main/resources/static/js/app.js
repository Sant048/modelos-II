// Función para obtener todos los productos
function obtenerProductos() {
    fetch('/api/productos/')
        .then(response => response.json())  // Convertimos la respuesta en formato JSON
        .then(data => {
            mostrarProductos(data);  // Llamamos a una función para mostrar los productos en la interfaz
        })
        .catch(error => {
            console.error('Error al obtener productos:', error);
           
        });
}

// Función para mostrar los productos (puedes modificar esto para agregar productos a una lista en HTML)
function mostrarProductos(productos) {
    const listaProductos = document.getElementById('productos-lista');
    listaProductos.innerHTML = '';  // Limpiamos la lista antes de agregar los nuevos productos
    productos.forEach(producto => {
        const li = document.createElement('li');
        li.textContent = `ID: ${producto.id} - ${producto.nombre} - ${producto.precio}`;
        listaProductos.appendChild(li);
    });
}

// Función para crear un nuevo producto
function crearProducto() {
    const producto = {
        nombre: document.getElementById('nombre').value,
        descripcion: document.getElementById('descripcion').value,
        precio: parseFloat(document.getElementById('precio').value)
    };

    fetch('/api/productos/', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(producto)  // Convertimos el objeto producto a JSON
    })
    .then(response => response.json())  // Convertimos la respuesta en formato JSON
    .then(data => {
        alert(`Producto creado: ${data.nombre}, Descripción: ${data.descripcion}, Precio: ${data.precio}`);
        obtenerProductos();  // Volvemos a obtener los productos para mostrar el nuevo
    })
    .catch(error => {
        console.error('Error al crear producto:', error);
    });
}

// Función para actualizar un producto
function actualizarProducto() {
    const id = document.getElementById('id').value;  // ID del producto a actualizar
    const producto = {
        id: id,
        nombre: document.getElementById('nombre-act').value,
        descripcion: document.getElementById('descripcion-act').value,
        precio: parseFloat(document.getElementById('precio-act').value)
    };

    fetch(`/api/productos/${id}`, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(producto)  // Convertimos el objeto producto a JSON
    })
    .then(response => {
        if (!response.ok) {
            throw new Error('No se pudo actualizar el producto');
        }
        return response.json();  // Intentamos convertir la respuesta en formato JSON
    })
    .then(data => {
        alert(`Producto actualizado: ${data.nombre}, Descripción: ${data.descripcion}, Precio: ${data.precio}`);
        obtenerProductos();  // Volvemos a obtener los productos para mostrar el actualizado
    })
    .catch(error => {
        console.error('Error al actualizar producto:', error);
        alert("Error al actualizar el producto.");
    });
}


// Función para eliminar un producto
function eliminarProducto() {
    const id = document.getElementById('id-eliminar').value;  // ID del producto a eliminar

    fetch(`/api/productos/${id}`, {
        method: 'DELETE',
    })
    .then(response => {
        if (response.ok) {
            alert('Producto eliminado con éxito');
            obtenerProductos();  // Volvemos a obtener los productos para mostrar los actualizados
        } else {
            alert('Error al eliminar producto');
        }
    })
    .catch(error => {
        console.error('Error al eliminar producto:', error);
    });
}
