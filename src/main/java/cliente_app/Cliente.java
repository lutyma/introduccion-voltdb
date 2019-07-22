package cliente_app;

import org.voltdb.*;
import org.voltdb.client.ClientConfig;
import org.voltdb.client.ClientFactory;
import org.voltdb.client.ClientResponse;

public class Cliente {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
// se crea el client y se configura para conectar a la BD
		org.voltdb.client.Client client = null;
		ClientConfig config = null;
		try {
			config = new ClientConfig("amadeoa","amadeoa");
			client = ClientFactory.createClient(config); 
			client.createConnection("falcondes-db1-vip.sis.personal.net.py");
		    client.createConnection("falcondes-db2-vip.sis.personal.net.py");
		    client.createConnection("falcondes-db3-vip.sis.personal.net.py");      
		} catch (java.io.IOException e) {
			e.printStackTrace();
			//System.exit(-1);
		}
        String filtro = "s1cliente";
        String ClienteID = "2";
        String Nombre = "";
        String Apellido = "";
        String ReservaID = "";
        String VueloID = "";
		VoltTable[] results = null;
		
	// con clientResponse se recibe los datos del procedimiento llamado.	
		ClientResponse clientResponse = null;
		try {
			
			clientResponse = client.callProcedure("ProcedurePartition",              
				filtro,
				ClienteID,
				Nombre,
				Apellido,
				ReservaID,
				VueloID );    
			
		} catch (Exception e) {                                           
			e.printStackTrace();
		
		}
	
// con results recibimos la tabla tipo volttable retornada por el procedimiento y la iteramos para obtener los datos.		
		results = clientResponse.getResults();
		
		
	//	displayResults(clientResponse.getResults());
	
		for(VoltTable resp : results) {
			System.out.println("resultado:" + resp);
			
		}
		
		
		
	//	clientResponse.getResults();
	
	}

	public static void displayResults(VoltTable[] results) {
		  int table = 1;
		     for (VoltTable result : results) {
		      System.out.printf("*** Table %d ***\n",table++);
		      displayTable(result);
		   }
		}
	public static void displayTable(VoltTable t) {
		 
		   final int colCount = t.getColumnCount();
		   int rowCount = 1;
		   t.resetRowPosition();
		   while (t.advanceRow()) {
		      System.out.printf("--- Row %d ---\n",rowCount++);

		      for (int col=0; col<colCount; col++) {
		         System.out.printf("%s: ",t.getColumnName(col));
		         switch(t.getColumnType(col)) {
		            case TINYINT: case SMALLINT: case BIGINT: case INTEGER:
		               System.out.printf("%d\n", t.getLong(col));
		               break;
		            case STRING:
		               System.out.printf("%s\n", t.getString(col));
		               break;
		            case DECIMAL:
		               System.out.printf("%f\n", t.getDecimalAsBigDecimal(col));
		               break;
		            case FLOAT:
		               System.out.printf("%f\n", t.getDouble(col));
		               break;
		         }
		      }
		   }
		}
}
