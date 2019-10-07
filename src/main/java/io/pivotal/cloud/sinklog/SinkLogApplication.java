package io.pivotal.cloud.sinklog;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource("classpath:/sink-log.properties")
@EnableConfigurationProperties(SinkLogProperties.class)
@EnableBinding(Sink.class)
public class SinkLogApplication {

	private final Logger log = LoggerFactory.getLogger(SinkLogApplication.class);

	// Log configuration properties (prefix and suffix)
	private final SinkLogProperties props;

	/**
	 * Automatically called by Spring (no need for <tt>@Autowired</tt>).
	 *
	 * @param props
	 *            Logging configuration properties.
	 */
	public SinkLogApplication(SinkLogProperties props) {
		this.props = props;
	}

	/**
	 * Log every message received, adding the prefix and suffix to each message.
	 */
	@StreamListener(Sink.INPUT)
	public void sinkLog(String message) {
		log.info(props.getPrefix() + message + props.getSuffix());
	}

	/**
	 * Run this application as a Spring Boot application.
	 */
	public static void main(String[] args) {
		SpringApplication.run(SinkLogApplication.class, args);
	}

}
