package com.example.demo.controller;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.example.demo.model.Bulb;
import com.example.demo.model.MessageRequest;
import com.example.demo.model.MessageResponse;
import com.example.demo.model.Room;
import com.example.demo.util.RWManager;
import com.example.demo.util.IPDatabaseManager;
import com.google.common.net.InetAddresses;
import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.exception.GeoIp2Exception;
import com.maxmind.geoip2.model.CountryResponse;

@Controller
public class IndexController {

	private List<String> countries = RWManager.readCountryNames();
	private List<Room> rooms = RWManager.readRooms();
	private Boolean error = false;
	
	@GetMapping(value = "")
	public String viewIndex(Model model) {
		model.addAttribute("countries", countries);
		model.addAttribute("rooms", rooms);
		if (error) {
			model.addAttribute("error", "Вам запрещён доступ в эту комнату.");
			error = false;
		}
		return "index";
	}

	@PostMapping(value = "")
	public String createRoom(@RequestParam String select, Model model) {
		Room room = new Room();
		Bulb bulb = new Bulb();
		room.setBulb(bulb);
		room.setCountry(select);
		room.setName(select + rooms.size());
		rooms.add(room);
		model.addAttribute("rooms", rooms);
		model.addAttribute("countries", countries);
		RWManager.writeToFile(rooms, new File("Rooms.txt"));
		return "index";
	}

	@GetMapping(value = "/rooms/{roomName}")
	public String room(@PathVariable String roomName, Model model, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		model.addAttribute("roomName", roomName);
		InetAddress ipAdress = InetAddresses.forString(request.getRemoteAddr());
		DatabaseReader dbReader = IPDatabaseManager.getDbReader();
		CountryResponse country = null;
		try {
			country = dbReader.country(ipAdress);
		} catch (GeoIp2Exception e) {
		} finally {
			dbReader.close();
		}
		if (country != null) {
			if (roomName.contains(country.getCountry().getName())) {
				return "room";
			} else {
				error = true;
				response.sendRedirect("/");
			}
		}
		return "room";
	}

	@MessageMapping("checkBulb")
	@SendTo("/topic/bulbInf")
	public MessageResponse bulbInfo(MessageRequest request) throws Exception {
		String roomName = request.getContent().substring(1);
		roomName = roomName.replaceFirst("&amp;", "&");
		Character toogle = request.getContent().charAt(0);
		Room requestRoom = null;
		for (Room room : rooms) {
			if (room.getName().equals(roomName)) {
				requestRoom = room;
				break;
			}
		}
		if (toogle == 't') {
			requestRoom.getBulb().toogle();
			RWManager.writeToFile(rooms, new File("Rooms.txt"));
		}
		MessageResponse response = null;
		if (requestRoom.getBulb().getIsOn()) {
			response = new MessageResponse('t' + roomName);
		} else {
			response = new MessageResponse('f' + roomName);
		}
		return response;
	}
}
