package com.skylink;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class SkyLinkServerTest {

    @Test
    public void testServerClassLoads() {
        assertDoesNotThrow(() -> Class.forName("com.skylink.SkyLinkServer"));
    }

    @Test
    public void testServerHasMainMethod() throws Exception {
        java.lang.reflect.Method main = SkyLinkServer.class.getMethod("main", String[].class);
        assertNotNull(main);
    }
}
