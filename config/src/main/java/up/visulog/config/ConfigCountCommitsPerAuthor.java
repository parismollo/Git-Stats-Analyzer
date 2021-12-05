package up.visulog.config;

public class ConfigCountCommitsPerAuthor extends PluginConfig {
	public ConfigCountCommitsPerAuthor() {
		  super();
		}
	public boolean isValid() {
		  return pluginConfig.get("date").equals("") 
		    && pluginConfig.get("startDate").equals("")
		    && pluginConfig.get("endDate").equals("");
		}
}
