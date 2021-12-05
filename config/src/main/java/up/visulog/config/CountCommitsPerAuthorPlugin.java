package up.visulog.config;

public class CountCommitsPerAuthorPlugin extends PluginConfig {
	public CountCommitsPerAuthorPlugin() {
		  super();
		}
	public boolean isValid() {
		  return pluginConfig.get("date").equals("") 
		    && pluginConfig.get("startDate").equals("")
		    && pluginConfig.get("endDate").equals("");
		}
}
