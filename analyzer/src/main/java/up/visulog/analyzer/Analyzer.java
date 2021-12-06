package up.visulog.analyzer;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import up.visulog.config.Configuration;
import up.visulog.config.PluginConfig;

public class Analyzer {
    private final Configuration config;

    private AnalyzerResult result;

    public Analyzer(Configuration config) {
        this.config = config;
    }

    public AnalyzerResult computeResults() {
        List<AnalyzerPlugin> plugins = new ArrayList<>();
        for (var pluginConfigEntry: config.getPluginConfigs().entrySet()) {
            var pluginName = pluginConfigEntry.getKey();
            var pluginConfig = pluginConfigEntry.getValue();
            var plugin = makePlugin(pluginName, pluginConfig);
            plugin.ifPresent(plugins::add);
        }
        // run all the plugins
        // TODO: try running them in parallel
        for (var plugin: plugins) plugin.run();

        // store the results together in an AnalyzerResult instance and return it
        return new AnalyzerResult(plugins.stream().map(AnalyzerPlugin::getResult).collect(Collectors.toList()));
    }

    // TODO: find a way so that the list of plugins is not hardcoded in this factory
    private Optional<AnalyzerPlugin> makePlugin(String pluginName, PluginConfig pluginConfig) {
        switch (pluginName) {
            case "countCommits" : return Optional.of(new CountCommitsPerAuthorPlugin(config));
            case "countCommitsPerWeekday": return Optional.of(new CountCommitsPerWeekdayPlugin(config));
            case "countMergeCommits": return Optional.of(new CountMergeCommitsPlugin(config));
            case "countMergePerAuthor": return Optional.of(new CountMergePerAuthorPlugin(config));
            case "countMergesBetweenDays": return Optional.of(new CountMergesBetweenDaysPlugin(config, pluginConfig.getStartDate(), pluginConfig.getEndDate()));
            case "countLinesChanged": return Optional.of(new CountLinesChangedPlugin(config));
            case "countCommitOnOneDay" : return Optional.of(new CountCommitOnOneDay(config, pluginConfig.getDate()));
            case "countCommitsBetweenDays" : return Optional.of(new CountCommitsBetweenDays(config, pluginConfig.getStartDate(), pluginConfig.getEndDate()));
            case "countCommitLinesChanged" : return Optional.of(new CountCommitLinesChanged(config));
            case "countCommitLinesChangedOnOneDay" : return Optional.of(new CountCommitLinesChangedOnOneDay(config, pluginConfig.getDate()));
            case "countCommitLinesChangedBetweenDays" : return Optional.of(new CountCommitLinesChangedBetweenDays(config, pluginConfig.getStartDate(), pluginConfig.getEndDate()));
            default : return Optional.empty();
        }

    }

}
