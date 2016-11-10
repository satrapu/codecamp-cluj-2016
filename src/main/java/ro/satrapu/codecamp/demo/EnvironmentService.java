package ro.satrapu.codecamp.demo;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;

/**
 * Provides access to environment variables.
 */
@Singleton
public class EnvironmentService {
  private static final String ENV_PIPELINE_STAGE = "PIPELINE_STAGE";
  private String pipelineStage;

  @PostConstruct
  public void init() {
    pipelineStage = System.getenv(ENV_PIPELINE_STAGE);
  }

  /**
   * Gets the value of the {@value EnvironmentService#ENV_PIPELINE_STAGE} environment variable.
   */
  public String getPipelineStage() {
    return pipelineStage;
  }
}
