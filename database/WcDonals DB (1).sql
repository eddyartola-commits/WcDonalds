

CREATE DATABASE wcdonalds_db;

USE wcdonalds_db;

-- 1. ROLES
CREATE TABLE roles (
    id_rol INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(30) NOT NULL UNIQUE
);

-- 2. USUARIOS
-- Sirve tanto para Administradores como para Cajeros.
CREATE TABLE usuarios (
    id_usuario INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    usuario VARCHAR(50) NOT NULL UNIQUE,
    clave VARCHAR(255) NOT NULL,
    pin VARCHAR(255),
    carnet VARCHAR(50) UNIQUE,
    id_rol INT NOT NULL,
    FOREIGN KEY (id_rol) REFERENCES roles(id_rol)
);

-- 3. CATEGORIAS
CREATE TABLE categorias (
    id_categoria INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL UNIQUE,
    estado BOOLEAN NOT NULL DEFAULT TRUE
);

-- 4. PRODUCTOS
CREATE TABLE productos (
    id_producto INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    descripcion VARCHAR(255),
    precio DECIMAL(10,2) NOT NULL,
    imagen_path VARCHAR(255),
    id_categoria INT NOT NULL,
    disponible BOOLEAN NOT NULL DEFAULT TRUE,
    FOREIGN KEY (id_categoria) REFERENCES categorias(id_categoria)
);

-- 5. PEDIDOS
CREATE TABLE pedidos (
    id_pedido INT AUTO_INCREMENT PRIMARY KEY,
    id_usuario INT NOT NULL,
    fecha_hora DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    subtotal DECIMAL(10,2) NOT NULL DEFAULT 0.00,
    descuento DECIMAL(10,2) NOT NULL DEFAULT 0.00,
    total DECIMAL(10,2) NOT NULL DEFAULT 0.00,
    estado ENUM('PENDIENTE','PAGADO','CANCELADO') NOT NULL DEFAULT 'PENDIENTE',
    FOREIGN KEY (id_usuario) REFERENCES usuarios(id_usuario)
);

-- 6. DETALLE DEL PEDIDO
CREATE TABLE detalle_pedido (
    id_detalle INT AUTO_INCREMENT PRIMARY KEY,
    id_pedido INT NOT NULL,
    id_producto INT NOT NULL,
    cantidad INT NOT NULL DEFAULT 1,
    precio_unitario DECIMAL(10,2) NOT NULL,
    subtotal DECIMAL(10,2) NOT NULL,
    observacion VARCHAR(255),
    FOREIGN KEY (id_pedido) REFERENCES pedidos(id_pedido),
    FOREIGN KEY (id_producto) REFERENCES productos(id_producto)
);

-- 7. PAGOS
CREATE TABLE pagos (
    id_pago INT AUTO_INCREMENT PRIMARY KEY,
    id_pedido INT NOT NULL,
    metodo ENUM('EFECTIVO','TARJETA') NOT NULL,
    total_pagado DECIMAL(10,2) NOT NULL,
    efectivo_recibido DECIMAL(10,2),
    cambio DECIMAL(10,2) DEFAULT 0.00,
    fecha_hora DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (id_pedido) REFERENCES pedidos(id_pedido)
);

-- 8. HISTORIAL / AUDITORIA
CREATE TABLE historial (
    id_historial INT AUTO_INCREMENT PRIMARY KEY,
    id_usuario INT NOT NULL,
    accion VARCHAR(100) NOT NULL,
    descripcion VARCHAR(255),
    fecha_hora DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (id_usuario) REFERENCES usuarios(id_usuario)
);

INSERT INTO roles (nombre) VALUES
('Administrador'),
('Cajero');

INSERT INTO usuarios 
(nombre, usuario, clave, pin, carnet, id_rol)
VALUES
('Administrador Principal', 'admin', 'admin123', '1234', 'ADMIN001', 1),
('Cajero Principal', 'cajero', 'cajero123', '5678', 'CAJERO001', 2);
