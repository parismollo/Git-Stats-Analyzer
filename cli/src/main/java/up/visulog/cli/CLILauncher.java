package up.visulog.cli;

import java.awt.FontFormatException;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.util.HashMap;
import java.util.Optional;

import up.visulog.analyzer.Analyzer;
import up.visulog.config.*;
import up.visulog.graphs.*;
import up.visulog.gui.*;

public class CLILauncher {

    public static void main(String[] args) throws FontFormatException, IOException {
        // Pour tester l'interface graphique: ./gradlew run --args=". --mode=demo"
    	// Pour tester l'affichage d'un graphique : ./gradlew run --args=". --test=graph"

        /* Pour ajouter un nouveau plugin :
            0. modifier la fonction makePlugin() du fichier Analyzer
            1. créer un fichier "Config" pour le plugin
            2. modifier makeConfigFromCommandLineArgs() (case "--addPlugin") (+ isPluginValid() si besoin)
            3. ajouter le graph correspondant dans createChart() (s'il existe) (+ le main si besoin)
            4. modifier la commande --help et displayHelpAndExit() */
    	
        var config = makeConfigFromCommandLineArgs(args);
  
        if (config.isPresent()) {
            var analyzer = new Analyzer(config.get());
            var results = analyzer.computeResults();
            var plugins = config.get().getPluginConfigs();
            for(var plugin : plugins.entrySet()) {
                if(plugin.getValue().isGraph()) {
                    if(plugin.getValue() instanceof ConfigCountLinesChanged) { // pour afficher les 2 graphs
                        var chartAdd = createChart("countLinesAdded", plugin.getValue(), config.get()).get();
                        var chartDel = createChart("countLinesDeleted", plugin.getValue(), config.get()).get();
                        (new PrintChart(chartAdd, plugin.getValue().getGraph())).afficheChart();
                        (new PrintChart(chartDel, plugin.getValue().getGraph())).afficheChart();

                    } else if (plugin.getValue() instanceof ConfigCountCommitLinesChanged) { // idem
                        var chartAdd = createChart("countCommitLinesAdded", plugin.getValue(), config.get()).get();
                        var chartDel = createChart("countCommitLinesDeleted", plugin.getValue(), config.get()).get();
                        (new PrintChart(chartAdd, plugin.getValue().getGraph())).afficheChart();
                        (new PrintChart(chartDel, plugin.getValue().getGraph())).afficheChart();
                    } else if (plugin.getValue() instanceof ConfigCountCommitLinesChangedOnOneDay) { // idem
                        var chartAdd = createChart("countCommitLinesAddedOnOneDay", plugin.getValue(), config.get()).get();
                        var chartDel = createChart("countCommitLinesDeletedOnOneDay", plugin.getValue(), config.get()).get();
                        (new PrintChart(chartAdd, plugin.getValue().getGraph())).afficheChart();
                        (new PrintChart(chartDel, plugin.getValue().getGraph())).afficheChart();
                    } else if (plugin.getValue() instanceof ConfigCountCommitLinesChangedBetweenDays) { // idem
                        var chartAdd = createChart("countCommitLinesAddedBetweenDays", plugin.getValue(), config.get()).get();
                        var chartDel = createChart("countCommitLinesDeletedBetweenDays", plugin.getValue(), config.get()).get();
                        (new PrintChart(chartAdd, plugin.getValue().getGraph())).afficheChart();
                        (new PrintChart(chartDel, plugin.getValue().getGraph())).afficheChart();
                    } else {
                        var chart = createChart(plugin.getKey(), plugin.getValue(), config.get());
                        if(chart.isPresent()) {
                            (new PrintChart(chart.get(), plugin.getValue().getGraph())).afficheChart();
                        } else {
                            noChart();
                        }
                    }
                } else {
                    System.out.println(results.toHTML());
                }
            }
        } else displayHelpAndExit(true, false);
    }

    static Optional<Configuration> makeConfigFromCommandLineArgs(String[] args) throws FontFormatException, IOException {
        var gitPath = FileSystems.getDefault().getPath(".");
        var plugins = new HashMap<String, PluginConfig>();
        for (var arg : args) {
            if (arg.startsWith("--")) {
                String[] parts = arg.split("=");
                if (parts.length != 2)  {
                    if(parts.length == 1 && parts[0].equals("--help")) displayHelpAndExit(false, false);
                    else return Optional.empty();
                }
                else {
                    String pName = parts[0];
                    String pValue = parts[1];
                    switch (pName) {
                        case "--addPlugin": 
                            switch(pValue) {
                                case "countCommits" : plugins.put(pValue, new ConfigCountCommitsPerAuthor()); break;
                                case "countCommitsPerWeekday" : plugins.put(pValue, new ConfigCountCommitsPerWeekday()); break;
                                case "countCommitLinesChanged" : plugins.put(pValue, new ConfigCountCommitLinesChanged()); break;
                                case "countCommitLinesChangedOnOneDay" : plugins.put(pValue, new ConfigCountCommitLinesChangedOnOneDay()); break;
                                case "countCommitLinesChangedBetweenDays" : plugins.put(pValue, new ConfigCountCommitLinesChangedBetweenDays()); break;
                                case "countCommitOnOneDay" : plugins.put(pValue, new ConfigCountCommitOnOneDay()); break;
                                case "countCommitsBetweenDays" : plugins.put(pValue, new ConfigCountCommitsBetweenDays()); break;
                                case "countMergeCommits" : plugins.put(pValue, new ConfigCountMergeCommits()); break;
                                case "countMergePerAuthor" : plugins.put(pValue, new ConfigCountMergePerAuthor()); break;
                                case "countMergesBetweenDays" : plugins.put(pValue, new ConfigCountMergesBetweenDays()); break;
                                case "countLinesChanged" : plugins.put(pValue, new ConfigCountLinesChanged()); break;
                                case "help" : displayHelpAndExit(false, true);
                                default : displayHelpAndExit(true, true); break;
                            }
                            break;
                        case "--graph": 
                            if(pValue.equals("pie") || pValue.equals("bar") || pValue.equals("line"))
                                addToPluginConfig(plugins, "graph", pValue);
                            else displayHelpAndExit(true, false); break;
                        case "--date": 
                            if(!rightDateFormat(pValue)) displayHelpAndExit(true, false);
                            else addToPluginConfig(plugins, "date", pValue); break;
                        case "--startDate": 
                            if(!rightDateFormat(pValue)) displayHelpAndExit(true, false);
                            addToPluginConfig(plugins, "startDate", pValue); break;
                        case "--endDate": 
                            if(!rightDateFormat(pValue)) displayHelpAndExit(true, false);
                            addToPluginConfig(plugins, "endDate", pValue); break;
                        case "--loadConfigFile":
                            // TODO (load options from a file)
                            break;
                        case "--justSaveConfigFile":
                            // TODO (save command line options to a file instead of running the analysis)
                            break;
                        case "--test":
                        	if(pValue.toUpperCase().equals("GRAPH")) {
                        		var conf = new Configuration(gitPath, new HashMap<String, PluginConfig>());
                        		var chart = new ChartCountCommitsPerAuthor(conf, "2021-09-1", "2021-10-1");
                        		/*List<String> list = new ArrayList<>();
                        		list.add("jadecrtl");
                        		list.add("Victoria");
                        		chart.refreshAuthors(list);*/
                                (new PrintChart(chart, "bar")).afficheChart();
                                /*(new PrintChart(new ChartCountLinesAdded(conf), "bar")).afficheChart();
                                (new PrintChart(new ChartCountLinesDeleted(conf), "bar")).afficheChart();
                                (new PrintChart(new ChartCountCommitsPerWeekday(conf), "bar")).afficheChart();*/
                        	}
                        	break;
                        case "--mode":
                            if (pValue.toUpperCase().equals("DEMO")) {
                                Gui.runGui();
                            }
                            break;
                        case "--help":
                            System.out.println("Here's the right template for using that command :\n");
                            switch(pValue) {
                                case "addPlugin" : displayHelpAndExit(false, true); break;
                                case "mode" : System.out.println("--mode=DEMO"); break;
                                case "graph" : System.out.println("--graph=[pie/bar/line]"); break;
                                case "countCommits" : System.out.println("--addPlugin=countCommits"); break;
                                case "countCommitsPerWeekday" : System.out.println("--addPlugin=countCommitsPerWeekday"); break;
                                case "countCommitLinesChanged" : System.out.println("--addPlugin=countCommitLinesChanged"); break;
                                case "countCommitLinesChangedOnOneDay" : System.out.println("--addPlugin=countCommitLinesChangedOnOneDay --date=YYYY-MM-DD"); break;
                                case "countCommitLinesChangedBetweenDays" : System.out.println("--addPlugin=countCommitLinesChangedBetweenDays --startDate=YYYY-MM-DD --endDate=YYYY-MM-DD"); break;
                                case "countCommitOnOneDay" : System.out.println("--addPlugin=countCommitOnOneDay --date=YYYY-MM-DD"); break;
                                case "countCommitsBetweenDays" : System.out.println("--addPlugin=countCommitsBetweenDays --startDate=YYYY-MM-DD --endDate=YYYY-MM-DD"); break;
                                case "countMergeCommits" : System.out.println("--addPlugin=countMergeCommits"); break;
                                case "countMergePerAuthor" : System.out.println("--addPlugin=countMergePerAuthor"); break;
                                case "countMergesBetweenDays" : System.out.println("--addPlugin=countMergesBetweenDays --startDate=YYYY-MM-DD --endDate=YYYY-MM-DD"); break;
                                case "countLinesChanged" : System.out.println("--addPlugin=countLinesChanged"); break;
                                default : displayHelpAndExit(true, false); break;
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
        if(!isPluginValid(plugins)) return Optional.empty();
        return Optional.of(new Configuration(gitPath, plugins));
    }

    private static void addToPluginConfig(HashMap<String,PluginConfig> plugins, String type, String value) {
        for(var plugin : plugins.entrySet()) {
            switch(type) {
                case "graph" : plugin.getValue().setGraph(value); break;
                case "date" : plugin.getValue().setDate(value); break;
                case "startDate" : plugin.getValue().setStartDate(value); break;
                case "endDate" : plugin.getValue().setEndDate(value); break;
            }
        }
    }

    // pour une date simple
    private static boolean rightDateFormat(String date) {
        var nbrs = date.split("-");
        if(nbrs.length != 3) return false;
        for(var item : nbrs) {
            for(int i=0; i<item.length(); i++)
                if(!Character.isDigit(item.charAt(0)))
                    return false;
        }
        if(nbrs[0].length() != 4) return false;
        if(nbrs[1].length() != 2) return false;
        if(nbrs[2].length() != 2) return false;
        return true;
    }

    // pour une intervale
    private static boolean rightDateFormat(String start, String end) {
        var spltStart = start.split("-");
        var spltEnd = end.split("-");
        // on compare les années
        if(Integer.parseInt(spltStart[0]) > Integer.parseInt(spltEnd[0])) return false;
        if(Integer.parseInt(spltStart[0]) < Integer.parseInt(spltEnd[0])) return true;
        // puis les mois
        if(Integer.parseInt(spltStart[1]) > Integer.parseInt(spltEnd[1])) return false;
        if(Integer.parseInt(spltStart[1]) < Integer.parseInt(spltEnd[1])) return true;
        // puis les jours
        if(Integer.parseInt(spltStart[2]) > Integer.parseInt(spltEnd[2])) return false;
        if(Integer.parseInt(spltStart[2]) < Integer.parseInt(spltEnd[2])) return true;
        return false; // si les deux dates sont égales, (à changer si il le faut)
    }

    // pour vérifier si la commande possède des arguments en trop / en moins
    private static boolean isPluginValid(HashMap<String,PluginConfig> plugins) {
        for(var plugin : plugins.entrySet()) {
            if(!plugin.getValue().isValid()) return false;
            if((plugin.getValue() instanceof ConfigCountCommitsBetweenDays)
                || (plugin.getValue() instanceof ConfigCountMergesBetweenDays)
                || (plugin.getValue() instanceof ConfigCountCommitLinesChangedBetweenDays))
                if(!rightDateFormat(plugin.getValue().getStartDate(), plugin.getValue().getEndDate()))
                    return false;
        }
        return true;
    }

    private static Optional<ChartAnalysis> createChart(String pName, PluginConfig pConfig, Configuration config) {
        switch(pName) { // décommenter quand les classes seront créées
            case "countCommits" :
                return Optional.of(new ChartCountCommitsPerAuthor(config));
            case "countCommitsPerWeekday":
                return Optional.of(new ChartCountCommitsPerWeekday(config)); 
            case "countCommitOnOneDay" : 
                return Optional.of(new ChartCountCommitsPerAuthor(config, pConfig.getDate()));
            case "countCommitsBetweenDays" : 
                return Optional.of(new ChartCountCommitsPerAuthor(config, pConfig.getStartDate(), pConfig.getEndDate()));
            case "countMergeCommits" : 
                return Optional.of(new ChartCountMergeCommitsPerAuthor(config));
            case "countMergePerAuthor" : 
                return Optional.of(new ChartCountMergeCommitsPerAuthor(config));
            case "countMergesBetweenDays" :
                return Optional.of(new ChartCountMergeCommitsPerAuthor(config, pConfig.getStartDate(), pConfig.getEndDate()));
            case "countLinesAdded" :
                return Optional.of(new ChartCountLinesAdded(config));
            case "countLinesDeleted" :
                return Optional.of(new ChartCountLinesDeleted(config));
            case "countCommitLinesAdded" :
                return Optional.of(new ChartCountCommitsLinesAdded(config));
            case "countCommitLinesDeleted" :
                return Optional.of(new ChartCountCommitsLinesDeleted(config));
            case "countCommitLinesAddedOnOneDay" :
                return Optional.of(new ChartCountCommitsLinesAdded(config, pConfig.getDate()));
            case "countCommitLinesDeletedOnOneDay" :
                return Optional.of(new ChartCountCommitsLinesDeleted(config, pConfig.getDate()));
            case "countCommitLinesAddedBetweenDays" :
                return Optional.of(new ChartCountCommitsLinesAdded(config, pConfig.getStartDate(), pConfig.getEndDate()));
            case "countCommitLinesDeletedBetweenDays" :
                return Optional.of(new ChartCountCommitsLinesDeleted(config, pConfig.getStartDate(), pConfig.getEndDate()));
            default :
                return Optional.empty();
        }
    }

    private static void displayHelpAndExit(boolean error, boolean addPlugin) {
        if(error) System.out.println("Wrong command...\n");
        System.out.println("List of avalaible options :");
        System.out.println("    --addPlugin=[plugin name]");
        System.out.println("        °countCommits");
        System.out.println("        °countCommitsPerWeekday");
        System.out.println("        °countCommitLinesChanged");
        System.out.println("        °countCommitLinesChangedOnOneDay --date=YYYY-MM-DD");
        System.out.println("        °countCommitLinesChangedBetweenDays --startDate=YYYY-MM-DD --endDate=YYYY-MM-DD");
        System.out.println("        °countCommitOnOneDay --date=YYYY-MM-DD");
        System.out.println("        °countCommitsBetweenDays --startDate=YYYY-MM-DD --endDate=YYYY-MM-DD");
        System.out.println("        °countMergeCommits");
        System.out.println("        °countMergePerAuthor");
        System.out.println("        °countMergesBetweenDays --startDate=YYYY-MM-DD --endDate=YYYY-MM-DD");
        System.out.println("        °countLinesChanged");
        if(!addPlugin) {
            System.out.println("    --graph=[pie/bar/line] (works only if a plugin is added)");
            // System.out.println("    --loadConfigFile");
            // System.out.println("    --justSaveConfigFile");
            System.out.println("    --mode=DEMO (to launch the GUI)");
            System.out.println("    --help=[plugin/option name]"); }
        System.exit(0);
    }

    private static void noChart() {
        System.out.println("No chart is avalaible for the moment...");
    }
}
