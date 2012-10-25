package nl.tjonahen.duiken.deco;

import java.io.Serializable;

/**
 *
 * Configuration holder
 */
public class Config implements Serializable {
    private boolean includeDeepStop;
    
    public Config() {
    }

    public boolean isIncludeDeepStop() {
        return includeDeepStop;
    }

    public void setIncludeDeepStop(final boolean includeDeepStop) {
        this.includeDeepStop = includeDeepStop;
    }
    
    
}
