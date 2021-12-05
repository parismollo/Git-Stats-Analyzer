package up.visulog.config;

public class CountCommitOnOneDay extends PluginConfig{
	public CountCommitOnOneDay() {
		  super();
		}
	public boolean isValid() {
		  return !pluginConfig.get("date").equals("") 
		    && pluginConfig.get("startDate").equals("")
		    && pluginConfig.get("endDate").equals("");
		}
}
