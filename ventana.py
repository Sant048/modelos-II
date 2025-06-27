import requests
import subprocess
import time
import os
from tkinter import *
from tkinter import messagebox

API_URL = "http://localhost:8080/api/productos/"

# Ruta relativa desde ventana.py hasta la raíz del proyecto
base_path = os.path.abspath(os.path.join(os.path.dirname(__file__), "Manejo_end_points", "api-productos"))
proceso = subprocess.Popen(["gradlew.bat", "bootRun"], cwd=base_path, shell=True)

window_main = Tk()
window_main.title("Gestión de Productos")
window_main.geometry("550x550")

canvas = Canvas(window_main)
scrollbar = Scrollbar(window_main, orient=VERTICAL, command=canvas.yview)
canvas.configure(yscrollcommand=scrollbar.set)

scrollable_frame = Frame(canvas)
canvas_window = canvas.create_window((0, 0), window=scrollable_frame, anchor="nw", tags="frame")
canvas.bind("<Configure>", lambda e: canvas.itemconfig("frame", width=e.width))

scrollable_frame.columnconfigure(0, weight=1)
center_frame = Frame(scrollable_frame)
center_frame.grid(row=0, column=0, sticky="n", padx=20, pady=10)

scrollable_frame.bind(
    "<Configure>",
    lambda e: canvas.configure(scrollregion=canvas.bbox("all"))
)

canvas.pack(side=LEFT, fill=BOTH, expand=True)
scrollbar.pack(side=RIGHT, fill=Y)

Label(center_frame, text="Gestión de Productos", font=("Arial", 16, "bold")).pack(pady=(0, 10))

def listar_productos():
    productos_lista.delete(0, END)
    try:
        response = requests.get(API_URL)
        response.raise_for_status()
        productos = response.json()
        for p in productos:
            productos_lista.insert(END, f"ID: {p['id']} - {p['nombre']} - ${p['precio']}")
    except Exception as e:
        productos_lista.insert(END, f"Error: {e}")

Button(center_frame, text="Lista de Productos", command=listar_productos).pack(fill="x", pady=2)
productos_lista = Listbox(center_frame, height=3)
productos_lista.pack(fill="x", pady=2)

Label(center_frame, text="Crear Producto", font=("Arial", 12, "bold")).pack(pady=(10, 0))

Label(center_frame, text="Nombre del producto").pack(anchor="w")
entry_nombre = Entry(center_frame)
entry_nombre.pack(fill="x", pady=2)

Label(center_frame, text="Descripción del producto").pack(anchor="w")
entry_descripcion = Entry(center_frame)
entry_descripcion.pack(fill="x", pady=2)

Label(center_frame, text="Precio del producto").pack(anchor="w")
entry_precio = Entry(center_frame)
entry_precio.pack(fill="x", pady=2)

def crear_producto():
    try:
        data = {
            "nombre": entry_nombre.get(),
            "descripcion": entry_descripcion.get(),
            "precio": float(entry_precio.get())
        }
        response = requests.post(API_URL, json=data)
        if response.status_code in (200, 201):
            messagebox.showinfo("Éxito", "Producto creado con éxito")
            listar_productos()
            entry_nombre.delete(0, END)
            entry_descripcion.delete(0, END)
            entry_precio.delete(0, END)
        else:
            messagebox.showerror("Error", f"No se pudo crear el producto. Código: {response.status_code}")
    except Exception as e:
        messagebox.showerror("Error", str(e))

Button(center_frame, text="Crear Producto", command=crear_producto).pack(pady=5)

Label(center_frame, text="Actualizar Producto", font=("Arial", 12, "bold")).pack(pady=(10, 0))

Label(center_frame, text="ID del producto a actualizar").pack(anchor="w")
entry_id_act = Entry(center_frame)
entry_id_act.pack(fill="x", pady=2)

Label(center_frame, text="Nuevo nombre del producto").pack(anchor="w")
entry_nombre_act = Entry(center_frame)
entry_nombre_act.pack(fill="x", pady=2)

Label(center_frame, text="Nueva descripción").pack(anchor="w")
entry_descripcion_act = Entry(center_frame)
entry_descripcion_act.pack(fill="x", pady=2)

Label(center_frame, text="Nuevo precio").pack(anchor="w")
entry_precio_act = Entry(center_frame)
entry_precio_act.pack(fill="x", pady=2)

def actualizar_producto():
    id_producto = entry_id_act.get()
    try:
        data = {
            "id" :entry_id_act.get(),
            "nombre": entry_nombre_act.get(),
            "descripcion": entry_descripcion_act.get(),
            "precio": float(entry_precio_act.get())
        }
        response = requests.put(API_URL + id_producto, json=data)
        if response.ok:
            messagebox.showinfo("Éxito", "Producto actualizado")
            listar_productos()
        else:
            messagebox.showerror("Error", f"No se pudo actualizar. Código: {response.status_code}")
    except Exception as e:
        messagebox.showerror("Error", str(e))

Button(center_frame, text="Actualizar Producto", command=actualizar_producto).pack(pady=5)

Label(center_frame, text="Eliminar Producto", font=("Arial", 12, "bold")).pack(pady=(10, 0))

Label(center_frame, text="ID del producto a eliminar").pack(anchor="w")
entry_id_del = Entry(center_frame)
entry_id_del.pack(fill="x", pady=2)

def eliminar_producto():
    id_producto = entry_id_del.get()
    try:
        response = requests.delete(API_URL + id_producto)
        if response.ok:
            messagebox.showinfo("Éxito", "Producto eliminado")
            listar_productos()
        else:
            messagebox.showerror("Error", f"No se pudo eliminar. Código: {response.status_code}")
    except Exception as e:
        messagebox.showerror("Error", str(e))

Button(center_frame, text="Eliminar Producto", command=eliminar_producto).pack(pady=5)

def cerrar_todo():
    proceso.terminate()
    window_main.destroy()

window_main.protocol("WM_DELETE_WINDOW", cerrar_todo)

window_main.mainloop()
