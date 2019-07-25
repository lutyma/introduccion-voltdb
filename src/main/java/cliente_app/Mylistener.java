package cliente_app;

import org.voltdb.client.ClientResponse;
import org.voltdb.client.ClientStatusListenerExt;
import org.voltdb.client.ProcedureCallback;

public class Mylistener extends ClientStatusListenerExt {

	public void uncaughtException(ProcedureCallback callback, ClientResponse r, Throwable e) {

		System.out.println("Error: " + e.getMessage());
	}

}
