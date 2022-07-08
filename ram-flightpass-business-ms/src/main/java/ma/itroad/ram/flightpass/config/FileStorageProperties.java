
package ma.itroad.ram.flightpass.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
@ConfigurationProperties(prefix = "file")
public class FileStorageProperties {
    private String uploadDir ;

    public String getUploadDir() {
        return System.getProperty("user.dir") + uploadDir;
    }

    public void setUploadDir(String uploadDir) {
        this.uploadDir = uploadDir;
    }
}

