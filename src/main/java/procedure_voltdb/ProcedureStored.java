package procedure_voltdb;

import org.voltdb.*;

public class ProcedureStored extends VoltProcedure {

	public final SQLStmt InsertReserva = new SQLStmt("INSERT INTO ls_reserva VALUES(?,?,?);");
	
	public final SQLStmt DeleteCliente = new SQLStmt(" DELETE FROM ls_cliente " + "WHERE ClienteID=?;");

	public final SQLStmt UpdateReserva = new SQLStmt(" UPDATE ls_reserva SET VueloID=? WHERE ReservaID=?;");
	
	public final SQLStmt SelectByVueloId = new SQLStmt(
			"SELECT Origen, Destino FROM ls_vuelo " +
			"WHERE VueloID=?;");
	public final SQLStmt SelectByVueloIdOrigen = new SQLStmt(
			"SELECT Origen, Destino FROM ls_vuelo " +
			"WHERE VueloID=? AND Origen=?;");
	public final SQLStmt SelectByVueloIdDestino = new SQLStmt(
			"SELECT Origen, Destino FROM ls_vuelo " +
			"WHERE VueloID=? AND Destino=?;");
	
	public VoltTable[] run(String filtro, String ClienteID, String ReservaID, String VueloID, String Origen, String Destino)                               
			throws VoltAbortException {  

		if(filtro.equals("ireserva")) {
			voltQueueSQL(InsertReserva, ReservaID, ClienteID, VueloID);
		}
		if(filtro.equals("dcliente")) {
			voltQueueSQL(DeleteCliente, ClienteID);
		}
		if(filtro.equals("ureversa")) {
			voltQueueSQL(UpdateReserva, VueloID, ReservaID);
		}
		
		if(filtro.equals("s1vuelo")) {
			voltQueueSQL(SelectByVueloId, VueloID);
		}   
		if(filtro.equals("s2vuelo")) {
			voltQueueSQL(SelectByVueloIdOrigen, VueloID, Origen);
		} 
		if(filtro.equals("s3vuelo")) {
			voltQueueSQL(SelectByVueloIdDestino, VueloID, Destino);
		} 
		
		return voltExecuteSQL();     
	}

}
