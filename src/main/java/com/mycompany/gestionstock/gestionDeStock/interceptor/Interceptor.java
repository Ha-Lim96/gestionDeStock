package com.mycompany.gestionstock.gestionDeStock.interceptor;

import org.hibernate.EmptyInterceptor;
import org.slf4j.MDC;
import org.springframework.util.StringUtils;

public class Interceptor extends EmptyInterceptor {

    // tout ça est pour ajouter l'id entreprise dans toutes les requettes
    @Override
    public String onPrepareStatement(String sql) {
        if (StringUtils.hasLength(sql) && sql.toLowerCase().startsWith("select")) {
            // select utilisateu0_.
            final String entityName = sql.substring(7, sql.indexOf(".")); // suppression du "select " et extraire utilisateu0_.
            final String idEntreprise = MDC.get("idEntreprise");

            if (StringUtils.hasLength(entityName)
                    && !entityName.toLowerCase().contains("entreprise")
                    && !entityName.toLowerCase().contains("entreprise")
                    && StringUtils.hasLength(idEntreprise)) {

                if (sql.contains("where")) {
                    sql = sql + " and " + entityName + ".idEntreprise = " + idEntreprise;
                } else {
                    sql = sql + " where " + entityName + ".idEntreprise = " + idEntreprise;
                }
            }
        }
        return super.onPrepareStatement(sql);
    }
}
