package cliente_app;

import java.io.IOException;

import org.voltdb.client.Client;
import org.voltdb.client.NoConnectionsException;

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

	public void run()
	{

		try {

			// idProcedimiento indica a cual de los procedimientos se le va a invocar..
			if(idProcedimiento == 1) {
				client.callProcedure(new MyCallback(client, modificarId, key),"Threadprocedure",key);
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
