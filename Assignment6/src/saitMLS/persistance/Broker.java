package saitMLS.persistance;

import java.io.IOException;
import java.util.List;

public interface Broker {
		
		public void closeBroker() throws IOException;
		
		public boolean persist(Object o);
		
		public boolean remove(Object o);
		
		public List search(Object o);

}
