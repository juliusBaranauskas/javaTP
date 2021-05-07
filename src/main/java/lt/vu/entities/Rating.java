package lt.vu.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "RATING")
@NamedQueries({
        @NamedQuery(name = "Rating.getByGame", query = "select r from Rating as r WHERE r.game.id = :gameId")
})
public class Rating {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Getter
    @Setter
    private Integer score;

    @Getter
    @Setter
    private String comment;

    @ManyToOne
    @JoinColumn(name = "game_id", referencedColumnName = "id")
    @Setter
    @Getter
    private Game game;

    public Rating() {
    }

    public Rating(Game game) {
        this.game = game;
    }

    public Rating(Integer score, Game game) {
        this.score = score;
        this.game = game;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Rating rating = (Rating) o;
        return Objects.equals(id, rating.id);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id);
    }
}
