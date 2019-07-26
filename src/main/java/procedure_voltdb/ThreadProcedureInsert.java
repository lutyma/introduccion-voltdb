package procedure_voltdb;

import org.voltdb.SQLStmt;
import org.voltdb.VoltProcedure;
import org.voltdb.VoltTable;

public class ThreadProcedureInsert extends VoltProcedure {

	public final SQLStmt InsertCliente = new SQLStmt("INSERT INTO ls_cliente VALUES(?,?,?);");

	//se realiza un insert de una tabla en particion dada.
	public VoltTable[] run(String id, String ClienteID, String Nombre, String Apellido) {

		voltQueueSQL(InsertCliente, ClienteID, Nombre, Apellido);

		return voltExecuteSQL(true);

	}
	
}
