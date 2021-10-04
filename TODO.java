
Analyzer.java :

    public AnalyzerResult computeResults()
	// run all the plugins
        // TODO: try running them in parallel
        for (var plugin: plugins) plugin.run();

        // store the results together in an AnalyzerResult instance and return it
        return new AnalyzerResult(plugins.stream().map(AnalyzerPlugin::getResult).collect(Collectors.toList()));

    private Optional<AnalyzerPlugin> makePlugin(String pluginName, PluginConfig pluginConfig)
        TODO: find a way so that the list of plugins is not hardcoded in this factory

TestCountCommitsPerAuthorPlugin.java :

    /* Let's check whether the number of authors is preserved and that the sum of the commits of each author is equal to the total number of commits */

CLILauncher.java :

    case "--addPlugin":
        TODO: parse argument and make an instance of PluginConfig

         // Let's just trivially do this, before the TODO is fixed:
         if (pValue.equals("countCommits")) plugins.put("countCommits", new PluginConfig() {});
     break;
    case "--loadConfigFile":
        TODO (load options from a file)
        break;
    case "--justSaveConfigFile":
        TODO (save command line options to a file instead of running the analysis)
        break;

    private static void displayHelpAndExit()
        //TODO: print the list of options and their syntax

TestCLILauncher.java :
    
    TODO: one can also add integration tests here:
    - run the whole program with some valid options and look whether the output has a valid format
    - run the whole program with bad command and see whether something that looks like help is printed

PluginConfig.java :

    TODO: define what this type should be (probably a Map: settingKey -> settingValue)

TestConfiguration.java :
    Do not forget writing tests as soon as class Configuration contains more than just getters
    (or if they or the constructor start doing something smart!).


Commit.java :
    FIXME: (some of) these fields could have more specialized types than String

    public static List<Commit> parseLogFromCommand(Path gitPath) :
        TODO: factor this out (similar code will have to be used for all git commands)

    /**
     * Parses a log item and outputs a commit object. Exceptions will be thrown in case the input does not have the proper format.
     * Returns an empty optional if there is nothing to parse anymore.
     */
    public static Optional<Commit> parseCommit(BufferedReader input) {
        try {

            var line = input.readLine();
            if (line == null) return Optional.empty(); // if no line can be read, we are done reading the buffer
            var idChunks = line.split(" ");
            if (!idChunks[0].equals("commit")) parseError();
            var builder = new CommitBuilder(idChunks[1]);

            line = input.readLine();
            while (!line.isEmpty()) {
                var colonPos = line.indexOf(":");
                var fieldName = line.substring(0, colonPos);
                var fieldContent = line.substring(colonPos + 1).trim();
                switch (fieldName) {
                    case "Author":
                        builder.setAuthor(fieldContent);
                        break;
                    case "Merge":
                        builder.setMergedFrom(fieldContent);
                        break;
                    case "Date":
                        builder.setDate(fieldContent);
                        break;
                    default: // TODO: warn the user that some field was ignored
                }
                line = input.readLine(); //prepare next iteration
                if (line == null) parseError(); // end of stream is not supposed to happen now (commit data incomplete)
            }

            // now read the commit message per se
            var description = input
                    .lines() // get a stream of lines to work with
                    .takeWhile(currentLine -> !currentLine.isEmpty()) // take all lines until the first empty one (commits are separated by empty lines). Remark: commit messages are indented with spaces, so any blank line in the message contains at least a couple of spaces.
                    .map(String::trim) // remove indentation
                    .reduce("", (accumulator, currentLine) -> accumulator + currentLine); // concatenate everything
            builder.setDescription(description);
            return Optional.of(builder.createCommit());
        } catch (IOException e) {
            parseError();
        }
        return Optional.empty(); // this is supposed to be unreachable, as parseError should never return
    }

    // Helper function for generating parsing exceptions. This function *always* quits on an exception. It *never* returns.
    private static void parseError() {
        throw new RuntimeException("Wrong commit format.");
    }

    @Override
    public String toString() {
        return "Commit{" +
                "id='" + id + '\'' +
                (mergedFrom != null ? ("mergedFrom...='" + mergedFrom + '\'') : "") + //TODO: find out if this is the only optional field
                ", date='" + date + '\'' +
                ", author='" + author + '\'' +
                ", description='" + description + '\'' +
                '}';
    }




