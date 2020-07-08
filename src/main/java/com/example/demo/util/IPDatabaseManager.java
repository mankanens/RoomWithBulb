package com.example.demo.util;

import java.io.File;
import java.io.IOException;

import com.maxmind.geoip2.DatabaseReader;

public class IPDatabaseManager {

	private static final File DATABASE = new File("GeoLite2-Country.mmdb");

	private static DatabaseReader createReader(File database) {
		try {
			return new DatabaseReader.Builder(database).build();
		} catch (IOException e) {
			System.out.println("Problems creating a database reader.");
		}
		return null;
	}

	public static DatabaseReader getDbReader() {
		return createReader(DATABASE);
	}

}
