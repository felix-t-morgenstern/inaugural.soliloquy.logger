package inaugural.soliloquy.logger;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.MessageFormat;

import soliloquy.logger.specs.ILogger;

public class Logger implements ILogger {
	private File _file;

	@Override
	public void setLogfileLocation(String logLocation) throws IOException {
		_file = new File(logLocation);
		_file.createNewFile();
	}

	@Override
	public String getInterfaceName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void logException(Exception e, String timestamp) throws IOException {
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		e.printStackTrace(pw);
		String stackTrace = sw.toString();
		Object[] params = new Object[]{timestamp, stackTrace};
		printFormattedMessage(params, "[ERROR] {0}\r\nStack Trace: {1}\r\n========");
	}

	@Override
	public void logWarning(String warning, String timestamp) throws IOException {
		Object[] params = new Object[] {timestamp, warning};
		String messageFormat = "[WARNING] {0} : {1}\r\n========";
		printFormattedMessage(params, messageFormat);
	}

	@Override
	public void logInfo(String info, String timestamp) throws IOException {
		Object[] params = new Object[] {timestamp, info};
		String messageFormat = "[INFO] {0} : {1}\r\n========";
		printFormattedMessage(params, messageFormat);
	}
	
	private void printFormattedMessage(Object[] params, String messageFormat) throws IOException
	{
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(_file)));
		String logMessage = MessageFormat.format(messageFormat, params);
		bw.write(logMessage);
		bw.close();
	}

}
