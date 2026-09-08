package com.oranbyte.ecom.util;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

/**
 * Service class for handling language-related properties.
 */
@Service
public class Language {

    @Autowired
    private Environment env;
    
    /**
     * Retrieves the value of a property from the environment.
     *
     * @param property The name of the property to retrieve.
     * @return The value of the specified property.
     */
    public String getValue(String property) {
        String envProperty = env.getProperty(property);
        
        if (envProperty == null || (!envProperty.contains("{") && !envProperty.contains("}"))) {
            return envProperty;
        }
        
        List<String> appProperties = new ArrayList<>(); 
        String propertyString = envProperty;
        
        while (propertyString.contains("{") && propertyString.contains("}")) {
            int startIndex = propertyString.indexOf("{");
            int endIndex = propertyString.indexOf("}", startIndex) + 1;
            String placeholder = propertyString.substring(startIndex, endIndex);
            appProperties.add(placeholder);
            propertyString = propertyString.substring(endIndex);
        }
        
        for (String key : appProperties) {
            String cleanKey = key.substring(1, key.length() - 1);
            String replacementValue = env.getProperty(cleanKey);
            if (replacementValue != null) {
                envProperty = envProperty.replace(key, replacementValue);
            } else {
                envProperty = envProperty.replace(key, "");
            }
        }
        
        return envProperty;
    }
}
