CREATE TABLE ls_cliente (
   ClienteID VARCHAR UNIQUE NOT NULL,
   Nombre VARCHAR(15),
   Apellido VARCHAR(15),
   PRIMARY KEY(ClienteID)
);
PARTITION TABLE ls_cliente ON COLUMN ClienteID;

CREATE TABLE ls_reserva (
   ReservaID VARCHAR NOT NULL,
   ClienteID VARCHAR NOT NULL,
   VueloID VARCHAR NOT NULL,
   PRIMARY KEY(ClienteID)
);
PARTITION TABLE ls_reserva ON COLUMN ClienteID;

CREATE TABLE ls_vuelo (
   VueloID VARCHAR UNIQUE NOT NULL,
   Origen VARCHAR(15),
   Destino VARCHAR(15),
   PRIMARY KEY(VueloID)
);

CREATE STREAM ls_total_reserva PARTITION ON COLUMN ClienteID(
    ReservaID VARCHAR NOT NULL,
    ClienteID VARCHAR NOT NULL,
    login TIMESTAMP
);
CREATE VIEW ls_reserva_vista 
    ( ReservaID)
    AS SELECT ReservaID, COUNT(*)
       FROM ls_total_reserva GROUP BY ReservaID;
