package com.mycompany.a3;

import java.util.Vector;

public class GameObjectCollection implements ICollection{

	private Vector<GameObject> theCollection;							// vector for objects
	
	public GameObjectCollection() {
		theCollection = new Vector<GameObject>();
	}
	
	@Override
	public void add(GameObject newObject) {
		theCollection.addElement(newObject);
		
	}

	@Override
	public IIterator getIterator() {
		
		return new GameVectorIterator();
	}

	private class GameVectorIterator implements IIterator{
		private int currElementIndex;
		
		public GameVectorIterator() {
			currElementIndex = -1;
		}

		@Override
		public boolean hasNext() {
			if (theCollection.size() <= 0)
				return false;
			if (currElementIndex == theCollection.size()-1)
				return false;
			return true;
		}

		@Override
		public Object getNext() {
			currElementIndex++;
			return theCollection.elementAt(currElementIndex);
		}
	}	// end of iterator class
}	// end of collection class

