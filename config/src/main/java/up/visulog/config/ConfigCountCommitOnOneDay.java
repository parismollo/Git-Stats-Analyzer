package up.visulog.config;

public class ConfigCountCommitOnOneDay extends PluginConfig{
	public ConfigCountCommitOnOneDay() {
		  super();
		}
	public boolean isValid() {
		  return !pluginConfig.get("date").equals("") 
		    && pluginConfig.get("startDate").equals("")
		    && pluginConfig.get("endDate").equals("");
		}
}
