package nl.hu.cisq1.lingo;

import nl.hu.cisq1.lingo.trainer.data.GameRepository;
import nl.hu.cisq1.lingo.words.WordTestDataFixtures;
import nl.hu.cisq1.lingo.words.data.WordRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

@Profile("ci")
@TestConfiguration
public class CiTestConfiguration {
    @Bean
    CommandLineRunner importWords(WordRepository repository){
        return new WordTestDataFixtures(repository);
    }
}
