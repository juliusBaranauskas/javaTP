package lt.vu.usecases;

import lombok.Getter;
import lombok.Setter;
import lt.vu.entities.Game;
import lt.vu.entities.Language;
import lt.vu.entities.Rating;
import lt.vu.interceptors.LoggedInvocation;
import lt.vu.persistence.GamesDAO;
import lt.vu.persistence.LanguagesDAO;
import lt.vu.persistence.RatingsDAO;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.persistence.OptimisticLockException;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Model
@Transactional
public class GameDetails {

    @Inject
    private GamesDAO gamesDAO;

    @Inject
    private RatingsDAO ratingsDAO;

    @Inject
    private LanguagesDAO languagesDAO;

    @Getter
    @Setter
    private Game game;

    @Getter
    @Setter
    private Game gameDetails = new Game();

    @Getter
    @Setter
    private Rating ratingToAdd = new Rating();

    @Getter
    private double averageRating;

    @Getter
    private List<Language> allLanguages;

    @Setter
    @Getter
    private List<Integer> selectedLanguages;

    @PostConstruct
    public void init() {
        Map<String, String> requestParameters =
                FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        Integer gameId = Integer.parseInt(requestParameters.get("gameId"));
        this.game = gamesDAO.findOne(gameId);
        loadAverage();
        loadAllLanguages();
    }

    @Transactional
    public void loadAverage() {
        this.averageRating = this.game.getAverageRating() == Double.NaN
                    ? 0
                    : this.game.getAverageRating();
    }

    @Transactional()
    public String editGame() {

        addNewRating();

        try {
            this.game.setDescription(gameDetails.getDescription());
            this.game.setName(gameDetails.getName());

            if (!selectedLanguages.isEmpty()) {
                List<Language> langs = new ArrayList<>();
                for (Integer langID: selectedLanguages) {
                    langs.add(languagesDAO.findOne(langID));
                }
                this.game.setLanguages(langs);
            }

            this.gamesDAO.update(this.game);

        } catch (OptimisticLockException e) {
            return "gameDetails?faces-redirect=true&gameId=" + this.game.getId().toString() + "&error=optimistic-lock-exception";
        }

        return "gameDetails?faces-redirect=true&" + "gameId=" + this.game.getId().toString() ;
    }

    private void addNewRating() {
        ratingToAdd.setGame(this.game);
        ratingsDAO.persist(ratingToAdd);
    }

    private void loadAllLanguages(){
        this.allLanguages = languagesDAO.loadAll();
    }
}
