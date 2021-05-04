package lt.vu.entities;

import lombok.Getter;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "LANGUAGE")
public class Language {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Getter
    private String name;

    private String langCode;

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
