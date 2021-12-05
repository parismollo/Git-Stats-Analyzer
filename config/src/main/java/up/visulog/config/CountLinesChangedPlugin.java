package up.visulog.config;

public class CountLinesChangedPlugin extends PluginConfig{
	public CountLinesChangedPlugin() {
		  super();
		}
	public boolean isValid() {
		  return pluginConfig.get("date").equals("") 
		    && pluginConfig.get("startDate").equals("")
		    && pluginConfig.get("endDate").equals("");
		}
}
