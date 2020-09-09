package up.visulog.config;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Configuration {

    private final Path gitPath;
    private final Map<String, PluginConfig> plugins;

    public Configuration(Path gitPath, Map<String, PluginConfig> plugins) {
        this.gitPath = gitPath;
        this.plugins = Map.copyOf(plugins);
    }

    public Path getGitPath() {
        return gitPath;
    }

    public Map<String, PluginConfig> getPluginConfigs() {
        return plugins;
    }
}
