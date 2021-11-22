package up.visulog.cli;

import java.awt.FontFormatException;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import up.visulog.analyzer.Analyzer;
import up.visulog.config.Configuration;
import up.visulog.config.PluginConfig;
import up.visulog.graphs.ChartCountCommitsPerAuthor;
import up.visulog.graphs.ChartCountCommitsPerWeekday;
import up.visulog.graphs.ChartCountLinesAdded;
import up.visulog.graphs.ChartCountLinesDeleted;
import up.visulog.graphs.PrintChart;
import up.visulog.gui.Gui;

public class CLILauncher {

    public static void main(String[] args) throws FontFormatException, IOException {
        // Pour tester l'interface graphique: ./gradlew run --args=". --mode=demo"
    	// Pour tester l'affichage d'un graphique : ./gradlew run --args=". --test=graph"
    	
        var config = makeConfigFromCommandLineArgs(args);
        
        if (config.isPresent()) {
            var analyzer = new Analyzer(config.get());
            var results = analyzer.computeResults();
            System.out.println(results.toHTML());
        } else displayHelpAndExit();
    }

    static Optional<Configuration> makeConfigFromCommandLineArgs(String[] args) throws FontFormatException, IOException {
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

                            // Plugin pour compter les lignes ajout�es ou supprim�es dans les
                            // fichiers d'un projet git
                            if(pValue.equals("countLinesChanged"))
                            	plugins.put("countLinesChanged", new PluginConfig(){});
                            if(pValue.equals("countCommitOnOneDay")) 
                                plugins.put("countCommitOnOneDay", new PluginConfig(){});
                            break;
                        case "--loadConfigFile":
                            // TODO (load options from a file)
                            break;
                        case "--justSaveConfigFile":
                            // TODO (save command line options to a file instead of running the analysis)
                            break;
                        case "--test":
                        	if(pValue.toUpperCase().equals("GRAPH")) {
                        		var conf = new Configuration(gitPath, new HashMap<String, PluginConfig>());
                        		var chart = new ChartCountCommitsPerAuthor(conf);
                        		List<String> list = new ArrayList<>();
                        		list.add("jadecrtl");
                        		list.add("Victoria");
                        		chart.refreshAuthors(list);
                                (new PrintChart(chart, "bar")).afficheChart();
                                (new PrintChart(new ChartCountLinesAdded(conf), "bar")).afficheChart();
                                (new PrintChart(new ChartCountLinesDeleted(conf), "bar")).afficheChart();
                                (new PrintChart(new ChartCountCommitsPerWeekday(conf), "bar")).afficheChart();
                        	}
                        	break;
                        case "--mode":
                            if (pValue.toUpperCase().equals("DEMO")) {
                                Gui.runDemo();
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
