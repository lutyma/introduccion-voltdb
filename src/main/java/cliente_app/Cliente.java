package cliente_app;

import java.io.IOException;

import org.voltdb.*;
import org.voltdb.client.ClientConfig;
import org.voltdb.client.ClientFactory;
import org.voltdb.client.ClientResponse;
import org.voltdb.client.ProcedureCallback;

public class Cliente {

	public static void main(String[] args)throws Exception {
		// se crea el client y se configura para conectar a la BD.
		// se crea una instancia de la clase Mylistener para atrapar y escribir una exepción del lado del cliente.

		org.voltdb.client.Client client = null;
		ClientConfig config = null;
		Mylistener exepcion = new Mylistener();

		// for que se utilizó para cargar las tablas con 10000 datos.

		for(int i = 1; i < 2; i++ ) {
			try {
				config = new ClientConfig("amadeoa","amadeoa",exepcion);
				client = ClientFactory.createClient(config); 
				client.createConnection("falcondes-db1-vip.sis.personal.net.py");
				client.createConnection("falcondes-db2-vip.sis.personal.net.py");
				client.createConnection("falcondes-db3-vip.sis.personal.net.py");      
			} catch (java.io.IOException e) {
				e.printStackTrace();
			}


			// parametros utilizados para cargar las diferentes tablas con las columnas especificas.
			String [] abecedario = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", 
					"K", "L", "M","N","O","P","Q","R","S","T","U","V","W", "X","Y","Z" };
			String id = "id";
			String rs = "rs";
			String v = "v";
			int numRandon1 = (int) Math.round(Math.random() * 25 );
			int numRandon2 = (int) Math.round(Math.random() * 25 );
			String filtro = "s1cliente";
			String ClienteID = id+3000;
			String Nombre = abecedario[numRandon1];
			String Apellido = abecedario[numRandon2];
			String ReservaID = rs+i;
			String VueloID = v+i;
			String Origen = "";
			String Destino =  "";
			int idProcedimiento = 1;

			try {

				// obtenemos todas las particiones con la que cuenta la BD.
				VoltTable[] results = client.callProcedure("@GetPartitionKeys", 
						"VARCHAR").getResults();

				VoltTable keys = results[0];

				// idProcedimiento = 1 >> se llama al procedimiento que realiza un select en cada particion.
				// idPorcedimiento = 2 >> se llama al procedimiento particionado sin uso del key de particion.
				// idProcedimiento = 3 >> se llama al procedimiento de insertar
				// si el procedimiento a realizar es un Insert, no se realiza dentro del for, para no insertar en cada particion.

				if(idProcedimiento != 3) {

					for(int k = 0;k < keys.getRowCount(); k++) {
						String key = keys.fetchRow(k).getString(1);

						//se crea una instancia de la clase hilo y se le pasa los parámetros para llamar al storeprocedure identificado por el primer parametro.
						Myhilo hilo = new Myhilo(idProcedimiento, client, key, filtro, ClienteID, Nombre, Apellido, ReservaID, VueloID, Origen, Destino);
						hilo.start();
						Thread.sleep(5000l);
						client.drain();
					}	
				}
				else if(idProcedimiento == 3) {
					String key = keys.fetchRow(0).getString(1);

					//se crea una instancia de la clase hilo y se le pasa los parámetros para llamar al storeprocedure identificado por el primer parametro.
					Myhilo hilo = new Myhilo(idProcedimiento, client, key, filtro, ClienteID, Nombre, Apellido, ReservaID, VueloID, Origen, Destino);
					hilo.start();
					Thread.sleep(5000l);
					client.drain();

				}
			} catch (Exception e) {

				System.out.println(e.getMessage());

			} finally {

				client.close();
			}
		}

	}

}