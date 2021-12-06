package up.visulog.config;

public class ConfigCountCommitLinesChangedOnOneDay extends PluginConfig{
	public ConfigCountCommitLinesChangedOnOneDay() {
		  super();
		}
	public boolean isValid() {
		  return !pluginConfig.get("date").equals("") 
		    && pluginConfig.get("startDate").equals("")
		    && pluginConfig.get("endDate").equals("");
		}
}
