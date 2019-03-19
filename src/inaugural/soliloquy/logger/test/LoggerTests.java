package inaugural.soliloquy.logger.test;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import inaugural.soliloquy.logger.Logger;
import soliloquy.logger.specs.ILogger;

public class LoggerTests {
	private ILogger _logger;
	
	private String _logFileLocation;
	
	@BeforeEach
	protected void setUp() throws Exception {
		_logFileLocation = "src/inaugural/soliloquy/logger/test/TestLogFile.txt";
		_logger = new Logger();
		_logger.setLogfileLocation(_logFileLocation);
		Files.deleteIfExists(Paths.get(_logFileLocation));
	}
	
	@AfterEach
	protected void tearDown() throws Exception {
		Files.deleteIfExists(Paths.get(_logFileLocation));
	}
	
	@Test
	public void testLogException() throws IOException {
		String exceptionMessage = "Exception message";
		Exception ex = new IllegalArgumentException(exceptionMessage);
		String timestamp = "This is the timestamp";
		
		_logger.logException(ex, timestamp);
		
		List<String> lines = new ArrayList<String>();
		try (Stream<String> stream = Files.lines(Paths.get(_logFileLocation))) {
			stream.forEach(lines::add);
		} catch (IOException e) {
			assertTrue(false);
		}
		
		assertTrue(lines.size() > 0);
		assertTrue(lines.get(0).equals("[ERROR] " + timestamp));
		assertTrue(lines.get(1).equals("Stack Trace: "
				+ IllegalArgumentException.class.getCanonicalName()
				+ ": "
				+ exceptionMessage));
	}
	
	@Test
	public void testLogWarning() throws IOException {
		String warningMessage = "This is the warning message";
		String timestamp = "This is the timestamp";
		
		_logger.logWarning(warningMessage, timestamp);
		
		List<String> lines = new ArrayList<String>();
		try (Stream<String> stream = Files.lines(Paths.get(_logFileLocation))) {
			stream.forEach(lines::add);
		} catch (IOException e) {
			assertTrue(false);
		}
		
		assertTrue(lines.size() == 2);
		assertTrue(lines.get(0).equals("[WARNING] " + timestamp + " : " + warningMessage));
		assertTrue(lines.get(1).equals("========"));
	}
	
	@Test
	public void testLogInfo() throws IOException {
		String infoMessage = "This is the info message";
		String timestamp = "This is the timestamp";
		
		_logger.logInfo(infoMessage, timestamp);
		
		List<String> lines = new ArrayList<String>();
		try (Stream<String> stream = Files.lines(Paths.get(_logFileLocation))) {
			stream.forEach(lines::add);
		} catch (IOException e) {
			assertTrue(false);
		}
		
		assertTrue(lines.size() == 2);
		assertTrue(lines.get(0).equals("[INFO] " + timestamp + " : " + infoMessage));
		assertTrue(lines.get(1).equals("========"));
	}
}
