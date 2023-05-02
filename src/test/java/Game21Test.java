import org.example.Game21;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Game21Test {

    @Test
    public void testSamBlackjack() {
        Game21 game = new Game21(Paths.get("src", "main", "resources", "testSamBlackjack.txt").toAbsolutePath().toString());

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        game.play();

        String expectedOutput = """
                sam
                sam: C10, HA
                dealer: D9, D2
                """;
        assertEquals(expectedOutput, outputStream.toString().replace("\r", ""));
    }

    @Test
    public void testDealerWins() {
        Game21 game = new Game21(Paths.get("src", "main", "resources", "testDealerWins.txt").toAbsolutePath().toString());

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        game.play();

        String expectedOutput = """
                dealer
                sam: C10, H8
                dealer: D10, D6, S3
                """;
        assertEquals(expectedOutput, outputStream.toString().replace("\r", ""));
    }

    @Test
    public void testSamWins() {
        Game21 game = new Game21(Paths.get("src", "main", "resources", "testSamWins.txt").toAbsolutePath().toString());

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        game.play();

        String expectedOutput = """
                sam
                sam: C9, D10
                dealer: H6, D2, S4, SA
                """;
        assertEquals(expectedOutput, outputStream.toString().replace("\r", ""));
    }

}
