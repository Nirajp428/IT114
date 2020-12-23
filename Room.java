package server;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;




public class Room implements AutoCloseable {
    private static SocketServer server;// used to refer to accessible server functions
    private String name;
    private final static Logger log = Logger.getLogger(Room.class.getName());

    // Commands
    private final static String COMMAND_TRIGGER = "/";
    private final static String CREATE_ROOM = "createroom";
    private final static String JOIN_ROOM = "joinroom";
   
   
    public Room(String name, boolean delayStart) {
	
	this.name = name;
	
    }

    public Room(String name) {
	this.name = name;
	// set this for BaseGamePanel to NOT draw since it's server-side
	
    }

    public static void setServer(SocketServer server) {
	Room.server = server;
    }

    public String getName() {
	return name;
    }

    

    protected synchronized void addClient(ServerThread client) {
	client.setCurrentRoom(this);
	boolean exists = false;}
	// since we updated to a different List type, we'll need to loop through to find
	// the client to check against
	
	


   

    /***
     * Syncs the existing players in the room with our newly connected player
     * 
     * @param client
     */
   

    /**
     * Syncs the existing clients in the room with our newly connected client
     * 
     * @param client
     */
    private synchronized void updateClientList(ServerThread client) {
	
    }

    protected synchronized void removeClient(ServerThread client) {
	
    }

    private void cleanupEmptyRoom() {
	// If name is null it's already been closed. And don't close the Lobby
	if (name == null || name.equalsIgnoreCase(SocketServer.LOBBY)) {
	    return;
	}
	try {
	    log.log(Level.INFO, "Closing empty room: " + name);
	    close();
	}
	catch (Exception e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
    }

    protected void joinRoom(String room, ServerThread client) {
	server.joinRoom(room, client);
    }

    protected void joinLobby(ServerThread client) {
	server.joinLobby(client);
    }

    /***
     * Helper function to process messages to trigger different functionality.
     * 
     * @param message The original message being sent
     * @param client  The sender of the message (since they'll be the ones
     *                triggering the actions)
     */
    private boolean processCommands(String message, ServerThread client) {
	boolean wasCommand = false;
	try {
	    if (message.indexOf(COMMAND_TRIGGER) > -1) {
		String[] comm = message.split(COMMAND_TRIGGER);
		log.log(Level.INFO, message);
		String part1 = comm[1];
		String[] comm2 = part1.split(" ");
		String command = comm2[0];
		if (command != null) {
		    command = command.toLowerCase();
		}
		String roomName;
		switch (command) {
		case CREATE_ROOM:
		    roomName = comm2[1];
		    if (server.createNewRoom(roomName)) {
			joinRoom(roomName, client);
		    }
		    wasCommand = true;
		    break;
		case JOIN_ROOM:
		    roomName = comm2[1];
		    joinRoom(roomName, client);
		    wasCommand = true;
		    break;
		}
	    }
	}
	catch (Exception e) {
	    e.printStackTrace();
	}
	return wasCommand;
    }

    protected void sendConnectionStatus(ServerThread client, boolean isConnect, String message) {
	
    }

    /***
     * Takes a sender and a message and broadcasts the message to all clients in
     * this room. Client is mostly passed for command purposes but we can also use
     * it to extract other client info.
     * 
     * @param sender  The client sending the message
     * @param message The message to broadcast inside the room
     */
    protected void sendMessage(ServerThread sender, String message) {
	
	if (processCommands(message, sender)) {
	    // it was a command, don't broadcast
	    return;
	}
	
    }

    /**
     * Broadcasts this client/player direction to all connected clients/players
     * 
     * @param sender
     * @param dir
     */
    protected void sendDirectionSync(ServerThread sender, Point dir) {
	boolean changed = false;
	// first we'll find the clientPlayer that sent their direction
	// and update the server-side instance of their direction
	
	
	// if the direction is "changed" (it should be, but check anyway)
	// then we'll broadcast the change in direction to all clients
	// so their local movement reflects correctly
	
	
    }

    /**
     * Broadcasts this client/player position to all connected clients/players
     * 
     * @param sender
     * @param pos
     */
    protected void sendPositionSync(ServerThread sender, Point pos) {
	
    }

    public List<String> getRooms() {
	return server.getRooms();
    }

    /***
     * Will attempt to migrate any remaining clients to the Lobby room. Will then
     * set references to null and should be eligible for garbage collection
     */
    @Override
    public void close() throws Exception {
	
	}{
	
	name = null;}
	// should be eligible for garbage collection now
    

   
    public void awake() {
	// TODO Auto-generated method stub

    }

   
    public void start() {
	// TODO Auto-generated method stub
	log.log(Level.INFO, getName() + " start called");
    }

    long frame = 0;



    

    
    public void update() {
	// We'll make the server authoritative
	// so we'll calc movement/collisions and send the action to the clients so they
	// can visually update. Client's won't be determining this themselves
	

    }

    // don't call this more than once per frame
    private void nextFrame() {
	// we'll do basic frame tracking so we can trigger events
	// less frequently than each frame
	// update frame counter and prevent overflow
	if (Long.MAX_VALUE - 5 <= frame) {
	    frame = Long.MIN_VALUE;
	}
	frame++;
    }

   
    

}