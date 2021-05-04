package lt.vu.usecases;

import lombok.Getter;
import lombok.Setter;
import lt.vu.entities.Game;
import lt.vu.persistence.GamesDAO;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;

@Model
public class Games {

    @Inject
    private GamesDAO gamesDAO;

    @Getter
    private List<Game> allGames;

    @Getter
    @Setter
    private Game gameToAdd = new Game();

    @PostConstruct
    public void init(){
        loadAllGames();
    }

    @Transactional
    public String addGame(){
        this.gamesDAO.persist(gameToAdd);
        return "index?faces-redirect=true";
    }

    private void loadAllGames(){
        this.allGames = gamesDAO.loadAll();
    }
}
