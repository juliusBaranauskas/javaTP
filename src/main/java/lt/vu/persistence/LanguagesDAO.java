package lt.vu.persistence;

import lt.vu.entities.Language;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;

@ApplicationScoped
public class LanguagesDAO {

    @Inject
    private EntityManager em;

    public List<Language> loadAll() {
        return em.createNamedQuery("Language.getAll", Language.class).getResultList();
    }

    public void persist(Language language){
        this.em.persist(language);
    }

    public Language findOne(Integer id) {
        return em.find(Language.class, id);
    }

    public Language update(Language language){
        return em.merge(language);
    }
}
