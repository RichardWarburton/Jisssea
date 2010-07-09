package jizzsea.config;

import java.util.Arrays;

public class Server {

	private int port;
	private String address;
	private String name;
	private String[] channels;

	public String[] getChannels() {
		return channels;
	}
	public void setChannels(String[] channels) {
		this.channels = channels;
	}
	
	@Override
	public String toString() {
		return "Server [port=" + port + ", address=" + address + ", name="
				+ name + ", channels=" + Arrays.toString(channels) + "]";
	}
	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
