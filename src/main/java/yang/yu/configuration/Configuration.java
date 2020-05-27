package yang.yu.configuration;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import yang.yu.configuration.internal.PropertiesFileUtils;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;
import java.util.Properties;

public class Configuration {

	private static final Logger LOGGER = LoggerFactory.getLogger(Configuration.class);
	private static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";
	private Hashtable<String, String> hashtable;
	private String dateFormat = DEFAULT_DATE_FORMAT;
	private boolean defaultWhenParseFailed = false;	//当类型转换失败时是否返回缺省值


	public static Builder builder() {
		return new Builder();
	}



	private Configuration(Hashtable<String, String> hashtable) {
		this.hashtable = hashtable;
	}

	public void setDateFormat(String dateFormat) {
		this.dateFormat = dateFormat;
	}

	public void setDefaultWhenParseFailed(boolean defaultWhenParseFailed) {
		this.defaultWhenParseFailed = defaultWhenParseFailed;
	}

	public String getString(String key, String defaultValue) {
		Assert.notBlank(key, "Key is null or empty!");
        String value = hashtable.get(key);
        return StringUtils.isBlank(value) ? defaultValue : value;
	}

	public String getString(String key) {
		Assert.notBlank(key, "Key is null or empty!");
		if (!hashtable.containsKey(key)) {
			throw new ConfigurationKeyNotFoundException("Configuration key '" + key + "' not found!");
		}
		return hashtable.get(key);
	}

	public int getInt(String key, int defaultValue) {
		if (!hashtable.containsKey(key)) {
			return defaultValue;
		}
		String value = hashtable.get(key);
		try {
			return Integer.parseInt(value);
		} catch (NumberFormatException e) {
			if (defaultWhenParseFailed) {
				return defaultValue;
			}
			throw new ConfigurationValueParseException("'" + value + "' cannot be parsed to int");
		}
	}

	public int getInt(String key) {
		if (!hashtable.containsKey(key)) {
			throw new ConfigurationKeyNotFoundException("Configuration key '" + key + "' not found!");
		}
		String value = hashtable.get(key);
		try {
			return Integer.parseInt(value);
		} catch (NumberFormatException e) {
			throw new ConfigurationValueParseException("'" + value + "' cannot be parsed to int");
		}
	}

    public long getLong(String key, long defaultValue) {
        if (!hashtable.containsKey(key)) {
            return defaultValue;
        }
        String value = hashtable.get(key);
        try {
            return Long.parseLong(value);
        } catch (NumberFormatException e) {
            if (defaultWhenParseFailed) {
                return defaultValue;
            }
            throw new ConfigurationValueParseException("'" + value + "' cannot be parsed to long");
        }
    }

    public long getLong(String key) {
        if (!hashtable.containsKey(key)) {
            throw new ConfigurationKeyNotFoundException("Configuration key '" + key + "' not found!");
        }
        String value = hashtable.get(key);
        try {
            return Long.parseLong(value);
        } catch (NumberFormatException e) {
            throw new ConfigurationValueParseException("'" + value + "' cannot be parsed to long");
        }
    }

    public double getDouble(String key, double defaultValue) {
        if (!hashtable.containsKey(key)) {
            return defaultValue;
        }
        String value = hashtable.get(key);
        try {
            return Double.parseDouble(value);
        } catch (NumberFormatException e) {
            if (defaultWhenParseFailed) {
                return defaultValue;
            }
            throw new ConfigurationValueParseException("'" + value + "' cannot be parsed to double");
        }
    }

    public double getDouble(String key) {
        if (!hashtable.containsKey(key)) {
            throw new ConfigurationKeyNotFoundException("Configuration key '" + key + "' not found!");
        }
        String value = hashtable.get(key);
        try {
            return Double.parseDouble(value);
        } catch (NumberFormatException e) {
            throw new ConfigurationValueParseException("'" + value + "' cannot be parsed to double");
        }
    }

    public boolean getBoolean(String key, boolean defaultValue) {
        if (!hashtable.containsKey(key)) {
            return defaultValue;
        }
        String value = hashtable.get(key);
        if ("true".equalsIgnoreCase(value)) {
            return true;
        }
        if ("false".equalsIgnoreCase(value)) {
            return false;
        }
        if (defaultWhenParseFailed) {
            return Boolean.parseBoolean(value);
        }
        throw new ConfigurationValueParseException("'" + value + "' cannot be parsed to boolean");
    }

    public boolean getBoolean(String key) {
        if (!hashtable.containsKey(key)) {
            throw new ConfigurationKeyNotFoundException("Configuration key '" + key + "' not found!");
        }
        String value = hashtable.get(key);
        if ("true".equalsIgnoreCase(value)) {
            return true;
        }
        if ("false".equalsIgnoreCase(value)) {
            return false;
        }
        throw new ConfigurationValueParseException("'" + value + "' cannot be parsed to boolean");
    }

    public Date getDate(String key, Date defaultValue) {
        if (!hashtable.containsKey(key)) {
            return defaultValue;
        }
        String value = hashtable.get(key);
        try {
            return new SimpleDateFormat(dateFormat).parse(value);
        } catch (ParseException e) {
            if (defaultWhenParseFailed) {
                return defaultValue;
            }
            throw new ConfigurationValueParseException("'" + value + "' cannot be parsed to date");
        }
    }

    public Date getDate(String key) {
        if (!hashtable.containsKey(key)) {
            throw new ConfigurationKeyNotFoundException("Configuration key '" + key + "' not found!");
        }
        String value = hashtable.get(key);
        try {
            return new SimpleDateFormat(dateFormat).parse(value);
        } catch (ParseException e) {
            throw new ConfigurationValueParseException("'" + value + "' cannot be parsed to date");
        }
    }


	public static class Builder {
		private boolean defaultWhenParseFailed = false;	//当类型转换失败时是否返回缺省值
		private Hashtable<String, String> hashtable = new Hashtable();
		private String dateFormat = "yyyy-MM-dd";
		private PropertiesFileUtils pfu = new PropertiesFileUtils("utf-8");

		public Builder fromClasspath(String confFile) {
			String path = getClass().getResource(confFile).getFile();
			return fromFile(path);
		}

		public Builder fromFile(String confFile) {
			return fromFile(new File(confFile));
		}

		public Builder fromFile(File confFile) {
			if (!confFile.exists()) {
				throw new ConfigurationFileNotFoundException();
			}
			if (!confFile.canRead()) {
				throw new ConfigurationFileReadException("Read configuration file is not permitted!");
			}
			InputStream in = null;
			try {
				in = new FileInputStream(confFile);
				Properties props = new Properties();
				props.load(in);
				hashtable = pfu.rectifyProperties(props);
				LOGGER.debug("Load configuration from {} at {}", confFile.getAbsolutePath(), new Date());
			} catch (IOException e) {
				throw new ConfigurationFileReadException("Cannot load config file: " + confFile, e);
			} finally {
				if (in != null) {
					try {
						in.close();
					} catch (IOException e) {
						throw new ConfigurationException("Cannot close input stream.", e);
					}
				}
			}
			return this;
		}

		public Builder dateFormat(String dateFormat) {
			this.dateFormat = dateFormat;
			return this;
		}

		public Builder defaultWhenParseFailed(boolean defaultWhenParseFailed) {
			this.defaultWhenParseFailed = defaultWhenParseFailed;
			return this;
		}

		public Configuration build() {
			if (hashtable.isEmpty()) {
				throw new ConfigurationException("Configuration source not specified!");
			}
			Configuration result = new Configuration(hashtable);
			result.setDateFormat(dateFormat);
			result.setDefaultWhenParseFailed(defaultWhenParseFailed);
			return result;
		}
	}
}
