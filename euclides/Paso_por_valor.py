# Algoritmo de Euclides por valor
def mcd_por_valor(a, b):
    while b != 0:  
        a, b = b, a % b  
    return a  

# Usamos los números directamente, que se pasan por valor
num1 = 3
num2 = 98

# Llamada al algoritmo
resultado = mcd_por_valor(num1, num2)

# Imprimimos el resultado, pero los valores originales no se modifican
print(f"El MCD de {num1} y {num2} es: {resultado}")
