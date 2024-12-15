package pt.iscte.poo.game;

import java.util.ArrayList;
import objects.Door;
import objects.GameObject;


public class Room {

	private ArrayList<GameObject> gameObjectList; //atributos
	private ArrayList<GameObject> addToGameObjectList;
	private ArrayList<GameObject> removeToGameObjectList;
	private int width;
	private int height;
	private int roomNumber;
	private Door roomDoor;
	
	public Room(ArrayList<GameObject> gameObjectList, int x, int y, int  roomNumber, Door roomDoor) { //construtor dos atributos
		this.gameObjectList = gameObjectList;
		this.addToGameObjectList = new ArrayList<>();
		this.removeToGameObjectList = new ArrayList<>();
		this.width = x;
		this.height = y;
		this.roomNumber = roomNumber;
		this.roomDoor = roomDoor;
	}

	public int getWidth(){
		return width;
	}
	public int getHeight(){
		return height;
	}

	public ArrayList<GameObject> getGameObjectList(){ // devolvem os atributos
		return gameObjectList;
	}
	public ArrayList<GameObject> getAddToGameObjectList(){ // devolvem os atributos
		return addToGameObjectList;
	}

	public ArrayList<GameObject> getRemoveToGameObjectList(){ // devolvem os atributos
		return removeToGameObjectList;
	}
	public int getRoomNumber(){
		return roomNumber;
	}

	public Door getRoomDoor(){
		return roomDoor;
	}

	public boolean hasDoor(){
		if(roomDoor == null){
			return false;
		}
		return true;
	}
	
}