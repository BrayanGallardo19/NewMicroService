-- Script para insertar datos de prueba con contraseñas hasheadas con BCrypt
-- Contraseña para todos los usuarios de prueba: "password123"
-- Hash BCrypt: $2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy

-- Insertar Personas de prueba
INSERT INTO persona (nombre, apellido, rut, telefono, email, id_comuna, calle, numero_puerta, username, pass_hash, fecha_registro, estado) VALUES
('Admin', 'Sistema', '11111111-1', '912345678', 'admin@zapateria.com', 1, 'Calle Admin', '100', 'admin', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', NOW(), 'activo'),
('Juan', 'Pérez', '22222222-2', '987654321', 'juan@cliente.com', 1, 'Calle Cliente', '200', 'juan', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', NOW(), 'activo'),
('María', 'González', '33333333-3', '956781234', 'maria@vendedor.com', 1, 'Calle Vendedor', '300', 'maria', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', NOW(), 'activo'),
('Pedro', 'Ramírez', '44444444-4', '945678123', 'pedro@transportista.com', 1, 'Calle Transportista', '400', 'pedro', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', NOW(), 'activo');

-- Insertar Usuarios con sus roles
-- Asumiendo que los roles ya existen con: 1=ADMIN, 2=CLIENTE, 3=VENDEDOR, 4=TRANSPORTISTA
INSERT INTO usuario (id_persona, id_rol) VALUES
(1, 1), -- Admin
(2, 2), -- Cliente
(3, 3), -- Vendedor
(4, 4); -- Transportista

-- Verificar que los roles existen, si no, crearlos
INSERT IGNORE INTO rol (id_rol, nombre_rol, descripcion) VALUES
(1, 'ADMIN', 'Administrador del sistema'),
(2, 'CLIENTE', 'Cliente de la zapatería'),
(3, 'VENDEDOR', 'Vendedor de la zapatería'),
(4, 'TRANSPORTISTA', 'Transportista de entregas');
