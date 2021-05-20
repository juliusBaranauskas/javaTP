package lt.vu.services;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Model;

@Model
@ApplicationScoped
public class UselessMath {

    public double penalisePoints(double pointReceived) {
        if (pointReceived > 15.0) {
            pointReceived *= 0.93;
        }

        return pointReceived;
    }
}
