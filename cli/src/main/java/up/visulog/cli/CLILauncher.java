package up.visulog.cli;

import java.nio.file.FileSystems;
import java.util.HashMap;
import java.util.Optional;

import up.visulog.analyzer.Analyzer;
import up.visulog.config.Configuration;
import up.visulog.config.PluginConfig;
import up.visulog.graphs.ChartCountCommitsPerAuthor;
import up.visulog.graphs.PrintChart;
import up.visulog.gui.Window;

public class CLILauncher {

    public static void main(String[] args) {
    	// Pour tester l'interface graphique : ./gradlew run --args=". --test=window"
    	// Pour tester l'affichage d'un graphique : ./gradlew run --args=". --test=graph"
    	
        var config = makeConfigFromCommandLineArgs(args);
        
        if (config.isPresent()) {
            var analyzer = new Analyzer(config.get());
            var results = analyzer.computeResults();
            System.out.println(results.toHTML());
        } else displayHelpAndExit();
    }

    static Optional<Configuration> makeConfigFromCommandLineArgs(String[] args) {
        var gitPath = FileSystems.getDefault().getPath(".");
        var plugins = new HashMap<String, PluginConfig>();
        for (var arg : args) {
            if (arg.startsWith("--")) {
                String[] parts = arg.split("=");
                if (parts.length != 2) return Optional.empty();
                else {
                    String pName = parts[0];
                    String pValue = parts[1];
                    switch (pName) {
                        case "--addPlugin":
                            // TODO: parse argument and make an instance of PluginConfig

                            // Let's just trivially do this, before the TODO is fixed:

                            if (pValue.equals("countCommits")) plugins.put("countCommits", new PluginConfig() {
                            });

                            if (pValue.equals("countCommitsPerWeekday")) {
                                plugins.put("countCommitsPerWeekday", new PluginConfig(){});
                                System.out.println("#0 - countCommitsPerWeekday Test");
                            } // Only for testing - Paris;

                            // Plugin pour compter les lignes ajoutées ou supprimées dans les
                            // fichiers d'un projet git
                            if(pValue.equals("countLinesChanged"))
                            	plugins.put("countLinesChanged", new PluginConfig(){});
                            
                            break;
                        case "--loadConfigFile":
                            // TODO (load options from a file)
                            break;
                        case "--justSaveConfigFile":
                            // TODO (save command line options to a file instead of running the analysis)
                            break;
                        case "--test":
                        	if(pValue.toUpperCase().equals("WINDOW")) {
                        		new Window(600, 600);
                        	}
                        	else if(pValue.toUpperCase().equals("GRAPH")) {
                        		var conf = new Configuration(gitPath, new HashMap<String, PluginConfig>());
                            	var chart = new ChartCountCommitsPerAuthor(conf);
                                (new PrintChart(chart, "bar")).afficheChart();
                        	}
                        	break;
                        default:
                            return Optional.empty();
                    }
                }
            } else {
                gitPath = FileSystems.getDefault().getPath(arg);
            }
        }
        return Optional.of(new Configuration(gitPath, plugins));
    }

    private static void displayHelpAndExit() {
        System.out.println("Wrong command...");
        //TODO: print the list of options and their syntax
        System.exit(0);
    }
}
