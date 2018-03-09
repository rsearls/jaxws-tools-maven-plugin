package org.jboss.ws.plugins.tools;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Util {
	
	public static int getJVMMajorVersion() {
        try {
            String vmVersionStr = System.getProperty("java.specification.version", null);
            Matcher matcher = Pattern.compile("^(?:1\\.)?(\\d+)$").matcher(vmVersionStr); //match 1.<number> or <number>
            if (matcher.find()) {
                return Integer.valueOf(matcher.group(1));
            } else {
                throw new RuntimeException("Unknown version of jvm " + vmVersionStr);
            }
        } catch (Exception e) {
            return 8;
        }
   }
}
