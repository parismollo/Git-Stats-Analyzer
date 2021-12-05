package up.visulog.config;

public class ConfigCountLinesChanged extends PluginConfig{
	public ConfigCountLinesChanged() {
		  super();
		}
	public boolean isValid() {
		  return pluginConfig.get("date").equals("") 
		    && pluginConfig.get("startDate").equals("")
		    && pluginConfig.get("endDate").equals("");
		}
}
