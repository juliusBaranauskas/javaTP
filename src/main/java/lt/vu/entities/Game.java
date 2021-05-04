package lt.vu.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@NamedQueries({
        @NamedQuery(name = "Game.getAll", query = "select g from Game as g")
})
@Table(name = "GAME")
@Getter
@Setter
public class Game implements Serializable {

    public Game() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "GAME_NAME", nullable = false)
    private String name;

    @ManyToMany
    @Getter
    private List<Language> languages = new ArrayList<>();

    private String description;

    @OneToMany(mappedBy = "game")
    private List<Rating> ratings = new ArrayList<>();

    public double getAverageRating() {  return ((double) ratings.stream().map(Rating::getScore).reduce(0, Integer::sum)) / ratings.size(); }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Game game = (Game) o;
        return Objects.equals(name, game.name);
    }

    @Override
    public int hashCode() {

        return Objects.hash(name);
    }
}
