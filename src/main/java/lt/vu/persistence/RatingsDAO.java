package lt.vu.persistence;

import lt.vu.entities.Game;
import lt.vu.entities.Rating;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

@ApplicationScoped
public class RatingsDAO {

    @Inject
    private EntityManager em;

    public List<Rating> loadByGame(Game game) {
        Query query = em.createNamedQuery("Rating.getByGame", Rating.class);
        query.setParameter("gameId", game.getId());
        return query.getResultList();
    }

    public void setEm(EntityManager em) {
        this.em = em;
    }

    public void persist(Rating rating){
        this.em.persist(rating);
    }

    public Rating findOne(Integer id) {
        return em.find(Rating.class, id);
    }
}
