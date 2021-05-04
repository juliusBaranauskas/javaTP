package lt.vu.usecases;

import lombok.Getter;
import lombok.Setter;
import lt.vu.entities.Game;
import lt.vu.entities.Rating;
import lt.vu.interceptors.LoggedInvocation;
import lt.vu.persistence.GamesDAO;
import lt.vu.persistence.RatingsDAO;
import org.mybatis.cdi.Transactional;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.persistence.OptimisticLockException;
import java.util.Map;

@Model
public class GameDetails {

    @Inject
    private GamesDAO gamesDAO;

    @Inject
    private RatingsDAO ratingsDAO;

    @Getter
    @Setter
    private Game game;

    @Getter
    @Setter
    private Integer newRatingScore;

    @Getter
    private double averageRating;

    @PostConstruct
    public void init() {
        Map<String, String> requestParameters =
                FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        Integer gameId = Integer.parseInt(requestParameters.get("gameId"));
        this.game = gamesDAO.findOne(gameId);
        loadAverage();
    }

    public void loadAverage() {
        this.averageRating = this.game.getAverageRating();
    }

    @Transactional
    @LoggedInvocation
    public String editGame() {
        try {
            Rating newRating = new Rating(this.newRatingScore, this.game);
            ratingsDAO.persist(newRating);

            Game gm = this.gamesDAO.update(this.game);
        } catch (OptimisticLockException e) {
            return "gameDetails?faces-redirect=true&gameId=" + this.game.getId().toString() + "&error=optimistic-lock-exception";
        }
        return "gameDetails?gameId=" + this.game.getId().toString() + "&faces-redirect=true";
    }
}
