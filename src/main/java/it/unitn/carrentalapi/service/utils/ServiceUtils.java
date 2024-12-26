package it.unitn.carrentalapi.service.utils;

import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.StringUtils;

@UtilityClass
public class ServiceUtils {

    public String addSqlWildcards(String arg) {
        return StringUtils.defaultIfBlank(arg, "") + "%";
    }
}
