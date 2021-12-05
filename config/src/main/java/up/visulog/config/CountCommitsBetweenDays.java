
public class CountCommitsBetweenDays extends PluginConfig{
	public CountCommitsBetweenDays() {
		  super();
		}
	public boolean isValid() {
		  return pluginConfig.get("date").equals("") 
		    && !pluginConfig.get("startDate").equals("")
		    && !pluginConfig.get("endDate").equals("");
		}
}
