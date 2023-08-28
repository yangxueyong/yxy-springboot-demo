package com.secbro.drools.utils;

import org.kie.api.io.ResourceType;
import org.kie.internal.utils.KieHelper;

public class ExKieHelper extends KieHelper {
    public ExKieHelper() {
        super();
    }

    public String getPath(String name, ResourceType type) {
        return "src/main/resources/" + name + "." + type.getDefaultExtension();
    }
}