package youngsditch.ancientlogistics.utility;

import turniplabs.halplibe.HalpLibe;

import java.io.*;
import java.util.Properties;

public class Config {
  private static final String CONFIG_DIRECTORY = "./config/";
  private String configFileName = "";
  private Properties defaultProperties;
  private Properties properties;

  public String getFilePath() {
    return CONFIG_DIRECTORY + configFileName;
  }

  public Config(String modID, Properties defaultProperties) {
    this.configFileName = modID + ".cfg";
    this.defaultProperties = defaultProperties;
    this.properties = new Properties();
    this.properties.putAll(defaultProperties);
    HalpLibe.LOGGER.info("Config file name: " + this.properties.toString());

    File configFile = new File(getFilePath());
    HalpLibe.LOGGER.info("Config file path: " + configFile.getAbsolutePath());
    try {
      if (!configFile.exists()) {
        HalpLibe.LOGGER.info("Config file does not exist. Creating...");
        configFile.getParentFile().mkdirs();
        configFile.createNewFile();
        writeDefaultConfig(configFile, this.defaultProperties);
      } else {
        // load only pulls in the ones we have in the file
        loadConfig(configFile, this.properties);
        // to make sure any new properties are added, we update the config
        updateConfig(configFile, this.properties);
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public String getProperty(String key) {
    return this.properties.getProperty(key);
  }

  public String getString(String key) {
    return this.properties.getProperty(key);
  }

  public Integer getInt(String key) {
    return Integer.parseInt(this.properties.getProperty(key));
  }

  public Boolean getBoolean(String key) {
    return Boolean.parseBoolean(this.properties.getProperty(key));
  }

  public void writeDefaultConfig() {
    File configFile = new File(getFilePath());
    writeDefaultConfig(configFile, this.defaultProperties);
  }

  public void loadConfig() {
    File configFile = new File(getFilePath());
    loadConfig(configFile, this.properties);
  }

  public void updateConfig() {
    File configFile = new File(getFilePath());
    updateConfig(configFile, this.properties);
  }

  // private

  private static void writeDefaultConfig(File configFile, Properties properties) {
    HalpLibe.LOGGER.info("Writing default config to " + configFile.getAbsolutePath());
    try (OutputStream output = new FileOutputStream(configFile)) {
      properties.store(output, "Default config values");
      output.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private static void updateConfig(File configFile, Properties properties) {
    HalpLibe.LOGGER.info("Updating config at " + configFile.getAbsolutePath());
    try (OutputStream output = new FileOutputStream(configFile)) {
      properties.store(output, "Updated config values");
      output.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private static void loadConfig(File configFile, Properties properties) {
    try (InputStream input = new FileInputStream(configFile)) {
      // only loads the ones that it finds in the file
      properties.load(input);
      input.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}