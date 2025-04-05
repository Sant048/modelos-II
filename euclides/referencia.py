# Algoritmo de Euclides por referencia
def mcd_por_referencia(a, b):
    while b[0] != 0:  
        temp = a[0] % b[0]  
        a[0], b[0] = b[0], temp  

# Usamos listas para simular el paso por referencia
num1 = [3]  
num2 = [98]  

# Llamada al algoritmo
mcd_por_referencia(num1, num2)

# Al finalizar, el MCD será el valor de num1[0]
print(f"El MCD de 3 y 98 es: {num1[0]}")
