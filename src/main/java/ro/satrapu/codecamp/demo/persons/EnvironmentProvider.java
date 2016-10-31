package ro.satrapu.codecamp.demo.persons;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;

/**
 * Provides access to environment variables.
 */
@Singleton
public class EnvironmentProvider {
    private static final String ENV_PIPELINE_STAGE = "PIPELINE_STAGE";
    private String pipelineStage;

    @PostConstruct
    public void init() {
        pipelineStage = System.getenv(ENV_PIPELINE_STAGE);
    }

    /**
     * Gets the value of the {@value EnvironmentProvider#ENV_PIPELINE_STAGE} environment variable.
     *
     * @return
     */
    public String getPipelineStage() {
        return pipelineStage;
    }
}
