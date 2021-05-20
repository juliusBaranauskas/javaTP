package lt.vu.usecases;

import lombok.Getter;
import lombok.Setter;
import lt.vu.entities.Language;
import lt.vu.interceptors.LoggedInvocation;
import lt.vu.persistence.LanguagesDAO;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.io.Serializable;
import java.util.List;

@Model
public class LanguageCreation implements Serializable {

    @Inject
    private LanguagesDAO languagesDAO;

    @Getter
    private List<Language> allLanguages;

    @Getter
    @Setter
    private Language newLanguage = new Language();

    @PostConstruct
    public void init(){
        loadAllLanguages();
    }

    @LoggedInvocation
    @Transactional
    public String addLanguage(){
        this.languagesDAO.persist(newLanguage);
        return "languageCreation?faces-redirect=true";
    }

    private void loadAllLanguages(){
        this.allLanguages = languagesDAO.loadAll();
    }
}
