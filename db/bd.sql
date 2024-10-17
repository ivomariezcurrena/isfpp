set schema 'poo2024'

CREATE TABLE usuarios_ivoma(
	id_usuario serial constraint pk_usuario  PRIMARY KEY,
 	nombre_usuario varchar(255) NOT NULL,
	Email varchar(255),
	contraseña varchar(255) NOT NULL,
	administrador boolean not null,
	codigo_verificacion varchar(200),
	estado varchar(200) DEFAULT ''
)
select * from usuarios_ivoma

