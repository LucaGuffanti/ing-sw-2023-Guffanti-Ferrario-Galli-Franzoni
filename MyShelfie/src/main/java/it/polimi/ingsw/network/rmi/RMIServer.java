package it.polimi.ingsw.network.rmi;

import it.polimi.ingsw.network.ClientConnection;
import it.polimi.ingsw.network.ServerNetworkHandler;
import it.polimi.ingsw.network.messages.Message;
import it.polimi.ingsw.network.utils.Logger;
import it.polimi.ingsw.network.utils.exceptions.NotYetImplementedException;

import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Map;

/**
 * The RMI server. it can be called by each client.
 */
public class RMIServer{
    ServerNetworkHandler serverNetworkHandler;
    RMIClientHandler clientHandler;
    public RMIServer(String RMIServiceName, String ip, int port, ServerNetworkHandler serverNetworkHandler) throws RemoteException {
        super();
        Logger.networkInfo("RMI Registry will be at "+ "rmi://"+ip+":"+port+"/"+RMIServiceName);
        this.serverNetworkHandler = serverNetworkHandler;
        clientHandler = new RMIClientHandler(this);
        Registry registry = LocateRegistry.createRegistry(port);
        registry.rebind(RMIServiceName, clientHandler);
        Logger.networkInfo("Rmi server bound");
    }

    public ServerNetworkHandler getServerNetworkHandler() {
        return serverNetworkHandler;
    }

}
