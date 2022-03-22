package nl.hu.cisq1.lingo.words;

import nl.hu.cisq1.lingo.words.data.WordRepository;
import nl.hu.cisq1.lingo.words.domain.Word;
import org.springframework.boot.CommandLineRunner;


public class WordTestDataFixtures implements CommandLineRunner {
    private final WordRepository repository;


    public WordTestDataFixtures(WordRepository repository) {
        this.repository = repository;
    }

    @Override
    public void run(String... args) throws Exception {
        this.repository.save(new Word("pizza"));
        this.repository.save(new Word("oranje"));
        this.repository.save(new Word("wanorde"));
    }
}
