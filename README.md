# 🖧 Sistema de Detección y Gestión de Redes

📄 [Descargar Manual de Usuario y Desarrollo (PDF)](Alcance%2C%20Manual%20de%20Desarrollo%20y%20Usuario.pdf)

Proyecto desarrollado como parte de la **Instancia Supervisada de Formación Práctica Profesional** de la carrera **Licenciatura en Informática** en la Facultad de Ingeniería, UNPSJB.

## 🧠 Objetivo

Este sistema está diseñado para facilitar la **gestión remota de una red de equipos** dentro de una institución, dirigido a personal técnico y de mantenimiento. Permite visualizar, consultar, modificar y analizar una red informática de forma gráfica, intuitiva y en dos modos: **simulación** y **modo real** (con pings a los equipos).

---

## 🚀 Funcionalidades

- Visualización gráfica de la red (equipos y conexiones).
- Consulta de estado de equipos (modo simulación o real).
- Añadir, modificar y eliminar equipos y conexiones.
- Soporte multilingüe: Español 🇦🇷 e Inglés 🇬🇧.
- Login con verificación por correo electrónico.
- Cambiar entre archivos de texto o base de datos PostgreSQL como fuente de datos.
- Consultas remotas concurrentes mediante hilos (uso real).
- Interfaz intuitiva basada en **Swing**.

---

## 🧱 Arquitectura

El sistema está desarrollado con una **arquitectura en capas** y patrones de diseño como:

- **MVC (Modelo - Vista - Controlador)**
- **DAO** para acceso a datos
- **Singleton** para instancias únicas
- **Factory** para generación de objetos DAO
- **Facade** para simplificar interfaces de servicio

### Estructura de Carpetas

```
📦 src/
 ├── main/
 │   └── java/com/example/red/
 │       ├── conexion/
 │       ├── controlador/
 │       ├── dao/
 │       ├── gui/
 │       ├── modelo/
 │       ├── negocio/
 │       └── servicio/
 ├── test/
 ├── lib/
 ├── db/
 └── config.properties
```

---

## 🛠️ Herramientas y Librerías

- **Java OpenJDK 17**
- **Maven 3.6.3+** (gestión de dependencias)
- **JGraphT** para algoritmos de grafos
- **mxGraph** para visualización
- **Javax Mail** para envío de correos
- **PostgreSQL Driver**
- **JUnit 5** para testing
- **Swing** + **MigLayout** para GUI
- **Git** para control de versiones

---

## 📥 Instalación y Ejecución

### Requisitos:

- Java 17 instalado
- NetBeans IDE recomendado
- PostgreSQL (opcional, si no se usan archivos de texto)

### Pasos:

1. Cloná este repo:
   ```bash
   git clone https://github.com/ivomariezcurrena/isfpp.git
   ```

2. Abrilo en **NetBeans** y agregá manualmente los `.jar` de `lib/` si es necesario (clic derecho > Instalar artefacto).

3. Ejecutá la clase:
   ```
   com.example.red.controlador.AplicacionConsultas.java
   ```

---

## 🔐 Inicio de Sesión

1. Al abrir la app, se mostrará una ventana de login/registro.
2. Si es tu primera vez, podés registrarte directamente desde allí.
3. El sistema valida datos y registra en base de datos o archivos.

---

## 🕹️ Uso de la App

- **Ver red:** Menú → “Grafo”
- **Ping, Traceroute, Rango:** pestañas principales
- **Editar elementos:** Menú → “Editar”
- **Cambiar idioma o modo:** editar `config.properties`

---

## ⚙️ Datos y Configuración

### Archivos de entrada:

- `equipo.txt`, `conexion.txt`, `ubicacion.txt`, etc.

### Configuración:

- `config.properties`:
  ```properties
  idioma=es
  modo=simulacion
  ```

---

## 💡 Posibles mejoras

- Sistema de **roles**: administrador, usuario, invitado.
- Soporte para **más protocolos de red**.
- Implementar interfaz web (por ejemplo con JavaFX o frontend JS).
- Exportar reportes de red (PDF, CSV, etc).

---

## 👨‍💻 Autores

- Ivo Mariezcurrena  
- Lautaro Crespo  
- Fabián Vidal Oropeza  

**Cátedra:** Solá Leiva Alejandro, Pollicelli Débora, Samec Gustavo, Mazzanti Renato  
**Fecha:** 11/11/2024

---

## 📄 Licencia

Este proyecto fue desarrollado con fines educativos.  
Podés usarlo, adaptarlo y mejorarlo. ¡Pero no te olvides de darnos el crédito! 😉

---

