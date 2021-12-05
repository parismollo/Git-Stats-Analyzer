package up.visulog.config;

public class ConfigCountMergePerAuthor extends PluginConfig {
	public ConfigCountMergePerAuthor() {
		  super();
		}
	public boolean isValid() {
		  return pluginConfig.get("date").equals("") 
		    && pluginConfig.get("startDate").equals("")
		    && pluginConfig.get("endDate").equals("");
		}
}
