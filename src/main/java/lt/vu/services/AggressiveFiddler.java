package lt.vu.services;


import lt.vu.entities.Game;
import lt.vu.entities.Rating;
import lt.vu.persistence.GamesDAO;
import lt.vu.persistence.RatingsDAO;

import javax.enterprise.inject.Alternative;
import javax.enterprise.inject.Default;
import javax.enterprise.inject.Model;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Model
public class AggressiveFiddler implements GameRatingFiddler {

    @Inject
    GamesDAO gamesDAO;

    @Inject
    RatingsDAO ratingsDAO;

    @Transactional
    @Override
    public void increaseBy(int id, int increase) {
        if (increase <= 0 || increase > 5)
            return;

        Game game = gamesDAO.findOne(id);
        int ratingLen = game.getRatings().stream().map(Rating::getScore).toArray().length;
        double avgRating = game.getAverageRating();

        if (avgRating + increase >= 10)
            return;

        int i = 1;
        while (i < 1000) {
            if ((10*i + avgRating*ratingLen)/(ratingLen+i) >= increase + avgRating)
                break;

            i++;
        }

        Rating newRating = new Rating();
        newRating.setGame(game);
        newRating.setComment("none");
        newRating.setScore(10);

        for (int j = 0; j < i; j++) {
            ratingsDAO.persist(newRating);
        }
        // (10x + avgRating*ratingLen)/(ratingLen+x) = increase + avgRating
    }

    @Override
    public void decreaseBy(int id, int decrease) {

    }
}
