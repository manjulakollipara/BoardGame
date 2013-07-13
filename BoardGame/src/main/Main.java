package main;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import exceptions.MyException;
import bean.Coordinates;

/**
 * @author Manjula Kollipara
 *
 */
public class Main implements Comparator{

	// size should not be hardcoded
	int size = 3;
	int maxSize = size + 1;
	int x, y;
	ArrayList<Coordinates> newMoves;
	List<Coordinates> kPath = new ArrayList<Coordinates>();
	int[][] chessBoard = new int[size][size];
	int finalIndex  = -1;
	
	public static void main(String[] args){

		try{
			if( args.length < 2){
				System.out.println("Format to run this program: java main xValue yValue");
			}

			Main mainObj = new Main();
			boolean validInput = mainObj.validateInput(args);

			if( validInput ){
				mainObj.move();

			}else{
				System.out.println("Invalid inputs to the Board");
			}
		}catch(MyException e){
			System.out.println(e.toString());
		}catch(Exception e){
			e.printStackTrace();
		}

	}

	/**
	 * This method determines the next list of valid coordinates that K can move  
	 */
	public void move(){

		// Add the provided input coordinates to kPath( Original List )
		Coordinates cooridnate = new Coordinates(x, y, 0, false);
		chessBoard[x-1][y-1] = 1; // initial Position of Knight
		
		// initial chessBoard
		displayChessBoard();

		// Original List that contains the list of valid coordinates 
		kPath.add(cooridnate);

		Coordinates cObj;
		
		// Since using Iterator would not allow modifying the list, call a function to get the next unvisited Coordinate
		while( ( cObj = getNextUnvisitedCoordinates(kPath) ) != null ){

			// Get nextMoves for a chosen coordinate from  the kPath 
			List<Coordinates> newMovesList = getNewMoves( cObj, kPath.indexOf( cObj ) + 1 );

			// Add the new and unvisited coordinates to original List kPath
			updateKPath( newMovesList, kPath);
			
			// Set the already visited coordinate to true to differentitate between the visited and unvisited coordinate
			cObj.setVisited(true);

			// Determine if the target cell was reached
			finalIndex = targetReached(kPath);

			if( finalIndex >= 0 )
				break;
		}

		displayPath(kPath, finalIndex);

	}

	private void displayChessBoard() {
		for(int i=0; i< size; i++){
			for(int j=0; j<size; j++)
				System.out.print(chessBoard[i][j]+ " ");
			
		System.out.println("\n");		
		}		
	}

	/**
	 * Get the next unvisited coordinate details
	 * @param list copy of original list to find the next unVisted coordinate
	 * @return unVisted Coordinate
	 */
	public Coordinates getNextUnvisitedCoordinates(List<Coordinates> list){

		Coordinates unVisited = null; 

		try{

			Iterator<Coordinates> it = list.iterator();
			while(it.hasNext()){
				Coordinates temp = (Coordinates) it.next();
				if(  !temp.isVisited() ) {
					unVisited = temp;
					break;
				}
			}

		}catch(Exception e){
			e.printStackTrace();
		}

		return unVisited;
	}
	
	/** 
	 * Loops through the original list kPath and displays the path that takes K to target cell
	 * @param list
	 * @param index
	 */
	public void displayPath(List<Coordinates> list, int index){

		if(index >=0 ){
			System.out.println(" Final chessBoard ");
			chessBoard[x-1][y-1]=0;
			chessBoard[size-1][size-1]=1;
			displayChessBoard();
		}
		
		Coordinates c = list.get(index);
		List<String> path = new ArrayList<String>();

		System.out.println(" Knight has been moved to bottom right corner successfully: and the path is:");
		while( c != null && c.getParent() >= 0){
			//System.out.print("( " +c.getX() + ", " + c.getY()+" ) <--");
			path.add("--> ( "+ c.getX()+", "+c.getY() + " )");
			index = c.getParent();
			c =  ( ( index -1 ) >= 0 ? list.get( index -1 ) : null ); 
		}
		
		for(int i=path.size()-1; i >= 0 ; i--)
			System.out.print(path.get(i) );
	}

	/**
	 * @param list copy of original list kPath
	 * @return index of target cell
	 */
	public int targetReached(List<Coordinates> list){
		// Find if the target reached
		for(Coordinates c: list){
			if(c.getX() == size &&  c.getY() == size )
				return list.indexOf( c );
		}
		return -1;
	}

	/**
	 * @param newList newly found coordinates
	 * @param kPath original list
	 */
	public void updateKPath(List<Coordinates> newList, List<Coordinates> kPath) {

		int addToKPath = 0;

		// Compare the new Moves with the existing kPath moves and add if KPath does not have that move
		for( Coordinates newElement : newList){
			for( Coordinates currentElement: kPath){
				addToKPath = compare(newElement, currentElement);
				
				if(addToKPath == 0)
					break;
			}

			if( addToKPath > 0 ){
				// add the unVisited coordinate
				kPath.add(new Coordinates(newElement.getX(), newElement.getY(), newElement.getParent(), false));
			}
		}
	}

	/* (non-Javadoc)
	 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
	 */
	@Override
	public int compare( Object obj1, Object obj2){

		Coordinates o1 = (Coordinates) obj1;
		Coordinates o2 = (Coordinates) obj2;

		if( o1.getX() == o2.getX() && o1.getY() == o2.getY())
			return 0;

		return 1;
	}


	/**
	 * @param values input coordinates
	 * @return boolean true on valid input and false on invalid input
	 * @throws MyException
	 */
	public boolean validateInput(String[] values) throws Exception{
		boolean isValid = true;
		try{

			// convert coordinate values from string to int
			x = Integer.parseInt(values[0]);
			y = Integer.parseInt(values[1]);

			if(x < 1 || x > size) isValid = false; 
			if(y < 1 || y > size) isValid = false;

			System.out.println("Input Coordinate: X = "+ x +", Y = "+ y);

			if(  x == 2 && y == 2 && size == 3)
				throw new MyException("Invalid inputs: Try other coordinates"); 

		}catch(NumberFormatException e){
			e.printStackTrace();
			e.getMessage();
		}

		return isValid;
	}

	public ArrayList<Coordinates> getNewMoves(Coordinates currentObj, int index) {

		newMoves = new ArrayList<Coordinates>();

		int x = currentObj.getX();
		int y = currentObj.getY();

		// perform the moves that knight can make and update the arraylist
		if( (x+2) < maxSize && (y+1) < maxSize)	
			newMoves.add( new Coordinates( x+2, y+1, index , false) );

		if( (x+2) < maxSize && (y-1) < maxSize && (y-1) > 0) 
			newMoves.add( new Coordinates( x-2, y-1, index, false) );

		if( (x-2) > 0 && (x-2) < maxSize && (y-1) > 0 && (y-1) < maxSize)
			newMoves.add( new Coordinates( x-2, y-1, index, false) );

		if( (x-2) > 0 && (x-2) < maxSize && (y+1) < maxSize)
			newMoves.add( new Coordinates( x-2, y+1, index, false) );

		if( (y+2) < maxSize && (x+1) < maxSize)
			newMoves.add( new Coordinates( x+1, y+2, index, false) );

		if( (y+2) < maxSize && (x-1) > 0 && (x-1) < maxSize)
			newMoves.add( new Coordinates( x-1, y+2, index, false) );

		if( (y-2) > 0 && (y-2) < maxSize && (x+1) < maxSize)
			newMoves.add( new Coordinates( x+1, y-2, index, false) );

		if( (y-2) > 0 && (y-2) < maxSize && (x-1) > 0 && (x-1) < maxSize)
			newMoves.add( new Coordinates( x-1, y-2, index, false) );


		return newMoves;
	}


}
