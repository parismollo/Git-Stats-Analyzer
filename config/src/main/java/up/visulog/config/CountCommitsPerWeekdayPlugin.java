
public class CountCommitsPerWeekdayPlugin extends PluginConfig {
	public CountCommitsPerWeekdayPlugin() {
		  super();
		}
	public boolean isValid() {
		  return pluginConfig.get("date").equals("") 
		    && pluginConfig.get("startDate").equals("")
		    && pluginConfig.get("endDate").equals("");
		}
}
