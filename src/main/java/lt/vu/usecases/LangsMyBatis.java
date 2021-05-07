package lt.vu.usecases;

import lombok.Getter;
import lombok.Setter;
import lt.vu.mybatis.dao.LanguageMapper;
import lt.vu.mybatis.model.Language;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;

@Model
public class LangsMyBatis {

    @Inject
    private LanguageMapper languageMapper;

    @Getter
    private List<Language> allLanguages;

    @Getter @Setter
    private Language languageToAdd = new Language();

    @PostConstruct
    public void init() {
        this.loadAllLangs();
    }

    private void loadAllLangs() {
        this.allLanguages = languageMapper.findAll();
    }

    @Transactional
    public String createLanguage() {
        languageMapper.insert(languageToAdd);
        return "/myBatis/languages?faces-redirect=true";
    }
}
