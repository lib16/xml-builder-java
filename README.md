# lib16 XML Builder for Java 8

## Dependency Information

```java
<dependency>
    <groupId>com.lib16</groupId>
    <artifactId>xml-builder</artifactId>
    <version>1.1.2</version>
</dependency>
```
See also http://search.maven.org/#artifactdetails|com.lib16|xml-builder|1.1.2|jar

## Basic Example

```java
package com.lib16.java.example.kml;

import com.lib16.java.xml.XmlProperties;

public class KmlProperties extends XmlProperties
{
    @Override
    public String getFilenameExtension()
    {
        return "kml";
    }

    @Override
    public String getMimeType()
    {
        return "application/vnd.google-earth.kml+xml";
    }
    
    @Override
    public String getXmlNamespace()
    {
        return "http://www.opengis.net/kml/2.2";
    }
}
```

```java
package com.lib16.java.example.kml;

import com.lib16.java.xml.Language;
import com.lib16.java.xml.Xml;

public final class Kml implements Language
{
    private Xml xml;

    private Kml(Xml xml)
    {
        this.xml = xml;
    }

    public static Kml createKml(KmlProperties properties)
    {
        if (properties == null) {
            properties = new KmlProperties();
        }
        return new Kml(Xml.createRoot("kml", properties));
    }

    public static Kml createKml()
    {
        return createKml(null);
    }

    public Kml placemark(String name, String description,
            Number longitude, Number latitude, Number altitude)
    {
        Kml pm = new Kml(xml.append("Placemark"));
        pm.xml.append("name", name);
        pm.xml.append("description", description);
        pm.xml.append("Point").append("coordinates", longitude + "," + latitude + "," + altitude);
        return pm;
    }

    public Kml placemark(String name, String description, Number longitude, Number latitude)
    {
        return placemark(name, description, longitude, latitude, 0);
    }

    @Override
    public Xml getXml() {
        return xml;
    }
}
```

```java
package com.lib16.java.example.kml;

public class KmlDemo
{
    public static void main(String[] args) {
        Kml myKml = Kml.createKml();
        myKml.placemark("Cologne Cathedral",
                "Cologne Cathedral is a Roman Catholic cathedral in Cologne, Germany.",
                50.9413, 6.958);
        System.out.print(myKml.getXml().toString());
    }
}
```

The generated markup:

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<kml xmlns="http://www.opengis.net/kml/2.2">
    <Placemark>
        <name>Cologne Cathedral</name>
        <description>Cologne Cathedral is a Roman Catholic cathedral in Cologne, Germany.</description>
        <Point>
            <coordinates>50.9413,6.958,0</coordinates>
        </Point>
    </Placemark>
</kml>
```