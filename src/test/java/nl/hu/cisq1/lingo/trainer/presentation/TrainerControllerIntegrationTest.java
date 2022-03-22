package nl.hu.cisq1.lingo.trainer.presentation;

import nl.hu.cisq1.lingo.CiTestConfiguration;
import nl.hu.cisq1.lingo.trainer.data.GameRepository;
import nl.hu.cisq1.lingo.trainer.domain.Game;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import javax.print.attribute.standard.Media;
import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Import(CiTestConfiguration.class)
@ActiveProfiles("ci")
public class TrainerControllerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;
    private Game game;

    @Autowired
    GameRepository gameRepository;

    @BeforeEach
    void setup(){
        game = new Game();
        game.startNewGame("woord");
        gameRepository.save(game);
    }

    @Test
    @DisplayName("Start new game 200 OK")
    void startNewGame() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/lingo/game/start");
        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.gameId").isNumber())
                .andExpect(jsonPath("$.gameStatus").value("ACTIVE"))
                .andExpect(jsonPath("$.score").value(0))
                .andExpect(jsonPath("$.hint").isArray())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    @DisplayName("Make a guess. 200 ok")
    void makeGuess() throws Exception {
        String guess = "pizza";

        long gameId = game.getId();
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/lingo/game/" + gameId +  "/guess/" + guess)
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk());
    }
}
