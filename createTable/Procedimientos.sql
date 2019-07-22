-- b. Partitioned Stored Procedure as sql select from A

CREATE PROCEDURE ls_select_cliente
   PARTITION ON 
      TABLE ls_cliente COLUMN ClienteID
   AS
      SELECT Nombre, Apellido FROM ls_cliente WHERE ClienteID=?;
      
-- c. Stored Procedure as sql select from B      
      
CREATE PROCEDURE ls_select_reserva AS
    SELECT ClienteID, VueloID FROM ls_reserva WHERE ReservaID=?;   

-- d. PartitionedStored Procedure from class

 CREATE PROCEDURE
    PARTITION ON 
       TABLE ls_cliente COLUMN ClienteID PARAMETER 1
    FROM CLASS procedure_voltdb.ProcedurePartition;     
    
-- e. Stored Procedure from class

CREATE PROCEDURE FROM CLASS procedure_voltdb.ProcedureStore;      


