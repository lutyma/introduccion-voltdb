package cliente_app;

import org.voltdb.VoltTable;
import org.voltdb.client.ClientResponse;
import org.voltdb.client.ProcedureCallback;

public class MyCallback implements ProcedureCallback{

	public void clientCallback(ClientResponse clientResponse) throws Exception {
		
		 if (clientResponse.getStatus() != ClientResponse.SUCCESS) {
			 throw new Exception(clientResponse.getStatusString());
		       
		   } else {

		       myEvaluateResultsProc(clientResponse.getResults());
		    }
	}

	private void myEvaluateResultsProc(VoltTable[] results) {
		for(VoltTable res : results) {
			System.out.println("resultado: " + res);
			System.out.println("###########");
				
		}
	}

}
