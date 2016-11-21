import java.util.ArrayList;

public class WriteBuffer {
	
	private boolean tso;
	private ArrayList<MixedBuffer> buffer = new  ArrayList<MixedBuffer>();
	
    static MixedBuffer mx = new MixedBuffer("touchdicks", 3);
	
	public WriteBuffer(boolean tso) {
		
		this.tso = tso;
	}
	
	public MixedBuffer pop()
	{
			
		return buffer.remove(0);	
	}
	
	public boolean isEmpty()
	{
		return buffer.isEmpty();
		
	}
	public int load(String x) throws Exception {
		
		int loadedElement;
		
		if (buffer.isEmpty()) {
			return -1;
		}
		
			for (int n = 0; n < buffer.size(); n++) {
				
				if(buffer.get(n).variable == x) {
					
					loadedElement = buffer.get(n).value;
					buffer.remove(n);
					
					return loadedElement;
				}

			}

			//Variable doesn't exist in buffer
			return -1;
		
		
	}
	
	public void store(String x, int v) {
		
		//Object representing variable and its associated value
		MixedBuffer value = new MixedBuffer(x, v);
		
		if (buffer.isEmpty()) {
			buffer.add(value);
		}

		else {
			
			//If tso add using FIFO
			if (tso) {
				
				buffer.add(value);	
			}
			
			//Otherwise we add using FIFO per variable basis
			else {
				boolean inBuffer = false;
				
				for (int i = 0; i < buffer.size(); i++) {
					
					if (buffer.get(i).variable == x) {
						inBuffer = true;
						break; //If in buffer, break out of loop
					}
				}
				
				if (inBuffer) {
					buffer.add(value);
				}
				
				else {
					buffer.add(0, value);
				}
			}
		}
	}
	
	
}
