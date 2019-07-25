package procedure_voltdb;

import org.voltdb.VoltTable;
import org.voltdb.SQLStmt;
import org.voltdb.VoltProcedure;

public class Threadprocedure extends VoltProcedure {

	public final SQLStmt selectCliente = new SQLStmt(
			"SELECT * FROM ls_cliente;");

	//se realiza un select de una tabla en particion dada.
	public VoltTable[] run(String id) {

		voltQueueSQL(selectCliente);

		return voltExecuteSQL(true);

	}

}
