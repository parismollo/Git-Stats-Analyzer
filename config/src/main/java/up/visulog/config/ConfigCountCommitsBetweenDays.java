package up.visulog.config;

public class ConfigCountCommitsBetweenDays extends PluginConfig{
	public ConfigCountCommitsBetweenDays() {
		  super();
		}
	public boolean isValid() {
		  return pluginConfig.get("date").equals("") 
		    && !pluginConfig.get("startDate").equals("")
		    && !pluginConfig.get("endDate").equals("");
		}
}
