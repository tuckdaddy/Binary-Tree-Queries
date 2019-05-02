package readers;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;

public class LineFileReader implements Iterator<String> {
		private final BufferedReader reader;
		private String nextLine;
		public LineFileReader(String filename) throws IOException {
				reader = new BufferedReader(new FileReader(filename));
				nextLine = reader.readLine();
		}

		@Override
		public boolean hasNext() {
			return nextLine != null;
		}

		@Override
		public String next() {
			String r = nextLine;
			try {
			nextLine = reader.readLine();
			} catch (IOException ex) {
				throw new RuntimeException(ex);
			}
			return r;
		}
}
