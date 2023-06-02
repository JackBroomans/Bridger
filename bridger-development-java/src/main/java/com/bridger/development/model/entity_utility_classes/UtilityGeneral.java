package com.bridger.development.model.entity_utility_classes;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "general")
public class UtilityGeneral {

    @Value("${short_date_format}")
    private String SHORT_DATE_FORMAT;

    @Value("${short_date_time_format}")
    private String SHORT_DATE_TIME_FORMAT;

    @Value("${age_calculation_not_possible}")
    private static String MSG_DATE_BEFORE_CURRENT;
}
