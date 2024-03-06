# Use the official WebSphere Liberty base image
FROM websphere-liberty:kernel

# Set the working directory
WORKDIR /opt/ibm/wlp/usr/servers/defaultServer

# Copy your application artifacts (e.g., WAR file) into the server directory
COPY target/gspi_web*.war /opt/ibm/wlp/usr/servers/defaultServer/apps

COPY server.xml /opt/ibm/wlp/usr/servers/defaultServer

RUN installUtility install --acceptLicense servlet-4.0 && installUtility install --acceptLicense springboot-2.0 && installUtility install --acceptLicense jsp-2.3 && installUtility install --acceptLicense jaxrs-2.1 && installUtility install --acceptLicense jaxb-2.2

# Expose the necessary ports (adjust as needed)
EXPOSE 5000

# Start the Liberty server when the container runs
CMD ["/opt/ibm/wlp/bin/server", "run", "defaultServer"]