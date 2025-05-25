-- Crear el usuario si no existe
DO
$do$
BEGIN
   IF NOT EXISTS (
      SELECT FROM pg_catalog.pg_roles
      WHERE  rolname = 'devsutest') THEN
      CREATE USER devsutest WITH PASSWORD 'devsu2025*';
   END IF;
END
$do$;

-- Crear la base de datos
CREATE DATABASE devsutest
    WITH 
    OWNER = devsutest
    ENCODING = 'UTF8';

-- Dar permisos al usuario
GRANT ALL PRIVILEGES ON DATABASE devsutest TO devsutest;

-- Conectar a la base de datos como el usuario postgres
\c devsutest postgres;

-- Dar permisos al usuario en el esquema public
GRANT ALL ON SCHEMA public TO devsutest;
GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA public TO devsutest;
GRANT ALL PRIVILEGES ON ALL SEQUENCES IN SCHEMA public TO devsutest;

-- Crear tablas para parámetros
CREATE TABLE tipo_parametro (
  id_tipo_parametro bigint GENERATED ALWAYS AS IDENTITY,
  nombre varchar(100) NOT NULL,
	PRIMARY KEY (id_tipo_parametro)
);

CREATE TABLE parametro (
    id_parametro bigint GENERATED ALWAYS AS IDENTITY,
	id_tipo_parametro bigint NOT NULL,
    nombre varchar(100) NOT NULL,
	PRIMARY KEY (id_parametro),
	FOREIGN KEY(id_tipo_parametro)
        REFERENCES tipo_parametro(id_tipo_parametro)
);



CREATE TABLE persona (
    id_persona bigint GENERATED ALWAYS AS IDENTITY,
    nombres varchar(100) ,
    direccion varchar(100) ,
	telefono varchar(100) ,
    email varchar(100) ,
    identificacion varchar(100) NOT NULL,
	id_tipo_identificacion bigint,
	id_genero bigint,
    id_estado bigint,
  	PRIMARY KEY (id_persona),
	FOREIGN KEY(id_tipo_identificacion)
        REFERENCES parametro(id_parametro),
	FOREIGN KEY(id_genero)
        REFERENCES parametro(id_parametro),
	FOREIGN KEY(id_estado)
        REFERENCES parametro(id_parametro)
);

CREATE TABLE cliente (
    id_cliente bigint GENERATED ALWAYS AS IDENTITY,
    contrasena varchar(100) ,
    id_persona bigint NOT NULL,	
	id_estado bigint,	
  	PRIMARY KEY (id_cliente),
	FOREIGN KEY(id_persona)
        REFERENCES persona(id_persona),
	FOREIGN KEY(id_estado)
        REFERENCES parametro(id_parametro)
);


CREATE TABLE cuenta (
    id_cuenta bigint GENERATED ALWAYS AS IDENTITY,
    numero_cuenta varchar(100) ,
	saldo_inicial float NOT NULL,
	saldo_actual float NOT NULL,
    id_cliente bigint NOT NULL,	
	id_tipo_cuenta bigint NOT NULL,	
	id_estado bigint,	
  	PRIMARY KEY (id_cuenta),
	FOREIGN KEY(id_cliente)
        REFERENCES cliente(id_cliente),
	FOREIGN KEY(id_tipo_cuenta)
        REFERENCES parametro(id_parametro),
	FOREIGN KEY(id_estado)
        REFERENCES parametro(id_parametro)
);


CREATE TABLE movimiento (
    id_movimiento bigint GENERATED ALWAYS AS IDENTITY,
    valor float NOT NULL,
	fecha timestamp NOT NULL DEFAULT now(),
    id_cuenta bigint NOT NULL,	
	id_tipo_movimiento bigint NOT NULL,	
	id_estado bigint,	
  	PRIMARY KEY (id_movimiento),
	FOREIGN KEY(id_cuenta)
        REFERENCES cuenta(id_cuenta),
	FOREIGN KEY(id_tipo_movimiento)
        REFERENCES parametro(id_parametro),
	FOREIGN KEY(id_estado)
        REFERENCES parametro(id_parametro)
);

-- Asegurar que el usuario sea dueño de todas las tablas
ALTER TABLE parametro OWNER TO devsutest;
ALTER TABLE tipo_parametro OWNER TO devsutest;
ALTER TABLE persona OWNER TO devsutest;
ALTER TABLE cliente OWNER TO devsutest;
ALTER TABLE cuenta OWNER TO devsutest;
ALTER TABLE movimiento OWNER TO devsutest;

insert into tipo_parametro (nombre) values ('Estado Persona');
insert into tipo_parametro (nombre) values ('Tipo Identificacion');
insert into tipo_parametro (nombre) values ('Genero');
insert into tipo_parametro (nombre) values ('Estado Cliente');
insert into tipo_parametro (nombre) values ('Tipo Cuenta');
insert into tipo_parametro (nombre) values ('Estado Cuenta');
insert into tipo_parametro (nombre) values ('Tipo Movimiento');
insert into tipo_parametro (nombre) values ('Estado Movimiento');

--ESTADO DE PERSONA
insert into parametro (id_tipo_parametro, nombre) values (1,'Activa');
insert into parametro (id_tipo_parametro, nombre) values (1,'Inactiva');
--TIPOS IDENTIFICACION
insert into parametro (id_tipo_parametro, nombre) values (2,'CC');
insert into parametro (id_tipo_parametro, nombre) values (2,'CE');
insert into parametro (id_tipo_parametro, nombre) values (2,'PA');
--GENERO
insert into parametro (id_tipo_parametro, nombre) values (3,'FEMENINO');
insert into parametro (id_tipo_parametro, nombre) values (3,'MASCULINO');
insert into parametro (id_tipo_parametro, nombre) values (3,'SIN RESPONDER');
--ESTADO DE CLIENTE
insert into parametro (id_tipo_parametro, nombre) values (4,'Activo');
insert into parametro (id_tipo_parametro, nombre) values (4,'Inactivo');
--TIPO CUENTA
insert into parametro (id_tipo_parametro, nombre) values (5,'Ahorros');
insert into parametro (id_tipo_parametro, nombre) values (5,'Corriente');
--ESTADO DE CUENTA
insert into parametro (id_tipo_parametro, nombre) values (6,'Abierta');
insert into parametro (id_tipo_parametro, nombre) values (6,'Cerrada');
insert into parametro (id_tipo_parametro, nombre) values (6,'Bloqueada');
--TIPO MOVIMIENTO
insert into parametro (id_tipo_parametro, nombre) values (7,'Credito');
insert into parametro (id_tipo_parametro, nombre) values (7,'Debito');
--ESTADO DE MOVIMIENTO
insert into parametro (id_tipo_parametro, nombre) values (8,'Pendiente aplicar');
insert into parametro (id_tipo_parametro, nombre) values (8,'Aplicado');
insert into parametro (id_tipo_parametro, nombre) values (8,'Rechazado');