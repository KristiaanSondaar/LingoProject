package nl.hu.cisq1.lingo.trainer.presentation;


import nl.hu.cisq1.lingo.trainer.application.TrainerService;
import nl.hu.cisq1.lingo.trainer.domain.GameProgress;
import nl.hu.cisq1.lingo.trainer.domain.exception.CannotStartNewRoundException;
import nl.hu.cisq1.lingo.trainer.domain.exception.GameNotFoundException;
import nl.hu.cisq1.lingo.trainer.domain.exception.TurnExeption;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/lingo")
public class TrainerController {
    private TrainerService trainerService;

    public TrainerController(TrainerService trainerService){
        this.trainerService = trainerService;
    }


    @PostMapping("/game/start")
    public GameProgress startGame(){
        return  trainerService.startNewGame();
    }


    @PostMapping("/game/{id}/guess/{attempt}")
    public GameProgress makeGuess(@PathVariable long id, @PathVariable String attempt){
        try {
            return trainerService.makeGuess(attempt, id);
        }catch (TurnExeption | GameNotFoundException exception){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, exception.getMessage());
        }
    }

    @PostMapping("/game/{id}/round")
    public GameProgress newRound(@PathVariable long id){
        try{
            return trainerService.startNewRound(id);
        } catch(CannotStartNewRoundException | GameNotFoundException exception){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, exception.getMessage());
        }
    }
}
