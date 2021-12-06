package up.visulog.config;

public class ConfigCountMergesBetweenDays extends PluginConfig{
	public ConfigCountMergesBetweenDays() {
		  super();
		}
	public boolean isValid() {
		  return pluginConfig.get("date").equals("") 
		    && !pluginConfig.get("startDate").equals("")
		    && !pluginConfig.get("endDate").equals("");
		}
}

