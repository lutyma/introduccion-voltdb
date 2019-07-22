package procedure_voltdb;

import org.voltdb.*;

public class ProcedurePartition extends VoltProcedure {

	// se crean todas las sentencias 
	public final SQLStmt SelectByClienteId = new SQLStmt(
			"SELECT Nombre, Apellido FROM ls_cliente " +
			"WHERE ClienteID=?;");

	public final SQLStmt SelectByIdNombre = new SQLStmt(
			"SELECT Nombre, Apellido FROM ls_cliente " +
			"WHERE ClienteID=? AND Nombre=?;");

	public final SQLStmt SelectByIdApellido = new SQLStmt(
			"SELECT Nombre, Apellido FROM ls_cliente " +
			"WHERE ClienteID=? AND Apellido=?;");

	public final SQLStmt SelectByReservaId = new SQLStmt(
			"SELECT ClienteID, VueloID FROM ls_reserva " +
			"WHERE ReservaID=?;");

	public final SQLStmt SelectByIdReservaVueloId = new SQLStmt(
			"SELECT ClienteID, VueloID FROM ls_reserva " +
			"WHERE ReservaID=? AND VueloID=?;");

	public final SQLStmt SelectByIdReservaClienteId = new SQLStmt(
			"SELECT ClienteID, VueloID FROM ls_reserva " +
			"WHERE ReservaID=? AND ClienteID=?;");

	public final SQLStmt InsertCliente = new SQLStmt("INSERT INTO ls_cliente VALUES(?,?,?);");

	public final SQLStmt DeleteReserva = new SQLStmt(" DELETE FROM ls_reserva " + "WHERE ReservaID=?;");

	public final SQLStmt UpdateCliente = new SQLStmt(" UPDATE ls_cliente SET Nombre=? WHERE ClienteID=?;");

	// se le llama a la funcion run con los parametros correspondientes
	public VoltTable[] run(String filtro, String ClienteID, String Nombre, String Apellido, String ReservaID, String VueloID)
			throws VoltAbortException{

		if(filtro.equals("s1cliente")) {
			voltQueueSQL(SelectByClienteId, ClienteID);
		}
		if(filtro.equals("s2cliente")) {
			voltQueueSQL(SelectByIdNombre, ClienteID, Nombre);
		}
		if(filtro.equals("s3cliente")) {
			voltQueueSQL(SelectByIdApellido, ClienteID, Apellido);
		}
		if(filtro.equals("s1reserva")) {
			voltQueueSQL(SelectByReservaId, ReservaID);
		}
		if(filtro.equals("s2reserva")) {
			voltQueueSQL(SelectByIdReservaVueloId, ReservaID, VueloID);
		}
		if(filtro.equals("s3reserva")) {
			voltQueueSQL(SelectByIdReservaClienteId, ReservaID, ClienteID);
		}

		if(filtro.equals("icliente")) {
			voltQueueSQL(InsertCliente, ClienteID, Nombre, Apellido);
		}
		if(filtro.equals("dreserva")) {
			voltQueueSQL(DeleteReserva, ReservaID);
		}
		if(filtro.equals("ucliente")) {
			voltQueueSQL(UpdateCliente, Nombre, ClienteID);
		}

		return voltExecuteSQL();

	}

}
