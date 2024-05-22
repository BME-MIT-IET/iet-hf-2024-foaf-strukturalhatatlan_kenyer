package drukmakor;

import static org.junit.jupiter.api.Assertions.assertSame;

import java.awt.Point;
import java.io.Console;
import java.util.regex.Pattern;

import javax.swing.JFrame;
import org.assertj.swing.annotation.GUITest;


import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.assertj.swing.core.BasicRobot;
import org.assertj.swing.core.MouseButton;
import org.assertj.swing.core.Robot;
import org.assertj.swing.core.matcher.JButtonMatcher;
import org.assertj.swing.core.matcher.JLabelMatcher;
import org.assertj.swing.edt.GuiActionRunner;
import org.assertj.swing.fixture.FrameFixture;
import org.assertj.swing.timing.Pause;



class GuiTest {
    FrameFixture window;
    @BeforeEach
    public void setUp() {
        // Get the working directory
       // String workingDir = System.getProperty("user.dir");
        
        // Print the working directory
       // System.out.println("Working Directory = " + workingDir);
        //assertSame(workingDir, "workingDir");

        Robot robot = BasicRobot.robotWithNewAwtHierarchy();
        JFrame frame = GuiActionRunner.execute(() -> {
            try {
                TitleFrame tf = new TitleFrame();
                return TitleFrame.window;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        });
        window = new FrameFixture(robot, frame);
        window.show(); // Shows the frame to test
        window.moveTo(new Point(0, 0));
        window.resizeHeightTo(720);
        window.resizeWidthTo(1280);
    }

    @AfterEach
    public void tearDown() {
        window.cleanUp();
    }

    @Test
    @GUITest
    public void shouldHaveCorrectTitle() {
        window.requireTitle("DrukmakorTitle"); // Change to the expected title of the frame
    }
    
    @Test
    @GUITest
    public void shouldAddSaboteur() {
        //window.button(JButtonMatcher.withText("Add Player")).
        window.button(JButtonMatcher.withText("Add Player")).click();
        //Pause.pause(1000);
        window.button(JButtonMatcher.withText("Saboteur")).click();
        window.label(JLabelMatcher.withText(Pattern.compile("Saboteurs: .* players"))).requireText("Saboteurs: 1 players");
    }
    
    @Test
    @GUITest
    public void shouldAddSaboteursAndMechanic() {
        window.button(JButtonMatcher.withText("Add Player")).click();
        window.button(JButtonMatcher.withText("Saboteur")).click();
        window.button(JButtonMatcher.withText("Add Player")).click();
        window.button(JButtonMatcher.withText("Saboteur")).click();
        window.button(JButtonMatcher.withText("Add Player")).click();
        window.button(JButtonMatcher.withText("Saboteur")).click();
        window.button(JButtonMatcher.withText("Add Player")).click();
        window.button(JButtonMatcher.withText("Mechanic")).click();
        window.label(JLabelMatcher.withText(Pattern.compile("Saboteurs: .* players"))).requireText("Saboteurs: 3 players");
        window.label(JLabelMatcher.withText(Pattern.compile("Mechanics: .* players"))).requireText("Mechanics: 1 players");
    }

    @Test
    @GUITest
    public void shouldStartTheGame(){
        window.button(JButtonMatcher.withText("Add Player")).click();
        window.button(JButtonMatcher.withText("Saboteur")).click();
        window.button(JButtonMatcher.withText("Add Player")).click();
        window.button(JButtonMatcher.withText("Mechanic")).click();
        window.button(JButtonMatcher.withText("Start Game")).click();
        window.requireTitle("DrukmakorTitle");
    }
}
