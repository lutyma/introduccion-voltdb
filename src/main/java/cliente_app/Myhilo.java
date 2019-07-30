package cliente_app;

import java.io.IOException;

import org.voltdb.VoltTable;
import org.voltdb.client.Client;
import org.voltdb.client.ClientResponse;
import org.voltdb.client.NoConnectionsException;
import org.voltdb.client.ProcCallException;

public class Myhilo extends Thread {
	int idProcedimiento;
	org.voltdb.client.Client client;
	String key;
	String filtro;
	String ClienteID;
	String Nombre;
	String Apellido;
	String ReservaID;
	String VueloID;
	String Origen;
	String Destino;	
	String modificarId;
	public Myhilo(int idProcedimiento, Client client, String key, String filtro, String clienteID, String nombre, String apellido,
			String reservaID, String vueloID, String origen, String destino, String modificarId) {
		super();
		this.idProcedimiento = idProcedimiento;
		this.client = client;
		this.key = key;
		this.filtro = filtro;
		this.ClienteID = clienteID;
		this.Nombre = nombre;
		this.Apellido = apellido;
		this.ReservaID = reservaID;
		this.VueloID = vueloID;
		this.Origen = origen;
		this.Destino = destino;
		this.modificarId = modificarId;
	}
	VoltTable[] results = null;
	ClientResponse clientResponse = null;
	public void run()
	{

		try {

			// idProcedimiento indica a cual de los procedimientos se le va a invocar..
			if(idProcedimiento == 1) {
				try {
					clientResponse =client.callProcedure("Threadprocedure", key);
					results = clientResponse.getResults();
					VoltTable res = results[0];
					System.out.println("resultado: " + res);
					System.out.println("###########");	

					while(res.advanceRow()) {
						if(res.getString(0).equals(modificarId)) {

							try {
								client.callProcedure(new MyCallback(),"ThreadProcedureUpdate", key, modificarId, "Joel");
							} catch (NoConnectionsException e) {

								e.printStackTrace();
							} catch (IOException e) {

								e.printStackTrace();
							}
						}	

					}

				} catch (ProcCallException e) {
					e.printStackTrace();
				}
			}
			else if(idProcedimiento == 2){

				client.callProcedure(new MyCallback(),"ProcedurePartition",              
						filtro,
						ClienteID,
						Nombre,
						Apellido,
						ReservaID,
						VueloID,
						Origen,
						Destino);	
			}

		} catch (NoConnectionsException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}  

	} 

}
