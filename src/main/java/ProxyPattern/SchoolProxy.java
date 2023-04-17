package ProxyPattern;

import java.net.URL;
import java.util.Set;

public class SchoolProxy implements ConnectionInterface {
	
	
	private Set<String> denylistedHosts;
	private URL redirectPage;
	private Set<Integer> teacherIDs;
	private boolean authorized = false;
	
	private NetworkConnection networkConnection;
	
	public SchoolProxy(Set<String> denylistedHosts, URL redirectPage,
	                   Set<Integer> teacherIDs
	) {

		this.teacherIDs = teacherIDs;
		this.redirectPage = redirectPage;
		this.denylistedHosts = denylistedHosts;
		this.networkConnection = new NetworkConnection();
		
		
	}
	
	
	public Set<String> getDenylistedHost() {
		return this.denylistedHosts;
	}
	
	public URL getRedirectPage() {
		return this.redirectPage;
	}
	
	public Set<Integer> getTeacherId() {
		return this.teacherIDs;
	}
	
	public Boolean getAuthorized() {
		return this.authorized;
	}
	
	public NetworkConnection getNetworkConnection() {
		return this.networkConnection;
	}
	@Override
	public void connect(URL url) {
		
		if (!authorized) {
			if (getDenylistedHost().contains(url.getHost())) {
				System.err.print("Connecting to '"
						                 + url
						                 + "' was rejected!");
				getNetworkConnection().connect(getRedirectPage());
				
			} else {
				
				getNetworkConnection().connect(url);
				
			}
			
		} else {
			getNetworkConnection().connect(url);
		}



//
	
	}
	
	
	@Override
	public void disconnect() {
		networkConnection.disconnect();
		this.authorized = false;
		
	}
	
	@Override
	public boolean isConnected() {
		return networkConnection.isConnected();
	}
	
	// TODO: Implement the SchoolProxy
	
	public void login(int teacherID) {
		
		if (getTeacherId().contains(teacherID)) {
			this.authorized = true;
		}
		else {
			System.out.println("You are not authorized to be on this page. " + "You will be redirected to '" + getRedirectPage() + "'.");
			getNetworkConnection().connect(redirectPage);
		}
		
		
	}
	
	public void logout() {
		
		this.authorized = false;
		
	}
	
}