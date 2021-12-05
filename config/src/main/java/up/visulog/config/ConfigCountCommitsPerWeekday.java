package up.visulog.config;

public class ConfigCountCommitsPerWeekday extends PluginConfig {
	public ConfigCountCommitsPerWeekday() {
		  super();
		}
	public boolean isValid() {
		  return pluginConfig.get("date").equals("") 
		    && pluginConfig.get("startDate").equals("")
		    && pluginConfig.get("endDate").equals("");
		}
}
