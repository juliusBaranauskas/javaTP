package lt.vu.persistence;

import lombok.Setter;
import lt.vu.entities.Game;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.transaction.Transactional;
import java.util.List;

@ApplicationScoped
public class GamesDAO {

    @Inject
    @Setter
    private EntityManager em;

    public List<Game> loadAll() {
        return em.createNamedQuery("Game.getAll", Game.class).getResultList();
    }

    public void persist(Game game){
        this.em.persist(game);
    }

    @Transactional
    public Game findOne(Integer id) {
        return em.find(Game.class, id, LockModeType.OPTIMISTIC);
    }

    public Game update(Game game){
        return em.merge(game);
    }
}
