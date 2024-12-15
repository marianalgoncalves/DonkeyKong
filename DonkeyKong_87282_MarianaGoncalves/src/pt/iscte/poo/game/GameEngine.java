package pt.iscte.poo.game;

import behaviors.Expirable;
import behaviors.Movable;
import behaviors.Player;
import java.awt.event.KeyEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.Instant;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import objects.Door;
import objects.Floor;
import objects.GameObject;
import objects.Manel;
import objects.ObjectFactory;
import pt.iscte.poo.gui.ImageGUI;
import pt.iscte.poo.observer.Observed;
import pt.iscte.poo.observer.Observer;
import pt.iscte.poo.utils.Direction;
import pt.iscte.poo.utils.Point2D;


public class GameEngine implements Observer {

	private static GameEngine INSTANCE; 

	private Manel manel = new Manel(new Point2D(0,8));
	private Room currentRoom = createRoom(0);
	private int lastTickProcessed = 0;

	private final String ROOMS_DIR = "rooms";
	LocalTime startTime = LocalTime.now();
	
	public GameEngine() {
		ImageGUI.getInstance().update();
	}

	public static GameEngine getInstance() {
		if ( INSTANCE == null) {
			INSTANCE = new GameEngine();
		}
		return INSTANCE;
	}


	public Room getCurrentRoom(){
		return currentRoom;
	}

	@Override
	public void update(Observed source) {
		ArrayList<GameObject> gameObjectList = GameEngine.getInstance().getCurrentRoom().getGameObjectList();
		ArrayList<GameObject> removeToGameObjectList = GameEngine.getInstance().getCurrentRoom().getRemoveToGameObjectList();
		
		if (ImageGUI.getInstance().wasKeyPressed() && !manel.isFalling()) {
			int k = ImageGUI.getInstance().keyPressed();
			System.out.println("Keypressed " + k);
			if (Direction.isDirection(k)) { 
				System.out.println("Direction! ");	
				Direction direction = Direction.directionFor(k);
				
				ImageGUI.getInstance().removeImages(removeToGameObjectList);
				gameObjectList.removeAll(removeToGameObjectList);
				removeToGameObjectList.clear();
				
				manel.jump(direction);
				
				if(currentRoom.hasDoor()){
					if(manel.getPosition().equals(currentRoom.getRoomDoor().getPosition()) ){
						nextLevel();
					}
				}
			}
			if( k == KeyEvent.VK_SPACE ){
				manel.loadJump();
			}
			
		}
		int t = ImageGUI.getInstance().getTicks();
		while (lastTickProcessed < t) {
			processTick();
		}
		ImageGUI.getInstance().update();
	}

	private void processTick() {
		if(manel.isFalling()){
			manel.fall();
		}

		ArrayList<GameObject> gameObjectList = GameEngine.getInstance().getCurrentRoom().getGameObjectList();
		for(GameObject gameObject: gameObjectList){
			if(gameObject instanceof Movable && !(gameObject instanceof Player)){
				((Movable)gameObject).move(); 
			}
			if(gameObject instanceof Expirable){
				((Expirable)gameObject).expire();
			}
		}

		ArrayList<GameObject> addToGameObjectList = GameEngine.getInstance().getCurrentRoom().getAddToGameObjectList();
		ImageGUI.getInstance().addImages(addToGameObjectList);
		gameObjectList.addAll(addToGameObjectList);
		addToGameObjectList.clear();

		ArrayList<GameObject> removeToGameObjectList = GameEngine.getInstance().getCurrentRoom().getRemoveToGameObjectList();
		ImageGUI.getInstance().removeImages(removeToGameObjectList);
		gameObjectList.removeAll(removeToGameObjectList);
		removeToGameObjectList.clear();

		System.out.println("Tic Tac : " + lastTickProcessed);
		lastTickProcessed++;
	}

	private Room createRoom(int roomNumber){
		ArrayList<GameObject> gameObjectList = new ArrayList<>();
		String roomName = "room" + roomNumber + ".txt";
		File file = new File(ROOMS_DIR + "/" + roomName);
		int width = 0;
		int height = 0;
		Door roomDoor = null;
		try (Scanner sc = new Scanner(file)) {
			while (sc.hasNextLine()){
				String nextLine = sc.nextLine();
				if (String.valueOf(nextLine.charAt(0)).equals("#")){
					continue;
				}
				
				int x = 0;
				for( ; x<nextLine.length(); x++){
					Point2D position = new Point2D(x,height);
					ImageGUI.getInstance().addImage(new Floor(position));
					GameObject gameObject = ObjectFactory.createObject(nextLine.charAt(x), position);
					if(gameObject instanceof Door ){
						roomDoor = (Door)gameObject;
					}
					if (gameObject!=null){
						System.out.println(position);
						ImageGUI.getInstance().addImage(gameObject);
						gameObjectList.add(gameObject);
					}
				}
				if(x > width){
					width = x;
				}
				height++;
			}
		}
		catch(FileNotFoundException e){
			System.out.println("File Not Found");
		}
		ImageGUI.getInstance().addImage(manel);
		gameObjectList.add(manel);
		return new Room(gameObjectList, width, height, roomNumber, roomDoor);
	}

	private void nextLevel(){
		int nextRoomNumber = currentRoom.getRoomNumber() + 1;
		ImageGUI.getInstance().clearImages();
		this.currentRoom = createRoom(nextRoomNumber); 
		manel.setPosition(new Point2D(1,8));
		ImageGUI.getInstance().setStatusMessage("Well Done! Heding to Level " + (nextRoomNumber+1));
	}

	public Manel getManel(){
		return manel;
	}

	public void startOver(){
		ImageGUI.getInstance().clearImages();
		this.manel = new Manel(new Point2D(0,8));
		this.currentRoom = createRoom(0); 
		ImageGUI.getInstance().setStatusMessage("Game Over! Try Again");
	}

	public void winGame(){
		LocalTime endTime = LocalTime.now();

		
		long totalMilis = startTime.until(endTime, ChronoUnit.MILLIS);
		String totalTime = Instant.ofEpochMilli(totalMilis).atOffset(ZoneOffset.UTC).toLocalTime().toString();

		ImageGUI.getInstance().showMessage("Game Won!", "Total Time: " + totalTime);
		
		saveScores(totalTime);
		ArrayList<LocalTime> top10 = getTopScores();

		ImageGUI.getInstance().showMessage("Top 10 Best Times", createHighScoreTable(top10));
		//ImageGUI.getInstance().dispose();
	
	}

	public void saveScores(String totalDateTime){
		Path path = Paths.get("scores", "scores.txt");
		String text = totalDateTime + "\n";


		try (BufferedWriter writer = Files.newBufferedWriter(path, StandardCharsets.UTF_8, StandardOpenOption.APPEND, StandardOpenOption.CREATE)){
			writer.write(text);
		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public ArrayList<LocalTime> getTopScores(){
		Path path = Paths.get("scores", "scores.txt");
		ArrayList<LocalTime> times = new ArrayList<>();
		
		try (Scanner sc = new Scanner(path)) {
			while (sc.hasNextLine()){
				String nextLine = sc.nextLine();
				if(!"".equals(nextLine)){
					times.add(LocalTime.parse(nextLine));
				}
				
			}
			sc.close();
		}
		catch (IOException ex) {
			System.out.println("File Not Found");
        }

		Collections.sort(times);
		if(times.size() > 10){
			times = new ArrayList<>(times.subList(0, 9));
		}
		return times;
	}

	private String createHighScoreTable(List<LocalTime> times) {
        StringBuilder table = new StringBuilder();
        table.append("Rank Time\n");
        table.append("=================\n");

        int rank = 1;
        for (LocalTime time : times) {
            table.append(String.format("%2d. \t%s\n", rank, time.toString()));
            rank++;
        }

        return table.toString();
    }





}
