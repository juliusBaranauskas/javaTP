package lt.vu.usecases;

import lt.vu.interceptors.LoggedInvocation;
import lt.vu.services.GodsParticleNumberGenerator;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@SessionScoped
@Named
public class GenerateGodParticleNumber implements Serializable {

    @Inject
    GodsParticleNumberGenerator godsParticleNumberGenerator;

    private CompletableFuture<Integer> godParticleGenTask = null;

    @LoggedInvocation
    public String generateGodParticleNumber() {

        godParticleGenTask = CompletableFuture.supplyAsync(() -> godsParticleNumberGenerator.generateNumber());
        return "/languageCreation.xhtml?faces-redirect=true";
    }

    public boolean isGenerationRunning() {
        return godParticleGenTask != null && !godParticleGenTask.isDone();
    }

    public String getGenerationStatus() throws ExecutionException, InterruptedException {
        if (godParticleGenTask == null) {
            return null;
        } else if (isGenerationRunning()) {
            return "God particle generation is in progress";
        }
        return "God particle number is: " + godParticleGenTask.get();
    }
}
