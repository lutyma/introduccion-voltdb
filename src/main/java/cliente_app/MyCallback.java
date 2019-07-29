package cliente_app;

import java.io.IOException;

import org.voltdb.VoltTable;
import org.voltdb.client.Client;
import org.voltdb.client.ClientResponse;
import org.voltdb.client.NoConnectionsException;
import org.voltdb.client.ProcedureCallback;

public class MyCallback implements ProcedureCallback{

	org.voltdb.client.Client client;
	String modificarId;
	String key;

	public MyCallback(Client client, String modificarId, String key) {
		super();
		this.client = client;
		this.modificarId = modificarId;
		this.key = key;
	}

	public MyCallback() {
		super();
	}
	public void clientCallback(ClientResponse clientResponse) throws Exception {

		if (clientResponse.getStatus() != ClientResponse.SUCCESS) {
			throw new Exception(clientResponse.getStatusString());

		} else {


			myEvaluateResultsProc(clientResponse.getResults(), modificarId);
		}
	}

	private void myEvaluateResultsProc(VoltTable[] results, String modificarId) {

		if (modificarId == null) {
			for(VoltTable res : results) {

				System.out.println("resultado: " + res);
				System.out.println("###########");		
			}
		}
		else if(!modificarId.isEmpty()){
			for(VoltTable res : results) {
				while(res.advanceRow()) {
					if(res.getString(0).equals(modificarId)) {

						try {
							client.callProcedure(new MyCallback(),"ThreadProcedureUpdate", key, modificarId, "NombreCambiado");
						} catch (NoConnectionsException e) {

							e.printStackTrace();
						} catch (IOException e) {

							e.printStackTrace();
						}
					}	

				}
			}
		}      

	}

}
