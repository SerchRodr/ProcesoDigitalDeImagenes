/*
 * UNAM, Facultad de Ciencias
 * Proceso Digital de Imágenes
 * Alumno: Rodríguez Romero Sergio Alfonso
 * No. de Cuenta: 314187785
 * Correo: sergiorodriguez@ciencias.unam.mx
 */

El programa aplica los filtros vistos en la clase de Proceso Digital de Imágenes, mismo que está hecho con ayuda de Java y NetBeans.

Basta ejecutar la clase principal para poder aplicar los fitros deseados. A continuación se especifican los pasos
a seguir para poder aplicar un filtro a una imagen.
Instrucciones:
	1. Ejectar la clase principal abrirImagen.java para correr el programa.
	2. Una vez abierta la interfaz gráfica, presionamos el botón "Seleccionar Imagen" para abrir
	   el explorador de archivos y buscar la imagen deseada para aplicar el filtro (se aceptan los formatos
	   .jpg, .jpeg y .png).
	3. Una vez seleccionada la imagen se mostrará dos veces la misma. Tanto del lado izquierdo como del
	   lado derecho.
	4. Ya que tenemos cargada la imagen, del lado superior derecho se muetra un menú con los filtros disponibles,
	   seleccionamos el filtro deseado.
	5. Del lado izquierdo se mantendrá la imagen original y del lado derecho se mostrará la imagen con el 
	   filtro aplicado.
	   NOTA: para los siguientes filtros se creará un archivo .html en el directorio del proyecto con la
	   	     imagen y el filtro aplicado.
	   	     -MColor
	   	     -MBlancoNegro
	   	     -LetrasColor
	   	     -LetrasBlancoNegro
	   	     -TextoImagen
	6. Para aplicar un nuevo filtro es necesario abrir de nuevo la imagen con el botón "Seleccionar Imagen" y 
	   aplicar el filtro deseado.

Los filtros disponibles son:
	1. Grises (r+g+b/3)
	2. Grises (r*0.30, g*0.59, b*0.11)
	3. Grises (Desaturación MAX(r,g,b)+MIN(r,g,b)/2)
	4. Grises (MAX(r,g,b))
	5. Grises (MIN(r,g,b))
	6. Azar
	7. Mica roja
	8. Mica verde
	9. Mica azul
	10. Inverso
	11. Alto Contraste
	12. Blur
	13. Motion Blur
	14. Encontrar Bordes
	15. Sharpen
	16. Emboss
	17. Promedio y Mediano
	18. Mosaico
	19. M Color
	20. M Blanco y Negro
	21. Letras Color
	22. Letras Blanco y Negro
	23. Texto en Imagen
	24. Marca de Agua
	25. Quitar Marca de Agua
	26. Reducción en porcentajes
	27. Reducción con Mosaico
	28. Óleo Tonos de Grises
	29. Óleo (A Color)
	30. Histograma
	31. Esteganografía Encriptar Texto
	32. Esteganografía Desencriptar Texto
	33. Sepia (En Tonos de grises)
	34. FotoMosaico

NOTA: De momento los filtros Óleo y Óleo Tonos de Grises sólo funcionan con matrices de 3x3.

AVISO: La biblioteca de imágenes que nos brindó el profesor tenía imágenes corruptas, por lo que tuve que eliminar algunas.