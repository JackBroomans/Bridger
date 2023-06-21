package com.bridger.development.model.entity_utility_classes;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:appVar.yml")
@ConfigurationProperties(prefix = "general")
public class UtilityGeneral {

    /* Regulier expressies */
    @Value("${short_date_format}")
    public String SHORT_DATE_FORMAT;

    @Value("${short_date_time_format}")
    public String SHORT_DATE_TIME_FORMAT;

    /* Messages */
    @Value("${age_calculation_not_possible}")
    public String MSG_DATE_BEFORE_CURRENT;
}
