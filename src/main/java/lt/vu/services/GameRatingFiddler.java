package lt.vu.services;

import lt.vu.persistence.GamesDAO;

import javax.inject.Inject;

public interface GameRatingFiddler {

    void increaseBy(int id, int increase);
    void decreaseBy(int id, int decrease);
}
