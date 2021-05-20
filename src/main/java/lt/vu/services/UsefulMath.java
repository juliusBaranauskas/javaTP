package lt.vu.services;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Model;
import javax.enterprise.inject.Specializes;

@Specializes
@Model
@ApplicationScoped
public class UsefulMath extends UselessMath {

    @Override
    public double penalisePoints(double pointReceived) {
        if (pointReceived > 20.0) {
            pointReceived *= 0.93;
        }

        return pointReceived;
    }
}
