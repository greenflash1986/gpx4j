package org.casaca.gpx4j.core.driver.jaxb;

import com.sun.xml.bind.marshaller.NamespacePrefixMapper;

public class JaxbNamespacePrefixMapper extends NamespacePrefixMapper {

        @Override
        public String getPreferredPrefix(String arg0, String arg1, boolean arg2) {
                return "csg";
        }
}