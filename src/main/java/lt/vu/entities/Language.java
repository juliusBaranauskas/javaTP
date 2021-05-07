package lt.vu.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@NamedQueries({
        @NamedQuery(name = "Language.getAll", query = "select l from Language as l")
})
@Table(name = "LANGUAGE")
public class Language {

    @Id
    @Getter
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Getter
    @Setter
    @Column(unique = true)
    private String name;

    @Getter
    @Setter
    @Column(name = "lang_code", unique = true)
    private String langCode;

    @Getter
    @ManyToMany(mappedBy = "languages")
    private List<Game> gamesSupporting;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Language language = (Language) o;
        return Objects.equals(name, language.name);
    }

    @Override
    public int hashCode() {

        return Objects.hash(name);
    }
}
