package com.example.demo.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import com.example.demo.model.Room;

public class RWManager {

	public static void writeToFile(List<Room> rooms, File file) {
		FileOutputStream fos = null;
		ObjectOutputStream oos = null;
		try {
			fos = new FileOutputStream(file);
			oos = new ObjectOutputStream(fos);
			oos.writeObject(rooms);
			oos.close();
			fos.close();
		} catch (IOException e) {
			System.out.println("Something went wrong.");
		}
	}

	public static List<Room> readRooms() {
		List<Room> rooms = new ArrayList<Room>();
		File file = new File("Rooms.txt");
		FileInputStream fis = null;
		ObjectInputStream ois = null;
		try {
			fis = new FileInputStream(file);
			ois = new ObjectInputStream(fis);
			rooms = (ArrayList<Room>) ois.readObject();
			ois.close();
			fis.close();
		} catch (IOException e) {
			return rooms;
		} catch (ClassNotFoundException e) {
		}
		return rooms;
	}

	public static List<String> readCountryNames() {
		File file = new File("CountryList.txt");
		List<String> countryNames = new ArrayList<String>();
		FileInputStream fis = null;
		ObjectInputStream ois = null;
		try {
			fis = new FileInputStream(file);
			ois = new ObjectInputStream(fis);
			countryNames = (ArrayList<String>) ois.readObject();
			ois.close();
			fis.close();
		} catch (IOException e) {
			return countryNames;
		} catch (ClassNotFoundException e) {
		}
		return countryNames;
	}
}
