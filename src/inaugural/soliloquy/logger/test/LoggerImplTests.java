package inaugural.soliloquy.logger.test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import inaugural.soliloquy.logger.LoggerImpl;
import soliloquy.specs.logger.Logger;

import static org.junit.jupiter.api.Assertions.*;

public class LoggerImplTests {
	private LoggerImpl _logger;
	
	private String _logFileLocation;
	
	@BeforeEach
	protected void setUp() throws Exception {
		_logFileLocation = "src/inaugural/soliloquy/logger/test/TestLogFile.txt";
		_logger = new LoggerImpl();
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
			fail(e.getMessage());
		}
		
		assertTrue(lines.size() > 0);
		assertEquals(lines.get(0), "[ERROR] " + timestamp);
		assertEquals(lines.get(1), "Stack Trace: "
				+ IllegalArgumentException.class.getCanonicalName()
				+ ": "
				+ exceptionMessage);
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
			fail(e.getMessage());
		}

		assertEquals(2, lines.size());
		assertEquals(lines.get(0), "[WARNING] " + timestamp + " : " + warningMessage);
		assertEquals("========", lines.get(1));
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
			fail(e.getMessage());
		}

		assertEquals(2, lines.size());
		assertEquals(lines.get(0), "[INFO] " + timestamp + " : " + infoMessage);
		assertEquals("========", lines.get(1));
	}
}
