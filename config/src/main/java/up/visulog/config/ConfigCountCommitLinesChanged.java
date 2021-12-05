package up.visulog.config;

public class ConfigCountCommitLinesChanged extends PluginConfig{
	public ConfigCountCommitLinesChanged() {
		  super();
		}
	public boolean isValid() {
		  return pluginConfig.get("date").equals("") 
		    && pluginConfig.get("startDate").equals("")
		    && pluginConfig.get("endDate").equals("");
		}
}
