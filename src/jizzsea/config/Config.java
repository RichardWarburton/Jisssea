package jizzsea.config;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;

import org.ho.yaml.Yaml;

public class Config {

	public static Config fromFile(String fileName) throws FileNotFoundException {
		File f = new File(fileName);
		return Yaml.loadType(f, Config.class);
	}
	
	@Override
	public String toString() {
		return "Config [user=" + user + ", servers=" + Arrays.toString(servers)
				+ "]";
	}

	private User user;
	
	private Server[] servers;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Server[] getServers() {
		return servers;
	}

	public void setServers(Server[] servers) {
		this.servers = servers;
	}
}
