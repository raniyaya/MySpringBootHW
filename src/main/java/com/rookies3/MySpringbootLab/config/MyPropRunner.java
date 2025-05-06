package com.rookies3.MySpringbootLab.config;

import com.rookies3.MySpringbootLab.config.MyPropProperties;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class MyPropRunner implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(MyPropRunner.class);

    @Value("${myprop.username}")
    private String username;

    @Value("${myprop.port}")
    private int port;

    private final MyPropProperties props;

    public MyPropRunner(MyPropProperties props) {
        this.props = props;
    }

    @Override
    public void run(String... args) {
        logger.info("@Value 사용자 이름: {}", username);
        logger.debug("@Value 포트: {}", port);

        logger.info("@ConfigurationProperties 사용자 이름: {}", props.getUsername());
        logger.debug("@ConfigurationProperties 포트: {}", props.getPort());
    }
}