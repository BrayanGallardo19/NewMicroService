# Guía de Configuración Inicial - Autenticación JWT

## Paso 1: Ejecutar Script de Datos de Prueba

El archivo `data-test.sql` contiene usuarios de prueba con contraseñas hasheadas.

**Usuarios de prueba:**
- **Admin**: admin@zapateria.com / password123
- **Cliente**: juan@cliente.com / password123  
- **Vendedor**: maria@vendedor.com / password123
- **Transportista**: pedro@transportista.com / password123

**Ejecutar el script:**
```bash
# Desde MySQL
mysql -u root -p nombre_base_datos < src/main/resources/data-test.sql

# O desde MySQL Workbench, abrir y ejecutar el archivo
```

## Paso 2: Verificar Configuración JWT

Asegúrate de que `application.properties` tenga la configuración JWT:

```properties
# JWT Configuration
jwt.secret=mi-clave-secreta-super-segura-de-al-menos-256-bits-para-jwt-tokens
jwt.expiration=3600000
jwt.refresh-expiration=86400000
```

## Paso 3: Iniciar Microservicios

```bash
# 1. UsuarioService (Puerto 8083)
cd UsuarioService
mvn spring-boot:run

# 2. InventarioService (Puerto 8082)
cd InventarioService
mvn spring-boot:run

# 3. VentasService (Puerto 8085)
cd VentasService
mvn spring-boot:run

# 4. EntregasService (Puerto 8084)
cd EntregasService
mvn spring-boot:run

# 5. GeografiaService (Puerto 8081)
cd geografiaService
mvn spring-boot:run
```

## Paso 4: Iniciar Frontend

```bash
cd ZapateriaReact
npm run dev
```

## Paso 5: Probar Autenticación

### Opción 1: Desde el Frontend
1. Ir a http://localhost:5173/login
2. Usar credenciales: admin@zapateria.com / password123
3. Verificar que se recibe el token y se redirige correctamente

### Opción 2: Desde Postman/cURL

**Login:**
```bash
curl -X POST http://localhost:8083/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "email": "admin@zapateria.com",
    "password": "password123"
  }'
```

**Respuesta esperada:**
```json
{
  "token": "eyJhbGciOiJIUzI1NiIs...",
  "refreshToken": "eyJhbGciOiJIUzI1NiIs...",
  "type": "Bearer",
  "userId": 1,
  "email": "admin@zapateria.com",
  "nombre": "Admin Sistema",
  "rol": "ADMIN"
}
```

**Usar el token:**
```bash
curl -X GET http://localhost:8083/api/usuarios \
  -H "Authorization: Bearer {token_aqui}"
```

## Paso 6: Registrar Nuevo Usuario

El registro ahora hashea automáticamente las contraseñas:

```bash
curl -X POST http://localhost:8083/api/personas \
  -H "Content-Type: application/json" \
  -d '{
    "nombre": "Nuevo",
    "apellido": "Usuario",
    "rut": "55555555-5",
    "telefono": "912345678",
    "email": "nuevo@test.com",
    "idComuna": 1,
    "calle": "Calle Nueva",
    "numeroPuerta": "500",
    "username": "nuevo",
    "passHash": "micontraseña",
    "fechaRegistro": "2025-11-27T00:00:00",
    "estado": "activo"
  }'
```

Luego crear el usuario:
```bash
curl -X POST http://localhost:8083/api/usuarios \
  -H "Content-Type: application/json" \
  -d '{
    "persona": { "idPersona": {id_de_persona_creada} },
    "rol": { "idRol": 2 }
  }'
```

## Troubleshooting

### Error: "Credenciales inválidas"
- Verificar que el usuario existe en la BD
- Verificar que la contraseña esté hasheada con BCrypt
- Verificar que el rol existe y está asignado

### Error: "401 Unauthorized" en endpoints protegidos
- Verificar que el token esté en el header: `Authorization: Bearer {token}`
- Verificar que el token no haya expirado (1 hora)
- Usar refresh token si es necesario

### Error: "403 Forbidden"
- El usuario no tiene el rol necesario para ese endpoint
- Verificar la configuración de roles en SecurityConfig

### Frontend no guarda el token
- Verificar que localStorage esté habilitado
- Abrir DevTools → Application → Local Storage
- Debe haber `token` y `refreshToken`

## Notas Importantes

⚠️ **Contraseñas**: Todas las contraseñas nuevas se hashean automáticamente con BCrypt
⚠️ **Tokens**: Los tokens expiran en 1 hora, usa refresh token para renovar
⚠️ **CORS**: Configurado para permitir todos los orígenes (cambiar en producción)
⚠️ **HTTPS**: En producción, usar HTTPS obligatoriamente
