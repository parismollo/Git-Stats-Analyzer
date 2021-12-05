
public class CountMergePerAuthorPlugin extends PluginConfig {
	public CountMergePerAuthorPlugin() {
		  super();
		}
	public boolean isValid() {
		  return pluginConfig.get("date").equals("") 
		    && pluginConfig.get("startDate").equals("")
		    && pluginConfig.get("endDate").equals("");
		}
}
