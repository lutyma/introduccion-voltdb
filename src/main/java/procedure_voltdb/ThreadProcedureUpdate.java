package procedure_voltdb;

import org.voltdb.SQLStmt;
import org.voltdb.VoltProcedure;
import org.voltdb.VoltTable;

public class ThreadProcedureUpdate extends VoltProcedure {

	public final SQLStmt UpdateCliente = new SQLStmt(" UPDATE ls_cliente SET Nombre=? WHERE ClienteID=?;");

	//se realiza un update de una tabla en particion dada.
	public VoltTable[] run(String key, String ClienteID, String Nombre) {

		voltQueueSQL(UpdateCliente, Nombre, ClienteID);

		return voltExecuteSQL(true);

	}
	
}
