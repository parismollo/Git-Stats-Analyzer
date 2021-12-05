package up.visulog.config;

public class ConfigCountMergeCommits extends PluginConfig {
	public ConfigCountMergeCommits() {
		  super();
		}
	public boolean isValid() {
		  return pluginConfig.get("date").equals("") 
		    && pluginConfig.get("startDate").equals("")
		    && pluginConfig.get("endDate").equals("");
		}
}
