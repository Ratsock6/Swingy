package fr.aallouv;

import fr.aallouv.enums.GameViews;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

/**
 * Unit tests for App
 */
public class AppTest {
    @Test
    public void testAppExists() throws IOException {
        assertNotNull(new App(GameViews.CONSOLE));
    }
}
