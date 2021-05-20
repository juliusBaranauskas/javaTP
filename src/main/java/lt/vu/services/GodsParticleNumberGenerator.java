package lt.vu.services;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.io.Serializable;
import java.util.Random;

@ApplicationScoped
public class GodsParticleNumberGenerator implements Serializable {

    @Inject
    UselessMath uselessMath;

    public Integer generateNumber() {
        try {
            Thread.sleep(3000); // Simulate intensive work
        } catch (InterruptedException e) {
            System.out.println("WHOOPSIE HAPPENED: " + e.getMessage());
        }
        Integer generatedNumber = new Random().nextInt(200);
        uselessMath.penalisePoints(19.0);
        return generatedNumber;
    }
}