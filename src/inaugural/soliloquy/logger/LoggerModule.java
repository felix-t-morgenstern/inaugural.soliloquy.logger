package inaugural.soliloquy.logger;

import com.google.inject.AbstractModule;

import soliloquy.logger.specs.ILogger;

public class LoggerModule extends AbstractModule {
	private ILogger _logger;
	
	public LoggerModule() {
		_logger = new Logger();
	}

	@Override
	protected void configure() {
		bind(ILogger.class).toInstance(_logger);
	}

}
