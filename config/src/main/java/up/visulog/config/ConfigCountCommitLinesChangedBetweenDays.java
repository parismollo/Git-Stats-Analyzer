package up.visulog.config;

public class ConfigCountCommitLinesChangedBetweenDays extends PluginConfig{
	public ConfigCountCommitLinesChangedBetweenDays() {
		  super();
		}
	public boolean isValid() {
		  return pluginConfig.get("date").equals("") 
		    && !pluginConfig.get("startDate").equals("")
		    && !pluginConfig.get("endDate").equals("");
		}
}

