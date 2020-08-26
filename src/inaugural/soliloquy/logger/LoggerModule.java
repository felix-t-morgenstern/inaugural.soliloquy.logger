package inaugural.soliloquy.logger;

import com.google.inject.AbstractModule;

import soliloquy.specs.logger.Logger;

public class LoggerModule extends AbstractModule {
	private Logger _logger;
	
	public LoggerModule() {
		_logger = new LoggerImpl();
	}

	@Override
	protected void configure() {
		bind(Logger.class).toInstance(_logger);
	}

}
