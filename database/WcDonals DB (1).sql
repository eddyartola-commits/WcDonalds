CREATE DATABASE wcdonalds_db;
USE wcdonalds_db;    
-- 1. ROLES
CREATE TABLE roles (
    id_rol INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(20) NOT NULL UNIQUE
);

-- 2. USUARIOS
-- Sirve tanto para Administradores como para Cajeros.
CREATE TABLE usuarios (
    id_usuario INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(25) NOT NULL,
    usuario VARCHAR(25) NOT NULL UNIQUE,
    clave VARCHAR(8) NOT NULL,
    correo VARCHAR(30) NOT NULL UNIQUE,
    id_rol INT NOT NULL,
    FOREIGN KEY (id_rol) REFERENCES roles(id_rol)
);

-- 3. CATEGORIAS
CREATE TABLE categorias (
    id_categoria INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL UNIQUE,
    estado BOOLEAN NOT NULL DEFAULT TRUE
);

select * from roles;
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
    descuento DECIMAL(10,2)  DEFAULT 0.00,
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
    metodo ENUM('EFECTIVO', 'TARJETA') NOT NULL,
    total_pagado DECIMAL(10,2) NOT NULL,
    efectivo_recibido DECIMAL(10,2) DEFAULT NULL,
    cambio DECIMAL(10,2) DEFAULT NULL,
    fecha_hora DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (id_pedido) REFERENCES pedidos(id_pedido),
    
    -- Restricción para validar lógica de pago por efectivo/tarjeta:
    CONSTRAINT chk_metodo_pago CHECK (
        (metodo = 'EFECTIVO' AND efectivo_recibido IS NOT NULL) OR
        (metodo = 'TARJETA' AND efectivo_recibido IS NULL AND cambio IS NULL)
    )
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
(nombre, usuario, clave, correo, id_rol)
VALUES
('Administrador', 'admin', 'admin123', 'wcadmin@gmail.com', 1),
('Cajero', 'cajero', 'cajero12', 'wc_cajero@gmail.coom',2);

-- Creacion de la tabla de las subcategorias 
CREATE TABLE subcategorias (
    id_subcategoria INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL,
    id_categoria INT NOT NULL,
    estado BOOLEAN NOT NULL DEFAULT TRUE,

    FOREIGN KEY (id_categoria)
    REFERENCES categorias(id_categoria),

    UNIQUE (nombre, id_categoria)
);

-- Insertar categorias
INSERT INTO categorias (nombre) 
VALUES ('Almuerzos'),('Antojos'),('Bebidas'),('Desayunos'),
('Extras'),('Wc Cafe'),('Postres'),('Para Compartir')
ON DUPLICATE KEY UPDATE nombre = VALUES(nombre);

select * from categorias; 

-- Inserta las subcategorias de Almuerzos
INSERT INTO subcategorias (nombre, id_categoria)
VALUES
('Cajita Feliz', 1),
('Gourmet', 1),
('Hamburguesas', 1),
('Pollo', 1);

select * from subcategorias;

ALTER TABLE productos
ADD COLUMN id_subcategoria INT NULL,
ADD FOREIGN KEY (id_subcategoria)
REFERENCES subcategorias(id_subcategoria);

-- Insertar las Cajitas Feliz con los nombres de archivo corregidos
INSERT INTO productos (nombre, precio, imagen_path, id_categoria, id_subcategoria, disponible)
VALUES
  ('Cajita Feliz® de Derretido', 45.00, 'src/ImaAlmCajaFeliz/Cajita_Feliz_Derretido.png', 1, 1, TRUE),
  ('Cajita Feliz® de Hamburguesa', 45.00, 'src/ImaAlmCajaFeliz/Cajita_Feliz_de_Hamburguesa.png', 1, 1, TRUE),
  ('Cajita Feliz® de Hamburguesa Jr.', 45.00, 'src/ImaAlmCajaFeliz/Cajita_Feliz_de_Hamburguesa_Jr.png', 1, 1, TRUE),
  ('Cajita Feliz® de McNuggets®', 45.00, 'src/ImaAlmCajaFeliz/Cajita_Feliz_de_McNuggets.png', 1, 1, TRUE),
  ('Cajita Feliz® de Pollo McCrispy®', 45.00, 'src/ImaAlmCajaFeliz/Cajita_Feliz_de_Pollo_McCrispy.png', 1, 1, TRUE),
  ('Cajita Feliz® de Quesoburguesa', 45.00, 'src/ImaAlmCajaFeliz/Cajita_Feliz_de_Quesoburguesa.png', 1, 1, TRUE);

-- Insertar los productos de la subcategoría Gourmet (id_subcategoria = 2)

-- 2. Insertar la lista COMPLETA de 11 productos Gourmet
INSERT INTO productos (nombre, precio, imagen_path, id_categoria, id_subcategoria, disponible)
VALUES
  ('Clásica Gourmet Res', 72.00, 'src/ImaAlmGourmet/Clasica_Gourmet_Res.png', 1, 2, TRUE),
  ('Clásica Gourmet Crujiente', 72.00, 'src/ImaAlmGourmet/Clasica_Gourmet_Crujiente.png', 1, 2, TRUE),
  ('Clásica Gourmet Grill', 72.00, 'src/ImaAlmGourmet/Clasica_Gourmet_Grill.png', 1, 2, TRUE),
  ('Clásica Gourmet Res', 72.00, 'src/ImaAlmGourmet/Clasica_Gourmet_Res.png', 1, 2, TRUE),
  ('Parrilla Gourmet Pico Guacamole', 75.00, 'src/ImaAlmGourmet/Parrilla_gourmet_Pico_Guacamol.png', 1, 2, TRUE),
  ('Pico Guacamole Gourmet Crujiente', 75.00, 'src/ImaAlmGourmet/Pico_Guacamol_Gourmet_Crujiente.png', 1, 2, TRUE),
  ('Pico Guacamole Gourmet Res', 75.00, 'src/ImaAlmGourmet/Pico_Guacamol_Gourmet_Res.png', 1, 2, TRUE),
  ('Smoke Tocino Gourmet Res', 75.00, 'src/ImaAlmGourmet/Smoke_Tocino_Gourmet_Res.png', 1, 2, TRUE),
  ('Tocino Ahumado Gourmet Res', 75.00, 'src/ImaAlmGourmet/Tocino_Ahumado_Gourmet_Res.png', 1, 2, TRUE),
  ('Tocino SmokeHouse Gourmet Crujiente', 75.00, 'src/ImaAlmGourmet/Tocino_SmokeHouse_Gourmet_Crujiente.png', 1, 2, TRUE),
  ('Tocino SmokeHouse Gourmet Grill', 75.00, 'src/ImaAlmGourmet/Tocino_SmokeHouse_Gourmet_Grill.png', 1, 2, TRUE);

-- 2. Insertar los 21 productos del paquete ImaAlmHamburguesas
INSERT INTO productos (nombre, precio, imagen_path, id_categoria, id_subcategoria, disponible)
VALUES
  ('Bacon Cheddar WcMelt', 50.00, 'src/ImaAlmHamburguesas/Bacon_Cheddar_WcMelt.png', 1, 3, TRUE),
  ('Big Tasty', 70.00, 'src/ImaAlmHamburguesas/Big_Tasty.png', 1, 3, TRUE),
  ('Big Tasty Doble', 86.00, 'src/ImaAlmHamburguesas/Big_Tasty_Doble.png', 1, 3, TRUE),
  ('Big Tasty Tocino', 76.00, 'src/ImaAlmHamburguesas/Big_Tasty_Tocino.png', 1, 3, TRUE),
  ('Big Wac', 55.00, 'src/ImaAlmHamburguesas/Big_Wac.png', 1, 3, TRUE),
  ('Big Wac Doble', 71.00, 'src/ImaAlmHamburguesas/Big_Wac_Doble.png', 1, 3, TRUE),
  ('Cuarto de Libra Bacon con Queso', 67.00, 'src/ImaAlmHamburguesas/Cuarto_de_Libr4a_Bacon_con_Queso.png', 1, 3, TRUE),
  ('Cuarto de Libra Bacon Doble con Queso', 83.00, 'src/ImaAlmHamburguesas/Cuarto_de_Libra_Bacon_Doble_con_Queso.png', 1, 3, TRUE),
  ('Cuarto de Libra Deluxe Con Queso', 64.00, 'src/ImaAlmHamburguesas/Cuarto_de_Libra_Deluxe_Con_Queso.png', 1, 3, TRUE),
  ('Cuarto de Libra Deluxe Doble Con Queso', 80.00, 'src/ImaAlmHamburguesas/Cuarto_de_Libra_Deluxe_Doble_Con_Queso.png', 1, 3, TRUE),
  ('Cuarto de Libra Doble con Queso', 77.00, 'src/ImaAlmHamburguesas/Cuarto_de_Libra_Doble_con_Queso.png', 1, 3, TRUE),
  ('Cuarto de Libra con Queso', 61.00, 'src/ImaAlmHamburguesas/Cuarto_de_Libra_con_Queso.png', 1, 3, TRUE),
  ('Hamburguesa', 20.00, 'src/ImaAlmHamburguesas/Hamburguesa.png', 1, 3, TRUE),
  ('Hamburguesa Jr.', 21.00, 'src/ImaAlmHamburguesas/Hamburguesa_Jr.png', 1, 3, TRUE),
  ('Quesoburguesa', 49.00, 'src/ImaAlmHamburguesas/Quesoburguesa.png', 1, 3, TRUE),
  ('Quesoburguesa Doble', 55.00, 'src/ImaAlmHamburguesas/Quesoburguesa_Doble.png', 1, 3, TRUE),
  ('Quesoburguesa Triple', 61.00, 'src/ImaAlmHamburguesas/Quesoburguesa_Triple.png', 1, 3, TRUE),
  ('Triple Bacon', 62.00, 'src/ImaAlmHamburguesas/Triple_Bacon.png', 1, 3, TRUE),
  ('WcCrispy Bacon Cheddar', 52.00, 'src/ImaAlmHamburguesas/WcCrispy_Bacon_Cheddar.png', 1, 3, TRUE),
  ('WcNifica de Res', 63.00, 'src/ImaAlmHamburguesas/WcNifica_de_Res.png', 1, 3, TRUE),
  ('WcNifica de Res Doble', 80.00, 'src/ImaAlmHamburguesas/WcNifica_de_Res_Doble.png', 1, 3, TRUE);
  
  INSERT INTO productos (nombre, precio, imagen_path, id_categoria, id_subcategoria, disponible)
VALUES
  ('Big Tasty Doble Pollo Grill', 78.00, 'src/ImaAlmPollo/Big_Tasty_Doble_Pollo_Grill.png', 1, 3, TRUE),
  ('Big Tasty Pollo Grill', 70.00, 'src/ImaAlmPollo/Big_Tasty_Pollo_Grill.png', 1, 3, TRUE),
  ('Ensalada Deluxe Pollo Crispy', 73.00, 'src/ImaAlmPollo/Ensalada_Deluxe_Pollo_Crujiente.png', 1, 4, TRUE),
  ('Ensalada Deluxe Pollo Grill', 73.00, 'src/ImaAlmPollo/Ensalada_Deluxe_Pollo_Grill.png', 1, 4, TRUE),
  ('Big Tasty Doble Pollo Crispy', 78.00, 'src/ImaAlmPollo/Parrilla_Pollo_Doble_Big_Tasty.png', 1, 3, TRUE),
  ('Parrilla de Pollo Big Tasty', 55.00, 'src/ImaAlmPollo/Parrilla_de_Pollo_Big_Tasty.png', 1, 3, TRUE),
  ('Big Tasty Pollo Crispy', 70.00, 'src/ImaAlmPollo/Pollo_Crujiente_Big_Tasty.png', 1, 3, TRUE),
  ('Pollo WcCrispy 2 Piezas', 68.00, 'src/ImaAlmPollo/Pollo_WcCrispy_2Piezas.png', 1, 4, TRUE),
  ('Pollo WcCrispy 3 Piezas', 75.00, 'src/ImaAlmPollo/Pollo_WcCrispy_3Piezas.png', 1, 4, TRUE),
  ('Sandwich Wacpollo', 49.00, 'src/ImaAlmPollo/Sandwich_Wacpollo.png', 1, 3, TRUE),
  ('Sandwich Wacpollo Doble', 64.00, 'src/ImaAlmPollo/Sandwich_Wcpollo_Doble.png', 1, 3, TRUE),
  ('WcCrispy Sandwich Bacon Ranch', 68.00, 'src/ImaAlmPollo/WcCrispy_Sandwich_Bacon_Ranch.png', 1, 3, TRUE),
  ('WcCrispy Sandwich Deluxe', 59.00, 'src/ImaAlmPollo/WcCrispy_Sandwich_Deluxe.png', 1, 3, TRUE),
  ('WcNuggets 10 Piezas', 60.00, 'src/ImaAlmPollo/WcNuggets_10piezas.png', 1, 4, TRUE),
  ('Wc Crispy Bacon Cheddar Pollo', 52.00, 'src/ImaAlmPollo/Wc_Crispy_Bacon_Cheddar.png', 1, 3, TRUE);
  
  -- 2. Insertar los 7 productos de la categoría Antojos (sin subcategoría)
INSERT INTO productos (nombre, precio, imagen_path, id_categoria, id_subcategoria, disponible)
VALUES
  ('Avena Frutas Maple', 22.00, 'src/ImaAntojos/Avena_Frutas_Maple.png', 2, NULL, TRUE),
  ('Papas Fritas', 25.00, 'src/ImaAntojos/Papas.png', 2, NULL, TRUE),
  ('Pollo WcCrispy 1 Pieza', 25.00, 'src/ImaAntojos/Pollo_WcCrispy_1pieza.png', 2, NULL, TRUE),
  ('Tostado de Queso', 22.00, 'src/ImaAntojos/Tostado_de_Queso.png', 2, NULL, TRUE),
  ('WcNuggets 4 Piezas', 24.00, 'src/ImaAntojos/WcNuggets_4piezas.png', 2, NULL, TRUE),
  ('WcNuggets 6 Piezas', 28.00, 'src/ImaAntojos/WcNuggets_6piezas.png', 2, NULL, TRUE),
  ('WcPatatas', 27.00, 'src/ImaAntojos/WcPatatas.png', 2, NULL, TRUE);
  
  -- 2. Insertar los 13 productos de la categoría Bebidas 
INSERT INTO productos (nombre, precio, imagen_path, id_categoria, id_subcategoria, disponible)
VALUES
  ('Agua Pura', 18.00, 'src/ImaBebidas/Agua_Pura.png', 3, NULL, TRUE),
  ('Café Negro', 21.00, 'src/ImaBebidas/Cafe.png', 3, NULL, TRUE),
  ('Café con Leche', 21.00, 'src/ImaBebidas/Cafe_con_Leche.png', 3, NULL, TRUE),
  ('Coca-Cola', 23.00, 'src/ImaBebidas/Coca_Cola.png', 3, NULL, TRUE),
  ('Coca-Cola sin Azúcar', 23.00, 'src/ImaBebidas/Coca_Cola_sin_Azucar.png', 3, NULL, TRUE),
  ('Fanta', 23.00, 'src/ImaBebidas/Fanta.png', 3, NULL, TRUE),
  ('Jugo de Manzana', 25.00, 'src/ImaBebidas/Jugo_de_Manzana.png', 3, NULL, TRUE),
  ('Jugo de Naranja', 21.00, 'src/ImaBebidas/Jugo_de_Naranja.png', 3, NULL, TRUE),
  ('Rosa de Jamaica', 19.00, 'src/ImaBebidas/Rosa_de_Jamaica.png', 3, NULL, TRUE),
  ('Sprite', 23.00, 'src/ImaBebidas/Sprite.png', 3, NULL, TRUE),
  ('Té Caliente', 14.00, 'src/ImaBebidas/Te_Caliente.png', 3, NULL, TRUE),
  ('Té Lipton', 21.00, 'src/ImaBebidas/Te_Lipton.png', 3, NULL, TRUE),
  ('WcFizz AM', 26.00, 'src/ImaBebidas/WcFizz_AM.png', 3, NULL, TRUE);
  
  -- 2. Insertar las 4 subcategorías asociadas a Desayunos (id_categoria = 4)
INSERT INTO subcategorias (id_subcategoria, nombre, id_categoria, estado) VALUES
(5, 'WcMuffin', 4, TRUE),
(6, 'Desayunos', 4, TRUE),
(7, 'WcGriddle', 4, TRUE),
(8, 'Cajita Feliz (Desayuno)', 4, TRUE);
 select * from subcategorias;
 
 -- 2. Insertar los 29 productos clasificados por su subcategoría correspondiente
INSERT INTO productos (nombre, precio, imagen_path, id_categoria, id_subcategoria, disponible)
VALUES
  -- 🥞 SUBCATEGORÍA 5: WcMuffin
  ('Egg WcMuffin', 44.00, 'src/ImaDesayunos/Egg_WcMuffin.png', 4, 5, TRUE),
  ('Egg WcMuffin Doble', 49.00, 'src/ImaDesayunos/Egg_WcMuffin_Doble.png', 4, 5, TRUE),
  ('Egg WcMuffin Doble Huevo', 52.00, 'src/ImaDesayunos/Egg_WcMuffin_Doble_Huevo.png', 4, 5, TRUE),
  ('WcMuffin Bacon Cheddar WcMelt', 32.00, 'src/ImaDesayunos/WcMuffin_Bacon_Cheddar_WcMelt.png', 4, 5, TRUE),
  ('WcMuffin Chapín con Salchicha', 49.00, 'src/ImaDesayunos/WcMuffin_Chapin_con_Salchicha.png', 4, 5, TRUE),
  ('WcMuffin Huevo y Queso', 21.00, 'src/ImaDesayunos/WcMuffin_Huevo_y_Queso.png', 4, 5, TRUE),
  ('WcMuffin Súper Chapín con Jamón', 53.00, 'src/ImaDesayunos/WcMuffin_Super_Chapin_con_Jamon.png', 4, 5, TRUE),
  ('WcMuffin Súper Chapín con Salchicha', 53.00, 'src/ImaDesayunos/WcMuffin_Super_Chapin_con_Salchicha.png', 4, 5, TRUE),
  ('WcMuffin Tocino y Doble Huevo', 53.00, 'src/ImaDesayunos/WcMuffin_Tocino_y_Doble_Huevo.png', 4, 5, TRUE),
  ('WcMuffin de Salchicha', 41.00, 'src/ImaDesayunos/WcMuffin_de_Salchicha.png', 4, 5, TRUE),
  ('WcMuffin de Salchicha Doble y Huevo', 50.00, 'src/ImaDesayunos/WcMuffin_de_Salchicha_Doble_y_Huevo.png', 4, 5, TRUE),
  ('WcMuffin de Tocino Doble y Huevo', 56.00, 'src/ImaDesayunos/WcMuffin_de_Tocino_Doble_y_Huevo.png', 4, 5, TRUE),
  ('WcMuffin de Tocino y Huevo', 48.00, 'src/ImaDesayunos/WcMuffin_de_Tocino_y_Huevo.png', 4, 5, TRUE),

  -- 🥞 SUBCATEGORÍA 6: Desayunos
  ('Burritos de Desayuno', 57.00, 'src/ImaDesayunos/Burritos.png', 4, 6, TRUE),
  ('Desayuno Clásico', 44.00, 'src/ImaDesayunos/Desayuno_Clasico.png', 4, 6, TRUE),
  ('Desayuno Deluxe', 59.00, 'src/ImaDesayunos/Desayuno_Deluxe.png', 4, 6, TRUE),
  ('Desayuno Pollo WcCrispy 1 Pz', 67.00, 'src/ImaDesayunos/Desayuno_Pollo_WcCrispy_1Pz.png', 4, 6, TRUE),
  ('Desayuno Pollo WcCrispy 2 Pz', 67.00, 'src/ImaDesayunos/Desayuno_Pollo_WcCrispy_2Pz.png', 4, 6, TRUE),
  ('Desayuno Tradicional', 56.00, 'src/ImaDesayunos/Desayuno_Tradicional.png', 4, 6, TRUE),
  ('Hotcakes', 51.00, 'src/ImaDesayunos/Hotcakes.png', 4, 6, TRUE),

  -- 🥞 SUBCATEGORÍA 7: WcGriddle
  ('WcGriddle de Salchicha', 46.00, 'src/ImaDesayunos/WcGriddle_de_Salchicha.png', 4, 7, TRUE),
  ('WcGriddle de Salchicha y Huevo', 50.00, 'src/ImaDesayunos/WcGriddle_de_Salchicha_y_Huevo.png', 4, 7, TRUE),
  ('WcGriddle de Tocino y Huevo', 50.00, 'src/ImaDesayunos/WcGriddle_de_Tocino_y_Huevo.png', 4, 7, TRUE),

  -- 🥞 SUBCATEGORÍA 8: Cajita Feliz (Desayuno)
  ('Cajita Feliz® Derretido Desayuno', 45.00, 'src/ImaDesayunos/Cajita_Feliz_de_Derretido.png', 4, 8, TRUE),
  ('Cajita Feliz® WcMuffin Frijol', 45.00, 'src/ImaDesayunos/Cajita_Feliz_de_WcMuffin_de_Frijol.png', 4, 8, TRUE),
  ('Cajita Feliz® WcMuffin Huevo y Frijol', 45.00, 'src/ImaDesayunos/Cajita_Feliz_de_WcMuffin_de_Huevo_y_Frijol.png', 4, 8, TRUE),
  ('Cajita Feliz® WcMuffin Huevo y Queso', 45.00, 'src/ImaDesayunos/Cajita_Feliz_de_WcMuffin_de_Huevo_y_Queso.png', 4, 8, TRUE),
  ('Cajita Feliz® WcMuffin Salchicha', 45.00, 'src/ImaDesayunos/Cajita_Feliz_de_WcMuffin_de_Salchicha.png', 4, 8, TRUE);
  
  
  -- 2. Insertar los 13 productos de la categoría Extras (sin subcategoría)
INSERT INTO productos (nombre, precio, imagen_path, id_categoria, id_subcategoria, disponible)
VALUES
  ('Cobertura de Caramelo', 3.50, 'src/ImaExtras/Cobertura_de_caramelo.png', 5, NULL, TRUE),
  ('Cobertura de Chocolate', 3.50, 'src/ImaExtras/Cobertura_de_chocolate.png', 5, NULL, TRUE),
  ('Cubierta de Fresa',3.50, 'src/ImaExtras/Cubierta_Fresa.png', 5, NULL, TRUE),
  ('Guacamole', 6.00, 'src/ImaExtras/Guacamole.png', 5, NULL, TRUE),
  ('Pan Bollo', 3.00, 'src/ImaExtras/Pan_Bollo.png', 5, NULL, TRUE),
  ('Queso Cheddar', 6.00, 'src/ImaExtras/Queso_Cheddar.png', 5, NULL, TRUE),
  ('Queso Emmental', 3.00, 'src/ImaExtras/Queso_Emmental.png', 5, NULL, TRUE),
  ('Aderezo Ranch', 2.50, 'src/ImaExtras/Rancho_Aderezo.png', 5, NULL, TRUE),
  ('Salsa Agridulce', 2.50, 'src/ImaExtras/Salsa_Agridulce.png', 5, NULL, TRUE),
  ('Salsa Big Tasty', 4.00, 'src/ImaExtras/Salsa_Big_Tasty.png', 5, NULL, TRUE),
  ('Salsa Mostaza',  2.50, 'src/ImaExtras/Salsa_Mostaza.png', 5, NULL, TRUE),
  ('Salsa Barbacoa',  2.50, 'src/ImaExtras/Salsa_barbacoa.png', 5, NULL, TRUE),
  ('Tiras de Tocino', 9.00, 'src/ImaExtras/Tiras_de_Tocino.png', 5, NULL, TRUE);
  
  -- 2. Insertar las 4 subcategorías asociadas a Wc Cafe (id_categoria = 6)
INSERT INTO subcategorias (id_subcategoria, nombre, id_categoria, estado) VALUES
(9, 'Bebidas Calientes', 6, TRUE),
(10, 'Bebidas Frias', 6, TRUE),
(11, 'Pasteles WcCafé', 6, TRUE),
(12, 'Tostados', 6, TRUE);

-- 2. Insertar los 21 productos clasificados de forma exacta
INSERT INTO productos (nombre, precio, imagen_path, id_categoria, id_subcategoria, disponible)
VALUES
  -- ☕ SUBCATEGORÍA 9: Bebidas Calientes
  ('Café Guatemala', 24.00, 'src/ImaMacCafeB/Cafe_Guatemala.png', 6, 9, TRUE),
  ('Capuchino', 29.00, 'src/ImaMacCafeB/Capuchino.png', 6, 9, TRUE),
  ('Latte', 29.00, 'src/ImaMacCafeB/Latte.png', 6, 9, TRUE),
  ('Té Chai Elephante Vainilla', 33.00, 'src/ImaMacCafeB/Te_Chai_Elefante_Vainilla.png', 6, 9, TRUE),
  ('Té Chai Flamingo Vainilla Light', 33.00, 'src/ImaMacCafeB/Te_Chai_Flamingo_Vainilla_Light.png', 6, 9, TRUE),
  ('Té Guatemalteco Bora Bora', 22.00, 'src/ImaMacCafeB/Te_Guatemalteco_Bor_Bora.png', 6, 9, TRUE),
  ('Té Guatemalteco Manzanilla Relax', 22.00, 'src/ImaMacCafeB/Te_Guatemalteco_Manzanilla_Relax.png', 6, 9, TRUE),
  ('Té Guatemalteco Melocotón Mix', 22.00, 'src/ImaMacCafeB/Te_Guatemalteco_Melocoton_Mix.png', 6, 9, TRUE),
  ('Té Guatemalteco Menta Fusión', 22.00, 'src/ImaMacCafeB/Te_Guatemalteco_Menta_Fusion.png', 6, 9, TRUE),

  -- 🍹 SUBCATEGORÍA 10: Bebidas Frías
  ('Batido de Frutos Rojos', 39.00, 'src/ImaMacCafeB/Batido_de_frutos_rojos.png', 6, 10, TRUE),
  ('Café Helado', 35.00, 'src/ImaMacCafeB/Cafe_helado.png', 6, 10, TRUE),
  ('Frappé Caramelo', 28.00, 'src/ImaMacCafeB/Frappe_Caramelo.png', 6, 10, TRUE),
  ('Frappé Oreo', 41.00, 'src/ImaMacCafeB/Frappe_Oreo.png', 6, 10, TRUE),
  ('Horchata', 28.00, 'src/ImaMacCafeB/Horchata.png', 6, 10, TRUE),
  ('Horchata de Café Helado', 35.00, 'src/ImaMacCafeB/Horchata_de_cafe_helado.png', 6, 10, TRUE),
  ('Smoothie de Mango', 22.00, 'src/ImaMacCafeB/Smoothie_de_Mango.png', 6, 10, TRUE),
  ('WcFizz AM', 26.00, 'src/ImaMacCafeB/WcFizz_AM.png', 6, 10, TRUE),
  ('WcFizz Azul', 26.00, 'src/ImaMacCafeB/WcFizz_Azul.png', 6, 10, TRUE),
  ('WcFizz Manzana Verde', 26.00, 'src/ImaMacCafeB/WcFizz_Manzana_Verde.png', 6, 10, TRUE),
  ('WcFizz Rosa', 26.00, 'src/ImaMacCafeB/WcFizz_Rosa.png', 6, 10, TRUE),
  ('WcFizz Uva', 26.00, 'src/ImaMacCafeB/WcFizz_Uva.png', 6, 10, TRUE);
  
  -- 2. Insertar los 11 productos clasificados de forma exacta
INSERT INTO productos (nombre, precio, imagen_path, id_categoria, id_subcategoria, disponible)
VALUES
  -- 🍰 SUBCATEGORÍA 11: Pasteles Mc Café
  ('3 WcPops', 31.00, 'src/ImaMacCafeP/3WcPops.png', 6, 11, TRUE),
  ('WcPop de Chocolate', 16.00, 'src/ImaMacCafeP/Chocolate_WcPop.png', 6, 11, TRUE),
  ('Pastel de Elote', 34.00, 'src/ImaMacCafeP/Pastel_De_Elote.png', 6, 11, TRUE),
  ('Porción de Bocaditos de Brownie', 29.00, 'src/ImaMacCafeP/Porcion_de_bocaditos_de_brownie.png', 6, 11, TRUE),
  ('Cheesecake de Mora', 34.00, 'src/ImaMacCafeP/Tarta_de_queso_De_Mora.png', 6, 11, TRUE),
  ('Cheesecake de Fresca', 34.00, 'src/ImaMacCafeP/Tarta_de_queso_fresca.png', 6, 11, TRUE),
  ('WcPop de Avellana', 16.00, 'src/ImaMacCafeP/WcPop_Avellana.png', 6, 11, TRUE),

  -- 🥪 SUBCATEGORÍA 12: Tostados
  ('Tostado con Queso y Loroco', 18.00, 'src/ImaMacCafeP/Tostado_Con_Queso_y_Loroco.png', 6, 12, TRUE),
  ('Tostado de Queso y Frijol', 27.00, 'src/ImaMacCafeP/Tostado_Queso_y_Frijol.png', 6, 12, TRUE),
  ('Tostado de Queso y Jamón', 29.00, 'src/ImaMacCafeP/Tostado_Queso_y_Jamón.png', 6, 12, TRUE),
  ('Tostado de Queso y Tomate', 24.00, 'src/ImaMacCafeP/Tostado_Queso_y_Tomate.png', 6, 12, TRUE);
  
  INSERT INTO productos (nombre, precio, imagen_path, id_categoria, id_subcategoria, disponible)
VALUES
  ('Bucket Para Todos', 195.00, 'src/ImaCompartir/Bucket_Para_Todos.png', 8, NULL, TRUE),
  ('Bucket Pollo WcCrispy Para Tres', 150.00, 'src/ImaCompartir/Bucket_Pollo_WcCrispy_Para_Tres.png', 8, NULL, TRUE),
  ('Caja 24 WcNuggets', 129.00, 'src/ImaCompartir/Caja_24_WcNuggets.png', 8, NULL, TRUE),
  ('Caja Grande', 190.00, 'src/ImaCompartir/Caja_Grande.png', 8, NULL, TRUE),
  ('Caja Grande Con Postres', 215.00, 'src/ImaCompartir/Caja_Grande_Con_Postres.png', 8, NULL, TRUE),
  ('Caja Grande Cuarto de Libra', 210.00, 'src/ImaCompartir/Caja_Grande_Cuarto_de_Libra.png', 8, NULL, TRUE),
  ('Caja Grande Snack', 145.00, 'src/ImaCompartir/Caja_Grande_Snack.png', 8, NULL, TRUE),
  ('Cubo de Pollo WcCrispy', 110.00, 'src/ImaCompartir/Cubo_de_pollo_WcCrispy.png', 8, NULL, TRUE),
  ('Cubo de Pollo WcCrispy Snack', 184.00, 'src/ImaCompartir/Cubo_de_pollo_WcCrispy_Snack.png', 8, NULL, TRUE);

INSERT INTO subcategorias (id_subcategoria, nombre, id_categoria, estado) VALUES
(13, 'Helados', 7, TRUE),
(14, 'Pasteles', 7, TRUE);

-- 2. Insertar los 16 productos de la categoría Postres de forma exacta
INSERT INTO productos (nombre, precio, imagen_path, id_categoria, id_subcategoria, disponible)
VALUES
  -- 🍦 SUBCATEGORÍA 13: Helados
  ('Sundae de Caramelo', 17.00, 'src/ImaPostres/Sundae_de_Caramelo.png', 7, 13, TRUE),
  ('Sundae de Chocolate', 17.00, 'src/ImaPostres/Sundae_de_Chocolate.png', 7, 13, TRUE),
  ('Sundae de Fresa', 17.00, 'src/ImaPostres/Sundae_de_Fresa.png', 7, 13, TRUE),
  ('WcFlurry Oreo Chocolate ', 25.00, 'src/ImaPostres/WcFlurry_Chocolate_Oreo.png', 7, 13, TRUE),
  ('WcFlurry M&Ms', 24.00, 'src/ImaPostres/WcFlurry_M&Ms.png', 7, 13, TRUE),
  ('WcFlurry M&Ms Caramelo', 25.00, 'src/ImaPostres/WcFlurry_M&Ms_Caramelo.png', 7, 13, TRUE),
  ('WcFlurry M&Ms Chocolate', 25.00, 'src/ImaPostres/WcFlurry_M&Ms_Chocolate.png', 7, 13, TRUE),
  ('WcFlurry M&Ms Fresa', 25.00, 'src/ImaPostres/WcFlurry_M&Ms_Fresa.png', 7, 13, TRUE),
  ('WcFlurry Oreo', 24.00, 'src/ImaPostres/WcFlurry_Oreo.png', 7, 13, TRUE),
  ('WcFlurry Oreo Caramelo', 25.00, 'src/ImaPostres/WcFlurry_Oreo_Caramelo.png', 7, 13, TRUE),
  ('WcFlurry Oreo Fresa', 25.00, 'src/ImaPostres/WcFlurry_Oreo_Fresa.png', 7, 13, TRUE),
  ('WcFlurry Uva Crocante', 26.00, 'src/ImaPostres/WcFlurry_Uva_Crocante.png', 7, 13, TRUE),
  ('Wc Cono', 6.00, 'src/ImaPostres/Wc_Cono.png', 7, 13, TRUE),

  -- 🍰 SUBCATEGORÍA 14: Pasteles
  ('Pastel Blueberry y Queso', 20.00, 'src/ImaPostres/Pastel_Blueberry_y_Queso.png', 7, 14, TRUE),
  ('Pastel de Manzana', 17.00, 'src/ImaPostres/Pastel_de_Manzana.png', 7, 14, TRUE),
  ('Pastel de Queso', 17.00, 'src/ImaPostres/Pastel_de_Queso.png', 7, 14, TRUE);
  
  
 -- Muestra la subcategoria 
 SELECT *
FROM productos
WHERE id_subcategoria = 4
AND disponible = TRUE;

SELECT * FROM productos;