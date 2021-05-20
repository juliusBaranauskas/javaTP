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
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.OptimisticLockException;
import javax.transaction.Transactional;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import lt.vu.services.GameRatingFiddler;
import org.primefaces.context.RequestContext;

@ViewScoped
@Named
@Getter @Setter
public class GameDetails implements Serializable {

    @Inject
    private GamesDAO gamesDAO;

    @Inject
    private RatingsDAO ratingsDAO;

    @Inject
    private LanguagesDAO languagesDAO;

    @Inject
    private GameRatingFiddler gameRatingFiddler;

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
    @Transactional
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
            RequestContext context = RequestContext.getCurrentInstance();
            context.execute("PF('confirmDialog').show();");
            return "gameDetails?faces-redirect=true&gameId=" + this.game.getId().toString();
        }

        return "gameDetails?faces-redirect=true&" + "gameId=" + this.game.getId().toString() ;
    }

    @LoggedInvocation
    @Transactional(Transactional.TxType.REQUIRES_NEW)
    public void forceAddGame() {
        Game game1 = this.gamesDAO.findOne(this.game.getId());
        game1.setLanguages(this.game.getLanguages());
        game1.setName(this.game.getName());
        game1.setDescription(this.game.getDescription());

        this.gamesDAO.update(game1);
    }

    private void addNewRating() {
        ratingToAdd.setGame(this.game);
        ratingsDAO.persist(ratingToAdd);
    }

    private void loadAllLanguages(){
        this.allLanguages = languagesDAO.loadAll();
    }

    @Transactional
    public String fiddleRatings() {
        gameRatingFiddler.increaseBy(this.game.getId(), 1);
        return "gameDetails?faces-redirect=true&gameId=" + this.game.getId().toString();
    }

}
