package nl.hu.cisq1.lingo.trainer.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class GameProgress extends AbstractEntity {
    private int score;
    @ElementCollection
    private List<String> hint;
    private GameStatus gameStatus;
    @OneToMany
    private List<Feedback> feedbackList;


}
